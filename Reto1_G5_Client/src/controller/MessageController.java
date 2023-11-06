package controller;

import static controller.SignUpController.LOGGER;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

/**
 * Este es el controlador para la ventana del mensaje
 *
 * @author Jessica.
 */
public class MessageController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Pane idPane3;

    private User user;

    private Stage thisStage;

    /**
     * Metodo para inicializar la ventana
     * @param root nodoraíz de la ventana
     * @param user objeto de tipo usuario
     */
    @FXML
    public void initStage(Parent root) {

        LOGGER.info("Initializing Message stage.");

        Scene scene = new Scene(root);
        //El campo de texto está deshabilitado
        lblBienvenida.setDisable(true);
        //El botón está habilitado
        btnCerrarSesion.setDisable(false);
        lblBienvenida.setVisible(true);
        btnCerrarSesion.setVisible(true);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("MESSAGE");

        stage.show();
        lblBienvenida.setText(user.getName() + " " + lblBienvenida.getText());

    }

    /**
     * Metodo para cerrar la ventana con confirmación.
     * @param event evento que sucede al pulsar el botón.
     */
     
    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        try {

            //El campo de texto está deshabilitado
            lblBienvenida.setDisable(true);

            //El botón está habilitado
            btnCerrarSesion.setDisable(false);
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
            ventanita.setHeaderText(null);
            ventanita.setTitle("Advertencia");
            ventanita.setContentText("¿Deseas Salir?");
            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = ventanita.showAndWait();
            //Si le da a OK el programa cesará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                //Si le da a cancelar la ventana emergente se cerrará pero la ventana principal se mantiene
                ventanita.close();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage() + ButtonType.OK).showAndWait();
        }
    }

     private void handleVolverButtonAction(Event event) {
        //Cuando el usuario pulse el botón de volver (btnVolver) saldrá un ventana con un mensaje de confirmación,
        //preguntando si está seguro de que quiere cerrar la aplicación.
        //Si se pulsa que sí, se cerrará la aplicación; ejecutándose la función platform.exit, acabando así la ejecución del programa.
        //En el caso de que no sea así, saldrá del método del botón (Volver).

        try {

            //El botón está habilitado
            btnCerrarSesion.setDisable(false);
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir

            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas volver?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
                Parent root = loader.load();
                SignInController signIn = loader.getController();
                signIn.setStage(thisStage);
                signIn.initStage(root);
                thisStage.close();

            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage() + ButtonType.OK).showAndWait();
        }
    }

    
    /**
     *
     * @param user objeto de tipo usuario
     */
    public void setUser(User user) {
        this.user = user;
    }

    void setStage(Stage stage) {
        this.thisStage = stage;
    }

}
