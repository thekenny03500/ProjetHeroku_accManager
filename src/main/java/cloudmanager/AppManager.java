/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudmanager;

import Model.BDD.ApprovalBDD;
import Model.BDD.CompteBancaireBDD;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author kenny
 */
@Path("Approval")
public class AppManager {
    
    /**
     * Creates a new instance of AppManager
     */
    public AppManager(){
    }
    
    public void initBase() throws Exception
    {
        CompteBancaireBDD.createTableIfExiste();
        ApprovalBDD.createTableIfExiste();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest(){
        //test
        
        return "Hello World";
    }

    @GET
    @Path("/Approvals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllApproval() throws Exception {
        initBase();
        return Response.status(Response.Status.OK).entity(ApprovalBDD.getAllApproval()).build();
    }
    
}
