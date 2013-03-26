package iaws.NBMR.service;

import iaws.NBMR.domaines.Utilisateur;

import java.util.List;

public interface RechercheVoisinsService {

	/**
	 * 
	 * @return la liste des utilisateur voisins, sans l'utilisateur lui même
	 */
	public List<Utilisateur> rechercherVoisin(Utilisateur utilisateur);
	
}
