package fxTuote;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Ohjelmalla luodaan ostoslistaa haaparantaan.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 15.2.2019
 * @version 1.1 12.12.2020
 *
 */
public class TuoteMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TuoteGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final TuoteGUIController tuoteCtrl = (TuoteGUIController)ldr.getController();
			
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("tuote.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tuote");
			
			primaryStage.setOnCloseRequest((event) -> {
			    if ( !tuoteCtrl.voikoSulkea() ) event.consume();
			});
			
			primaryStage.show();
			


		   primaryStage.getIcons().add(new Image("/Kuvat/tuote.png"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
		
	}
}
