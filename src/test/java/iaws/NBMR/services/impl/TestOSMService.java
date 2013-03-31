package iaws.NBMR.services.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.services.impl.OSMServiceImpl;


public class TestOSMService {
	@Test
	public void TestFindCoordonneesPourAdresse() throws Exception{
		try{
			Coordonnees coordonnees=OSMServiceImpl.getInstance().findCoordonneesPourAdresse("Rue du taur");
		}catch(CustomException e){
			fail("Exception levée: " + e.getCode() + ": " + e.getMessage());
		}
		try{
			Coordonnees coordonnees=OSMServiceImpl.getInstance().findCoordonneesPourAdresse("rhezhfh");
			fail("Adresse non trouve mais exception non levé");
		}catch(CustomException e){
	
		}
			
	}
}