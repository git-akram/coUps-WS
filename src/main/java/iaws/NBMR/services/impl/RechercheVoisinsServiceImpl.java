package iaws.NBMR.services.impl;

import java.util.ArrayList;
import java.util.List;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.service.RechercheVoisinsService;

public class RechercheVoisinsServiceImpl implements RechercheVoisinsService {

	public List<Utilisateur> rechercherVoisin(String email,int distance) {
		// TODO Auto-generated method stub
		List<Utilisateur> voisins=new ArrayList<Utilisateur>();
		voisins=DataServiceImpl.getInstance().findUtilisateurACoteDe(email, distance);
		return voisins;
	}

}
