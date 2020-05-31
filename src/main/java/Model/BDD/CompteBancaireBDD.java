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
                .append(C_RISK).append(" BIT DEFAULT(0)")
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
                      rs.getNString(C_NOM),
                      rs.getNString(C_PRENOM),
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
    
    // Permet de recuperer un compte bancaire en base par rapport a son nom et prenom
    public static CompteBancaire getCompteBancaireByNomAndPrenom(String _nom, String _prenom) throws Exception
    {
        CompteBancaire toSender = null;
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            createTableIfExiste(stmt);
            
            StringBuilder query = new StringBuilder("SELECT * FROM compteBancaires WHERE ")
                    .append(C_NOM)
                    .append("='")
                    .append(_nom)
                    .append("' AND ")
                    .append(C_PRENOM)
                    .append("='")
                    .append("';");
            
            ResultSet rs = stmt.executeQuery(query.toString());
          
            if(rs.first())
            {
               toSender = new CompteBancaire(
                      rs.getInt(C_ID),
                      rs.getNString(C_NOM),
                      rs.getNString(C_PRENOM),
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
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            createTableIfExiste(stmt);
            StringBuilder query;
            query = new StringBuilder("INSERT INTO compteBancaires(nom,prenom,account,C_RISK) VALUES ('test','test',1000,0);");
            /*
            query = new StringBuilder("INSERT INTO compteBancaires(")
                    .append(C_NOM).append(",").append(C_PRENOM).append(",").append(C_ACCOUNT).append(",").append(C_RISK)
                    .append(") VALUES ('")
                    .append(newCompte.getNom()).append("','")
                    .append(newCompte.getPrenom()).append("','")
                    .append(newCompte.getAccount()).append("','")
                    .append(newCompte.getRisk().equals(ERisk.High)?"1":"0")
                    .append("');");
            */
            stmt.executeUpdate(query.toString());
            
            return getCompteBancaireByNomAndPrenom(newCompte.getNom(),newCompte.getPrenom());
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }
    
    // Permet de supprimer un compte bancaire en base
    public static boolean delCompteBancaires(CompteBancaire delCompte) throws Exception
    {
        if(delCompte != null)
        {
            try (Connection connection = getConnectPG()) 
            {
                Statement stmt = connection.createStatement();
                createTableIfExiste(stmt);

                StringBuilder query = new StringBuilder("DELETE FROM compteBancaires WHERE ")
                        .append(C_ID).append("=").append(delCompte.getId())
                        .append(";");

                stmt.executeUpdate(query.toString());

                return true;
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

