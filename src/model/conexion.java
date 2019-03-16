package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

	private static Connection conexionBD;
	
	public conexion() {
			
		try {

			// Carregar el controlador per la BD PostgreSQL
			Class.forName("org.postgresql.Driver");

			// Establir la connexió amb la BD
			String urlBaseDades = "jdbc:postgresql://localhost:5432/botiga";
			String usuari = "postgres";
			String contrasenya = "pelocho";
			conexionBD = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);
			
		} catch (SQLException sqle) {
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLErrorCode: " + sqle.getErrorCode());
			sqle.printStackTrace();
		} catch (Exception e) {
			System.out.println("mensaje de error " + e.getMessage());
			  e.printStackTrace() ;
		
		}
	}
	
	public Connection getConnection() {
		return this.conexionBD;
	}
	

	
	
}
