package model;

public class Adreça {

	private String poblacio;
	private String provincia;
	private String cp;
	private String domicili;
	
	public Adreça() {
		this("","","","");
	}
	
	public Adreça(String poblacio, String provincia, String cp, String domicili) {
		this.poblacio = poblacio;
		this.provincia = provincia;
		this.cp = cp;
		this.domicili = domicili;
	}
	
	
	public String getPoblacio() {
		return poblacio;
	}
	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getDomicili() {
		return domicili;
	}
	public void setDomicili(String domicili) {
		this.domicili = domicili;
	}
	
	
	public void imprimir() {
		
		System.out.println("\nPoblacio: " + this.getPoblacio() + "\nProvincia: " + this.getProvincia() +
				"\nDomicili: " + this.getDomicili() + "\nCodigo Postal: " + this.getCp());
		
	}
	
	
}
