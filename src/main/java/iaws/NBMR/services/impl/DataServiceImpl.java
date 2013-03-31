package iaws.NBMR.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
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
	
	private Map<String, Utilisateur> listeUtilisateurs;
	Session dbSession;
	String dbname;
	Database db;
	
	private DataServiceImpl(){
		listeUtilisateurs = new HashMap<String, Utilisateur>();
		dbSession = new Session("localhost", 5984);
		dbname="utilisateur-coUps";
		Database db = dbSession.getDatabase(dbname);
	}
	
	public static DataServiceImpl getInstance(){
		if(instance == null)
			instance = new DataServiceImpl();
		return instance;
	}
	
	
	public void saveUtilisateur(Utilisateur utilisateur) {
		
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
		}
	}
	
	public Utilisateur findUtilisateurByEmail(String email){
		
		String resultat= null;
		Document docView = new Document();
	    docView.setId("_design/couchview");
	                     
	    String str = "{\"javaemail\": {\"map\": \"function(doc) { if (doc.email == '"+email+"')  emit(null, doc) } \"}}";
	             
	    docView.put("views", str);
	    try {
			db.saveDocument(docView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpGet get = new HttpGet("http://localhost:5984/exemple-couchdb/_design/couchview/_view/javaemail");
	     
	    
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
		
		JSONObject json= (JSONObject) JSONSerializer.toJSON(resultat);
	    String nom = json.getString("nom");
	    String prenom = json.getString("prenom");
	    String emailAdresse = json.getString("email");
	    String adresse = json.getString("adresse");
	    Coordonnees coordonnees=new Coordonnees(json.getDouble("lat"),json.getDouble("lon"));
	    
	    Utilisateur utilisateur=new Utilisateur(nom,prenom,emailAdresse,adresse,coordonnees);
	    return utilisateur;
	}

	public List<Utilisateur> findUtilisateurACoteDe(String email, int distance) {
		
		List<Utilisateur> toReturn = new ArrayList<Utilisateur>();
		Utilisateur reference = this.findUtilisateurByEmail(email);
		Iterator<Utilisateur> it = this.listeUtilisateurs.values().iterator();
		while(it.hasNext()){
			Utilisateur current = it.next();
			if(current.equals(reference)) continue;
			if(null == current.getCoordonnees()) continue;
		
			if(Math.sqrt(Math.pow(current.getCoordonnees().getLatitude() - reference.getCoordonnees().getLatitude(), 2) + Math.pow(current.getCoordonnees().getLongitude() - reference.getCoordonnees().getLongitude(), 2)) <= distance){
				// On a un match
				toReturn.add(current);
			}
		}
		
		return toReturn;
	}

	public void print() {
		
		System.out.println("==== Etat de la base ====");
		
		Document docView = new Document();
	    docView.setId("_design/couchview");
	                     
	    String str = "{\"javaemail\": {\"map\": \"function(doc) { emit(null, doc) } \"}}";
	             
	    docView.put("views", str);
	    try {
			db.saveDocument(docView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    HttpClient httpclient = new DefaultHttpClient();
		 
		HttpGet get = new HttpGet("http://localhost:5984/utilisateur-coUps/_design/couchview/_view/javaemail");
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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
	    	db.deleteDocument(docView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
