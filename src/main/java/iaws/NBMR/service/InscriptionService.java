package iaws.NBMR.service;

import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;

public interface InscriptionService {
	
	/**
	 * Inscrit l'utilisateur qui lui est passé en paramètre,
	 * après avoir vérifié son adresse e-mail: sa syntaxe et son unicité
	 * et après être aller chercher les coordonnées de l'utilisateur par OSM
	 * 
	 * @throws CustomException dans le cas où l'email est invalide ou déjà utilisé
	 * ou que la récupération des coordonnées par OSM a échouée
	 */
	public void inscrireUtilisateur(Utilisateur utilisateur) throws CustomException;

}
