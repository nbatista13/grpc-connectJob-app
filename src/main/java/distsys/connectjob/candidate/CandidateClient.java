/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.candidate;

import grpc.generated.candidate.Candidate;
import grpc.generated.candidate.CandidateRequest;
import grpc.generated.candidate.CandidateResponse;
import grpc.generated.candidate.CandidateServiceGrpc;
import grpc.generated.candidate.Skill;
import grpc.generated.candidate.SkillSummary;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * CandidateClient.java
 * @author Nailton Batista
 * Version 1.0
 * 13/07/2026
 */
public class CandidateClient {

    //Coomunication channel between client and gRPC server
    private ManagedChannel channel;

    //Blocking stub that will be used for Unary rpc
    //Since the client is going to wait for server response before doing something else 
    private CandidateServiceGrpc.CandidateServiceBlockingStub blockingStub;

    //Client streaming rpc UploadSkills will be using an asynchronous stub
    //since on client streaming a many request will be performed while one response will be received
    private CandidateServiceGrpc.CandidateServiceStub asyncStub;

    //This constructor will be responsible to find a JobService using jmDNS
    //every time that a JobClient object is created.
    public CandidateClient() throws IOException {

        // searching for CandidateService in the local network.
        ServiceInfo serviceInfo = discoverCandidateService();

        //Since jmDNS can return more than one address, we will get the first one.
        String[] candidateServiceAddresses = serviceInfo.getHostAddresses();

        //throw an exception if no address has been returned
        if (candidateServiceAddresses.length == 0) {

            throw new IllegalStateException("Candidate Service has been found, but no address has been provided.");
        }
        
        String host = candidateServiceAddresses[0];

        //Gets the door announced by CandidateService
        int port = serviceInfo.getPort();

        System.out.println("CandidateService found at: " + host + ":" + port);

        //Creating the communication channel between client and service
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        //-----------------------------
        // Creating the blocking stub
        //------------------------------
        blockingStub =CandidateServiceGrpc.newBlockingStub(channel);

        //creating asynchronous stub
        asyncStub = CandidateServiceGrpc.newStub(channel);
    }

    private ServiceInfo discoverCandidateService()
            throws IOException {

        //Searching the CandidateService on the local network, if found, 'serviceInfo' will be returned
        //at the end of the method, if any problem show up a throw exception will be trigged.
        try (JmDNS jmdns =JmDNS.create(InetAddress.getLocalHost())) {

            System.out.println("[jmDNS] - Searching "+ CandidateServiceConfig.SERVICE_NAME);

            //Searching the service for about 5 seconds
            ServiceInfo serviceInfo = jmdns.getServiceInfo(CandidateServiceConfig.SERVICE_TYPE,CandidateServiceConfig.SERVICE_NAME,5000);
            
            //if the service isn't found, 'null' is going to be returned with a message
            if (serviceInfo == null) {

                throw new IllegalStateException("CandidateService was not found. Please, verify if CandidateServer is ON and registered.");
            }

            return serviceInfo;
        }
    }
    
    //-----------------------
    // Unary RPC - RegisterCandidate
    //-----------------------
    
    //This method will receive the data from GUI, validate the data, create a candidate object, and send the obj to the server.
    public CandidateResponse registerCandidate(String candidateId,String name,String email) {
        
        //calling the validation method
        validateCandidateData(candidateId, name, email);

        //Creating an Job obj
        Candidate candidate = Candidate.newBuilder()
                .setCandidateId(candidateId.trim())
                .setName(name.trim())
                .setEmail(email.trim())
                .build();

        //Creating the request obj that is going to be sent to the server
        CandidateRequest request =CandidateRequest.newBuilder()
                        .setCandidate(candidate)
                        .build();

        try {

            //Remotely calling addJob method from server
            return blockingStub.registerCandidate(request);

        } catch (StatusRuntimeException e) {

            throw new RuntimeException("Error when trying to register candidate.",e);
        }
    }

    //-------------------------------------------
    // Client Streaming RPC - UploadSkills
    //-------------------------------------------

    public SkillSummary uploadSkills(String candidateId,List<String> skills) {
        
        if (candidateId == null || candidateId.isBlank()) {

            throw new IllegalArgumentException("Candidate ID can not be empty");
        }

        if (skills == null || skills.isEmpty()) {

            throw new IllegalArgumentException("At least one skill need to be added.");
        }

        
        //since our Client streaming is asynchronous we need the method below
        //to make the client waits.
        CountDownLatch finishLatch = new CountDownLatch(1);
        
        //This array will store the response from the server
        //Array has been used since the response will be changed inside of StreamObserver
        SkillSummary[] responseHolder = new SkillSummary[1];


        //This Observer is responsible for receveing the final response from CandidateServer.
        StreamObserver<SkillSummary> responseObserver=new StreamObserver<SkillSummary>() {


            //when server sends the SKillSummary this 'onNext' is executed
            @Override
            public void onNext(SkillSummary summary) {

                responseHolder[0] = summary;
            }

            //If a error happens a message will be print
            @Override
            public void onError(Throwable error) {

                System.out.println("Error when trying to upload skills: "+ error.getMessage());
                
                //release method that is awaiting
                finishLatch.countDown();
            }


            //executed when server finishs sending the response
            @Override
            public void onCompleted() {

                finishLatch.countDown();
            }
        };

        //calling uploadSkills() on the asyncStub
        StreamObserver<Skill> requestObserver =asyncStub.uploadSkills(responseObserver);

        try {
            //A loop that runs all the skills that was sent by the Interface
            for (String skillName : skills) {

                //Creating skill request
                Skill skill = Skill.newBuilder()
                        .setCandidateId(candidateId.trim())
                        .setSkillName(skillName.trim())
                        .build();

                //sending skill request to the server
                //onNext will be called for each skill
                requestObserver.onNext(skill);
            }

            //TElls the server that all the skills was sent
            requestObserver.onCompleted();

            //Waiting 10 seconds for a response
            finishLatch.await(10,TimeUnit.SECONDS);
            
            //making sure that GUI will not try to use a null obj if a response
            //was not received.
            if (responseHolder[0] == null) {

                throw new RuntimeException("No response has been received from CandidateServer.");
            }

            return responseHolder[0];

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException("Skill upload has been interrupted.",e);
        }
    }
    
    //method responsible of doing some data validations
        private void validateCandidateData(
                String candidateId,
                String name,
                String email) {

            if (candidateId == null || name.isBlank()) {
                throw new IllegalArgumentException("Candidate ID can not be empty.");
            }

            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Candidate name can not be empty.");
            }

            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Candidate email can not be empty");
            }        
        }

    //When the main window from GUI is closed this method will be called to end the gRPC
    public void shutdown() {

        if (channel == null || channel.isShutdown()) {
            return;
        }

        try {
            channel.shutdown()
                    .awaitTermination(5,TimeUnit.SECONDS);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {
            //forcing the channel closing if necessary    
            if (!channel.isTerminated()) {
                channel.shutdownNow();
            }
        }
    }
}