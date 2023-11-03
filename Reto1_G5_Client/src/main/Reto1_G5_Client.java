
package main;

import controller.SignInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Clase principal para inicializar la aplicación.
 * @author Jessica, Jason e Ian.
 */
public class Reto1_G5_Client extends Application {

    
    /**
     *  Este metodo inicializa la aplicación abriendo la ventana de SIGN IN
     * @param stage es el contenedor principal de la ventana.
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
        Parent root = loader.load();
        SignInController signIn = loader.getController();
        signIn.setStage(stage);
        signIn.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
}
