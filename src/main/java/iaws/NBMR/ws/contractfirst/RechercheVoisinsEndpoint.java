package iaws.NBMR.ws.contractfirst;

import iaws.NBMR.service.RechercheVoisinsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;

@Endpoint
public class RechercheVoisinsEndpoint {
	private final RechercheVoisinsService rechercheVoisinsService;
	
	@Autowired
	public RechercheVoisinsEndpoint(RechercheVoisinsService rechercheVoisinsService){
		System.out.println("je suis dans le constructeur du Endpoint");
		this.rechercheVoisinsService=rechercheVoisinsService;
	}
	
	@PayloadRoot(localPart="rechercheVoisinsRequest", namespace="http://coUps/RechercheVoisinsSchema")
	@ResponsePayload
	public void handleRechercheVoisinsRequest( @XPathParam("/rechercheVoisinsRequest/email") String email,
											   @XPathParam("/rechercheVoisinsRequest/distance") int distance){
		
		System.out.println("je suis dans handleRechercheVoisinsRequest");
		
		rechercheVoisinsService.rechercherVoisin(email, distance);
	}
}
