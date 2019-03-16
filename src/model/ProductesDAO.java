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

import model.ProducteAbstract;
import model.paks;
import model.Producte;
import model.conexion;

public class ProductesDAO {

	private static Connection conexionBD;
	public static conexion conexion;

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

		/*
		 * try {
		 * 
		 * String sql = ""; Statement stmt = conexionBD.createStatement();
		 * 
		 * if (find(producte.getIdProducte()) == null){
		 * 
		 * sql =
		 * "INSERT INTO producte (idproductes, nom, dataInici, dataFinal, preu_venda, preu_compra, stock, idproveidors  ) VALUES ("
		 * +
		 * 
		 * producte.getIdProducte() + "," + producte.getNom() + "," +
		 * producte.getDataInici() + "," + producte.getDataFinal() + "," +
		 * 
		 * 
		 * ")";
		 * 
		 * 
		 * 
		 * } else{ sql = "UPDATE persones SET nom='" + persona.getNom() +
		 * "',apellidos='" + persona.getApellidos() + "',email='" + persona.getEmail() +
		 * "',telefon='" + persona.getTelefon() + "'" + " WHERE id=" + persona.getId();
		 * } int rows = stmt.executeUpdate(sql); if (rows == 1) return true; else return
		 * false;
		 * 
		 * } catch (SQLException e) { System.out.println(e.getMessage()); } return
		 * false;
		 */

	}

	public static void showAll() {
		conexion = new conexion();
		conexionBD = conexion.getConnection();

		try {
			Statement stmt = conexionBD.createStatement();
			ResultSet result = stmt.executeQuery("select * from producte");
			while (result.next()) {
				Producte p = new Producte();

				System.out.println(result.getString("idproductes") + " " + result.getString("nom"));

				// p.imprimir();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean save(Producte producte) {
		try {

			String sql = "";

			conexion = new conexion();
			conexionBD = conexion.getConnection();
			Statement stmt = conexionBD.createStatement();

			if (find(producte.getIdProducte()) == null) {
		
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

	public static Producte find(String id) {
		conexion = new conexion();
		conexionBD = conexion.getConnection();

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
				 p.setDataInici(result.getDate("dataInici"));
				 p.setPreu_compra(result.getInt("preu_compra"));
				 p.setPreu_venda(result.getInt("preu_venda"));
				 p.setStock(result.getInt("stock"));
				 

			}
		} catch (SQLException e) {
			System.out.println("error de busqueda: " + e.getMessage());
		}

		return p;
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
		
		/*
		if (TotsProductes.containsKey(keyTotsProductes)) {

			TotsProductes.remove(keyTotsProductes);
			return true;
		} else {
			return false;
		}
		*/
		try {
			String sql = "";

			conexion = new conexion();
			conexionBD = conexion.getConnection();
			Statement stmt = conexionBD.createStatement();
			

			if (find(keyTotsProductes) != null) {
				sql = "DELETE FROM producte	 WHERE idproductes=" + "'"+ keyTotsProductes +"'";
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
