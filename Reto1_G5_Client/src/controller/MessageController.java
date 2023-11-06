package controller;

import static controller.SignUpController.LOGGER;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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

    private Stage stage;

    /**
     * Metodo para inicializar la ventana
     *
     * @param root nodoraíz de la ventana
     * @param user objeto de tipo usuario
     */
    @FXML
    public void initStage(Parent root) {
        LOGGER.info("Initializing Message stage.");
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        stage.setTitle("MESSAGE");

        //El campo de texto está deshabilitado
        lblBienvenida.setDisable(true);

        //El botón está habilitado
        btnCerrarSesion.setDisable(false);

        lblBienvenida.setVisible(true);
        btnCerrarSesion.setVisible(true);

        lblBienvenida.setText("Bienvenido " + user.getName() + " a la aplicacion.");

        stage.show();

    }

    /**
     * Metodo para cerrar la ventana con confirmación.
     *
     * @param event evento que sucede al pulsar el botón.
     */
    @FXML
    private void handleExitButtonAction(Event event) {
        try {
            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas salir?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                Platform.exit();
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
        this.stage = stage;
    }

}
