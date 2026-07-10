/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.job;

import grpc.generated.job.AddJobRequest;
import grpc.generated.job.AddJobResponse;
import grpc.generated.job.Job;
import grpc.generated.job.JobServiceGrpc;
import grpc.generated.job.SearchJobPositionRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * JobClient.java
 * @author Nailton Batista
 * Version 1.0
 * 09/07/2026
 */
public class JobClient {
    
    //Blocking stub that will be used for Unary rpc
    //Since the client is going to wait for server response before doing something else
    private static JobServiceGrpc.JobServiceBlockingStub blockingStub;
    
    public static void main(String[] args) throws InterruptedException{
        
        //----------------------------------------------
        //  Local Conection with server (TEST PURPOSE)
        //----------------------------------------------
        
        //Creating the communication channel between client and service
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        
        //-----------------------------
        // Creating the blocking stub
        //------------------------------
        blockingStub = JobServiceGrpc.newBlockingStub(channel);
        
        //----------------------
        // TEST PURPOSE - [***To be removed when GUI is built***]
        //---------------------
        
        try{
            //Unary RPC - to add a new job
            addJob(); //To be created in Server Class
            
            //Streaming RPC - to search for a job opportunity
            searchJobPosition(); //To be created in Server Class
        } finally{
            //Finishing the connection with the server after the requisition and response
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
    
    //-----------------------
    // Unary RPC - [***To be changed once GUI is built***]
    //-----------------------
    
    public static void addJob(){
        //Creating an Job obj
        Job job = Job.newBuilder()
                .setJobId(1)
                .setJobTitle("Software Tester")
                .setCompanyName("Riot Games")
                .setJobLocation("Dublin")
                .setJobDescription("Testing games")
                .setSalary(60000.00)
                .build();
        
        //Creating the request obj that is going to be sent to the server
        AddJobRequest request = AddJobRequest.newBuilder()
                .setJob(job)
                .build();
        
        //Calling the remote method AddJob - (TEST PURPOSE)
        AddJobResponse response = blockingStub.addJob(request);
        
        //Displaying the response sent by the server
        System.out.println("[*** ADD JOB ***]");
        System.out.println("Success: " + response.getSuccess());
        System.out.println("Message: " + response.getMessage());
    }
    
    //-----------------------
    // Server Streaming RPC - [***To be changed once GUI is built***]
    //-----------------------
    
    public static void searchJobPosition(){
        //Creating the request obj that is going to be sent to the server
        SearchJobPositionRequest request = SearchJobPositionRequest.newBuilder()
                .setJobTitle("Software Tester")
                .setJobLocation("Dublin")
                .build();
        
        //Due to the fact that our RPC is a Streaming we can have several answers(Job).
        //When looking to the blocking stub class in JobServiceGrpc, 
        //we can see that the method signature is an Iterator.
        Iterator<Job> jobResponses = blockingStub.searchJobPosition(request);
        
        //Displaying the response sent by the server
        System.out.println("[*** SEARCH RESULTS ***]");
        
        //The responses will be printed as long as we have answers
        while (jobResponses.hasNext()){
            
            Job job = jobResponses.next();
            
            System.out.println("---------------------------");

            System.out.println("Job Id: " + job.getJobId());

            System.out.println("Title: " + job.getJobTitle());

            System.out.println("Company name: " + job.getCompanyName());

            System.out.println("Company location: " + job.getJobLocation());

            System.out.println("Job description: " + job.getJobDescription());

            System.out.println("Annual salary: " + job.getSalary());
        }       
        
    }
}
