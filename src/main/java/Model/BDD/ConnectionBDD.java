/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.BDD;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Aimeric-Thomas
 */
public class ConnectionBDD {
    protected static final String DB_URl = "jdbc:postgresql://";
    
    protected static Connection getConnectPG() throws URISyntaxException, SQLException 
    {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
		
	String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = DB_URl + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
