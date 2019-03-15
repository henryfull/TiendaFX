package model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Locale.Category;

public class Producte extends ProducteAbstract implements  Comparable<Producte>, Serializable, Imprimible {

	
	private double preu_venda;
	private double preu_compra;
	private int stock;

	public Producte() {
		
		this("","","",null,null,0,0,0);
	}
	
	public Producte(String string, String nom, String descripcio,LocalDate dataInici, LocalDate dataFinal, double preu_venda, double preu_compra, int stock) {
		super();
		this.preu_venda = preu_venda;
		this.preu_compra = preu_compra;
		this.stock = stock;
	}

	private Connection conexionBD;

	public Producte(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}


	public double getPreu_venda() {
		return preu_venda;
	}

	public void setPreu_venda(double preu_venda) {
		this.preu_venda = preu_venda;
	}

	public double getPreu_compra() {
		return preu_compra;
	}

	public void setPreu_compra(double preu_compra) {
		this.preu_compra = preu_compra;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	public void afegirStock(int quantitat) {
		
		stock += quantitat;
		
	}
	
	public void t​reureStock(int quantitat) throws StockInsuficientException {
		
		if(quantitat > stock) {
			
			StockInsuficientException error = new StockInsuficientException("La quantitat a a treure es major al stock dispobible");
			throw error;
		}
		else {
			stock = stock - quantitat;
		}
	}
	
	

	
	public void imprimir() {
		
		Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
		NumberFormat numberFormatter = NumberFormat.getInstance(localitzacioFormat);
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localitzacioFormat);
		
		
		super.imprimir();
		System.out.println(
				"Preu compra: " + currencyFormatter.format(this.getPreu_compra()) + "\nPreu venda: " + currencyFormatter.format(this.getPreu_venda()) 
				+ "\nStock: " + numberFormatter.format(this.getStock()));
	}

	@Override
	public int compareTo(Producte o) {
		
		// TODO Auto-generated method stub
		return Integer.compare(Integer.parseInt(this.getIdProducte()), Integer.parseInt(o.getIdProducte()));
	}
	
	

	
	
	
}
