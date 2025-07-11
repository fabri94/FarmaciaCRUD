package farmaciacrud;

/**
 *
 * @author Fabri
 */
import controllers.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Fabri
 */
public class FarmaciaCRUD extends Application{

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/view.fxml"));
    
        Scene scene = new Scene(loader.load());
        
        ViewController controllerVista = loader.getController();
        
        stage.setScene(scene);
        
        stage.setOnCloseRequest(e ->{
            controllerVista.guardarArchivoCSV();
            controllerVista.guardarArchivoJSON();
        });   
        
        stage.show();
    }
    
}
