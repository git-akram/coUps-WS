package iaws.NBMR.service;

import java.util.List;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;

public interface DataService {

	/**
	 * Sauvegarde un utilisateur, si celui-ci est deja present, on le met à jour
	 * 
	 * @param utilisateur
	 */
	public void saveUtilisateur(Utilisateur utilisateur);
	
	/**
	 * Cherche un utilisateur par son adresse email
	 * 
	 * @param email
	 * @return l'utilisateur trouvé, ou null si aucun n'a été trouvé
	 */
	public Utilisateur findUtilisateurByEmail(String email);
	
	/**
	 * 
	 * @param utilisateur
	 * @param distance en mètres
	 * @return la liste des utilisateurs trouvés, y compris l'utilisateur courant
	 */
	public List<Utilisateur> findUtilisateurACoteDe(String email, int distance);
	
}
