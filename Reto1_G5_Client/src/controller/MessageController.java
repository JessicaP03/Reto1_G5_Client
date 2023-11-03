package controller;

import static controller.SignUpController.LOGGER;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 * This is de "Message" window.
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
     * Method to start the window
     *
     * @param root the root of the window
     * @param user object of User type
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
     * "exit" button method with confirmation
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

    /**
     *
     * @param user object of User type
     */
    public void setUser(User user) {
        this.user = user;
    }

    void setStage(Stage stage) {
        this.thisStage = stage;
    }

}
