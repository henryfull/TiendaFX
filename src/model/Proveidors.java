package model;

public class Proveidors extends Persona {

	String idProveidors;
	double descompte_pronto_pago;
	
	public Proveidors() {
		
		this("",0);
	}
	
	public Proveidors(String idProveidors, double descompte_pronto_pago) {
		super();
		this.idProveidors = idProveidors;
		this.descompte_pronto_pago = descompte_pronto_pago;
	}
	
	public String getIdProveidors() {
		return idProveidors;
	}
	public void setIdProveidors(String idProveidors) {
		this.idProveidors = idProveidors;
	}
	public double getDescompte_pronto_pago() {
		return descompte_pronto_pago;
	}
	public void setDescompte_pronto_pago(double descompte_pronto_pago) {
		this.descompte_pronto_pago = descompte_pronto_pago;
	}
	
	
	public void imprimir() {
		
		super.imprimir();
		
		System.out.println("\nId Proveidors: " + this.getIdProveidors() + "\nDescompte pago: " + this.getDescompte_pronto_pago());

		
	}
	

	
}
