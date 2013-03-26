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
	public Element handleInscriptionRequest( @XPathParam("/inscriptionRequest/inscription:nom") String nom,
						 @XPathParam("/inscriptionRequest/inscription:prenom") String prenom,
						 @XPathParam("/inscriptionRequest/inscription:email") String email,
						 @XPathParam("/inscriptionRequest/inscription:adresse") String adresse){
		
		Utilisateur utilisateur = new Utilisateur(nom, prenom, email, adresse);
		
		try {
			inscriptionService.inscrireUtilisateur(utilisateur);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Namespace namespace = Namespace.getNamespace("inscriptionResponse", "http://coUps/InscriptionSchema");
		Element element = new Element("inscriptionResponse", namespace);
        Element valeur = new Element("valeur", namespace);
        element.addContent(valeur);
        
		return element;
	}

}
