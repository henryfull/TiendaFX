package model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class Persona implements Imprimible {

	private String idPersona;
	private String nom;
	private String dni;
	private Adreça adreça;
	private LinkedHashSet<String> telefons = new LinkedHashSet();

	
	public Persona() {
		super();
	}

	public Persona(String idPersona, String nom, String dni, List<String> telefon, Adreça adreça) {
		super();
		this.idPersona = idPersona;
		this.nom = nom;
		this.dni = dni;
		this.telefons = (LinkedHashSet) telefon;
		this.adreça = adreça;
	}
	
	

	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	public LinkedHashSet getTelefons() {
		return telefons;
	}

	public void setTelefons(LinkedHashSet telefons) {
		this.telefons = telefons;
	}
	
	public Adreça getAdreça() {
		return adreça;
	}

	public void setAdreça(Adreça adreça) {
		this.adreça = adreça;
	}
	
	public void addTelefon(String texto) {	
	
		
		if(!telefons.add(texto)) {
			System.out.println("no se ha añadido el telefono: " + texto);
		}
	}
	
	public void deleteTelefon(Persona telefon) {
		telefons.remove(telefon);
	}

	@SuppressWarnings("unchecked")
	public void imprimir() {
		
		System.out.print("\nId: " + this.getIdPersona() + "\nNom: " + this.getNom() +
				"\nDNI: " + this.getDni() + "\nAdreça: " );
		this.getAdreça().imprimir();
		
			
		for (String movil : telefons) {
			System.out.println("Telefon:" +  movil);
		
		}

		
	}

	
	
}
