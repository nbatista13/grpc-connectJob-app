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
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * JobClient.java
 * @author Nailton Batista
 * Version 1.0
 * 09/07/2026
 */
public class JobClient {


    //Coomunication channel between client and gRPC server
    private ManagedChannel channel;

    //Blocking stub that will be used for Unary rpc
    //Since the client is going to wait for server response before doing something else   
    private JobServiceGrpc.JobServiceBlockingStub blockingStub;


    //This constructor will be responsible to find a JobService using jmDNS
    //every time that a JobClient object is created.
    public JobClient() throws IOException {

        ServiceInfo serviceInfo = discoverJobService();


        //Since jmDNS can return more than one address, we will get the first one.
        String[] JobServiceAddresses = serviceInfo.getHostAddresses();
        
        //throw an exception if no address has been returned
        if (JobServiceAddresses.length == 0) {
            throw new IllegalStateException("JobService has been found, but no address has been provided.");
        }

        String host = JobServiceAddresses[0];
        int port = serviceInfo.getPort();

        System.out.println("JobService found at: " + host + ":" + port);

        //Creating the communication channel between client and service
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        //-----------------------------
        // Creating the blocking stub
        //------------------------------
        blockingStub = JobServiceGrpc.newBlockingStub(channel);
    }
  
    //Searching the JobService on the local network, if found, 'serviceInfo' will be returned
    //at the end of the method, if any problem show up a throw exception will be trigged.
    private ServiceInfo discoverJobService() throws IOException {

        try (JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost())) {

            System.out.println("[jmDNS] - Searching " + JobServiceConfig.SERVICE_NAME);

            //Searching the service for about 5 seconds
            ServiceInfo serviceInfo = jmdns.getServiceInfo(JobServiceConfig.SERVICE_TYPE,JobServiceConfig.SERVICE_NAME,5000);

            if (serviceInfo == null) {
                throw new IllegalStateException("JobService was not found. Please, verify if JobServer is ON and registered.");
            }

            return serviceInfo;
        }
    }
    
    //-----------------------
    // Unary RPC - addJob
    //-----------------------

    //This method will receive the data from GUI, validate the data, create a Job object, and send the obj to the server.
    public AddJobResponse addJob(int jobId,String jobTitle,String companyName,String jobLocation,String jobDescription,double salary) {
        
        //calling the validation method
        validateJobData(jobId,jobTitle,companyName,jobLocation,jobDescription,salary);

        //Creating an Job obj
        Job job = Job.newBuilder()
                .setJobId(jobId)
                .setJobTitle(jobTitle.trim())
                .setCompanyName(companyName.trim())
                .setJobLocation(jobLocation.trim())
                .setJobDescription(jobDescription.trim())
                .setSalary(salary)
                .build();

        //Creating the request obj that is going to be sent to the server
        AddJobRequest request = AddJobRequest.newBuilder()
                .setJob(job)
                .build();

        try {

            //Remotely calling addJob method from server
            return blockingStub.addJob(request);

        } catch (StatusRuntimeException e) {

            throw new RuntimeException("Error when trying to register job opportunity.",e);
        }
    }
    //-------------------------------------------
    // Server Streaming RPC - searchJobPosition
    //-------------------------------------------

    //This method will receive the data from GUI (Job title and location) and will return 0 or more results.
    public List<Job> searchJobPosition(String jobTitle,String jobLocation) {

        if (jobTitle == null || jobTitle.isBlank()) {
            throw new IllegalArgumentException("Plase inform the job title.");
        }

        if (jobLocation == null || jobLocation.isBlank()) {
            throw new IllegalArgumentException("Please, inform the job location.");
        }
        
        //Creating the request obj that is going to be sent to the server
        SearchJobPositionRequest request =
                SearchJobPositionRequest.newBuilder()
                        .setJobTitle(jobTitle.trim())
                        .setJobLocation(jobLocation.trim())
                        .build();

        List<Job> jobsFound = new ArrayList<>();

        try {

            //Due to the fact that our RPC is a Streaming we can have several answers(Job).
            //When looking to the blocking stub class in JobServiceGrpc, 
            //we can see that the method signature is an Iterator.
            Iterator<Job> jobResponses = blockingStub.searchJobPosition(request);
            
            //The responses will be printed as long as we have answers
            while (jobResponses.hasNext()) {
                jobsFound.add(jobResponses.next());
            }

            return jobsFound;

        } catch (StatusRuntimeException e) {

            throw new RuntimeException("ERROR - It was not possible to research for job opportunities.",e);
        }
    }
    
    //method responsible of doing some data validations
    private void validateJobData(
            int jobId,
            String jobTitle,
            String companyName,
            String jobLocation,
            String jobDescription,
            double salary) {

        if (jobId <= 0) {
            throw new IllegalArgumentException("Job ID must be bigger than 0");
        }

        if (jobTitle == null || jobTitle.isBlank()) {
            throw new IllegalArgumentException("Job title can not be empty.");
        }

        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("Campany name can not be empty");
        }

        if (jobLocation == null || jobLocation.isBlank()) {
            throw new IllegalArgumentException("Location can not be empty");
        }

        if (jobDescription == null || jobDescription.isBlank()) {
            throw new IllegalArgumentException("Description can not be empty.");
        }

        if (salary < 0) {
            throw new IllegalArgumentException("Salary can not be a negative number.");
        }
    }

  
    //When the main window from GUI is closed this method will be called to end the gRPC
    public void shutdown() {

        if (channel == null || channel.isShutdown()) {
            return;
        }

        try {

            channel.shutdown()
                    .awaitTermination(5, TimeUnit.SECONDS);

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
