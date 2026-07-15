/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.interview;

/**
 * InterviewServiceConfig.java
 * @author Nailton Batista
 * Version 1.0
 * 14/07/2026
 */
public class InterviewServiceConfig {
    
    /*
    This file is going to be responsible to store information shared between InterviewServer and InterviewClient.
    */
    
    //Responsible for announce and discover the CandidaiteService
    public static final String SERVICE_TYPE = "_interviewservice._tcp.local.";

    //Service instance name announced on network.
    public static final String SERVICE_NAME =  "InterviewService";

    // Port used by gRPC server.
    public static final int SERVICE_PORT = 50053;

    private InterviewServiceConfig() {
    }
    
}
