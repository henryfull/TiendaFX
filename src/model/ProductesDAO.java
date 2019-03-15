package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;

import model.ProducteAbstract;
import model.paks;

public class ProductesDAO {
	
	static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
	
	public static HashMap<String, ProducteAbstract> TotsProductes = new HashMap<String, ProducteAbstract>();
	
	
	
	/**
	 * Añade todos los valores a un producto
	 * @param producte
	 */
	public static void afegirProducte(ProducteAbstract producte, String keyTotsProductes) {
		
		
		if(TotsProductes.get(keyTotsProductes) != null) {			

			TotsProductes.put(producte.getIdProducte(), producte);

		}
		else {
			TotsProductes.put(keyTotsProductes, producte);	

		}
		

		
	}


	
	/**
	 * Esta funcion buscar un producto en la tienda en base a una id, esta funcion llama a otra 
	 * que es la pedira la key al usuario por teclado
	 * @param producte
	 */
	public static void buscarProducteDao(ProducteAbstract producte, String keyTotsProductes) {
		
			MostrarCosas(TotsProductes.get(keyTotsProductes));

	}
	
	public static ProducteAbstract returnProduct(String keyTotsProductes) {
		
		return TotsProductes.get(keyTotsProductes);
	}
	

	
	/**
	 * Esta funcion permite modificar cada uno de los parametros de un producto
	 */
	public static void ModificarProducte(ProducteAbstract producte, String keyTotsProductes) {
				

		TotsProductes.put(keyTotsProductes, producte);
		MostrarCosas(TotsProductes.get(keyTotsProductes));
		
	}
	
	
	/**
	 * Borra un producto de lista de productos
	 */
	public static boolean borrarProducte(ProducteAbstract producte,String keyTotsProductes ) {
		if(TotsProductes.containsKey(keyTotsProductes)) {

			TotsProductes.remove(keyTotsProductes);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Muestra todos los valores de un objeto
	 * @param producte
	 */
	
	public static void MostrarProducte(ProducteAbstract producte) {
		for (ProducteAbstract prod : TotsProductes.values()) { 
			prod.imprimir();
		}

	}
	
	public static void i​mprimirDescatalogats(LocalDate data) {
		
	
		for (ProducteAbstract prod : TotsProductes.values()) { 
		
			
			if(prod.getDataFinal().compareTo(data) > 0) {
				 
				System.out.println("fecha hash " + prod.getDataFinal() + " fecha puesta " + data);
				
				prod.imprimir();
				
			}
			
		
		
		
		}
		
		
		
		
	}
	
	
	
	
	public static void guardar() {
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("src/vista/lista.txt");
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);		
			
			oos.writeObject(TotsProductes);
			oos.close();	
			System.out.println("se ha guardado el fichero");
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public static void obrir() throws IOException {
		
		
		FileInputStream fos = new FileInputStream("src/vista/lista.txt");
		
		ObjectInputStream oos = new ObjectInputStream(fos);		
		
			try {
				TotsProductes = (HashMap<String, ProducteAbstract>) oos.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		oos.close();
		
	}
	
	public static String checkFile() {
		String contador = "";

		File p = new File("src/vista/lista.txt");
		
		if(p.exists()) {
			try {
				obrir();
				
				contador = keyBig();
				System.out.println("se ha cargdo el fichero");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			System.out.println("No existe el fichero");
			contador = "0";
		}

		return contador;
		
	}
	
	public static String keyBig() {
		String numero ="";
		for (ProducteAbstract prod : TotsProductes.values()) { 
			numero = prod.getIdProducte();
		}
		return numero;
	}

	
	public static void MostrarCosas(ProducteAbstract producte) {
		
		producte.imprimir();
	}
	
	
	/**
	 * Obetenemos los datos de un producto de tipo pack
	 * @param producte
	 * @param keyTotsProductes
	 */
	public static void dadesProductePack(paks producte, String keyTotsProductes ) {
	
		TotsProductes.put(keyTotsProductes, producte);
		
	}

	

	
	
}


