package ProjHeruku;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("{Name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@PathParam("Name") String name) {
		return "Hello, " + name + "!";
	}
	
	@GET
	@Path("/db")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDbShow() {
		return this.showDatabasePG();
	}

	public Connection getConnectPG() throws URISyntaxException, SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		
		String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
	public String showDatabasePG() {
		try (Connection connection = this.getConnectPG()) {
		      Statement stmt = connection.createStatement();
		      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
		      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
		      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

		      StringBuilder output = new StringBuilder();
		      while (rs.next()) {
		        output.append("Read from DB: ").append(rs.getTimestamp("tick")).append("\n\r");
		      }

		      return output.toString();
		    } catch (Exception e) {
		      e.printStackTrace();
		      return "error => "+ e.getMessage();
		    }
	}
}
