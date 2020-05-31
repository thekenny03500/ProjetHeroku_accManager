/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudaccmanager;

import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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

/**
 * REST Web Service
 *
 * @author Aimeric-Thomas
 */
@Path("Comptes")
public class ComptesResource {

    /**
     * Creates a new instance of ComptesResource
     */
    public ComptesResource() {
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
    public ArrayList<CompteBancaire> getAllCompteBancaires() throws Exception {
            return CompteBancaireBDD.getAllCompteBancaires();
    }
    
    @GET
    @Path("/comptebancaires/{nomPrenom}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompteBancaire getCompteBancaireByNomPrenom(@PathParam("nomPrenom") String nomPrenom) throws Exception {
        	
	String nom = nomPrenom.split("-")[0];
        String prenom = nomPrenom.split("-")[1];
        
        return CompteBancaireBDD.getCompteBancaireByNomAndPrenom(nom, prenom);
    }
    
    
    @DELETE
    @Path("/comptebancaires/{nomPrenom}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delCompteBancaireByNomPrenom(@PathParam("nomPrenom") String nomPrenom) throws Exception {
        	
	String nom = nomPrenom.split("-")[0];
        String prenom = nomPrenom.split("-")[1];
        
        CompteBancaire toSuppr = CompteBancaireBDD.getCompteBancaireByNomAndPrenom(nom, prenom);
        
        return CompteBancaireBDD.delCompteBancaires(toSuppr);
    }
    
    @POST
    @Path("/comptebancaires/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CompteBancaire addCompteBancaireByNomPrenom(CompteBancaire newCompte) throws Exception {
        return CompteBancaireBDD.addCompteBancaires(newCompte);
    }
}
