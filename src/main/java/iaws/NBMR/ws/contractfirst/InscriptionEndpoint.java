package iaws.NBMR.ws.contractfirst;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.service.InscriptionService;

import org.jdom.Element;
import org.jdom.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;

@Endpoint
public class InscriptionEndpoint {
	private final InscriptionService inscriptionService;
	
	@Autowired
	public InscriptionEndpoint(InscriptionService inscriptionService){
		this.inscriptionService=inscriptionService;
	}
	
	@PayloadRoot(localPart="inscriptionRequest", namespace="http://coUps/InscriptionSchema")
	@ResponsePayload
	public void handleInscriptionRequest( @XPathParam("/inscriptionRequest/nom") String nom,
						 @XPathParam("/inscriptionRequest/prenom") String prenom,
						 @XPathParam("/inscriptionRequest/email") String email,
						 @XPathParam("/inscriptionRequest/adresse") String adresse){
		
		System.out.println("je suis dans handleInscriptionRequest");
		Utilisateur utilisateur = new Utilisateur(nom, prenom, email, adresse);
		
		
		try {
			inscriptionService.inscrireUtilisateur(utilisateur);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
