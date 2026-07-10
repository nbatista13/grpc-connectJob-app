/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.job;

import grpc.generated.job.AddJobRequest;
import grpc.generated.job.AddJobResponse;
import grpc.generated.job.Job;
import grpc.generated.job.JobServiceGrpc.JobServiceImplBase;
import grpc.generated.job.SearchJobPositionRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JobServer.java
 * @author Nailton Batista
 * Version 1.0
 * 10/07/2026
 */
public class JobServer extends JobServiceImplBase {
    
    //List to store all the jobs
    private final List<Job> jobs = new ArrayList<>();
    
    public static void main(String args[]){
    
        //Creating a instance of the JobServer
        //where we are going to have the methods; 'addJob()' and 'searchJobPosition()'
        JobServer jobServer = new JobServer();


        int port = 50051;

        try {

            //Here the gRPC is being created
            Server server = ServerBuilder
                    .forPort(port)          //Setting the port the server will use
                    .addService(jobServer)  //JobService implementation
                    .build()                // built the system
                    .start();               // responsible for starting the server

            System.out.println(LocalTime.now() + ": Job server started, listening on " + port);
            
            //Keeps the server on
            server.awaitTermination();

            }catch (IOException e){

            //If the port is being used or for some reason the server can't start
            //this exception will catch the error.
                System.out.println("Error when trying to start JobServer.");
                e.printStackTrace();    
            } catch (InterruptedException e){

                //in case the server is awaiting and we have an issue
                System.out.println("JobServer was interruped.");
                e.printStackTrace();

                Thread.currentThread().interrupt();
            }
        }
    
    //--------------------------
    //  Unary Rpc: Add Job
    //--------------------------
    /*
    Communication: 1 --- 1.
    The client is going to send 1 'AddJobRequest', then the server will send 1 responses.
    */
    
    @Override
    public void addJob(AddJobRequest request,StreamObserver<AddJobResponse> responseObserver) {
       

        //Here we are getting the object that came inside of the 'AddJobRequest'
        Job job = request.getJob();
        
        Job newJob = request.getJob();

        boolean jobAlreadyExists = false;

        for (Job existingJob : jobs) {
            if (existingJob.getJobId() == newJob.getJobId()) {
                jobAlreadyExists = true;
                break;
            }
        }

        AddJobResponse response;
        
        //Verifying if ID is unique
        if (jobAlreadyExists) {

            response = AddJobResponse.newBuilder().setSuccess(false).setMessage("A job position with ID " + newJob.getJobId() + " already exists.").build();

        }
        else{
        //Now after recovering the object from request, we are going to insert it in the list
        jobs.add(job);


        //Creating the response that will be sent to the client.
        response = AddJobResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Job position " + job.getJobTitle() + " was successfully registered.")
                .build();

        }
        //Calling 'onNext()' to send the response obj to the client
        responseObserver.onNext(response);
        
        //Informing Client that there won't be more other answers
        responseObserver.onCompleted();

        System.out.println(LocalTime.now() + ": new job position has been registered - " + job.getJobTitle()+ " / " + job.getCompanyName());
    }
    
    //--------------------------------------------
    // Server Streaming Rpc - Search Job Position
    //--------------------------------------------
    /*
    Communication: 1 --- N.
    The client is going to send 1 'SearchJobPositionRequest', then the server will send 'N' responses
    */
    
    @Override
    public void searchJobPosition(SearchJobPositionRequest request,StreamObserver<Job> responseObserver) {

        //Getting the titlw and locaiont from the obj
        String requestedTitle = request.getJobTitle();
        String requestedLocation = request.getJobLocation();


        //In case a job position is found this variable will let us know
        boolean jobFound = false;
        
        //For loop to compare each job position available with 'title' and 'location'
        for (Job job : jobs) {

            //For the compatison 'equalsIgnoreCase()' is being used to ignore differents between uppercase letters and lowercase letters.
            boolean titleMatches = job.getJobTitle().equalsIgnoreCase(requestedTitle);          //comparing Title.

            boolean locationMatches =job.getJobLocation().equalsIgnoreCase(requestedLocation);  //comparing Location.

            
            //If a match happens the result (job) is going to be sent to the client through 'onNext()'
            if (titleMatches && locationMatches) {

                responseObserver.onNext(job);

                jobFound = true;
            }
        }
        
        //Displaying that the search was successful - TEST PURPOSE
        if (jobFound) {

            System.out.println(LocalTime.now() + ": job search was completed for title '" + requestedTitle + "' and location '" + requestedLocation + "'.");

        } else {

            System.out.println(LocalTime.now() + ": no jobs found for title '" + requestedTitle + "' and location '" + requestedLocation + "'.");
        }

        //Informing Client that there won't be more other answers
        responseObserver.onCompleted();
    }
    
}
