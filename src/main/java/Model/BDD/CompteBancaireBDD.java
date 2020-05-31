/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.BDD;

import Model.CompteBancaire;
import Model.ERisk;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Aimeric-Thomas
 */
public class CompteBancaireBDD extends ConnectionBDD
{
    // Nom des colonnes dans la base
    private static final String C_ID = "id";
    private static final String C_NOM = "nom";
    private static final String C_PRENOM = "prenom";
    private static final String C_ACCOUNT = "account";
    private static final String C_RISK = "risk";
    
    private static void createTableIfExiste(Statement stmt) throws SQLException
    {
        StringBuilder query;
        query = new StringBuilder("CREATE TABLE IF NOT EXISTS compteBancaires(")
                .append(C_ID).append(" SERIAL,")
                .append(C_NOM).append(" VARCHAR(20) NOT NULL,")
                .append(C_PRENOM).append(" VARCHAR(20) NOT NULL,")
                .append(C_ACCOUNT).append(" float8 DEFAULT(0),")
                .append(C_RISK).append(" BIT DEFAULT('0'),")
                .append("CONSTRAINT pk_compteBancaires PRIMARY KEY(").append(C_ID).append("));");
        
        stmt.executeUpdate(query.toString());
    }
    
    // Permet de recuperer tous les compte bancaires en base
    public static ArrayList<CompteBancaire> getAllCompteBancaires() throws Exception
    {
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            createTableIfExiste(stmt);
            ResultSet rs = stmt.executeQuery("SELECT * FROM compteBancaires;");

            ArrayList<CompteBancaire> output = new ArrayList<>();
            while (rs.next()) {
              CompteBancaire temp = new CompteBancaire(
                      rs.getInt(C_ID),
                      rs.getString(C_NOM),
                      rs.getString(C_PRENOM),
                      rs.getFloat(C_ACCOUNT),
                      rs.getBoolean(C_RISK)? ERisk.High: ERisk.Low
              );
                
              output.add(temp);
            }

            return output;
        } catch (Exception e) {
           throw e;
        }
    }
    
    // Permet de recuperer les compte bancaires en base par rapport a son nom et prenom
    public static ArrayList<CompteBancaire> getAllCompteBancaireByNomAndPrenom(String _nom, String _prenom) throws Exception
    {
        ArrayList<CompteBancaire> toSender = new ArrayList<>();
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            createTableIfExiste(stmt);
            
            StringBuilder query;
            query = new StringBuilder("SELECT * FROM compteBancaires WHERE ")
                    .append(C_NOM)
                    .append("='")
                    .append(_nom)
                    .append("' AND ")
                    .append(C_PRENOM)
                    .append("='")
                    .append(_prenom)
                    .append("';");
            
            ResultSet rs = stmt.executeQuery(query.toString());
          
            while(rs.next())
            {
               toSender.add(new CompteBancaire(
                      rs.getInt(C_ID),
                      rs.getString(C_NOM),
                      rs.getString(C_PRENOM),
                      rs.getFloat(C_ACCOUNT),
                      rs.getBoolean(C_RISK)? ERisk.High: ERisk.Low
              ));
            }
            
        } catch (Exception e) {
            throw e;
        }
        return toSender;
    }
    
    // Permet de recuperer un compte bancaire en base par rapport a son nom et prenom
    public static CompteBancaire getCompteBancaireByid(int id) throws Exception
    {
        CompteBancaire toSender = null;
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            createTableIfExiste(stmt);
            
            StringBuilder query;
            query = new StringBuilder("SELECT * FROM compteBancaires WHERE ")
                    .append(C_ID)
                    .append("='")
                    .append(id)
                    .append("';");
            
            ResultSet rs = stmt.executeQuery(query.toString());
          
            if(rs.first())
            {
               toSender = new CompteBancaire(
                      rs.getInt(C_ID),
                      rs.getString(C_NOM),
                      rs.getString(C_PRENOM),
                      rs.getFloat(C_ACCOUNT),
                      rs.getBoolean(C_RISK)? ERisk.High: ERisk.Low
              );
            }
            
        } catch (Exception e) {
            throw e;
        }
        return toSender;
    }
    
    // Permet de d'ajouter un compte bancaire en base
    public static CompteBancaire addCompteBancaires(CompteBancaire newCompte) throws Exception
    {
        int nblign = 0;
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            createTableIfExiste(stmt);
            
            StringBuilder query;
            query = new StringBuilder("INSERT INTO compteBancaires(")
                    .append(C_NOM).append(",").append(C_PRENOM).append(",").append(C_ACCOUNT).append(",").append(C_RISK)
                    .append(") VALUES ('")
                    .append(newCompte.getNom()).append("','")
                    .append(newCompte.getPrenom()).append("',")
                    .append(newCompte.getAccount()).append(",'")
                    .append(newCompte.getRisk().equals(ERisk.High)?"1":"0")
                    .append("');");
            
            nblign = stmt.executeUpdate(query.toString());
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        if(nblign > 0)
        {        
            ArrayList<CompteBancaire> listCompte = getAllCompteBancaireByNomAndPrenom(newCompte.getNom(),newCompte.getPrenom());
            return listCompte.get(listCompte.size()-1);
        }
        else 
            return null;
    }
    
    // Permet de supprimer un compte bancaire en base
    public static boolean delCompteBancaire(int id) throws Exception
    {
        if(id > 0)
        {
            try (Connection connection = getConnectPG()) 
            {
                Statement stmt = connection.createStatement();
                createTableIfExiste(stmt);

                StringBuilder query = new StringBuilder("DELETE FROM compteBancaires WHERE ")
                        .append(C_ID).append("=").append(id)
                        .append(";");

                int nblign = stmt.executeUpdate(query.toString());

                if(nblign > 0 )
                    return true;
                else
                    return false;
            } 
            catch (Exception e) 
            {
                throw e;
            }
        }
        else
            return false;
    }
}

