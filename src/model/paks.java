package model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.TreeSet;
import java.util.Locale.Category;

public final class paks extends ProducteAbstract implements Serializable {
	
	private TreeSet<Producte>  ListaProductes = new TreeSet<Producte>();
	private double preu_venda;
	
	public paks() {
		super();
	}
	
	public paks(int idproducte, String nom, String descripcio, double preu_venta) {
		super();
	}
	

	public void addProducte(Producte product) {
		
		
			if(!ListaProductes.add(product)) {
				System.out.println("no se ha añadido el producto con la id " + product.getIdProducte() );
			}
		
		

		descompte(product.getPreu_venda());
	}
	
	
	public void deleteProducte(Producte producte) {
		
		ListaProductes.remove(producte);
	}
	
	public void descompte(double precio) {
		double descompte = 10, total = this.getPreu_venda();
			
			total += Math.abs(precio-(precio * descompte /100));
			
			this.setPreu_venda(total);
		
	}
	
	
	public void MostrarProducte(paks producte) {
		super.imprimir();
		
		this.imprimir();
	}

	// SETTERS AND GETTERS
	
	public TreeSet getListaProductes() {
		return ListaProductes;
	}

	public void setListaProductes(TreeSet listaProductes) {
		ListaProductes = listaProductes;
	}
	

	
	public double getPreu_venda() {
		return preu_venda;
	}

	public void setPreu_venda(double preu_venda) {
		this.preu_venda = preu_venda;
	}
	

	
	public boolean equals(paks producte, paks producte2) {
		
		boolean iguales = false;
		
		if(producte.getListaProductes().equals(producte2.getListaProductes())) {
			iguales = true;
		}
		
		return iguales;
		
	}


	public void imprimir() {
		
		Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
		NumberFormat numberFormatter = NumberFormat.getInstance(localitzacioFormat);
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localitzacioFormat);
		
		
		super.imprimir();
		for (Producte object : this.ListaProductes) {
			
			object.imprimir();

		}
		System.out.println("Preu venda pack: " + currencyFormatter.format(this.getPreu_venda()));

	}
	
	
	
}
