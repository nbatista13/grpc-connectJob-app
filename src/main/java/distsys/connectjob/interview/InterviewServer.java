/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.interview;

import grpc.generated.interview.Interview;
import grpc.generated.interview.InterviewMessage;
import grpc.generated.interview.InterviewRequest;
import grpc.generated.interview.InterviewResponse;
import grpc.generated.interview.InterviewServiceGrpc.InterviewServiceImplBase;
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
 * InterviewServer.java
 * @author Nailton Batista
 * Version 1.0
 * 16/07/2026
 */
public class InterviewServer extends InterviewServiceImplBase {

    //List to store all the scheduled interviews
    private final List<Interview> interviews = new ArrayList<>();

    public static void main(String[] args) {

        //Creating a instance of the InterviewServer
        //where we are going to have the methods; 'scheduleInterview()' and 'interviewChat()'
        InterviewServer interviewService = new InterviewServer();

        //Keeping those references outside of 'try' to end them on 'finally'
        Server grpcServer = null;
        JmDNS jmdns = null;
        ServiceInfo serviceInfo = null;

        try {

            //Here the gRPC is being created and started
            grpcServer = ServerBuilder
                    .forPort(InterviewServiceConfig.SERVICE_PORT)   //Setting the port the server will use
                    .addService(interviewService)                   //interviewService implementation
                    .build()                                        // built the system
                    .start();                                       // responsible for starting the server

            System.out.println(LocalTime.now() + ": InterviewServer started, listening on " + InterviewServiceConfig.SERVICE_PORT);

            //Here an instance of jmDNS is being created using the address where the server is located.
            InetAddress localAddress = InetAddress.getLocalHost();
            jmdns = JmDNS.create(localAddress);

            //Creating the service description that will be announced.
            serviceInfo = ServiceInfo.create(InterviewServiceConfig.SERVICE_TYPE,InterviewServiceConfig.SERVICE_NAME,InterviewServiceConfig.SERVICE_PORT,"ConnectJob Interview Service");

            //Here the service is being published so the Client can locate it.
            jmdns.registerService(serviceInfo);

            System.out.println(LocalTime.now() + ": InterviewService registered with jmDNS as " + InterviewServiceConfig.SERVICE_NAME
                    + "\nAddress: "+ localAddress.getHostAddress());

            //Keeps the server on
            grpcServer.awaitTermination();

        } catch (IOException e) {

            //If the port is being used or for some reason the server can't start
            //this exception will catch the error.
            System.out.println("Error when trying to start InterviewServer.");

            e.printStackTrace();

        } catch (InterruptedException e) {

            //in case the server is awaiting and we have an issue
            System.out.println("InterviewServer was interrupted.");

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
    //  Unary Rpc: ScheduleInterview
    //--------------------------
    /*
    Communication: 1 --- 1.
    The client is going to send 1 'InterviewRequest', then the server will send 1 InterviewResponse.
    */
    @Override
    public void scheduleInterview(InterviewRequest request,StreamObserver<InterviewResponse> responseObserver) {

        //Here we are getting the object that came inside of the 'InterviewRequest'
        Interview interview = request.getInterview();

        //Verifying if a interview with the same ID already exists
        boolean interviewAlreadyExists = false;

        for (Interview existingInterview : interviews) {

            if (existingInterview
                    .getInterviewId()
                    .equalsIgnoreCase(
                            interview.getInterviewId()
                    )) {

                interviewAlreadyExists = true;
                break;
            }
        }

        InterviewResponse response;
        
        //Verifying if ID is unique
        if (interviewAlreadyExists) {

            //Creating the response to inform that the ID already exists
            response = InterviewResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("An interview with ID " + interview.getInterviewId() + " already exists.")
                    .build();

            System.out.println(LocalTime.now() + ": interview registration rejected. ID " + interview.getInterviewId()+ " already exists.");

        } else {

            //Now after recovering the object from request, we are going to insert it in the list
            interviews.add(interview);

            //Creating the response that will be sent to the client
            response = InterviewResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Interview " + interview.getInterviewId() + " was successfully scheduled.")
                    .build();

            System.out.println(LocalTime.now() + ": interview scheduled - " + interview.getInterviewId()
                    + " / Candidate " + interview.getCandidateId() + " / Job " + interview.getJobId());
        }

        //Calling 'onNext()' to send the response obj to the client(only one since is a unary rpc)
        responseObserver.onNext(response);

        //Informing Client that there won't be more other answers
        responseObserver.onCompleted();
    }

    //--------------------------------------------
    // Birectional Streaming Rpc - UploadSkills
    //--------------------------------------------
    /*
    Communication: N --- N.
    The client is going to send 'N' 'InterviewMessage', then the server will send 'N' 'InterviewMessage'
    */
    @Override
    public StreamObserver<InterviewMessage> interviewChat(StreamObserver<InterviewMessage> responseObserver) {

        //An Oberserver will be returned, and on that Observer all messages sent by the client
        //will be stored.
        return new StreamObserver<InterviewMessage>() {

            //executed for each Skill that has been received
            @Override
            public void onNext(InterviewMessage message) {

                //Displaying the message that was received on the console
                System.out.println(LocalTime.now() + ": chat message received - Interview " + message.getInterviewId() + " / " + message.getSender() + ": " + message.getMessage());
                
                //Here a simple message is being created, in order to have a fully bidirectional streaming.
                //So, client will send a message and server will send a response with a confirmation message.
                InterviewMessage response = InterviewMessage.newBuilder()
                                .setInterviewId(message.getInterviewId())
                                .setSender("Interview Service")
                                .setMessage("Message received from " + message.getSender()+ ": " + message.getMessage())
                                .setTimestamp(LocalTime.now().withNano(0).toString())
                                .build();

                //response is being sent to the client as many time as needed with 'onNext()'
                responseObserver.onNext(response);
            }
    
            //This method will be executed if an error occurs while chat is in progress 
            @Override
            public void onError(Throwable error) {

                System.out.println(LocalTime.now() + ": error during InterviewChat - " + error.getMessage());
            }

            //This method will be execute when the client inform that it finish sending messages
            @Override
            public void onCompleted() {

                System.out.println(LocalTime.now() + ": InterviewChat completed.");

                //The observer below tells the client that the server finished the stream
                responseObserver.onCompleted();
            }
        };
    }
}
