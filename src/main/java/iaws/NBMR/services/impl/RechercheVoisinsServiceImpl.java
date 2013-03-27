package iaws.NBMR.services.impl;

import java.util.ArrayList;
import java.util.List;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.service.DataService;
import iaws.NBMR.service.RechercheVoisinsService;

public class RechercheVoisinsServiceImpl implements RechercheVoisinsService {

	public List<Utilisateur> rechercherVoisin(String email,int distance) {
		// TODO Auto-generated method stub
		List<Utilisateur> voisins=new ArrayList<Utilisateur>();
		/*DataService serviceDonnees = null;
		voisins=serviceDonnees.findUtilisateurACoteDe(email, distance);*/
		Utilisateur user1=new Utilisateur("nom1","prenom1","user1@exemple.com","21 rue robert 31200 toulouse");
		Utilisateur user2=new Utilisateur("nom2","prenom2","user2@exemple.com","34 rue albert 31500 toulouse");
		voisins.add(user1);
		voisins.add(user2);
		return voisins;
	}

}
