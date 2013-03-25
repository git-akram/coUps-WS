package iaws.NBMR.domaines;


public class Utilisateur {
	String nom;
	String prenom;
	String email;
	String adresse;
	
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
}
