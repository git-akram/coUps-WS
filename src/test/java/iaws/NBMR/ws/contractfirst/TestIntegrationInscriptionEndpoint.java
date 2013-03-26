package iaws.NBMR.ws.contractfirst;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("application-context.xml")
public class TestIntegrationInscriptionEndpoint {
	@Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
    	System.out.println("entr�e createClient()");
        mockClient = MockWebServiceClient.createClient(applicationContext);
        System.out.println("cr�ation du mock Client");
    }

    @Test
    public void inscriptionEndpoint() throws Exception {
    	System.out.println("entr�e inscriptionEndpoint()");
        Source requestPayload = new StreamSource(new ClassPathResource("InscriptionRequest.xml").getInputStream() );
        System.out.println("cr�ation de la source requestPayload");
        Source responsePayload = new StreamSource(new ClassPathResource("InscriptionResponse.xml").getInputStream());
        System.out.println("cr�ation de la source responsePayload");
        
        mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
        System.out.println("sendRequest with Payload(requestPayload)");
    }

}
