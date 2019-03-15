package model;

import java.io.Serializable;
import java.sql.Connection;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Locale.Category;

public abstract class ProducteAbstract implements Imprimible, Serializable  {

	
	
	private String idproducte;
	private String nom;
	private String descripcio;
	private LocalDate dataInici;
	private LocalDate dataFinal;
	
	


	public ProducteAbstract() {
		
		this("","","",null,null);
	}
	
	
	public ProducteAbstract(String idproducte, String nom, String descripcio, LocalDate dataInici, LocalDate dataFinal) {
		this.idproducte = idproducte;
		this.nom = nom;
		this.descripcio = descripcio;
		this.dataInici = dataInici;
		this.dataFinal = dataFinal;
	}
	
	


	// Getters and Setters
	
	public String getIdProducte() {
		return idproducte;
	}
	public void setIdProducte(String countIdProduct) {
		this.idproducte = countIdProduct;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	public LocalDate getDataInici() {
		return dataInici;
	}

	public void setDataInici(LocalDate dataInici) {
		this.dataInici = dataInici;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}	
	
	
	public boolean comparar(Object uno, Object dos) {
		
		if (uno.equals(dos)) {
			
			return true;
			
		}
		else {
			return false;
		}
			
		
	}
	

	
	public void imprimir() {
		
		
		System.out.println(""
				+ "\nId: " 
				+ this.getIdProducte() 
				+ "\nNom: " 
				+ this.getNom() +
				"\nDescripcio: " 
				+ this.getDescripcio() +
				"\nData Inici: " 
				+ this.getDataInici() +
				"\nData Final: " 
				+ this.getDataFinal()
				
				);

		
	}
	
	
}
