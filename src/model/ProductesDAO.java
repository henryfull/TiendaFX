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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeSet;

import model.ProducteAbstract;
import model.paks;
import model.Producte;
import model.conexion;

public class ProductesDAO {

	public static conexion conexion = new conexion();
	private static Connection conexionBD = conexion.getConnection();;


	// muestra por consola los datos de 
	public static void showAll() {
		conexion = new conexion();
		conexionBD = conexion.getConnection();

		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet result = stmt.executeQuery("select * from producteabstract");
			while (result.next()) {
				System.out.println(result.getString("idproductes") + " " + result.getString("nom"));

				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Guarda un producto en la base de datos - productes
	public static boolean saveProducte(Producte producte) {
		try {

			String sql = "";

			conexion = new conexion();
			conexionBD = conexion.getConnection();
			Statement stmt = conexionBD.createStatement();

			if (findProduct(producte.getIdProducte()) == null) {
		
				sql = "INSERT INTO producte (idproductes, nom,descripcio, dataInici, dataFinal, preu_venda, preu_compra, stock  ) VALUES \n" + 
						" ( '"+producte.getIdProducte()+"', '"+producte.getNom()+"' , '"+producte.getDescripcio()+"', '"+producte.getDataInici()+"', '"+producte.getDataFinal()+"', "+ producte.getPreu_venda()+ ", "+producte.getPreu_compra()+", "+producte.getStock()+"  )";

			} 
			
			else {
				
				producte.imprimir();
				
				sql = "UPDATE producte SET nom='" + producte.getNom() + "'" +
						",dataInici='" + producte.getDataInici() + "'" +
						",dataFinal='" + producte.getDataFinal() + "'" +
						",preu_venda=" + producte.getPreu_venda() + 
						",preu_compra=" + producte.getPreu_compra() + 
						",stock=" + producte.getStock() + 
						",idproveidors=" + 4 + 
						" WHERE idproductes=" + "'" +producte.getIdProducte() +"'";
				
			}
			System.out.println(sql);

			int rows = stmt.executeUpdate(sql);
			if (rows == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.out.println("error insert o update" + e.getMessage());
		}
		return false;
	}
	
	// Guarda un pack en la base de datos - packs
	public static boolean savePacks(paks producte) {
		try {

			String sql = "";


			Statement stmt = conexionBD.createStatement();

			if (find(producte.getIdProducte()) == null) {
		
				sql = "INSERT INTO packs (idproductes, nom,descripcio, dataInici, dataFinal, preu_venda  ) VALUES \n" + 
						" ( '"+producte.getIdProducte()+"', '"+producte.getNom()+"' , '"+producte.getDescripcio()+"', '"+producte.getDataInici()+"', '"+producte.getDataFinal()+"', "+ producte.getPreu_venda()+ " )";
			} 
			
			else {
				
				producte.imprimir();
				
				sql = "UPDATE packs SET nom='" + producte.getNom() + "'" +
						",dataInici='" + producte.getDataInici() + "'" +
						",dataFinal='" + producte.getDataFinal() + "'" +
						",preu_venda=" + producte.getPreu_venda() + 
						" WHERE idproductes=" + "'" +producte.getIdProducte() +"'";
				
			}
			System.out.println(sql);

			int rows = stmt.executeUpdate(sql);
			if (rows == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.out.println("error insert o update" + e.getMessage());
		}
		return false;
	}
	
	// Guarda los productos de los packs en la base de datos - listaproductes
	public static boolean saveProductsPacks(String idpacks, String idproducte) {
		try {

			String sql = "";

			Statement stmt = conexionBD.createStatement();
			
			if (find(idpacks) != null) {
		
				sql = "INSERT INTO listaproductes (idpacks, idproducte ) VALUES \n" + 
						" ( '"+idpacks+"', '"+idproducte+"'  )";
			} 
			else {
				sql = "UPDATE listaproductes SET idproducte='" + idproducte + "'" +
						" WHERE idpacks=" + "'" +idpacks +"'";
			}
			
			System.out.println("linea 202 \n	 " + sql);

			int rows = stmt.executeUpdate(sql);
			if (rows == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.out.println("error insert o update" + e.getMessage());
		}
		return false;
	}
	
	// busca un producto en la lista de productos de los packs
	public static TreeSet<String> findProductsPacks(String id) {
		

		ResultSet result = null;
		
		TreeSet<String> resultado = new TreeSet<String>();

		
		if (id == null || id.equals("")) { return null;}
		paks p = null;
		try {
			Statement stmt = conexionBD.createStatement();
			result = stmt.executeQuery("SELECT idproducte FROM listaproductes WHERE idpacks = " + "'"+ id +"'");
			while (result.next()) {
				resultado.add(result.getString("idproducte"));
			}
			
		} catch (SQLException e)	 {
			System.out.println("error de busqueda: " + e.getMessage());
		}

		return resultado;
	}
	
	// busca un producto en la base de datos - producteabstract
	// Devuelve la id
	public static Producte find(String id) {
		

		if (id == null || id.equals("")) {
			return null;
		}

		Producte p = null;
		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM producteabstract WHERE idproductes = " + "'"+ id +"'");
			if (result.next()) {
						
				p = new Producte();
				p.setIdProducte(result.getString("idproductes"));
				 

			}
		} catch (SQLException e) {
			System.out.println("error de busqueda: " + e.getMessage());
		}

		return p;
	}
	
	// Busca un pack en la base de datos - Devuelve un objeto del tipo pack
	public static paks findPacks(String id) {


		if (id == null || id.equals("")) {
			return null;
		}

		paks p = null;
		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM packs WHERE idproductes = " + "'"+ id +"'");
			if (result.next()) {
				
				System.out.println("buscando " + result.getString("idproductes"));
				
				p = new paks();
				 p.setIdProducte(result.getString("idproductes"));
				 p.setNom(result.getString("nom"));
				 p.setDescripcio(result.getString("descripcio"));
				 p.setDataInici( result.getObject("dataInici", LocalDate.class));
				 p.setDataFinal( result.getObject("dataFinal", LocalDate.class));
				 p.setPreu_venda(result.getInt("preu_venda"));
				 

			}
		} catch (SQLException e) {
			System.out.println("error de busqueda: " + e.getMessage());
		}

		return p;
	}
	
	// Busca un producto en la base de datos - Devuelve un objeto del tipo producto
	public static Producte findProduct(String id) {

		
		if (id == null || id.equals("")) {
			return null;
		}

		Producte p = null;
		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM producte WHERE idproductes = " + "'"+ id +"'");
			if (result.next()) {
				
				System.out.println("buscando " + result.getString("idproductes"));
				
				p = new Producte();
				 p.setIdProducte(result.getString("idproductes"));
				 p.setNom(result.getString("nom"));
				 p.setDescripcio(result.getString("descripcio"));
				 p.setDataInici( result.getObject("dataInici", LocalDate.class));
				 p.setDataFinal( result.getObject("dataFinal", LocalDate.class));
				 p.setPreu_compra(result.getInt("preu_compra"));
				 p.setPreu_venda(result.getInt("preu_venda"));
				 p.setStock(result.getInt("stock"));
				 

			}
		} catch (SQLException e) {
			System.out.println("error de busqueda: " + e.getMessage());
		}
//		p.imprimir();
		return p;
	}

	/*
	 * #####################################################################################
	 * Metodos que no trabajan con la base de datos y que conservo de lo que utilizaba antes
	 * #####################################################################################
	 */
	
	static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

	public static HashMap<String, ProducteAbstract> TotsProductes = new HashMap<String, ProducteAbstract>();

	
	/**
	 * Añade todos los valores a un producto
	 * 
	 * @param producte
	 */
	public static void afegirProducte(ProducteAbstract producte, String keyTotsProductes) {

		if (TotsProductes.get(keyTotsProductes) != null) {

			TotsProductes.put(keyTotsProductes, producte);

		} else {
			TotsProductes.put(keyTotsProductes, producte);

		}

	}

	
	/**
	 * Esta funcion buscar un producto en la tienda en base a una id, esta funcion
	 * llama a otra que es la pedira la key al usuario por teclado
	 * 
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
	public static boolean borrarProducte(String keyTotsProductes) {
		
		try {

			String sql = "";

			conexion = new conexion();
			conexionBD = conexion.getConnection();
			Statement stmt = conexionBD.createStatement();
			

			if (find(keyTotsProductes) != null) {
	//			TotsProductes.remove(keyTotsProductes);

				sql = "DELETE FROM producteabstract	WHERE idproductes=" + "'"+ keyTotsProductes +"'";
				int rows = stmt.executeUpdate(sql);
				if (rows == 1)
					return true;
				else
					return false;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
		
		
	}
	
	/**
	 * Muestra todos los valores de un objeto
	 * 
	 * @param producte
	 */

	public static void MostrarProducte(ProducteAbstract producte) {
		for (ProducteAbstract prod : TotsProductes.values()) {
			prod.imprimir();
		}
	}

	public static void i​mprimirDescatalogats(LocalDate data) {
		for (ProducteAbstract prod : TotsProductes.values()) {
			if (prod.getDataFinal().compareTo(data) > 0) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showAll();
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

		if (p.exists()) {
			try {
				obrir();

				contador = keyBig();
				System.out.println("se ha cargdo el fichero");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("No existe el fichero");
			contador = "0";
		}

		return contador;

	}

	public static String keyBig() {
		String numero = "";
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
	 * 
	 * @param producte
	 * @param keyTotsProductes
	 */
	public static void dadesProductePack(paks producte, String keyTotsProductes) {

		TotsProductes.put(keyTotsProductes, producte);

	}

}
