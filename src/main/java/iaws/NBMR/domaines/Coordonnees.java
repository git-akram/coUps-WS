package iaws.NBMR.domaines;

public class Coordonnees {

	private double latitude;
	private double longitude;
	
	public Coordonnees(){
		
	}
	
	public Coordonnees(double x, double y){
		this.latitude = x;
		this.longitude = y;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double x) {
		this.latitude = x;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double y) {
		this.longitude = y;
	}
	@Override
	public String toString() {
		return "Coordonnees [latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
	
	public int getDistanceEnMetreAvec(Coordonnees coordonnees){
		System.out.println("Distance between " + this + " and " + coordonnees);
		
		int toReturn = getDistanceBetween(this.getLatitude(), this.getLongitude(),
				this.getLatitude(),coordonnees.getLongitude());
		
		System.out.println("= " + toReturn);
		return toReturn;
	}
	
	/**
	 * Distance en metre entre 2 coordonnees
	 * http://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude-points
	 * @return distance en metre
	 */
	private int getDistanceBetween(double lat1, double lon1, double lat2,double lon2) {
		  int R = 6371; // Radius of the earth in km
		  double dLat = deg2rad(lat2-lat1);  // deg2rad below
		  double dLon = deg2rad(lon2-lon1); 
		  Double a = 
		    Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
		    Math.sin(dLon/2) * Math.sin(dLon/2)
		    ; 
		  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		  double d = R * c; // Distance in km
		  return (int)(d * 1000);
	}

	private double deg2rad(double deg) {
		return deg * (Math.PI/180);
	}
}
