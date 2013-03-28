package iaws.NBMR.services.impl;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.domaines.Utilisateur;
import iaws.NBMR.exception.CustomException;

public class InscriptionServiceImpl implements iaws.NBMR.service.InscriptionService{

	public void inscrireUtilisateur(Utilisateur utilisateur) throws CustomException {
		
		// On va vérifier la validité de l'adresse e-mail
		try {
			(new InternetAddress(utilisateur.getEmail())).validate();
		} catch (AddressException e) {
			// Adresse email invalide
			throw new CustomException(110, "Adresse email déjà utilisée");
		}
		
		// On va chercher les coordonnées OSM de l'utilisateur
		Coordonnees coordonnees = 
				(new OSMServiceImpl()).findCoordonneesPourAdresse(utilisateur.getAdresse());
		
		// On met a jour l'utilisateur avec ses coordonnées
		utilisateur.setCoordonnees(coordonnees);
		
		// On enregistre notre nouvel utilisateur
		(new DataServiceImpl()).saveUtilisateur(utilisateur);
		
	}

}
