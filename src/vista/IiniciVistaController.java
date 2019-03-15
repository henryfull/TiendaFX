package vista;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import model.ClientDAO;
import model.ProductesDAO;
import model.ProveidorsDAO;
import model.Adreça;
import model.Client;
import model.Imprimible;
import model.Producte;
import model.ProducteAbstract;
import model.Proveidors;
import model.paks;

public class IiniciVistaController implements Serializable {
//	Locale currentLocale = new Locale("en", "US");
//	Locale bLocale = new Locale.Builder().setLanguage("ca").setRegion("ES").build();

	static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
	static String countIdProduct = "0", countIdClient = "0", countIdPersona = "0", countIdProveidors = "0";
	static Locale localitzacio = Locale.getDefault(Category.FORMAT);
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("javatienda2.Texts", localitzacioDisplay);
	

	
	public static void main(String[] args) throws IOException, StockInsuficientException, ClassNotFoundException, model.StockInsuficientException {
		
		Logger logger = Logger.getLogger("Javatienda");
		
		FileHandler fh = new FileHandler("log.txt", true);
		fh.setFormatter( new SimpleFormatter());
		logger.addHandler(fh);
		logger.setLevel(Level.ALL);
	//	logger.info("inici del log");
		
		try {
			
			menu();
			
		}
		catch (RuntimeException ex) { 
			// TODO: handle exception
			
			logger.log(Level.SEVERE, "Problema greu", ex);
		}
		catch (StockInsuficientException e) {

			System.out.println(e.getMessage());
			logger.log(Level.SEVERE, e.getMessage());
			
		}
		
		catch (EOFException e) {
			System.out.println(e.getMessage());
		}
		
		catch (IOException ex) {
			// TODO: handle exception
			
			logger.log(Level.WARNING, "Posible problema", ex);
		}
		logger.fine("Fi del log");
		
		
	}
	
	


	public static void menu() throws IOException, StockInsuficientException, model.StockInsuficientException {
		countIdProduct = ProductesDAO.checkFile();
		

		int opcion = -1;
		while(opcion != 0) {
			System.out.println("-------\tMenu -------\n");
			System.out.println(""
					+ texts.getString("0000") + " \n" 	// Salir
					+ texts.getString("0001")+ " \n" 	//
					+ texts.getString("0002") + "\n" 
					+ texts.getString("0003") + "\n\n" 
					+  "--------------------- \n");
			opcion = returnNumber();
			switch(opcion){
				case 0:
					System.out.println("Good Bye");
					ProductesDAO.guardar();
					opcion= 0;
					break;
			
				case 1:
					System.out.println("Productes");
					menuPrincipal("productes");
					break;
				
				case 2:				
					System.out.println("Clients");
					menuPrincipal("clients");
					
					break;
					
				case 3:				
					System.out.println("Proveidors");
					menuPrincipal("proveidors");					
					break;
					
				default:
					System.out.println("Escribe una opcion valida");
					break;
			}
		}
	}
	
	
	
	public static void menuPrincipal(String tipus) throws IOException, StockInsuficientException, model.StockInsuficientException {
		int opcion = 0;
		while(opcion != 99) {
		
			System.out.println("-------\tMenu -------\n");
			System.out.println(""
					+ texts.getString("0000") + " \n" 	// Salir 
					+ "1.\t Afefir " + tipus +  "\n" 
					+ "2.\t Buscar " +  tipus + "\n" 
					+ "3.\t Modificar " + tipus + "\n" 
					+ "4.\t Esborrar " + tipus + "\n" 
					+ "5.\t Mostrar tots els " + tipus + "\n" 
					+ "6.\t Realizar pedido proveedor" + "\n" + "\n\n" 
					+  "--------------------- \n");
			
			System.out.println("Escribe el numero de la opcion deseada");
			
			opcion = returnNumber();
			
			switch(opcion){
			
				case 0:
					System.out.println("Good Bye");
					opcion= 99;
					break;
					
				case 1:
					if (tipus.equalsIgnoreCase("productes")) {opcion = subMenuProducte(); afegirTipusProducte(opcion, tipus); }
					else if (tipus.equalsIgnoreCase("clients")) { afegirPersona(); }
					else if (tipus.equalsIgnoreCase("proveidors")) {afegirProveidor(); }
					break;
				
				case 2:
					System.out.println("Buscar " + tipus);
					if (tipus.equalsIgnoreCase("productes")) { subMenuBusqueda();  }
					else if (tipus.equalsIgnoreCase("clients")) { buscarClient(); }
					else if (tipus.equalsIgnoreCase("proveidors")) {buscarProveidor(); }
					break;
					
				case 3:
					System.out.println("Modificar " + tipus);
					if (tipus.equalsIgnoreCase("productes")) {opcion = subMenuProducte(); modificarTipusProducte(opcion, tipus); }
					else if (tipus.equalsIgnoreCase("clients")) {ModificarPersona(); }
					else if (tipus.equalsIgnoreCase("proveidors")) {ModificarProveidor();}									
					break;
	
				case 4:
					System.out.println("Esborrar " + tipus);
					if (tipus.equalsIgnoreCase("productes")) {borrarProductes(); }
					else if (tipus.equalsIgnoreCase("clients")) {borrarClient(); }
					else if (tipus.equalsIgnoreCase("proveidors")) {borrarProveidor();}					
					break;
		
				case 5:
					System.out.println("Mostrar tots els " + tipus);
					if (tipus.equalsIgnoreCase("productes")) {imprimirTodo(ProductesDAO.TotsProductes);}
					else if (tipus.equalsIgnoreCase("clients")) {imprimirTodo(ClientDAO.TotesPersones); }
					else if (tipus.equalsIgnoreCase("proveidors")) {imprimirTodo(ProveidorsDAO.TotesPersones);}							
					break;							
				case 6:
					System.out.println("Mostrar tots els " + tipus);
					if (tipus.equalsIgnoreCase("productes")) {realizarPedido();}
					break;							
					
					
				default:
					System.out.println("Escribe una opcion valida");
					break;
			}
		}	
	}
	
	
	private static void realizarPedido() {

		DataOutputStream dos = null;
		
		try {
		
			System.out.println("Nombre del pedido a realizar");
			String nombrearchivo = devuelveTexto();
			
			
		dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nombrearchivo+ ".txt",true)));
			
			
//		dos = new DataOutputStream(new BufferWriter(new FileOutputStream("comanda_rebuda.txt")));

		
		String producte ="";
		
		do {
			System.out.println("id del producto");
			producte = devuelveTexto();
			System.out.println("Cantidad del producto");
			int cantidad = devuelveNumero();
			
			
			dos.writeUTF(producte);
			dos.writeInt(cantidad);
			
	
			
			
			
		}while(!producte.isEmpty());
		
		}
		catch (EOFException ex) {

            System.out.println("Fin de fichero  ");            

        } catch (IOException ex) {

            System.out.println("Error intentando leer del fichero 1  " + ex.getMessage());

        } finally {

            try {

            	dos.close();

            } catch (IOException ex3) {

                System.out.println("Mensaje error cierre fichero: " + ex3.getMessage());

            }

        }
		
	}



// METODOS PARA PRODUCTES Y PACKS
	
	
	public static int subMenuProducte() throws IOException {
		System.out.println("-------\tMenu Afegir-------\n");
		System.out.println(""
				+ texts.getString("0000") + " \n" 	// Salir 
				+ texts.getString("0001") + " \n" 	// Producto 
				+ "2.\t Pack \n" 
				+ "#################### \n");
		return returnNumber();
	}	
	
	
	public static void afegirTipusProducte(int opcio, String tipus) throws IOException {
		 if (opcio==1) {
			 System.out.println("Afefir " + tipus);
				Producte product1 = new Producte();
				afegirProducte(product1); 
			}
		 else  if (opcio==2)  { 
			System.out.println("Afefir producte al pack");
			paks producto1 = new paks();
			afegirPaks(producto1);
			producto1.MostrarProducte(producto1);						 
		 }		
	}
	
	
	public static void afegirProducte(Producte producte ) throws IOException {

		countIdProduct =  String.valueOf(Integer.parseInt(countIdProduct) + 1);
		System.out.println(countIdProduct);
		producte.setNom( texto());
		producte.setDescripcio(Descripcio());
		producte.setIdProducte(countIdProduct);
		producte.setPreu_compra(PreuCompra());
		producte.setPreu_venda(PreuVenda());
		producte.setStock(StockProducte());

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("Data de inici (dd/MM/yyyy): ");
		
		LocalDate data = LocalDate.parse(devuelveTexto(), dtf);
		System.out.println(data.format(dtf));
		
		producte.setDataInici(data);

		System.out.println("Data de Final (dd/MM/yyyy): ");
		
		data = LocalDate.parse(devuelveTexto(), dtf);
		System.out.println(data.format(dtf));	
		producte.setDataFinal(data);
		
		
		ProductesDAO.afegirProducte(producte, countIdProduct);
		
	}
	
	public static void subMenuBusqueda() throws IOException {
		System.out.println("-------\tMenu Afegir-------\n");
		System.out.println(""
				+ texts.getString("0000") + " \n" 	// Salir 
				+ "1.\t Buscar por ID \n" 
				+ "2.\t Buscar por Fecha Final \n" 
				+ "#################### \n");
		
		int opcio = returnNumber();
		 if (opcio==1) {
			 System.out.println("Introduce la Id del producto a buscar");
			 buscarProducteId();;
		 }
		 else  if (opcio==2)  { 
			 System.out.println("Introduce la fecha por la que buscar");
			 
			 buscarPorFecha();;
		 }	
		
		
	//	return returnNumber();
	}	
		

	
	/**
	 * funcion para buscar un producto
	 */
	public static void buscarProducteId() throws IOException {
		Producte producte = new Producte();
		String keyTotsProductes = devuelveTexto(); // me pide la key a buscar
		ProductesDAO.buscarProducteDao(producte, keyTotsProductes);
	}
	
	
	public static void buscarPorFecha() throws IOException {
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data;
		System.out.println("Data de inici (dd/MM/yyyy): ");
		
		String dataTemporal =  devuelveTexto();
				
		if(dataTemporal.length() == 0) {
			System.out.println(dataTemporal);
			data = LocalDate.now();
		}
		else {
			data = LocalDate.parse(dataTemporal, dtf);
		}
		

		
		
		
	//	System.out.println(data.format(dtf));
		
		
		
		
		ProductesDAO.i​mprimirDescatalogats(data);
		
		
	//	lista1 = productes.toString();:
	//	lsita2 =  pack.getProducte().toString();
		
		
	}
	


	

	/**
	 * Funcion para modificar un producte
	 * @param opcio
	 * @param tipus
	 * @throws StockInsuficientException 
	 * @throws model.StockInsuficientException 
	 */
	public static void modificarTipusProducte(int opcio, String tipus) throws IOException, StockInsuficientException, model.StockInsuficientException {
		
		 if (opcio==1) {
			 ModificarProductes("producte");
		 }
		 else  if (opcio==2)  { 
			 ModificarProductes("pack");
		 }		
	}
	
	
	
	/**
	 * Sirve para modificar cada uno de los parametros de un producto
	 * @throws StockInsuficientException 
	 * @throws model.StockInsuficientException 
	 */
	public static void ModificarProductes(String tipus) throws IOException, StockInsuficientException, model.StockInsuficientException {
		Producte producte = new Producte();
		paks packs = new paks();
		
		String opcio, text;

		System.out.println("Que " + tipus + " quieres modificar?");
		String keyTotsProductes = devuelveTexto(); // me pide la key a buscar
		
		if (tipus.equalsIgnoreCase("pack")) {
			packs = (paks) ProductesDAO.returnProduct(keyTotsProductes);
		
			text = "¿Quieres cambiar el Nombre del Pack?\n" + "S / N";
			opcio = SiAndNo(text);
			System.out.println(opcio);
			if (opcio.equalsIgnoreCase("s")) {
				packs.setNom(texto());
			}	
			
			text = "¿Quieres cambiar algun producto ?\n" + "S / N";
			opcio = SiAndNo(text);
			System.out.println(opcio);
			if (opcio.equalsIgnoreCase("s")) {
				int opcionModificar = miniMenuModificacionPacks();
				modificarProductosPack(packs, opcionModificar);				
			}	
			
			
			
		}
		else {
			
			producte = (Producte) ProductesDAO.returnProduct(keyTotsProductes);
			
				
				switch (menuModificarProducte()) {
				case 1:
					producte.setNom(texto());
					break;
				case 2:
					producte.setDescripcio(Descripcio());
					break;
				case 3:
					producte.setPreu_compra(PreuCompra());
					break;
				case 4:
					producte.setPreu_venda(PreuVenda());
					break;
				case 5:
					int opcionStock = tipoStock();
					
					if(opcionStock == 1) {
						producte.t​reureStock(devuelveNumero());

					}
					else {
					 try {
						leerStock();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					}					
					
					break;

					
				default:
					break;
				}
				
				
				
				ProductesDAO.ModificarProducte(producte, keyTotsProductes);
		
			
	
			
		}
		
	}
	
	
	public static int menuModificarProducte() {
		System.out.println("-------\tMenu Afegir-------\n");
		System.out.println(""
				+ texts.getString("0000") + " \n" 	// Salir 
				+ "1.\t Nombre \n" 
				+ "2.\t Descripcion \n" 
				+ "3.\t Precio compra \n" 				
				+ "4.\t Precio Venta \n" 				
				+ "5.\t Stock \n" 								
				+ "#################### \n");		
		return returnNumber();	
	}
	
	public static int tipoStock() {
		System.out.println("-------\tMenu Afegir-------\n");
		System.out.println(""
				+ texts.getString("0000") + " \n" 	// Salir 
				+ "1.\t Manual \n" 
				+ "2.\t Automatico \n" 
				+ "#################### \n");
		int valor = returnNumber();
		return valor;	
		
		
	}
	

	public static void leerStock() throws ClassNotFoundException {
		
		DataInputStream dis = null;
		
		try {
		
			System.out.println("Nombre del fichero del pedido");
			String nombrearchivo = devuelveTexto();

			
		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(nombrearchivo+ ".txt")));

		
		String producte = "";
		
		int stock =0;
		
		while(true) {
			
			producte = dis.readUTF();
			stock = dis.readInt();
			
			if (ProductesDAO.TotsProductes.get(producte) instanceof Producte) {
			
				Producte p = new Producte();
				p = (Producte) ProductesDAO.returnProduct(producte);
				
				p.imprimir();
				p.afegirStock(stock);
		//		System.out.println("Producte:" + producte + "Stock: " + stock);

				
			};
			
			
			
		}
		
		}
		catch (EOFException ex) {

            System.out.println("Fin de fichero  ");            

        } catch (IOException ex) {

            System.out.println("Error intentando leer del fichero 1  " + ex.getMessage());

        } finally {

            try {

            	dis.close();

            } catch (IOException ex3) {

                System.out.println("Mensaje error cierre fichero: " + ex3.getMessage());

            }

        }
		
		

		
		
	}
	
	
	
	
	public static void modificarProductosPack(paks product, int opcion) throws IOException {
		Producte producte = new Producte();

		if (opcion == 1) {
			System.out.println("¿Que producto quieres añadir al pack?");

			int keyTotsProductes = devuelveNumero(); // me pide la key a buscar
			producte = (Producte)ProductesDAO.TotsProductes.get(keyTotsProductes);		
					
			if (keyTotsProductes <= ProductesDAO.TotsProductes.size()) {product.addProducte(producte); }
			
			
		}
		else if (opcion == 2) {
			System.out.println("¿Que producto quieres Borrar al pack?");

	//		int keyTotsProductes = returnEntero(); // me pide la key a buscar
	//		producte = (Producte)ProductesDAO.TotsProductes.get(keyTotsProductes);		
					
	//		if (keyTotsProductes <= ProductesDAO.TotsProductes.size()) {
				
				
				
		//		product.deleteProducte(keyTotsProductes);
	//		}
			
			
			int posicion = devuelveNumero();
			
					
			product.deleteProducte(producte);

		}
		
		
		

	}
	
	public static int miniMenuModificacionPacks()throws IOException {
		
		System.out.println("-------\tMenu Modificar-------\n");
		System.out.println(""
				+ texts.getString("0000") + " \n" 	// Salir 
				+ "1.\t Añadir \n" 
				+ "2.\t Eliminar \n" 
				+ "#################### \n");
		int valor = returnNumber();
		return valor;	
		
		
		
	}
	
	
	
	
	/**
	 * Borra un producto de lista de productos
	 * @param producte
	 */
	public static void borrarProductes( ) throws IOException {
		Producte producte = new Producte();
		String keyTotsProductes = devuelveTexto(); // me pide la key a buscar
		ProductesDAO.borrarProducte(producte, keyTotsProductes);
		
		
	}
	

// METODO PARA AÑADIR UN PACK A PRODUCTOS	
	
	
	public static void afegirPaks(paks packs) throws IOException {
		countIdProduct =  Integer.toString(Integer.parseInt(countIdProduct) + 1);
		packs.setIdProducte(countIdProduct);
		packs.setNom(devuelveTexto());
		packs.setDescripcio(Descripcio());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("Data de inici (dd/MM/yyyy): ");
		
		LocalDate data = LocalDate.parse(devuelveTexto(), dtf);
		System.out.println(data.format(dtf));
		
		packs.setDataInici(data);

		System.out.println("Data de Final (dd/MM/yyyy): ");
		
		data = LocalDate.parse(devuelveTexto(), dtf);
		System.out.println(data.format(dtf));	
		packs.setDataFinal(data);		
		
		Producte product = new Producte();
		
		System.out.println("¿Cuantos productos quieres añadir?");
		
		int cantidadProductosPack = devuelveNumero();
		
		while(cantidadProductosPack > 0) {
			System.out.println("¿Que producto quieres añadir al pack?");

			String keyTotsProductes = devuelveTexto(); // me pide la key a buscar
			product = (Producte)ProductesDAO.TotsProductes.get(keyTotsProductes);		

			packs.addProducte(product);
				
			
			
			cantidadProductosPack--;
		}
		
		boolean cierto = true;
		for (ProducteAbstract producto : ProductesDAO.TotsProductes.values()) {
			
			if(producto instanceof paks) {
				
				if(packs.equals(packs,(paks)producto)) {
					System.out.println("El pack ya existe en vista");
					cierto = false;
					break;
				}
			}
			
		}
		
		if(cierto) {
			ProductesDAO.dadesProductePack(packs, countIdProduct);	

		}
		
	}
	
	
// METODO PARA LA DIRECCION DE PERSONAS	
	
	public static Adreça demanarAdreça() throws IOException {
		
		Adreça adreces = new Adreça();
	
		adreces.setPoblacio(direccion("Poblacion"));
		adreces.setDomicili(direccion("Domicilio"));
		adreces.setProvincia(direccion("Provincia"));
		adreces.setCp(direccion("Codigo postal"));
		return adreces;
		
	}
	
// METODOS PARA CLIENTES
	
	/**
	 * Sirve para añadir un cliente
	 */
	public static void afegirPersona() throws IOException {
		Client persona = new Client();
		countIdPersona =  Integer.toString(Integer.parseInt(countIdPersona) + 1);
		countIdClient =  Integer.toString(Integer.parseInt(countIdClient) + 1);
			
		
		persona.setIdPersona(countIdPersona);
		System.out.println(countIdClient);
		persona.setNom( texto());
		persona.setDni(DNI());
		persona.isEnviar_publicitat();
		persona.setAdreça(demanarAdreça());
		persona.setIdClient(persona.getIdPersona());
		
		
		System.out.println("¿Cuantos telefonos quieres añadir?");
		int cantidadProductosPack = devuelveNumero();
		
		while(cantidadProductosPack > 0) {
			System.out.println("¿Escribe el telefono que quieras añadir?");

			persona.addTelefon(texto());
			
			cantidadProductosPack--;
		}
		
		ClientDAO.afegirPersona(persona, countIdPersona);
		
	}
	
	/**
	 * Sirve para buscar un client
	 */
	public static void buscarClient() throws IOException {
		Client client = new Client();
		String keyTotsPersones = devuelveTexto(); // me pide la key a buscar
		ClientDAO.buscarClientDAO(client, keyTotsPersones);
	}
	
	public static void ModificarPersona() throws IOException {
		
		Client persona = new Client();
		Adreça adreça = new Adreça();
		String keyTotsProductes = devuelveTexto(); // me pide la key a buscar
		
		
		persona = (Client)ClientDAO.returnPersona(keyTotsProductes);
		
		String opcio, text;
			
		text = "¿Quieres cambiar el Nombre?\n" + "S / N";
		opcio = SiAndNo(text);
		System.out.println(opcio);
		if (opcio.equalsIgnoreCase("s")) {
			persona.setNom(texto());				
		}	
		
		text = "¿Quieres cambiar la DNI?\n" + "S / N";
		opcio = SiAndNo(text);
		System.out.println(opcio);
		if (opcio.equalsIgnoreCase("s")) {
			persona.setDni(DNI());
		}	
		
		text = "¿Quieres cambiar el Telefono?\n" + "S / N";
		opcio = SiAndNo(text);
		System.out.println(opcio);

		if (opcio.equalsIgnoreCase("s")) {
		//	persona.setTelefon();
		}			
		text = "¿Quieres cambiar la direccion ?\n" + "S / N";
		opcio = SiAndNo(text);
		if (opcio.equalsIgnoreCase("s")) {
			persona.setAdreça(adreça);
		}
		
		text = "¿Quieres cambiar el estado de la publicidad ?\n" + "S / N";
		opcio = SiAndNo(text);
		if (opcio.equalsIgnoreCase("s")) {
			persona.setEnviar_publicitat(devuelveBoleano());
		}	
		
		ClientDAO.ModificarClientDAO(persona, keyTotsProductes);

	}
	
	
	
	
	/**
	 * SE utiliza borrar un cleinte pidiendo su id
	 */
	public static void borrarClient() throws IOException {
		Client client = new Client();
		String keyTotsPersones = devuelveTexto(); // me pide la key a buscar
		ClientDAO.borrarPersonaDAO(client, keyTotsPersones);
	}
	
	

	// METODOS PARA PROVEIDORS
	
		/**
		 * Sirve para añadir un cliente
		 */
		public static void afegirProveidor() throws IOException {
			Proveidors persona = new Proveidors();
	
			countIdProveidors =  Integer.toString(Integer.parseInt(countIdProveidors) + 1);
			
			
			persona.setIdPersona(countIdProveidors);
			persona.setNom( texto());
			persona.setDni(DNI());
			persona.setDescompte_pronto_pago(returnNumberDecimal());;
			persona.setAdreça(demanarAdreça());
			ProveidorsDAO.afegirProveidor(persona, countIdProveidors);
			
		}
		
		/**
		 * Sirve para buscar un proveidor
		 */
		public static void buscarProveidor() throws IOException {
			Proveidors proveidor = new Proveidors();
			String keyTotsPersones = devuelveTexto(); // me pide la key a buscar
			ProveidorsDAO.buscarProveidorDAO(proveidor, keyTotsPersones);
		}
		
		public static void ModificarProveidor() throws IOException {
			Proveidors persona = new Proveidors();
			Adreça adreça = new Adreça();
			
			String keyTotsProductes = devuelveTexto(); // me pide la key a buscar
			persona = (Proveidors) ProveidorsDAO.returnProveidor(keyTotsProductes);
			
			String opcio, text;
				
				
				text = "¿Quieres cambiar el Nombre?\n" + "S / N";
				opcio = SiAndNo(text);
				System.out.println(opcio);
				if (opcio.equalsIgnoreCase("s")) {
					persona.setNom(texto());				
				}	
				
				text = "¿Quieres cambiar la DNI?\n" + "S / N";
				opcio = SiAndNo(text);
				System.out.println(opcio);
				if (opcio.equalsIgnoreCase("s")) {
					persona.setDni(DNI());
				}	
				
				text = "¿Quieres cambiar el Telefono?\n" + "S / N";
				opcio = SiAndNo(text);
				System.out.println(opcio);

				if (opcio.equalsIgnoreCase("s")) {
				//	persona.setTelefon();
				}			
				text = "¿Quieres cambiar la direccion ?\n" + "S / N";
				opcio = SiAndNo(text);
				if (opcio.equalsIgnoreCase("s")) {
					persona.setAdreça(adreça);
				}
				
				text = "¿Quieres cambiar el descuento del pago ?\n" + "S / N";
				opcio = SiAndNo(text);
				if (opcio.equalsIgnoreCase("s")) {
					persona.setDescompte_pronto_pago(returnNumberDecimal());
				}	
				
				ProveidorsDAO.ModificarProveidorDAO(persona, keyTotsProductes);

	
		}
		
		
		/**
		 * SE utiliza borrar un proveidor pidiendo su id
		 */
		public static void borrarProveidor() throws IOException {
			Proveidors proveidor = new Proveidors();
			String keyTotsPersones = devuelveTexto(); // me pide la key a buscar
			ProveidorsDAO.borrarProveidorDAO(proveidor, keyTotsPersones);
		}
	
		

		public static void cargarStock() throws IOException {
			
			
			
			
		}
		
		
		
		
		
// METODOS COMUNES O SEPARADOS
	
	
	
	public static int returnNumber() {
		int numero=0;
		try {	
			String temp = teclado.readLine();
			if (!temp.equalsIgnoreCase("")) { return Integer.parseInt(temp); }
		}
		catch (IOException errorDeFichero){
            System.out.println( "Ha habido problemas: " + errorDeFichero.getMessage() );	
		}
		return numero;
	}
	
	
	/**
	 * Te pide por pantalla si quieres o no modificar un producto y comprueba que pongas si o no
	 * @param text
	 * @return
	 */
	public static String SiAndNo(String text) throws IOException {
		String opcio = "";
		do {
			System.out.println(text);
			opcio = devuelveTexto();
		}while (!opcio.equalsIgnoreCase("s") && !opcio.equalsIgnoreCase("n") && !opcio.equalsIgnoreCase("")  );
		return opcio;
	}
	
	
	/**
	 * Pide por pantalla el precio de venta del producto
	 * @return
	 */
	public static double PreuVenda() {
		System.out.println("A cuanto lo vas a vender?");
		double valor = returnNumberDecimal();
		return valor;
	}
	
	/**
	 * Pide por pantalla el precio al que se va a comprar el producto
	 * @return
	 */
	public static double PreuCompra()  {
		System.out.println("A cuanto lo vas a comprar?");
		double valor = returnNumberDecimal();
		return valor;
	}
	
	/**
	 * Pide por pantalla el Stock que hay de ese producto
	 * @return
	 */
	public static int StockProducte() throws IOException{
		System.out.println("Que Stock?");
		int valor = devuelveNumero();
		return valor;
	}	
	
	/**
	 * Pide por pantalla el nombre del producto
	 * @return
	 * @throws IOException 
	 */
	public static String texto() throws IOException {
		String nom = "";
		System.out.println("Escribe el nombre");
		nom = devuelveTexto();
		return nom;	
	}
	
	public static String DNI() throws IOException {
		String nom = "";
		System.out.println("Escribe el DNI");
		nom = devuelveTexto();
		return nom;	
	}
	
	public static String direccion(String opcion) throws IOException {
		if (opcion.equalsIgnoreCase("poblacion")) { System.out.println("Escribe la " + opcion); }
		else if (opcion.equalsIgnoreCase("domicilio")) { System.out.println("Escribe el " + opcion); }
		else if (opcion.equalsIgnoreCase("provincia")) { System.out.println("Escribe la " + opcion); }
		else if (opcion.equalsIgnoreCase("codigo postal")) { System.out.println("Escribe el " + opcion); }
		String nom = "";
		nom = devuelveTexto();
		return nom;	
	}
	
	/**
	 * Pide por pantalla la descripcion del producto
	 * @return
	 * @throws IOException 
	 */
	public static String Descripcio() throws IOException {
		String nom = "";
		System.out.println("Escribe la descripcion");
		nom = devuelveTexto();
		return nom;
		
	}
	
	/**
	 * Lo que escribes por teclado te lo pasa a decimal
	 * @return
	 */
	public static double returnNumberDecimal() {
		double numero =0; String temp= "";
		try {	
			temp = teclado.readLine();
			if( !temp.equals("")) { numero = Double.parseDouble(temp);}
			else { numero = 0;}
		}
		catch (IOException errorDeFichero){
            System.out.println( "Ha habido problemas: " + errorDeFichero.getMessage() );	
		}
		return numero;
	}
		
	
	/**
	 * Lo que escribes por teclado te lo pasa a numero entero
	 * @return
	 * @throws IOException 
	 */
	public static int devuelveNumero() throws IOException  {
		int numero =0; String temp= "";
			temp = teclado.readLine();
			if( !temp.equals("")) { numero =(int)Integer.parseInt(temp);}
			else { numero = 0;}
	
		return numero;
	}
	
	public static Boolean devuelveBoleano() {		
		String palabra = ""; boolean tipo = true;
		try {	
			palabra = teclado.readLine();
			if(palabra.equalsIgnoreCase("si")) { tipo = true; }
			else { tipo = false; 	}	
		}
		catch (IOException errorDeFichero){
            System.out.println( "Ha habido problemas: " + errorDeFichero.getMessage() );
		}
		return tipo;
	}
	
	
	/**
	 * Lo que escribes por teclado de lo devuelve como String
	 * @return
	 * @throws IOException 
	 */
	public static String devuelveTexto () throws IOException {
		String palabra = "";
			palabra = teclado.readLine();

		return palabra;
	}	
	
	public static String contador(String count) {
		
		return  Integer.toString((int)Integer.parseInt(count) + 1);
	}
	
	
	public static void imprimirTodo(HashMap<?, ?> llista){ //polimorfisme i​nterfaces
		for (Object obj : llista.values()) {
			Imprimible imp = (Imprimible)obj;
			imp.imprimir(); 
		}
	}
	
	
	
	
	
	
}
