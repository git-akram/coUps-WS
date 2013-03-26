package iaws.NBMR.service;

import java.util.List;

import iaws.NBMR.domaines.Utilisateur;

public interface DataService {

	/**
	 * Sauvegarde un utilisateur
	 * 
	 * @param utilisateur
	 */
	public void saveUtilisateur(Utilisateur utilisateur);
	
	/**
	 * 
	 * @param utilisateur
	 * @param distance en mètres
	 * @return
	 */
	public List<Utilisateur> findUtilisateurACoteDe(Utilisateur utilisateur, int distance);
	
}
