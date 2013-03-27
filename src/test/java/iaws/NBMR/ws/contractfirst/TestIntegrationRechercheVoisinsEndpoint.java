package iaws.NBMR.ws.contractfirst;

import static org.springframework.ws.test.server.RequestCreators.withPayload;

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
@ContextConfiguration("/application-context.xml")
public class TestIntegrationRechercheVoisinsEndpoint {
	@Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
    	System.out.println("entrée createClient()");
        mockClient = MockWebServiceClient.createClient(applicationContext);
        System.out.println("création du mock Client");
    }

    @Test
    public void rechercheVoisinsEndpoint() throws Exception {
    	System.out.println("entrée inscriptionEndpoint()");
        Source requestPayload = new StreamSource(new ClassPathResource("RechercheVoisinsRequest.xml").getInputStream() );
        System.out.println("création de la source requestPayload");
        Source responsePayload = new StreamSource(new ClassPathResource("RechercheVoisinsResponse.xml").getInputStream());
        System.out.println("création de la source responsePayload");
        
        mockClient.sendRequest(withPayload(requestPayload))/*.andExpect(payload(responsePayload))*/;
        System.out.println("sendRequest with Payload(requestPayload)");
    }
}
