package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import model.Persona;

public class ClientDAO {
	
	static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
	
	public static HashMap<String, Persona> TotesPersones = new HashMap<String, Persona>();
	

	public static void afegirPersona(Persona persona, String keyTotsPersones) {
		
		TotesPersones.put(keyTotsPersones, persona);
			 				
	}
	
	
	public static void buscarClientDAO(Persona persona, String keyTotsPersones) {
		
			MostrarCosas(TotesPersones.get(keyTotsPersones));
	
	}
	
	public static void ModificarClientDAO(Persona persona, String keyTotsProductes) {
					
		TotesPersones.put(keyTotsProductes, persona);
		MostrarCosas(TotesPersones.get(keyTotsProductes));
			
	}
	
	public static Persona returnPersona(String keyTotsProductes) {
		return TotesPersones.get(keyTotsProductes);
	}
	
	
	public static void borrarPersonaDAO(Persona persona,String keyTotsPersones ) {
		TotesPersones.remove(keyTotsPersones);
	}
	


	
	public static void MostrarCosas(Persona persona) {
		
		persona.imprimir();
	}
	
}
