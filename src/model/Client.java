package model;

public class Client extends Persona {
	
	String idClient;
	boolean enviar_publicitat;
	
	public Client() {
		
		this("",true);
	}
	
	public Client(String idClient, boolean enviar_publicitat) {
		super();
		this.idClient = idClient;
		this.enviar_publicitat = enviar_publicitat;
	}
	
	public String getIdClient() {
		return idClient;
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	public boolean isEnviar_publicitat() {
		return enviar_publicitat;
	}
	public void setEnviar_publicitat(boolean enviar_publicitat) {
		this.enviar_publicitat = enviar_publicitat;
	}

	
	public void imprimir() {
		
		super.imprimir();
		
		System.out.println("Id Client: " + this.getIdClient() + "\nEnviar Publicitat: " + this.isEnviar_publicitat());
	}
	

	
}
