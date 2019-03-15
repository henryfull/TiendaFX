package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import model.Persona;

public class ProveidorsDAO {
	
	
	
	static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
	
	public static HashMap<String, Persona> TotesPersones = new HashMap<String, Persona>();
	
	
	public static void afegirProveidor(Persona persona, String keyTotsPersones) {
		
		TotesPersones.put(keyTotsPersones, persona);
			 				
	}
	
	
	public static void buscarProveidorDAO(Persona persona, String keyTotsPersones) {
		
			MostrarCosas(TotesPersones.get(keyTotsPersones));

	}
	
	public static void ModificarProveidorDAO(Persona persona, String keyTotsProductes) {
					
		TotesPersones.put(keyTotsProductes, persona);
		MostrarCosas(TotesPersones.get(keyTotsProductes));
			
	}
	
	public static Persona returnProveidor(String keyTotsProductes) {
		return TotesPersones.get(keyTotsProductes);
	}
	
	
	public static void borrarProveidorDAO(Persona persona,String keyTotsPersones ) {
		TotesPersones.remove(keyTotsPersones);
	}
	
	


	
	public static void MostrarCosas(Persona persona) {
		
		persona.imprimir();
	}
	
	

}
