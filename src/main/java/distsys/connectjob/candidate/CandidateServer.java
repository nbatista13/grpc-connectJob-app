/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.candidate;

import grpc.generated.candidate.Candidate;
import grpc.generated.candidate.CandidateRequest;
import grpc.generated.candidate.CandidateResponse;
import grpc.generated.candidate.CandidateServiceGrpc.CandidateServiceImplBase;
import grpc.generated.candidate.Skill;
import grpc.generated.candidate.SkillSummary;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * CandidateServer.java
 * @author Nailton Batista
 * Version 1.0
 * 13/07/2026
 */
public class CandidateServer extends CandidateServiceImplBase {

    //List to store all the candidates
    private final List<Candidate> candidates = new ArrayList<>();
    
    //List to store all the skills linked to Candidate through candidateId
    private final List<Skill> skills = new ArrayList<>();

    public static void main(String[] args) {

        //Creating a instance of the CandidateServer
        //where we are going to have the methods; 'registerCandidate()' and 'uploadSkills()'
        CandidateServer candidateService = new CandidateServer();

        //Keeping those references outside of 'try' to end them on 'finally'
        Server grpcServer = null;
        JmDNS jmdns = null;
        ServiceInfo serviceInfo = null;

        try {

            //Here the gRPC is being created
            grpcServer = ServerBuilder
                    .forPort(CandidateServiceConfig.SERVICE_PORT)   //Setting the port the server will use
                    .addService(candidateService)                   //CandidateService implementation
                    .build()                                        // built the system
                    .start();                                       // responsible for starting the server

            System.out.println(LocalTime.now()+ ": CandidateServer started, listening on "+ CandidateServiceConfig.SERVICE_PORT);

            //Here an instance of jmDNS is being created using the address where the server is located.
            InetAddress localAddress = InetAddress.getLocalHost();
            jmdns = JmDNS.create(localAddress);

            //Creating the service description that will be announced.
            serviceInfo = ServiceInfo.create(CandidateServiceConfig.SERVICE_TYPE,CandidateServiceConfig.SERVICE_NAME,CandidateServiceConfig.SERVICE_PORT,"ConnectJob Candidate Service");

            //Here the service is being published so the Client can locate it.
            jmdns.registerService(serviceInfo);

            System.out.println(LocalTime.now()+ ": CandidateService registered with jmDNS as "+ CandidateServiceConfig.SERVICE_NAME + 
                    "\nAddress: " + localAddress.getHostAddress());

            //Keeps the server on
            grpcServer.awaitTermination();

        } catch (IOException e) {

            //If the port is being used or for some reason the server can't start
            //this exception will catch the error.
            System.out.println("Error when trying to start CandidateService.");
            e.printStackTrace();

        } catch (InterruptedException e) {

            //in case the server is awaiting and we have an issue
            System.out.println("CandidateService was interruped.");
            e.printStackTrace();

            Thread.currentThread().interrupt();

        } finally {

            //Before the server is ended the jmDNS announcement is be removed
            if (jmdns != null && serviceInfo != null) {
                jmdns.unregisterService(serviceInfo);
            }
            
            //Closing jmDNS
            if (jmdns != null) {

                try {

                    jmdns.close();

                } catch (IOException e) {
                    System.out.println("Error closing jmDNS.");
                    e.printStackTrace();
                }
            }

            //Closing server
            if (grpcServer != null) {
                grpcServer.shutdown();
            }
        }
    }


    //--------------------------
    //  Unary Rpc: RegisterCandidate
    //--------------------------
    /*
    Communication: 1 --- 1.
    The client is going to send 1 'CandidateRequest', then the server will send 1 CandidateResponse.
    */
    @Override
    public void registerCandidate(CandidateRequest request,StreamObserver<CandidateResponse> responseObserver) {

        //Here we are getting the object that came inside of the 'CandidateRequest'
        Candidate candidate = request.getCandidate();

        //Verifying if a candidate with the same ID already exists
        boolean candidateAlreadyExists = false;

        for (Candidate existingCandidate : candidates) {

            if (existingCandidate
                    .getCandidateId()
                    .equalsIgnoreCase(
                            candidate.getCandidateId()
                    )) {

                candidateAlreadyExists = true;
                break;
            }
        }

        CandidateResponse response;
        
        //Verifying if ID is unique
        if (candidateAlreadyExists) {

            response = CandidateResponse.newBuilder().setSuccess(false).setMessage("A candidate with ID "+ candidate.getCandidateId()+ " already exists.").build();

        } else {
            //Now after recovering the object from request, we are going to insert it in the list
            candidates.add(candidate);

            //Creating the response that will be sent to the client
            response = CandidateResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Candidate " + candidate.getName() + " was successfully registered.")
                    .build();
        }

        //Calling 'onNext()' to send the response obj to the client(only one since is a unary rpc)
        responseObserver.onNext(response);

        //Informing Client that there won't be more other answers
        responseObserver.onCompleted();
    }

    
    //--------------------------------------------
    // Client Streaming Rpc - UploadSkills
    //--------------------------------------------
    /*
    Communication: N --- 1.
    The client is going to send N 'Skill', then the server will send '1' SkillSummary
    */
    @Override
    public StreamObserver<Skill> uploadSkills(StreamObserver<SkillSummary> responseObserver) {

        // A temporary list to stores the skills
        List<String> receivedSkills = new ArrayList<>();

        //An array with just one position to store the candidateId that was received from the stream
        String[] candidateIdHolder =  new String[1];

        // This server will return the Observer to the gRpc
        return new StreamObserver<Skill>() {

            //executed for each Skill that has been received
            @Override
            public void onNext(Skill skill) {

                //when receiving the first skill the candidateId will be stored
                if (candidateIdHolder[0] == null) {

                    candidateIdHolder[0] = skill.getCandidateId();
                }

                //addin skill name to the temporary list
                receivedSkills.add(skill.getSkillName());

                System.out.println("Skill received: " + skill.getSkillName()+ " / Candidate ID: "+ skill.getCandidateId());
            }

            //This method will be executed if a error while sending the skills happens
            @Override
            public void onError(Throwable error) {

                System.out.println(
                        LocalTime.now()
                        + ": error receiving skills - "
                        + error.getMessage()
                );
            }

            //method that is going to be called when the Client calls 'requestObserver.onCompleted()'
            @Override
            public void onCompleted() {

                String candidateId = candidateIdHolder[0];

                //If skill was not sent CandidateId will be null
                if (candidateId == null) {

                    SkillSummary response = SkillSummary.newBuilder()
                                    .setCandidateId("")
                                    .setTotalSkills(0)
                                    .setMessage("No skills were received.")
                                    .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }

                //verifying if candidete exists    
                boolean candidateExists = false;

                for (Candidate candidate : candidates) {

                    if (candidate.getCandidateId().equalsIgnoreCase(candidateId)) {

                        candidateExists = true;
                        break;
                    }
                }

                SkillSummary response;

                if (!candidateExists) {

                    response = SkillSummary.newBuilder()
                            .setCandidateId(candidateId)
                            .setTotalSkills(0)
                            .setMessage("Candidate ID " + candidateId + " was not found.")
                            .build();

                } else {

                    //adding skill to a general list
                    for (String skillName : receivedSkills) {

                        Skill skill = Skill.newBuilder()
                                .setCandidateId(candidateId)
                                .setSkillName(skillName)
                                .build();

                        skills.add(skill);
                    }

                    response = SkillSummary.newBuilder()
                            .setCandidateId(candidateId)
                            .setTotalSkills(receivedSkills.size())
                            .setMessage("Skills uploaded successfully.")
                            .build();

                    System.out.println(receivedSkills.size() + " skills was uploaded for candidate " + candidateId);
                }

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}
