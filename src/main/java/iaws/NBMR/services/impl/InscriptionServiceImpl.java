package iaws.NBMR.services.impl;

import iaws.NBMR.domaines.Utilisateur;

public class InscriptionServiceImpl implements iaws.NBMR.service.InscriptionService{

	public String inscrireUtilisateur(Utilisateur u) {
		Utilisateur utilisateur=new Utilisateur(u);
		return "OK";
	}

}
