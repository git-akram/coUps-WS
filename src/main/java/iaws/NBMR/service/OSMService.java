package iaws.NBMR.service;

import iaws.NBMR.domaines.Coordonnees;

public interface OSMService {

	public Coordonnees findCoordonneesPourAdresse(String adresse);
	
}
