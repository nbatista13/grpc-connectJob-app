/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.connectjob.candidate;

/**
 * CandidateServiceConfig.java
 * @author Nailton Batista
 * Version 1.0
 * 13/07/2026
 */
public final class CandidateServiceConfig {

    /*
    This file is going to be responsible to store information shared between JobServer and JobClient.
    */
    
    //Responsible for announce and discover the CandidaiteService
    public static final String SERVICE_TYPE = "_candidateservice._tcp.local.";

    //Service instance name announced on network.
    public static final String SERVICE_NAME = "CandidateService";

    // Port used by gRPC server.
    public static final int SERVICE_PORT = 50052;

    private CandidateServiceConfig() {
    }
}
