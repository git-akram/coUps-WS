package iaws.NBMR.services.impl;

import org.junit.Test;
import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.exception.CustomException;
import iaws.NBMR.services.impl.OSMServiceImpl;


public class TestOSMService {
	@Test
	public void TestFindCoordonneesPourAdresse() throws Exception{
		try{
			Coordonnees coordonnees=OSMServiceImpl.getInstance().findCoordonneesPourAdresse("rhezhfh");
			System.out.println("Lat : "+coordonnees.getLatitude());
			System.out.println("Lon : "+coordonnees.getLongitude());
		}catch(CustomException e){
			e.printStackTrace();
		}
			
	}
}