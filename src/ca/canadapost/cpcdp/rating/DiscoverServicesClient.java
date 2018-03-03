package ca.canadapost.cpcdp.rating;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.xml.bind.JAXBContext;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;

import ca.canadapost.cpcdp.rating.generated.discovery.Services;
import ca.canadapost.cpcdp.rating.generated.messages.Messages;

/**
 * Sample code for the DiscoverServices Canada Post service.
 * <p>
 * The DiscoverServices service returns the list of available postal services for shipment 
 * of a parcel to a particular destination. 
 * <p>
 * This sample is configured to access the Developer Program sandbox environment. 
 * Use your development key username and password for the web service credentials.
 * <p>
 *  Jersey REST client using JAXB for DiscoverServices Request.  <br>
 *  USAGE:<pre>
 *        DiscoverServicesClient client = new DiscoverServicesClient(username, password);
 *        ClientResponse response = client.discoverServices();
 *        // do whatever with response
 *        client.close();
 *  </pre>
 *
 */
public class DiscoverServicesClient {

    private Client aClient;
    private static final String LINK = "https://ct.soa-gw.canadapost.ca/rs/ship/service";
    
    // Main class for local testing only
    public static void main(String[] args) {

       	// Your username and password are imported from the following file   	
    	// CPCWS_Rating_Java_Samples/user.properties 
    	Properties userProps = new Properties();
    	FileInputStream propInputStream;
		try {
			propInputStream = new FileInputStream("user.properties");
			userProps.load(propInputStream);
			propInputStream.close(); // better in finally block
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
			return;
		}
    	String username = userProps.getProperty("username");
    	String password = userProps.getProperty("password");
		
		// Execute DiscoverServices Request
        DiscoverServicesClient myClient = new DiscoverServicesClient(username, password);
        ClientResponse resp = myClient.discoverServices();
        InputStream respIS = resp.getEntityStream();
        
        System.out.println("HTTP Response Status: " + resp.getStatus() + " " + resp.getClientResponseStatus());

        // Example of using JAXB to parse xml response
        JAXBContext jc;
        try {
        	jc = JAXBContext.newInstance(Services.class, Messages.class);
            Object entity = jc.createUnmarshaller().unmarshal(respIS);
            // Determine whether response data matches Services schema.
            if (entity instanceof Services) {
            	Services services = (Services) entity;
                for (Iterator<Services.Service> iter = services.getServices().iterator(); iter.hasNext();) { 
                	Services.Service aItem = (Services.Service) iter.next();                	
                    System.out.println("Service Code: " + aItem.getServiceCode());
                    System.out.println("Service Name: " + aItem.getServiceName());
                    System.out.println("Href: " + aItem.getLink().getHref() + "\n");
                }            	
            	
            } else {
                // Assume Error Schema
                Messages messageData = (Messages) entity;
                for (Iterator<Messages.Message> iter = messageData.getMessage().iterator(); iter.hasNext();) {
                    Messages.Message aMessage = (Messages.Message) iter.next();
                    System.out.println("Error Code: " + aMessage.getCode());
                    System.out.println("Error Msg: " + aMessage.getDescription());
                }
            }
        } catch (Exception e) {
        	e.printStackTrace(System.out);
        }

        myClient.close();
    }

    public DiscoverServicesClient(String username, String password) {
        ClientConfig config = new DefaultClientConfig();
        aClient = Client.create(config);
        aClient.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(username, password));
    }

    public ClientResponse discoverServices() throws UniformInterfaceException {
    	WebResource aWebResource = aClient.resource(LINK);
        return aWebResource.accept("application/vnd.cpc.ship.rate-v3+xml").acceptLanguage("en-CA").get(ClientResponse.class);
    }

    public void close() {
        aClient.destroy();
    }
}
