package iaws.NBMR.service;

import java.util.List;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;

public interface DataService {

	/**
	 * Sauvegarde un utilisateur
	 * 
	 * @param utilisateur
	 * @throws CustomException si l'email de l'utilisateur est déjà dans la base
	 */
	public void saveUtilisateur(Utilisateur utilisateur) throws CustomException;
	
	/**
	 * 
	 * @param utilisateur
	 * @param distance en mètres
	 * @return la liste des utilisateurs trouvés, y compris l'utilisateur courant
	 */
	public List<Utilisateur> findUtilisateurACoteDe(String email, int distance);
	
}
