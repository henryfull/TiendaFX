package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BtnIniciController extends Application {
	
	private ResourceBundle texts;
	private Connection conexionBD;

    @FXML private Button btnProductes;
    @FXML private Button btnClients;
    @FXML private Button btnProveidors;
    @FXML private Button btnSortir;

    
	public BtnIniciController() {
		super();
		try{
			
			//Carregar el controlador per la BD PostgreSQL
			Class.forName("org.postgresql.Driver");

			//Establir la connexió amb la BD
			String urlBaseDades = "jdbc:postgresql://localhost/botigafx";
			String usuari = "postgres";
			String contrasenya = "pelocho";
			conexionBD = DriverManager.getConnection(urlBaseDades , usuari, contrasenya);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
    
    
	@Override
	public void start(Stage primaryStage) throws IOException {

		//Carrega el fitxer amb la interficie d'usuari inicial (Scene)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/IniciBtnVista.fxml"));
		
		//Carregar fitxer de textos multiidioma de la localització actual
		Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
		ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
		//fins aquí tot igual, només falta assignar el fitxer de recursos al formulari
		loader.setResources(texts);

		Scene fm_inici = new Scene(loader.load());
		
		//Li assigna la escena a la finestra inicial (primaryStage) i la mostra
		primaryStage.setScene(fm_inici);
		primaryStage.setTitle(texts.getString("title.productes"));
		primaryStage.show();
	}

	@FXML
	private void onAction(ActionEvent e) throws Exception {
		if(e.getSource() == btnProductes){
			//Carrega el fitxer amb la interficie d'usuari
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ProductesControlador.fxml"));
			AnchorPane pagina = (AnchorPane)loader.load();
			
			//Crea una nova finestra i l'obre 
			Stage stage = new Stage();
			Scene fm_personas = new Scene(pagina);
			stage.setTitle("Producte");
			stage.setScene(fm_personas);
			stage.show();
			
			//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mètodes d'aquesta classe
			ProductesControlador productesController = (ProductesControlador)loader.getController();
			productesController.setVentana(stage);
			productesController.setConexionBD(conexionBD);

			//Programem l'event que s'executará quan es tanqui la finestra
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
				//	productesControlador.sortir();
				}
			});  

		}else if(e.getSource() == btnSortir){
			Platform.exit();
		}

	}
    
    /*
	public void start(Stage primaryStage) throws IOException {

		//Carrega el fitxer amb la interficie d'usuari inicial (Scene)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/IniciBtnVista.fxml"));
		
		//Carregar fitxer de textos multiidioma de la localització actual
		Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
		ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
		//fins aquí tot igual, només falta assignar el fitxer de recursos al formulari
		loader.setResources(texts);

		Scene fm_inici = new Scene(loader.load());
		
		//Li assigna la escena a la finestra inicial (primaryStage) i la mostra
		primaryStage.setScene(fm_inici);
		primaryStage.setTitle(texts.getString("title.producte"));
		primaryStage.show();
	}

	 void onAction(ActionEvent e) throws Exception {
		if(e.getSource() == btnProductes){
			//Carrega el fitxer amb la interficie d'usuari
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/productesControlador.fxml"));
			AnchorPane pagina = (AnchorPane)loader.load();
			
			//Crea una nova finestra i l'obre 
			Stage stage = new Stage();
			Scene fm_personas = new Scene(pagina);
			stage.setTitle("Producte");
			stage.setScene(fm_personas);
			stage.show();
			
			//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mètodes d'aquesta classe
			productesControlador productesController = (productesControlador)loader.getController();
			productesController.setVentana(stage);
			
			//Programem l'event que s'executará quan es tanqui la finestra
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
				//	productesControlador.sortir();
				}
			});   

		}
		else if(e.getSource() == btnSortir){
			Platform.exit();
		}
	}
	
*/




}
