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
import java.sql.Date;
import java.sql.PreparedStatement;
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
			String sql = "select * from producteabstract";
			PreparedStatement stmt = conexionBD.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				System.out.println(result.getString("idproductes") + " " + result.getString("nom"));

				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// Guarda un producto en la base de datos - pack o producto
		public static boolean save(ProducteAbstract object) {
			String sql = "";
			
			try {
				sql = "INSERT INTO producte values (?,?,?,?,?,?,?,?)";
				PreparedStatement stmt = conexionBD.prepareStatement(sql);

				if(object instanceof Producte) {
					if (findProduct(object.getIdProducte()) == null) {

						setQuery( stmt, object, "insert");
						
					} 
					
					else {
	
						sql = "UPDATE producte SET nom=?, descripcio=?,dataInici=?, dataFinal=? ,preu_venda=?, preu_compra=?,stock=?, idproveidors=? WHERE idproductes=?";
						stmt = conexionBD.prepareStatement(sql);

						setQuery( stmt, object, "update");

					}
				
				}
				else if(object instanceof paks){

					if (find(object.getIdProducte()) == null) {
						sql = "INSERT INTO packs values (?,?,?,?,?,?)";
						stmt = conexionBD.prepareStatement(sql);
						setQuery( stmt, object, "insert");
					} 
					
					else {
		
						sql = "UPDATE packs SET nom=?, descripcio=?, dataInici=?, dataFinal=? ,preu_venda=?  WHERE idproductes=?";
						stmt = conexionBD.prepareStatement(sql);

						setQuery( stmt, object, "update");

					}
				}
				
				
				System.out.println(sql);

				System.out.println(stmt);

				int rows = stmt.executeUpdate();
				if (rows == 1)
					return true;
				else
					return false;

			} catch (SQLException e) {
				System.out.println("error insert o update" + e.getMessage());
			}
			return false;
		}
		
		// Castea el tipo de producto que le esta llegando
		public static Object returnObject(Object object) {

			Producte producte = new Producte();
			paks packs = new paks();
			
			if(object instanceof Producte) {
				return producte = (Producte)object;
			}
			else {
				return packs = (paks)object;
			}
	
		}
		
		// Prepara la consulta en funcion de si es packs o productos y si hace un insert o un update
		public static PreparedStatement setQuery(PreparedStatement stmt, Object object, String tipo) {
			
			Object producte = returnObject(object);
			int i = 1;

			try {
				
				if(tipo == "insert") stmt.setString(i++, ((ProducteAbstract) object).getIdProducte());
				
				stmt.setString(i++, ((ProducteAbstract) producte).getNom());
				stmt.setString(i++, ((ProducteAbstract) producte).getDescripcio());
				stmt.setDate(i++, Date.valueOf(((ProducteAbstract) producte).getDataInici()) );
				stmt.setDate(i++, Date.valueOf(((ProducteAbstract) producte).getDataFinal()) );
				if(object instanceof Producte) {
					stmt.setDouble(i++, ((Producte) producte).getPreu_venda());
					stmt.setDouble(i++, ((Producte) producte).getPreu_compra());
					stmt.setInt(i++, ((Producte) producte).getStock());
					stmt.setInt(i++, 4);

				}
				else {
					stmt.setDouble(i++, ((paks) producte).getPreu_venda());

				}
				if(tipo == "update") stmt.setString(i++, ((ProducteAbstract) object).getIdProducte());

	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return stmt;

		}
	
	
	// Guarda los productos de los packs en la base de datos - listaproductes
	public static boolean saveProductsPacks(String idpacks, String idproducte) {
		try {

			String sql = "INSERT INTO listaproductes VALUES (?,?)";

			PreparedStatement stmt = conexionBD.prepareStatement(sql);
			int i = 1;

			if (find(idpacks) != null) {
		
			///	sql = "INSERT INTO listaproductes (idpacks, idproducte ) VALUES  ( '"+idpacks+"', '"+idproducte+"'  )";
				
				stmt.setString(i++, idpacks);
				stmt.setString(i++, idproducte);

			} 
			else {
				
				sql = "UPDATE listaproductes SET idproducte= ? WHERE idpacks= ? ";
			//	sql = "UPDATE listaproductes SET idproducte='" + idproducte + "' WHERE idpacks=" + "'" +idpacks +"'";
				stmt = conexionBD.prepareStatement(sql);
				stmt.setString(i++, idproducte);
				stmt.setString(i++, idpacks);

			}
			
			System.out.println("linea 202 \n	 " + stmt);

			int rows = stmt.executeUpdate();
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
			String sql = "SELECT idproducte FROM listaproductes WHERE idpacks = ?";
			PreparedStatement stmt = conexionBD.prepareStatement(sql);
			
			stmt.setString(1, id);
			result = stmt.executeQuery();
			
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
			String sql = "SELECT * FROM producteabstract WHERE idproductes = ?";

			PreparedStatement stmt = conexionBD.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet result = stmt.executeQuery();
	
			
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
			
			String sql = "SELECT * FROM packs WHERE idproductes = ?";
			PreparedStatement stmt = conexionBD.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
								
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
			
			String sql = "SELECT * FROM producte WHERE idproductes = ?";
			PreparedStatement stmt = conexionBD.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet result = stmt.executeQuery();
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


			conexion = new conexion();
			conexionBD = conexion.getConnection();
			String sql = "DELETE FROM producteabstract WHERE idproductes=?";
			PreparedStatement stmt = conexionBD.prepareStatement(sql);

			if (find(keyTotsProductes) != null) {
	//			TotsProductes.remove(keyTotsProductes);

				stmt.setString(1, keyTotsProductes);

				
				int rows = stmt.executeUpdate();
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
