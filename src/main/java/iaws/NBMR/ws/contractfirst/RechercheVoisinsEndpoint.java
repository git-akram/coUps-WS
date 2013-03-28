package iaws.NBMR.ws.contractfirst;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import iaws.NBMR.service.RechercheVoisinsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Endpoint
public class RechercheVoisinsEndpoint {
	//private final RechercheVoisinsService rechercheVoisinsService;
	
	private static final String NAMESPACE_URI = "http://coUps/RechercheVoisinsSchema";
	
	public RechercheVoisinsEndpoint(){//RechercheVoisinsService rechercheVoisinsService){
		System.out.println("je suis dans le constructeur du Endpoint");
		//this.rechercheVoisinsService=rechercheVoisinsService;
	}
	
	@PayloadRoot(namespace=NAMESPACE_URI, localPart="rechercheVoisinsRequest")
	@ResponsePayload
	public Element handleRechercheVoisinsRequest(@RequestPayload Element request) throws ParserConfigurationException, IOException, SAXException{
		
		System.out.println("je suis dans handleRechercheVoisinsRequest");
		
		Element elmt = XmlHelper.getRootElementFromFileInClasspath("RechercheVoisinsResponse.xml");

		return elmt;
		
		//rechercheVoisinsService.rechercherVoisin(email, distance);
	}
}
