/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudmanager;

import Model.Approval;
import Model.BDD.ApprovalBDD;
import Model.BDD.CompteBancaireBDD;
import Model.EApproval;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author kenny
 */
@Path("AppManager")
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
    
    @GET
    @Path("/Approvals/{idCompte}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllApprovalByIdCompte(@PathParam("idCompte") int idCompte) throws Exception {
        initBase();
        return Response.status(Response.Status.OK).entity(ApprovalBDD.getAllApprovalByIdCompte(idCompte)).build();
    }
    
    @GET
    @Path("/Approval/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApprovalById(@PathParam("id") int id) throws Exception {
        initBase();
        return Response.status(Response.Status.OK).entity(ApprovalBDD.getApprovalByid(id)).build();
    }
    
    @DELETE
    @Path("/Approval/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delApprovalById(@PathParam("id") int id) throws Exception {
        initBase();
        return Response.status(Response.Status.OK).entity(ApprovalBDD.delApproval(id)).build();
    }
    
    @POST
    @Path("/Approval/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addApproval(Approval newApproval) throws Exception {
        initBase();
        return Response.status(Response.Status.CREATED).entity(ApprovalBDD.addApproval(newApproval)).build();
    }
    
    @GET
    @Path("/Approval/Accepte/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response accepteApproval(@PathParam("id") int id) throws Exception {
        initBase();
        return Response.status(Response.Status.OK).entity(ApprovalBDD.ChangeEtatApproval(id,EApproval.Accepted)).build();
    }
    
    @GET
    @Path("/Approval/Refuse/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refuseApproval(@PathParam("id") int id) throws Exception {
        initBase();
        return Response.status(Response.Status.OK).entity(ApprovalBDD.ChangeEtatApproval(id,EApproval.Refused)).build();
    }
}
