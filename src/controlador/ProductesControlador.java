package controlador;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Imprimible;
import model.Persona;
import model.Producte;
import model.ProducteAbstract;
import model.ProductesDAO;
import model.paks;

public class ProductesControlador {

	private Stage ventana;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField idProducteInput;
    @FXML private TextField nombreProductoInput;
    @FXML private TextField inputStockProductoInput;
    @FXML private TextField descripcionProductoInput;
    @FXML private TextField precioCompraInput;
    @FXML private TextField precioVentaInput;
    @FXML private DatePicker iniciCatalegInput;
    @FXML private DatePicker fiCatalegInput;
    @FXML private ComboBox<String> tipusInput;
    @FXML private TextField idProductoListaPack;
    
    @FXML private TextField precioVentaPacksInput;
    @FXML private Label labelPrecioVenta;

    @FXML private TextArea listaProductos;
    @FXML private Label nombreProductoListaPack;
    @FXML private Button btnBuscarProductoPack;
    @FXML private Button btnEliminarProductoPack;
    @FXML private Button btnAddProductoPack;

    @FXML private TabPane tabs;
    @FXML private Tab tabProducte;
    @FXML private Tab tabPack;
	private Object listProducts;

    
    
/*    
    @FXML private TableView<?> productsTable;
    @FXML private TableColumn<?, ?> idProducteTable;
    @FXML private TableColumn<?, ?> nombreProducteTable;
*/

	public static void imprimirTodo(HashMap<?, ?> llista){ //polimorfisme i​nterfaces
		for (Object obj : llista.values()) {
			Imprimible imp = (Imprimible)obj;
			imp.imprimir(); 
		}
	}
	
	public void setConexionBD(Connection conexionBD) {	
		//Crear objecte DAO de persones
		Producte producte = new Producte(conexionBD);
	}


	@SuppressWarnings({ "unused", "unused" })
	@FXML 
	private void onKeyPressedId(KeyEvent e) throws IOException {
		
		if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB){
			
			Producte producte = new Producte();
			paks packs = new paks();

			ProducteAbstract Object = ProductesDAO.returnProduct(idProducteInput.getText());
			Object = ProductesDAO.findProduct(idProducteInput.getText());

			if(Object instanceof Producte) {
			}
			else {
				Object = ProductesDAO.findPacks(idProducteInput.getText());
			}

			
			nombreProductoInput.setText(Object.getNom());
			descripcionProductoInput.setText(Object.getDescripcio());
			iniciCatalegInput.setValue(Object.getDataInici());
			fiCatalegInput.setValue(Object.getDataFinal());
			
			
			
			if(Object instanceof Producte) {
				
				System.out.println("es un productoso");
				producte = (Producte)Object;
				producte.imprimir();

				precioCompraInput.setText( Double.toString( producte.getPreu_compra() ) );
				precioVentaInput.setText( Double.toString(producte.getPreu_venda()));
				inputStockProductoInput.setText( Integer.toString(producte.getStock()));

				tipusInput.setValue("productes");
				
			}
			else if(Object instanceof paks) {
				System.out.println("es un pack");
				packs = (paks)Object;
				packs.imprimir();
				
				tipusInput.setValue("packs");
				listaProductos.setText("");
				double precio_venta = 0;
				
				Producte product = new Producte();

				// se recogen la listsa de productos que etan dentro de un pack
				TreeSet<String> listProducts = ProductesDAO.findProductsPacks(idProducteInput.getText());

				for (String object : listProducts) {
					
					// se obtiene el precio de cada uno de los productos de un pack
					precio_venta += ProductesDAO.findProduct(object).getPreu_venda();
					
					// se añade la id de cada uno de los productos a la lista visual
					listaProductos.setText(listaProductos.getText()+(object + "\n"));			
				}
				
				// se añade el precio con descuento en un pack con la suma de sus productos
				packs.descompte(precio_venta);

				// se muestra el precio de venta con descuento
				labelPrecioVenta.setText(Double.toString( packs.getPreu_venda() ) + " €");

				for (Object productoAux : packs.getListaProductes()) {
					
				/*	
					Producte producto = (Producte)productoAux;
					System.out.println( "ID producto: " +((Producte) productoAux).getIdProducte()  );
					listaProductos.setText(listaProductos.getText()+((Producte) productoAux).getIdProducte() + "\n");
				*/	
					
				}

				
			}

			else {
				
				nombreProductoInput.setText("");
				descripcionProductoInput.setText("");
				precioCompraInput.setText( "" );
				precioVentaInput.setText("");
				inputStockProductoInput.setText( "");
				iniciCatalegInput.setValue(null);
				fiCatalegInput.setValue(null);
				listaProductos.setText("");
				tipusInput.getItems().addAll("productes", "packs");
			}
			
			

/*
			ObservableList<Producte> productData = FXCollections.observableArrayList(paks.Lis);

			idProducteTable.setCellValueFactory(new PropertyValueFactory<Producte, String>("id"));
			nombreProducteTable.setCellValueFactory(new PropertyValueFactory<Producte, String>("nom"));
			productsTable.setItems(productData);
*/
		}
		

		
	}
	
	/*
	 *  Guarda el producto en hashmap
	 */
    
	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides

			
	    	if(tipusInput.getValue().equals("productes")) {
	    		System.out.println("guardo producto");
				Producte producte = new Producte();
				if(isDatosValidos()){
				
	    		producte.setIdProducte(idProducteInput.getText());
				producte.setNom( nombreProductoInput.getText() );	
				producte.setDescripcio( descripcionProductoInput.getText() );	
				producte.setDataInici(iniciCatalegInput.getValue());
				producte.setDataFinal(fiCatalegInput.getValue());

		    	producte.setPreu_compra( Double.parseDouble(precioCompraInput.getText() ) );		
				producte.setPreu_venda( Double.parseDouble(precioVentaInput.getText() ) );		
				producte.setStock( Integer.parseInt(inputStockProductoInput.getText() ) );	
				ProductesDAO.afegirProducte(producte, idProducteInput.getText());
				ProductesDAO.save(producte);

				limpiarFormulario();
				producte.imprimir();
				}
	    	}
	    	else if(tipusInput.getValue().equals("packs")) {
	    		System.out.println("guardo pack");

				paks packs = new paks();
	    		packs.setIdProducte(idProducteInput.getText());
	    		packs.setNom( nombreProductoInput.getText() );	
	    		packs.setDescripcio( descripcionProductoInput.getText() );	
				packs.setDataInici(iniciCatalegInput.getValue());
				packs.setDataFinal(fiCatalegInput.getValue());
				
				// se guarda el pack
				ProductesDAO.save(packs);

				
				Producte product = new Producte();

				String ids [] =  listaProductos.getText().split("\n");
				
				for (int i = 0; i < ids.length; i++) {
					product = (Producte)ProductesDAO.TotsProductes.get(ids[i]);		
					System.out.println("linea 255 pc " + ids[i]);
				//	packs.addProducte(product);	
					
					// guardo los productos del pack
					ProductesDAO.saveProductsPacks(idProducteInput.getText(),ids[i] );
				
				}

				limpiarFormulario();
				packs.imprimir();
	    		
			
		}
	    	
		
	}
	

	

	
	
	/*
	 *  BUSCA UN PRODUCTO EN LISTA, SI EXISTE TE MUESTRA EL NOMBRE DEL PRODUCTO PARA PODER AÑADIRLO
	 */
	
	 @FXML void onActionidProductoListaPack(ActionEvent event) {
		Producte producte = new Producte();

		ProducteAbstract Object = ProductesDAO.returnProduct(idProductoListaPack.getText());

		Object = ProductesDAO.findProduct(idProductoListaPack.getText());
				
		if(Object instanceof Producte) {
			
			Producte producte2 = new Producte();
			producte2 = (Producte) Object;
			nombreProductoListaPack.setText(producte2.getNom());
			System.out.println("existe producto");
		}
		else if(Object instanceof paks) {
			System.out.println("No se puede añadir un pack en un pack");
			nombreProductoListaPack.setText("Es un pack");


		}
		else {
			nombreProductoListaPack.setText("No existe");
			System.out.println("no existe producto");
		}	

 
    }
	 
	/*
	 * RECOGE LA ID DEL PRODUCTO QUE PREVIAMENTE SE HA BUSCADO Y LO AÑADE EN EL TEXTAREA 
	 */
	 
	@FXML void onActionAddProductoPack(ActionEvent event) {
		
		
		ProducteAbstract Object = ProductesDAO.returnProduct(idProductoListaPack.getText());
		Object = ProductesDAO.findProduct(idProductoListaPack.getText());

		if(Object instanceof Producte) {
			Producte product = new Producte();
			product = (Producte) Object;
			String textId = listaProductos.getText();
			
			
			System.out.println();
			
			if (textId.contains(idProductoListaPack.getText())) {
				System.out.println("El " + product.getNom() + " ya esta introducido");
				nombreProductoListaPack.setText("El " + product.getNom() + " ya esta introducido");
			}
			else {
				textId += idProductoListaPack.getText();
				listaProductos.setText(textId + "\n");
				nombreProductoListaPack.setText("Se ha añadido " + product.getNom());
			}

		}
		else {
			nombreProductoListaPack.setText("No se puede añadir");

		}
    }
	

	/*
	 * ELIMINA UN PRODUCTO DE LISTA DE PRODUCTOS DE UN PACK
	 */
    @FXML void onActionBtnEliminarProductoPack(ActionEvent event) {
		paks packs = new paks();
    	Producte product = new Producte();

		product = (Producte) ProductesDAO.returnProduct(idProductoListaPack.getText());
		packs = (paks) ProductesDAO.returnProduct(idProducteInput.getText());

		packs.deleteProducte(product);

		packs.imprimir();
    	
    }

	/*
	 * BORRA UN PRODUCTO DEL HASHMAP
	 */
	
    @FXML
    void onActionEliminar(ActionEvent event) {
		
		if(isDatosValidos()){
			Producte producte = new Producte();
	//		if( ProductesDAO.borrarProducte(producte, idProducteInput.getText()) ){
			if( ProductesDAO.borrarProducte( idProducteInput.getText() )){

				limpiarFormulario();
				producte.imprimir();
			}
		}
		
		;
    }
    

    @FXML
    void onActionStock(ActionEvent event) {

    	try {
			ProductesDAO.leerStock();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    

    /*
     * GUARDA TODOS LOS PRODUCTOS EN UN ARCHIVO
     */
    @FXML
    void onActionSortir(ActionEvent event) {
		imprimirTodo(ProductesDAO.TotsProductes);
		
		ProductesDAO.guardar();
    }

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}
    
    
    @FXML
    void keyPressidProducte(KeyEvent event) {

    }
    
    @FXML
    void actionComboBox(ActionEvent event) {
    	
    	
    	if(tipusInput.getValue().equals("productes")) {
    		tabs.getSelectionModel().select(tabPack);
    		tabPack.setDisable(true);
    		tabProducte.setDisable(false);
    		
    		
    	}
    	else if(tipusInput.getValue().equals("packs")) {

    		tabs.getSelectionModel().select(tabProducte);
    		tabPack.setDisable(false);
    		tabProducte.setDisable(true);
    	}
    }

    private boolean isDatosValidos() {
		String error = "";

		if (nombreProductoInput.getText() == null || nombreProductoInput.getText().length() == 0) {
			error += "Nom incorrecte\n"; 
		}
		if (descripcionProductoInput.getText() == null || descripcionProductoInput.getText().length() == 0) {
			error += "Cognoms incorrectes\n"; 
		}
		if (precioCompraInput.getText() == null || precioCompraInput.getText().length() == 0) {
			error += "valor precio compra incorrecto\n"; 
		}


		if (error.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(ventana);
			alert.setTitle("Camps incorrectes");
			alert.setHeaderText("Corregeix els camps incorrectes");
			alert.setContentText(error);

			alert.showAndWait();

			return false;
		}
	}
    
    
	private void limpiarFormulario(){
		idProducteInput.setText("");
		nombreProductoInput.setText("");
		descripcionProductoInput.setText("");
		precioCompraInput.setText("");
		precioVentaInput.setText("");
		inputStockProductoInput.setText("");
		iniciCatalegInput.setValue(null);
		fiCatalegInput.setValue(null);
	}

	/*
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		
		try {
			if (conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    */

    @FXML
    void initialize() {
		String contador = ProductesDAO.checkFile();
		tipusInput.getItems().addAll("productes", "packs");
		tabPack.setDisable(true);
		tabProducte.setDisable(false);
		tabs.getSelectionModel().select(tabPack);

        assert idProducteInput != null : "fx:id=\"idProducteInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert nombreProductoInput != null : "fx:id=\"nombreProductoInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert inputStockProductoInput != null : "fx:id=\"inputStockProductoInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert iniciCatalegInput != null : "fx:id=\"iniciCatalegInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert fiCatalegInput != null : "fx:id=\"fiCatalegInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert tipusInput != null : "fx:id=\"tipusInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert descripcionProductoInput != null : "fx:id=\"descripcionProductoInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert precioCompraInput != null : "fx:id=\"precioCompraInput\" was not injected: check your FXML file 'productesControlador.fxml'.";
        assert precioVentaInput != null : "fx:id=\"precioVentaInput\" was not injected: check your FXML file 'productesControlador.fxml'.";

    }
}
