package iaws.NBMR.ws.contractfirst;

import org.junit.Test;
import iaws.NBMR.domaines.Coordonnees;
import iaws.NBMR.services.impl.OSMServiceImpl;


public class TestOSMService {
	@Test
	public void TestFindCoordonneesPourAdresse() throws Exception{
		
		Coordonnees coordonnees=OSMServiceImpl.getInstance().findCoordonneesPourAdresse("Rue Maurice Becanne");
		System.out.println("Lat : "+coordonnees.getLatitude());
		System.out.println("Lon : "+coordonnees.getLongitude());
	
	}
}