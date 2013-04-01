package iaws.NBMR.ws.contractfirst;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.service.DataService;
import iaws.NBMR.service.UtilisateurService;
import iaws.NBMR.services.impl.DataServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.xml.sax.SAXException;

@Endpoint
public class UtilisateurEndpoint {
	
	private UtilisateurService utilisateurService;
	
	@Autowired
	public UtilisateurEndpoint(UtilisateurService utilisateurService){
		this.utilisateurService = utilisateurService;
	}
	
	
	@PayloadRoot(localPart="inscriptionRequest", namespace="http://coUps/InscriptionSchema")
	@Namespace(prefix="ins", uri="http://coUps/InscriptionSchema")
	@ResponsePayload
	public Element handleInscriptionRequest(@XPathParam("/ins:inscriptionRequest/ins:nom") String nom,
			@XPathParam("/ins:inscriptionRequest/ins:prenom") String prenom,
			@XPathParam("/ins:inscriptionRequest/ins:email") String email,
			@XPathParam("/ins:inscriptionRequest/ins:adresse") String adresse) throws ParserConfigurationException {
		
		
		// On construit l'utilisateur à inscrire
		Utilisateur utilisateur = new Utilisateur(nom, prenom, email, adresse);
		
		System.out.println("Service inscription in="+utilisateur);		
		
		String namespace = "http://coUps/InscriptionSchema";
		
		// On crée la réponse
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();
        
        Element racine = document.createElementNS(namespace,"inscriptionResponse");
        Element valeur = document.createElementNS(namespace, "valeur");
        racine.appendChild(valeur);
        
		try {
			// On tente d'inscrire notre utilisateur
			utilisateurService.inscrireUtilisateur(utilisateur);
			valeur.appendChild(document.createTextNode("OK"));
		} catch (CustomException e) {
			valeur.appendChild(document.createTextNode("KO"));
			
			Element erreur = document.createElementNS(namespace, "erreur");

        	Node textNode = document.createTextNode(""+e.getCode());
        	Element elementNode = document.createElementNS(namespace, "code");
        	elementNode.appendChild(textNode);
        	erreur.appendChild(elementNode);

        	textNode = document.createTextNode(e.getMessage());
        	elementNode = document.createElementNS(namespace, "message");
        	elementNode.appendChild(textNode);
        	erreur.appendChild(elementNode);
        	
			racine.appendChild(erreur);
		}
		
		System.out.println("[Inscription] Reponse : ");
		System.out.println(nodeToString(racine));
		
		DataServiceImpl.getInstance().print();
		
		
		return racine;
		//*/
	}
	
	@PayloadRoot(localPart="rechercheVoisinsRequest", namespace="http://coUps/RechercheVoisinsSchema")
	@Namespace(prefix="rv", uri="http://coUps/RechercheVoisinsSchema")
	@ResponsePayload
	public Element handleRechercheVoisinsRequest(@XPathParam("/rv:rechercheVoisinsRequest/rv:email") String email,
												@XPathParam("/rv:rechercheVoisinsRequest/rv:distance") int distance) throws ParserConfigurationException {
		

		
		
		Utilisateur utilisateur = DataServiceImpl.getInstance().findUtilisateurByEmail(email);

		System.out.println("Service recherche voisin :: "+utilisateur);
		
		// On récupère la liste des utilisateur a coté de celui indiqué
		List<Utilisateur> utilisateurs = utilisateurService.rechercherVoisins(utilisateur, distance);

		String namespace = "http://coUps/RechercheVoisinsSchema";

		// On crée la réponse
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();
        
        Element racine = document.createElementNS(namespace,"rechercheVoisinsResponse");
        Iterator<Utilisateur> it = utilisateurs.iterator();
        while(it.hasNext()){
            Utilisateur unVoisin = it.next();
        	Element voisinElement = document.createElementNS(namespace, "voisin");

        	Node textNode = document.createTextNode(unVoisin.getNom());
        	Element elementNode = document.createElementNS(namespace, "nom");
        	elementNode.appendChild(textNode);
        	voisinElement.appendChild(elementNode);
        	
        	textNode = document.createTextNode(unVoisin.getPrenom());
        	elementNode = document.createElementNS(namespace, "prenom");
        	elementNode.appendChild(textNode);
        	voisinElement.appendChild(elementNode);
        	
        	textNode = document.createTextNode(unVoisin.getEmail());
        	elementNode = document.createElementNS(namespace, "email");
        	elementNode.appendChild(textNode);
        	voisinElement.appendChild(elementNode);

        	textNode = document.createTextNode(unVoisin.getAdresse());
        	elementNode = document.createElementNS(namespace, "adresse");
        	elementNode.appendChild(textNode);
        	voisinElement.appendChild(elementNode);
        	
        	textNode = document.createTextNode(""+unVoisin.getCoordonnees().getDistanceEnMetreAvec(utilisateur.getCoordonnees()));
        	elementNode = document.createElementNS(namespace, "distance");
        	elementNode.appendChild(textNode);
        	voisinElement.appendChild(elementNode);
    	
        	Element coordonneesNode = document.createElementNS(namespace, "coordonnees");
        	textNode = document.createTextNode(""+unVoisin.getCoordonnees().getLongitude());
        	elementNode = document.createElementNS(namespace, "longitude");
        	elementNode.appendChild(textNode);
        	coordonneesNode.appendChild(elementNode);
        	textNode = document.createTextNode(""+unVoisin.getCoordonnees().getLatitude());
        	elementNode = document.createElementNS(namespace, "latitude");
        	elementNode.appendChild(textNode);
        	coordonneesNode.appendChild(elementNode);
        	voisinElement.appendChild(coordonneesNode);
        	
        	racine.appendChild(voisinElement);
        }

		System.out.println("[Recherche voisin] Reponse : ");
		System.out.println(nodeToString(racine));

		DataServiceImpl.getInstance().print();
        
		return racine;
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
