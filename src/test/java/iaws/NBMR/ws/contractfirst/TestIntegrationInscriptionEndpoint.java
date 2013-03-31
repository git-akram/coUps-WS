package iaws.NBMR.ws.contractfirst;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import java.io.IOException;

import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.services.impl.DataServiceImpl;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("/application-context.xml")
public class TestIntegrationInscriptionEndpoint {
	@Autowired
    private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;
	
    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }
    
    
    @Test
    public void testInscriptionEndpoint() throws IOException {
    	
        Source requestPayload = new StreamSource(new ClassPathResource("InscriptionRequest.xml").getInputStream() );
        Source responsePayload = new StreamSource(new ClassPathResource("InscriptionResponse.xml").getInputStream());
        
    	mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
    }

}
