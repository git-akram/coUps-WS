package iaws.NBMR.service;

import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.exception.CustomException;

public interface OSMService {

	/**
	 * 
	 * @param adresse l'adresse postale dont il faut trouver les coordonnées
	 * @return un objet coordonnées
	 * @throws CustomException
	 */
	public Coordonnees findCoordonneesPourAdresse(String adresse) throws CustomException;
	
}
