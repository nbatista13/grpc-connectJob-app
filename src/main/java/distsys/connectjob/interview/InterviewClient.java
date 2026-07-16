/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.interview;

import grpc.generated.interview.Interview;
import grpc.generated.interview.InterviewMessage;
import grpc.generated.interview.InterviewRequest;
import grpc.generated.interview.InterviewResponse;
import grpc.generated.interview.InterviewServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.concurrent.TimeUnit;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * InterviewClient.java
 * @author Nailton Batista
 * Version 1.0
 * 14/07/2026
 */
public class InterviewClient {
    
    //Coomunication channel between client and gRPC server
    private ManagedChannel channel;

    //Blocking stub that will be used for Unary rpc
    //Since the client is going to wait for server response before doing something else 
    private InterviewServiceGrpc.InterviewServiceBlockingStub blockingStub;

    //Bidirectional streaming rpc InterviewChat will be using an asynchronous stub
    //since on Bidirectional streaming many request and responses will be performed
    private InterviewServiceGrpc.InterviewServiceStub asyncStub;


    //This observer will be created when the chat start and will be responsible to send
    //'InterviewMessage' to the server
    private StreamObserver<InterviewMessage> chatRequestObserver;


    //This constructor will be responsible to find a InterviewService using jmDNS
    //every time that a JobClient object is created.
    public InterviewClient() throws IOException {

        // searching for InterviewService in the local network.
        ServiceInfo serviceInfo = discoverInterviewService();

        //Since jmDNS can return more than one address, we will get the first one.
        String[] interviewServiceAddresses = serviceInfo.getHostAddresses();

        //throw an exception if no address has been returned
        if (interviewServiceAddresses.length == 0) {

            throw new IllegalStateException("InterviewService was found, " + "but no address was provided.");
        }

        String host = interviewServiceAddresses[0];

        //Gets the door announced by InterviewServer
        int port = serviceInfo.getPort();

        System.out.println("InterviewService found at: " + host + ":" + port);

        //Creating the communication channel between client and service
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        //-----------------------------
        // Unary rpc - Creating the blocking stub
        //------------------------------
        blockingStub = InterviewServiceGrpc.newBlockingStub(channel);

        //----------------------------------------------------------------------
        // Bidirectional Streaming rpc - Creating an asynchronous blocking stub
        //---------------------------------------------------------------------
        asyncStub = InterviewServiceGrpc.newStub(channel);
    }

    private ServiceInfo discoverInterviewService()
            throws IOException {

        //Searching the InterviewService on the local network, if found, 'serviceInfo' will be returned
        //at the end of the method, if any problem show up a throw exception will be trigged.
        try (JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost())) {

            System.out.println("[jmDNS] - Searching " + InterviewServiceConfig.SERVICE_NAME);

            //searching service for 5 seconds
            ServiceInfo serviceInfo = jmdns.getServiceInfo(InterviewServiceConfig.SERVICE_TYPE,InterviewServiceConfig.SERVICE_NAME,5000);

            //if the service isn't found, 'null' is going to be returned with a message
            if (serviceInfo == null) {

                throw new IllegalStateException("InterviewService was not found. Please verify that InterviewServer is running and registered with jmDNS.");
            }

            return serviceInfo;
        }
    }
    
    //-----------------------
    // Unary RPC - ScheduleInterview
    //-----------------------
    
    //This method will receive the data from GUI, validate the data, create a candidate object, and send the obj to the server.
    public InterviewResponse scheduleInterview(String interviewId,String candidateId,int jobId,String interviewDate,String interviewTime) {

        //calling the method to validate data from 'scheduleInterview' (GUI)
        validateInterviewData(interviewId, candidateId, jobId, interviewDate,interviewTime);
        

        //Creating an Interview obj
        Interview interview = Interview.newBuilder()
                .setInterviewId(interviewId.trim())
                .setCandidateId(candidateId.trim())
                .setJobId(jobId)
                .setInterviewDate(interviewDate.trim())
                .setInterviewTime(interviewTime.trim())
                .build();

        //Creating the request obj that is going to be sent to the server
        InterviewRequest request = InterviewRequest.newBuilder()
                        .setInterview(interview)
                        .build();

        try {

            //Remotely calling unary ScheduleInterview method from server
            return blockingStub.scheduleInterview(request);

        } catch (StatusRuntimeException e) {

            throw new RuntimeException("Error when trying to schedule the interview.",e);
        }
    }

    //-------------------------------------------
    // Bidirectional Streaming RPC - InterviewChat
    //-------------------------------------------
    public void startChat(Consumer<InterviewMessage> messageHandler) {

        //If a chat was already started this method is going to prevent that a second one
        // is started.
        if (chatRequestObserver != null) {

            return;
        }

        //This Observer is responsible for receveing the final response from InterviewServer.
        StreamObserver<InterviewMessage> responseObserver = new StreamObserver<InterviewMessage>() {
            
            //Everytime that the server sends a new message, onNext() will be executed
            @Override
            public void onNext(InterviewMessage message) {

                //Sending the message to the interface (GUI)
                if (messageHandler != null) {

                    messageHandler.accept(message);
                }
            }

            //If we get any error during the chatting, this method will be executed
            // where a message will be print, and Observer is going to get a null
            //so a new chat can be started.
            @Override
            public void onError(Throwable error) {

                System.out.println("Error in InterviewChat: " + error.getMessage());

                chatRequestObserver = null;
            }

            //Ending the server when chat is finished
            @Override
            public void onCompleted() {

                System.out.println( "Interview chat was completed.");

                chatRequestObserver = null;
            }
        };
        
        //calling InterviewChat() on the asyncStub
        chatRequestObserver = asyncStub.interviewChat(responseObserver);
    }

    public void sendChatMessage(String interviewId,String sender,String messageText) {

        //CHecking if the chat was already started
        //if not a message will be sent
        if (chatRequestObserver == null) {

            throw new IllegalStateException("The interview chat has not been started.");
        }

        //calling the method to validate data from 'chat' (GUI)
        validateChatData(interviewId, sender,messageText);

        //Creating the message obj (request)that is going to be sent to the server
        InterviewMessage message = InterviewMessage.newBuilder()
                        .setInterviewId(interviewId.trim())
                        .setSender(sender.trim())
                        .setMessage(messageText.trim())
                        .setTimestamp(LocalTime.now().withNano(0).toString())
                        .build();

        try {

            //Since wi have a bidirectional streaming rpc, onNext can be called many times
            chatRequestObserver.onNext(message);

        } catch (RuntimeException e) {

            throw new RuntimeException("Error when trying to send the chat message.",e);
        }
    }


    //Method that is going to be called when user end the chat through the GUI
    public void closeChat() {

        if (chatRequestObserver == null) {
            return;
        }

        //telling the server that the user will not send more messages
        chatRequestObserver.onCompleted();

        chatRequestObserver = null;
    }
    
    //method responsible of doing some data validations on data coming from scheduleInterview method
    private void validateInterviewData(String interviewId,String candidateId, int jobId, String interviewDate, String interviewTime){
        if (interviewId == null || interviewId.isBlank()) {

            throw new IllegalArgumentException("Interview ID must be informed.");
        }

        if (candidateId == null || candidateId.isBlank()) {

            throw new IllegalArgumentException("Candidate ID must be informed.");
        }

        if (jobId <= 0) {

            throw new IllegalArgumentException("Job ID must be greater than zero.");
        }

        if (interviewDate == null || interviewDate.isBlank()) {

            throw new IllegalArgumentException("Interview date must be informed.");
        }

        if (interviewTime == null || interviewTime.isBlank()) {

            throw new IllegalArgumentException("Interview time must be informed.");
        }
    }
    
    //method responsible of doing some data validations on data coming from chat method
    private void validateChatData(String interviewId,String sender,String messageText){
            if (interviewId == null || interviewId.isBlank()) {

            throw new IllegalArgumentException( "Interview ID must not be empty.");
            }

            if (sender == null || sender.isBlank()) {

                throw new IllegalArgumentException("Sender must not be empty.");
            }

            if (messageText == null || messageText.isBlank()) {

                throw new IllegalArgumentException("Message must not be empty.");
            }
        }

    //When the main window from GUI is closed this method will be called to end the gRPC
    public void shutdown() {

        closeChat();

        if (channel == null || channel.isShutdown()) {
            return;
        }

        try {

            channel.shutdown().awaitTermination(5,TimeUnit.SECONDS);

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
