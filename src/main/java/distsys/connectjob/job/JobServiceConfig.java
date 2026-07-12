/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.job;

/**
 * JobServiceConfig.java
 * @author Nailton Batista
 * Version 1.0
 * 10/07/2026
 */
public class JobServiceConfig {
    
    /*
    This file is going to be responsible to store information shared between JobServer and JobClient.
    */
    
    //Responsible for announce and discover the JobService
    public static final String SERVICE_TYPE = "_jobservice._tcp.local.";

    //Service instance name announced on network.
    public static final String SERVICE_NAME = "JobService";

    // Port used by gRPC server.
    public static final int SERVICE_PORT = 50051;

    private JobServiceConfig() {
    }
    
}
