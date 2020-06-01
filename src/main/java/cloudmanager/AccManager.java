/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudmanager;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

import Model.CompteBancaire;
import Model.BDD.CompteBancaireBDD;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Aimeric-Thomas
 */
@Path("Comptes")
public class AccManager {

    /**
     * Creates a new instance of ComptesResource
     */
    public AccManager() {
    }
    
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTest(){
        //test
        
        return "Hello World";
    }
    
    @GET
    @Path("/comptebancaires")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCompteBancaires() throws Exception {
        return Response.status(Response.Status.OK).entity(CompteBancaireBDD.getAllCompteBancaires()).build();
    }
    
    @GET
    @Path("/comptebancaires/{nomprenom}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCompteBancaireByNomPrenom(@PathParam("nomprenom") String nomPrenom) throws Exception {
        String nom = nomPrenom.split("-")[0];
        String prenom = nomPrenom.split("-")[1];
        
        return Response.status(Response.Status.OK).entity(CompteBancaireBDD.getAllCompteBancaireByNomAndPrenom(nom, prenom)).build();
    }
    
    @GET
    @Path("/comptebancaire/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompteBancaireById(@PathParam("id") int id) throws Exception {
        return Response.status(Response.Status.OK).entity(CompteBancaireBDD.getCompteBancaireByid(id)).build();
    }
    
    @PUT
    @Path("/comptebancaire/Credit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CreditCompteBancaireById(@PathParam("id") int id,int money) throws Exception {
        return Response.status(Response.Status.OK).entity(CompteBancaireBDD.CreditDebitAccount(id,money,true)).build();
    }
    
    @DELETE
    @Path("/comptebancaire/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delCompteBancaireById(@PathParam("id") int id) throws Exception {
        return Response.status(Response.Status.OK).entity(CompteBancaireBDD.delCompteBancaire(id)).build();
    }
    
    @POST
    @Path("/comptebancaire/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCompteBancaireByNomPrenom(CompteBancaire newCompte) throws Exception {
        return Response.status(Response.Status.CREATED).entity(CompteBancaireBDD.addCompteBancaires(newCompte)).build();
    }
    
}
