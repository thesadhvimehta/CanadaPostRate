import ca.canadapost.cpcdp.rating.generated.messages.*;
import ca.canadapost.cpcdp.rating.generated.rating.*;
import ca.canadapost.cpcdp.rating.generated.rating.MailingScenario.Destination;
import ca.canadapost.cpcdp.rating.generated.rating.MailingScenario.Destination.Domestic;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;

import javax.xml.bind.JAXBContext;

import java.io.FileInputStream;
import java.io.InputStream;
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
public class GetRat {

   
}
