package iaws.NBMR.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

//import org.w3c.dom.Document;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.Document;


import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.service.DataService;

public class DataServiceImpl implements DataService{

	private static DataServiceImpl instance = null;
	
	Session dbSession;
	String dbname;
	Database db;
	
	private DataServiceImpl(){
		new HashMap<String, Utilisateur>();
		dbSession = new Session("localhost", 5984);
		dbname="utilisateur-coups";
		db = dbSession.getDatabase(dbname);
	}
	
	public static DataServiceImpl getInstance(){
		if(instance == null)
			instance = new DataServiceImpl();
		return instance;
	}
	
	
	public void saveUtilisateur(Utilisateur utilisateur) throws IOException{
		
		Document doc = new Document();
	    
	    doc.put("nom", utilisateur.getNom());
	    doc.put("prenom", utilisateur.getPrenom());
	    doc.put("email", utilisateur.getEmail());
	    doc.put("adresse", utilisateur.getAdresse());
	    doc.put("lat", utilisateur.getCoordonnees().getLatitude());
	    doc.put("lon", utilisateur.getCoordonnees().getLongitude());
	    
	    try {
			db.saveDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Utilisateur findUtilisateurByEmail(String email) throws IOException, ClientProtocolException{
		
		String resultat= "";
		Document docView = new Document();
	    docView.setId("_design/couchview");
	                     
	    String str = "{\"javaemail\": {\"map\": \"function(doc) { if (doc.email == '"+email+"')  emit(null, doc) } \"}}";
	    JSONObject strobject= (JSONObject) JSONSerializer.toJSON(str);
	    docView.put("language", "javascript");
	    docView.put("views", strobject);
	    try {
			db.saveDocument(docView);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	    
	    
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpGet get = new HttpGet("http://localhost:5984/"+dbname+"/_design/couchview/_view/javaemail");
	     
		try {
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity=response.getEntity();
		    InputStream instream;
		    instream = entity.getContent();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		    String strdata = null;
		    while( (strdata =reader.readLine())!=null)
			{
			       resultat=resultat+strdata;
			}
		    
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (IOException e1) {
			e1.printStackTrace();
			throw e1;
		}
	    
		try {
	    	db.deleteDocument(docView);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw e1;
		}
		System.out.println(resultat);
		JSONObject json= (JSONObject) JSONSerializer.toJSON(resultat);
		JSONArray rows= json.getJSONArray("rows");
		
		if(rows.size() == 0) return null;
		
		JSONObject element= rows.getJSONObject(0);
		JSONObject value= element.getJSONObject("value");
	    String nom = value.getString("nom");
	    String prenom = value.getString("prenom");
	    String emailAdresse = value.getString("email");
	    String adresse = value.getString("adresse");
	    Coordonnees coordonnees=new Coordonnees(value.getDouble("lat"),value.getDouble("lon"));
	    
	    Utilisateur utilisateur=new Utilisateur(nom,prenom,emailAdresse,adresse,coordonnees);
	    return utilisateur;
	}

	public List<Utilisateur> findUtilisateurACoteDe(String email, int distance) throws IOException, ClientProtocolException {
		
		String resultat= "";
		List<Utilisateur> toReturn = new ArrayList<Utilisateur>();
		Utilisateur reference=new Utilisateur();
		
		try {
			reference = this.findUtilisateurByEmail(email);
			if(null == reference) return toReturn;
		} catch (ClientProtocolException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Document docView = new Document();
	    docView.setId("_design/voisinsview");
	                     
	    
	    	String fonctionRetour = "function(doc) {"
	    		+ "if(doc.email == '" + reference.getEmail() + "') return;"
	    		+ "function getDistanceBetween(lat1, lon1, lat2, lon2) {"
	    		+ "var R = 6371;"
	    		+ "var dLat = deg2rad(lat2-lat1);"
	    		+ "var dLon = deg2rad(lon2-lon1);"
			    + "var a = "
			    + "Math.sin(dLat/2) * Math.sin(dLat/2) +"
			    + "Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *"
			    + "Math.sin(dLon/2) * Math.sin(dLon/2);"
			    + "var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));"
			    + "var d = R * c;"
			    + "return parseInt(d * 1000)"
			    + "}"

	    		+ "function deg2rad(deg) {"
	    		+ "return deg * (Math.PI/180);"
				+ "}"
	    		
				+ "if(getDistanceBetween("+reference.getCoordonnees().getLatitude()+", "+reference.getCoordonnees().getLongitude()+", doc.lat, doc.lon) <= " + distance +")"
				+ "{ emit(null, doc) }"
				
				+ "}"
				;
			
		String str = "{\"javavoisins\": {\"map\": \" "+fonctionRetour+" \"}}";

	    
	    JSONObject strobject= (JSONObject) JSONSerializer.toJSON(str);        
	    docView.put("views", strobject);
	    try {
			db.saveDocument(docView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpGet get = new HttpGet("http://localhost:5984/"+dbname+"/_design/voisinsview/_view/javavoisins");
	     
	    
		try {
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity=response.getEntity();
		    InputStream instream;
		    instream = entity.getContent();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		    String strdata = null;
		    while( (strdata =reader.readLine())!=null)
			{
			       resultat=resultat+strdata;
			}
		    
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
		try {
	    	db.deleteDocument(docView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(resultat);
		JSONObject json= (JSONObject) JSONSerializer.toJSON(resultat);
		JSONArray rows = json.getJSONArray("rows"); // Capturer tout les elements rows et les mettre dans un objet JSONArray

		if(rows.size() == 0) return toReturn;
		
		
        for(int i=0; i < rows.size(); i++) { // parcourir l'objet JSONArray
            JSONObject row = rows.getJSONObject(i);
            JSONObject value= row.getJSONObject("value");
            String nom = value.getString("nom");
    	    String prenom = value.getString("prenom");
    	    String emailAdresse = value.getString("email");
    	    String adresse = value.getString("adresse");
    	    Coordonnees coordonnees=new Coordonnees(value.getDouble("lat"),value.getDouble("lon"));
    	    toReturn.add(new Utilisateur(nom,prenom,emailAdresse,adresse,coordonnees));
        }
		
		return toReturn;
	}

	public void print() throws IOException, ClientProtocolException {
		
		System.out.println("==== Etat de la base ====");
		
		Document docView = new Document();
	    docView.setId("_design/printview");
	                     
	    String str = "{\"javaprint\": {\"map\": \"function(doc) { emit(null, doc) } \"}}";
	    JSONObject strobject= (JSONObject) JSONSerializer.toJSON(str);        
	    docView.put("views", strobject);
	    try {
			db.saveDocument(docView);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	    
	    HttpClient httpclient = new DefaultHttpClient();
		 
		HttpGet get = new HttpGet("http://localhost:5984/utilisateur-coups/_design/printview/_view/javaprint");
		try {
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity=response.getEntity();
		    InputStream instream;
		    instream = entity.getContent();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		    String strdata = null;
		    while( (strdata =reader.readLine())!=null)
			{
			       System.out.println(strdata);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (IOException e1) {
			e1.printStackTrace();
			throw e1;
		}
		
		try {
	    	db.deleteDocument(docView);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw e1;
		}
	}

}
