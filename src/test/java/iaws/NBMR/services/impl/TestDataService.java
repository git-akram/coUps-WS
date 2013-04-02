package iaws.NBMR.services.impl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.domaines.Utilisateur;

public class TestDataService {
	
	@Test
	public void saveUtilisateur() {
		Coordonnees coordonnees=new Coordonnees(43.88,1.89);
		Utilisateur utilisateur = new Utilisateur("mynom", "myprenom", "mymail1@univ-tlse3.fr", "rue du taur", coordonnees);
		try {
			DataServiceImpl.getInstance().saveUtilisateur(utilisateur);
		} catch (IOException e) {
			fail("Exception levée: " + e.hashCode() + ": " + e.getMessage());
		}
	}
	
	@Test
	public void findUtilisateurByEmail(){
		try {
			Utilisateur utilisateur=DataServiceImpl.getInstance().findUtilisateurByEmail("mymail@univ-tlse3.fr");
		} catch (ClientProtocolException e) {
			fail("Exception levée: " + e.hashCode() + ": " + e.getMessage());
		} catch (IOException e) {
			fail("Exception levée: " + e.hashCode() + ": " + e.getMessage());
		}
		
	}
	
	@Test
	public void findUtilisateurACoteDe() {
		try {
			List<Utilisateur> utilisateur=DataServiceImpl.getInstance().findUtilisateurACoteDe("myemail@ndd.com", 100);
		} catch (ClientProtocolException e) {
			fail("Exception levée: " + e.hashCode() + ": " + e.getMessage());
		} catch (IOException e) {
			fail("Exception levée: " + e.hashCode() + ": " + e.getMessage());
		}
	}
	
	@Test
	public void print() {
			try {
				DataServiceImpl.getInstance().print();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
}
