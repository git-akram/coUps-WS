package iaws.NBMR.services.impl;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.http.client.ClientProtocolException;

import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.service.UtilisateurService;

public class UtilisateurServiceImpl implements UtilisateurService {

	public void inscrireUtilisateur(Utilisateur utilisateur)
			throws CustomException {
		// On va vérifier la validité de l'adresse e-mail
		try {
			(new InternetAddress(utilisateur.getEmail())).validate();
			if(!utilisateur.getEmail().endsWith("@univ-tlse3.fr"))
				throw new Exception();
		} catch (Exception e) {
			// Adresse email invalide
			throw new CustomException(110, "Adresse email invalide");
		}
		
		// On va vérifier que l'adresse email n'est pas déjà utilisée
		try {
			if(null != DataServiceImpl.getInstance().findUtilisateurByEmail(utilisateur.getEmail()))
				throw new CustomException(100, "Adresse email déjà utilisée");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// On va chercher les coordonnées OSM de l'utilisateur
		Coordonnees coordonnees = 
				(new OSMServiceImpl()).findCoordonneesPourAdresse(utilisateur.getAdresse());
		
		// On met a jour l'utilisateur avec ses coordonnées
		utilisateur.setCoordonnees(coordonnees);
		
		// On enregistre notre nouvel utilisateur
		try {
			DataServiceImpl.getInstance().saveUtilisateur(utilisateur);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public List<Utilisateur> rechercherVoisins(Utilisateur utilisateur, int distance) {
		
		List<Utilisateur> ListUtilisateur=new ArrayList<Utilisateur>();
		if(null == utilisateur) return ListUtilisateur;
		
		try {
			ListUtilisateur= DataServiceImpl.getInstance().findUtilisateurACoteDe(utilisateur.getEmail(), distance);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ListUtilisateur;
		
		/*
		List<Utilisateur> voisins=new ArrayList<Utilisateur>();
		Utilisateur user1=new Utilisateur("nom1","prenom1","user1@exemple.com","21 rue robert 31200 toulouse");
		Utilisateur user2=new Utilisateur("nom2","prenom2","user2@exemple.com","34 rue albert 31500 toulouse");
		voisins.add(user1);
		voisins.add(user2);
		return voisins;
		//*/
	}

}
