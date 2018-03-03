package ca.canadapost.cpcdp.rating;

import ca.canadapost.cpcdp.rating.generated.messages.Messages;
import ca.canadapost.cpcdp.rating.generated.rating.*;
import ca.canadapost.cpcdp.rating.generated.rating.MailingScenario.Destination;
import ca.canadapost.cpcdp.rating.generated.rating.MailingScenario.Destination.Domestic;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;

import javax.xml.bind.JAXBContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Properties;

import java.math.*;

/**
 * Sample code for the GetRates Canada Post service.
 * <p>
 * The GetRates service returns a list of shipping services, prices and transit times 
 * for a given item to be shipped. 
 * <p>
 * This sample is configured to access the Developer Program sandbox environment. 
 * Use your development key username and password for the web service credentials.
 * <p>
 *  Jersey REST client using JAXB for GetRates Request.  <br>
 *  USAGE:<pre>
 *        GetRatesClient client = new GetRatesClient(username, password);
 *        ClientResponse response = client.createMailingScenario(mailingScenario);
 *        // do whatever with response
 *        client.close();
 *  </pre>
 *
 */
public class GetRatesClient {

    private Client aClient;
    private static final String LINK = "https://ct.soa-gw.canadapost.ca/rs/ship/price";
    
    // Main class for local testing only
    public static void main(String[] args) throws FileNotFoundException {
    	
       	// Your username, password and customer number are imported from the following file    	
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
    	String mailedBy = userProps.getProperty("mailedBy"); 
    	
		// Create GetRates XML Request Object
		MailingScenario mailingScenario = new MailingScenario();
		
		mailingScenario.setCustomerNumber(mailedBy);
		
		String[] pcArr = {"H9W6C3", "M6J1H7", "V6T1Z4", "T6G2R3", "B3H4R2", "S7N5E8", "R3T2N2", "A1C5S7", "E2L4L5", "C1A4P3",
				"X0E0T0", "Y1A5K4", "X0A0H0"};
		PrintStream out = new PrintStream(new FileOutputStream("output_originBC.txt"));
		System.setOut(out);
		MailingScenario.ParcelCharacteristics parcelCharacteristics = new MailingScenario.ParcelCharacteristics();
		for(int i = 1; i < pcArr.length; i++){
			System.out.println("Currently in" + pcArr[i]);
				parcelCharacteristics.setWeight(new BigDecimal(10));
				mailingScenario.setParcelCharacteristics(parcelCharacteristics);
				
				mailingScenario.setOriginPostalCode(pcArr[i]);

				Domestic domestic = new Domestic();
				domestic.setPostalCode(pcArr[i]);		
				Destination destination = new Destination();
				destination.setDomestic(domestic);
				mailingScenario.setDestination(destination);
				
				// Execute GetRates Request
		        GetRatesClient myClient = new GetRatesClient(username, password);
		        ClientResponse resp = myClient.createMailingScenario(mailingScenario);
		        InputStream respIS = resp.getEntityInputStream();
		        
		        System.out.println("HTTP Response Status: " + resp.getStatus() + " " + resp.getClientResponseStatus());

		        // Example of using JAXB to parse xml response
		        JAXBContext jc;
		        try {
		        	jc = JAXBContext.newInstance(PriceQuotes.class, Messages.class);
		            Object entity = jc.createUnmarshaller().unmarshal(respIS);
		            // Determine whether response data matches GetRatesInfo schema.
		            if (entity instanceof PriceQuotes) {
		            	PriceQuotes priceQuotes = (PriceQuotes) entity;
		                for (Iterator<PriceQuotes.PriceQuote> iter = priceQuotes.getPriceQuotes().iterator(); iter.hasNext();) { 
		                	PriceQuotes.PriceQuote aPriceQuote = (PriceQuotes.PriceQuote) iter.next();                	
			                System.out.println("Service Name: " + aPriceQuote.getServiceName());
			                System.out.println("Price: $" + aPriceQuote.getPriceDetails().getDue() + "\n");
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
		

    }

    public GetRatesClient(String username, String password) {
        ClientConfig config = new DefaultClientConfig();
        aClient = Client.create(config);
        aClient.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(username, password));
    }

    public ClientResponse createMailingScenario(Object xml) throws UniformInterfaceException {
        WebResource aWebResource = aClient.resource(LINK);
        return aWebResource.accept("application/vnd.cpc.ship.rate-v3+xml").header("Content-Type", "application/vnd.cpc.ship.rate-v3+xml").acceptLanguage("en-CA").post(ClientResponse.class, xml);
    }

    public void close() {
        aClient.destroy();
    }
}
