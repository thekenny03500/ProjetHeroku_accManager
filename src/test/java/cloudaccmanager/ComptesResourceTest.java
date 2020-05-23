/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudaccmanager;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ComptesResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(ComptesResource.class);
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        final String responseMsg = target().path("Comptes/test").request().get(String.class);

        assertEquals("Hello World", responseMsg);
    }
}

