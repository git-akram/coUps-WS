package iaws.NBMR.domaines;


public class Utilisateur {
	private String nom;
	private String prenom;
	private String email;
	private String adresse;
	private Coordonnees coordonnees;
	
	public Utilisateur(){
		
	}
	
	public Utilisateur(String nom, String prenom, String email, String adresse){
		this.nom=nom;
		this.prenom=prenom;
		this.email=email;
		this.adresse=adresse;
	}

	public Utilisateur(Utilisateur u) {
		// TODO Auto-generated constructor stub
		this.nom=u.nom;
		this.prenom=u.prenom;
		this.email=u.email;
		this.adresse=u.adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	@Override
	public String toString() {
		return "Utilisateur [nom=" + nom + ", prenom=" + prenom + ", email="
				+ email + ", adresse=" + adresse + "]";
	}

	

}
