package iaws.NBMR.service;

import java.io.IOException;
import java.util.List;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;

public interface DataService {

	/**
	 * Sauvegarde un utilisateur, si celui-ci est deja present, on le met à jour
	 * 
	 * @param utilisateur
	 * @throws IOException 
	 */
	public void saveUtilisateur(Utilisateur utilisateur) throws IOException;
	
	/**
	 * Cherche un utilisateur par son adresse email
	 * 
	 * @param email
	 * @return l'utilisateur trouvé, ou null si aucun n'a été trouvé
	 * @throws IOException 
	 */
	public Utilisateur findUtilisateurByEmail(String email) throws IOException;
	
	/**
	 * 
	 * @param utilisateur
	 * @param distance en mètres
	 * @return la liste des utilisateurs trouvés, y compris l'utilisateur courant
	 * @throws IOException 
	 */
	public List<Utilisateur> findUtilisateurACoteDe(String email, int distance) throws IOException;
	
	
	/**
	 * Affiche l'etat des donnees
	 * @throws IOException 
	 */
	public void print() throws IOException;
}
