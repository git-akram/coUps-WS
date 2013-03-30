package iaws.NBMR.ws.contractfirst;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.service.UtilisateurService;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.xml.sax.SAXException;

@Endpoint
public class UtilisateurEndpoint {
	
	private UtilisateurService utilisateurService;
	
	@Autowired
	public UtilisateurEndpoint(UtilisateurService utilisateurService){
		System.out.println("Enter constructor endpoint");
		this.utilisateurService = utilisateurService;
	}
	
	
	@PayloadRoot(localPart="inscriptionRequest", namespace="http://coUps/InscriptionSchema")
	@org.springframework.ws.server.endpoint.annotation.Namespace(prefix="ins", uri="http://coUps/InscriptionSchema")
	@ResponsePayload
	public Element handleInscriptionRequest(@XPathParam("/ins:inscriptionRequest/ins:nom") String nom,
			@XPathParam("/ins:inscriptionRequest/ins:prenom") String prenom,
			@XPathParam("/ins:inscriptionRequest/ins:email") String email,
			@XPathParam("/ins:inscriptionRequest/ins:adresse") String adresse) throws ParserConfigurationException {
		

		System.out.println("-----------> Enter the hanler inscription");
		
		//org.w3c.dom.Element elmt = XmlHelper.getRootElementFromFileInClasspath("InscriptionResponse.xml");
		//return elmt;
		
		//*
		
		
		// On construit l'utilisateur à inscrire
		Utilisateur utilisateur = new Utilisateur(nom, prenom, email, adresse);
		System.out.println(utilisateur);
		/*
		Utilisateur utilisateur = new Utilisateur();
		NodeList elementsUtilisateur = elementRequest.getChildNodes();
		for(int i = 0; i< elementsUtilisateur.getLength(); i++){
			if(elementsUtilisateur.item(i).getNodeType() != Element.ELEMENT_NODE) continue;
			
			System.out.println("i: " + i + "/" + elementsUtilisateur.item(i).getTextContent());
			String localName = elementsUtilisateur.item(i).getLocalName();
			if(localName.equals("nom"))
				utilisateur.setNom(elementsUtilisateur.item(i).getTextContent());
			
			else if(localName.equals("prenom"))
				utilisateur.setPrenom(elementsUtilisateur.item(i).getTextContent());
			
			else if(localName.equals("email"))
				utilisateur.setEmail(elementsUtilisateur.item(i).getTextContent());
			
			else if(localName.equals("adresse"))
				utilisateur.setAdresse(elementsUtilisateur.item(i).getTextContent());
		}
		*/
		
		System.out.println("Trace 0");
		
		String namespace = "http://coUps/InscriptionSchema";
		
		// On crée la réponse
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();
        
        Element racine = document.createElementNS(namespace,"inscriptionResponse");
        Element valeur = document.createElementNS(namespace, "valeur");
        racine.appendChild(valeur);
        System.out.println("Trace 1");
		try {
			// On tente d'inscrire notre utilisateur
			utilisateurService.inscrireUtilisateur(utilisateur);
			valeur.appendChild(document.createTextNode("OK"));
		} catch (CustomException e) {
			System.out.println("exception..");
			valeur.appendChild(document.createTextNode("KO"));
			
			Element erreur = document.createElementNS(namespace, "erreur");
			erreur.appendChild(
					document.createElementNS(namespace, "code").appendChild(
							document.createTextNode(""+e.getCode())
					));
			erreur.appendChild(
					document.createElementNS(namespace, "message").appendChild(
							document.createTextNode(e.getMessage())
					));
			
			racine.appendChild(erreur);
		}
		
		System.out.println("[Inscription] Reponse : ");
		System.out.println(nodeToString(racine));
		
		
		return racine;
		//*/
	}
	
	@PayloadRoot(localPart="rechercheVoisinsRequest", namespace="http://coUps/RechercheVoisinsSchema")
	@ResponsePayload
	public Element handleRechercheVoisinsRequest(@RequestPayload Element request) 
			throws ParserConfigurationException, IOException, SAXException{
		
		System.out.println("je suis dans handleRechercheVoisinsRequest");
		
		//Element elmt = XmlHelper.getRootElementFromFileInClasspath("RechercheVoisinsResponse.xml");

		//return elmt;
		
		return null;
		//rechercheVoisinsService.rechercherVoisin(email, distance);
	}
	
	/**
	 * Converti un Element en string
	 * Remarque: encodage utf-16 par defaut
	 * @return String
	 */
	private String nodeToString(Node node){

		Document document = node.getOwnerDocument();
		DOMImplementationLS domImplLS = (DOMImplementationLS) document
		    .getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		return serializer.writeToString(node);
	}
	
}
