package iaws.NBMR.services.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.service.OSMService;

public class OSMServiceImpl implements OSMService{
	private static OSMServiceImpl instance = null;
	
	public static OSMServiceImpl getInstance(){
		if(instance == null)
		instance = new OSMServiceImpl();
		return instance;
	}

	public Coordonnees findCoordonneesPourAdresse(String adresse) throws CustomException {
		// Création des coordonnées a retourné
		Coordonnees coordonnees=new Coordonnees();
		// Construction de l'adresse
		String adress=adresse;
		adress=adress.replaceAll("\\s", "+");
		
		// Construction de l'url avec l'adresse entrée 
		final StringBuilder searchUrlBuilder = new StringBuilder();
		searchUrlBuilder.append("http://nominatim.openstreetmap.org");
		searchUrlBuilder.append("/search?q=");
		searchUrlBuilder.append(adress);
		searchUrlBuilder.append("&format=xml&addressdetails=1");
		String searchUrl = searchUrlBuilder.toString();
		
		//construction de la requête http avec la méthode Get
		final HttpGet req = new HttpGet(searchUrl);
		ResponseHandler<String> gestionnaire_reponse = new BasicResponseHandler();
		String reponse=null;
		HttpClient httpclient= new DefaultHttpClient();
		
		// Envoie de la requête http et reception de la réponse
		try{
			reponse = httpclient.execute(req, gestionnaire_reponse);
			
			// Récupération de la réponse et transformation en document XML en sortie
			InputStream source = new ByteArrayInputStream(reponse.getBytes("UTF8"));
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document ret = builder.parse(new InputSource(source));
			
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    //File file=new File("OSMsortie.xml");
		    Result output = new StreamResult("OSMsortie.xml");
		    Source input = new DOMSource(ret);

		    transformer.transform(input, output);
		    
		    // Récupération des valeur latitude et longitude à l'aide d'objet XPath
		    // Et mise à jour des coordonnees latitude et longitude 
		    // selon les valeurs récupéré
		    // les coordonnees en cas de resultat null sont lat=NaN et lon=NaN
			XPathFactory xfactory = XPathFactory.newInstance();
		    XPath xPath = xfactory.newXPath();
		    Number resultLat = (Number) xPath.evaluate("/searchresults/place/@lat", 
		    		new InputSource(new FileReader("OSMsortie.xml")), XPathConstants.NUMBER);
		    Double lat=resultLat.doubleValue();
		    coordonnees.setLatitude(lat);
		    
		    Number resultLon = (Number) xPath.evaluate("/searchresults/place/@lon", 
		    		new InputSource(new FileReader("OSMsortie.xml")), XPathConstants.NUMBER);
		    Double lon=resultLon.doubleValue();
		    coordonnees.setLongitude(lon);
	
		    // Suppression du fichier XML de sortie après utilisation
		    new File("OSMsortie.xml").delete();

		} catch (ClientProtocolException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		finally {
        		httpclient.getConnectionManager().shutdown();
    	}
		
		String s="Coordonnees [lat=NaN, lon=NaN]";
	    if(s.equals(coordonnees.toString()))
	    	throw new CustomException(200, "Adresse postale non connue de Open Street Map"); 
		return coordonnees;
	}
	
}
