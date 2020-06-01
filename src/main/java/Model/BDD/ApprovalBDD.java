/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.BDD;

import static Model.BDD.ConnectionBDD.getConnectPG;
import Model.Approval;
import Model.EApproval;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author kenny
 */
public class ApprovalBDD extends ConnectionBDD
{
    // Nom des colonnes dans la base
    private static final String C_ID = "id";
    private static final String C_IDCOMPTE = "idCompte";
    private static final String C_ACCOUNT = "account";
    private static final String C_ETAT = "etat";
    
    public static void createTableIfExiste() throws SQLException, Exception
    {
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            StringBuilder query;
            query = new StringBuilder("CREATE TABLE IF NOT EXISTS Approval(")
                    .append(C_ID).append(" SERIAL,")
                    .append(C_IDCOMPTE).append(" INTEGER NOT NULL,")
                    .append(C_ACCOUNT).append(" float8 NOT NULL,")
                    .append(C_ETAT).append(" VARCHAR(10) NOT NULL,")
                    .append("CONSTRAINT pk_compteBancaires PRIMARY KEY(").append(C_ID).append("),")
                    .append("CONSTRAINT fk_Approval_compteBancaires FOREIGN KEY(").append(C_IDCOMPTE).append(") REFERENCES compteBancaires(id)")
                    .append(");");

            stmt.executeUpdate(query.toString());
         } catch (Exception e) {
           throw e;
        }
    }
    
    // Permet de recuperer tous les Approval en base
    public static ArrayList<Approval> getAllApproval() throws Exception
    {
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Approval;");

            ArrayList<Approval> output = new ArrayList<>();
            while (rs.next()) {
              Approval temp = new Approval(
                      rs.getInt(C_ID),
                      rs.getInt(C_IDCOMPTE),
                      rs.getFloat(C_ACCOUNT),
                      EApproval.valueOf(rs.getString(C_ETAT))
              );
                
              output.add(temp);
            }

            return output;
        } catch (Exception e) {
           throw e;
        }
    }
    
    // Permet de recuperer les Approval en base par rapport a son idCompte
    public static ArrayList<Approval> getAllApprovalByIdCompte(int id) throws Exception
    {
        ArrayList<Approval> toSender = new ArrayList<>();
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            
            StringBuilder query;
            query = new StringBuilder("SELECT * FROM Approval WHERE ")
                    .append(C_IDCOMPTE)
                    .append("=")
                    .append(id)
                    .append(";");
            
            ResultSet rs = stmt.executeQuery(query.toString());
          
            while(rs.next())
            {
               toSender.add(new Approval(
                      rs.getInt(C_ID),
                      rs.getInt(C_IDCOMPTE),
                      rs.getFloat(C_ACCOUNT),
                      EApproval.valueOf(rs.getString(C_ETAT))
              ));
            }
            
        } catch (Exception e) {
            throw e;
        }
        return toSender;
    }
    
    // Permet de recuperer un Approval en base par rapport a son id
    public static Approval getApprovalByid(int id) throws Exception
    {
        Approval toSender = null;
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            
            StringBuilder query;
            query = new StringBuilder("SELECT * FROM Approval WHERE ")
                    .append(C_ID).append("=").append(id)
                    .append(";");
            
            ResultSet rs = stmt.executeQuery(query.toString());
          
            while(rs.next())
            {
               toSender = new Approval(
                       rs.getInt(C_ID),
                      rs.getInt(C_IDCOMPTE),
                      rs.getFloat(C_ACCOUNT),
                      EApproval.valueOf(rs.getString(C_ETAT))
              );
            }
            
        } catch (Exception e) {
            throw e;
        }
        return toSender;
    }
    
    // Permet de d'ajouter un Approval en base
    public static Approval addApproval(Approval newApproval) throws Exception
    {
        int nblign = 0;
        try (Connection connection = getConnectPG()) 
        {
            Statement stmt = connection.createStatement();
            
            StringBuilder query;
            query = new StringBuilder("INSERT INTO Approval(")
                    .append(C_IDCOMPTE).append(",").append(C_ACCOUNT).append(",").append(C_ETAT)
                    .append(") VALUES (")
                    .append(newApproval.getIdCompte()).append(",")
                    .append(newApproval.getAccount()).append(",")
                    .append(newApproval.getEtat())
                    .append(");");
            
            nblign = stmt.executeUpdate(query.toString());
        } 
        catch (Exception e) 
        {
            throw e;
        }
        
        if(nblign > 0)
        {        
            ArrayList<Approval> listCompte = getAllApprovalByIdCompte(newApproval.getIdCompte());
            return listCompte.get(listCompte.size()-1);
        }
        else 
            return null;
    }
    
    // Permet de supprimer un Approval en base
    public static boolean delApproval(int id) throws Exception
    {
        if(id > 0)
        {
            try (Connection connection = getConnectPG()) 
            {
                Statement stmt = connection.createStatement();

                StringBuilder query;
                query = new StringBuilder("DELETE FROM Approval WHERE ")
                        .append(C_ID).append("=").append(id)
                        .append(";");

                int nblign = stmt.executeUpdate(query.toString());

                return nblign > 0;
            } 
            catch (Exception e) 
            {
                throw e;
            }
        }
        else
            return false;
    }
    
    // Permet de modifier l'etat d'un approval
    public static boolean ChangeEtatApproval(int id, EApproval etat) throws Exception
    {
        if(id > 0)
        {
            try (Connection connection = getConnectPG()) 
            {
                Statement stmt = connection.createStatement();

                StringBuilder query;
                query = new StringBuilder("UPDATE Approval SET ")
                        .append(C_ETAT).append("=").append(etat)
                        .append(" WHERE ")
                        .append(C_ID).append("=").append(id)
                        .append(";");

                int nblign = stmt.executeUpdate(query.toString());

                return nblign > 0;
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
