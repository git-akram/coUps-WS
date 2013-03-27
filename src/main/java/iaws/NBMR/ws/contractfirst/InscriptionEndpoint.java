package iaws.NBMR.ws.contractfirst;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

@Endpoint
public class InscriptionEndpoint {
	private final InscriptionService inscriptionService;
	
	@Autowired
	public InscriptionEndpoint(InscriptionService inscriptionService){
		this.inscriptionService=inscriptionService;
	}
	
	@PayloadRoot(localPart="inscriptionRequest", namespace="http://coUps/InscriptionSchema")
	@ResponsePayload
	public org.w3c.dom.Element handleInscriptionRequest( @XPathParam("/inscriptionRequest/nom") String nom,
						 @XPathParam("/inscriptionRequest/prenom") String prenom,
						 @XPathParam("/inscriptionRequest/email") String email,
						 @XPathParam("/inscriptionRequest/adresse") String adresse) throws ParserConfigurationException, IOException, SAXException{
		

		System.out.println("-----------> Enter the hanler");
		
		org.w3c.dom.Element elmt = XmlHelper.getRootElementFromFileInClasspath("InscriptionResponse.xml");

		return elmt;
		
		
		//Utilisateur utilisateur = new Utilisateur(nom, prenom, email, adresse);
		
		/*
		// On crée la réponse
		Namespace namespace = Namespace.getNamespace("inscriptionResponse", "http://coUps/InscriptionSchema");
		Element racine = new Element("inscriptionResponse", namespace);
        Element valeur = new Element("valeur", namespace);
        racine.addContent(valeur);
        
		try {
			// On tente d'inscrire notre utilisateur
			inscriptionService.inscrireUtilisateur(utilisateur);
			valeur.setText("OK");
		} catch (CustomException e) {
			valeur.setText("KO");
			
			// On crée le détail de l'erreur
			Element erreur = new Element("erreur", namespace);
			Element code = new Element("code", namespace);
			Element message = new Element("message", namespace);
			code.setText(""+e.getCode());
			message.setText(e.getMessage());
			erreur.addContent(code);
			erreur.addContent(message);
		}
		System.out.println("FIn handler");
		
		return racine;*/
	}

}
