package controller;

import exceptions.CredentialErrorException;
import exceptions.ServerErrorException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SignableSocket;
import model.User;

/**
 * Esta clase es el controlador encargado de que la ventna SignIn funcione.
 *
 * @author Jason.
 */
public class SignInController {

    @FXML
    private Label lblCuenta;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPasswd;
    @FXML
    private TextField txtShowPasswd;
    @FXML
    private ToggleButton tbtnPasswd;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private ImageView ivCorreo;
    @FXML
    private ImageView ivPasswd;
    @FXML
    private ImageView ivTbntPasswd;
    @FXML
    private ImageView ivPanel;
    @FXML
    private Hyperlink hlSignUp;

    private Stage thisStage;

    /**
     * Guarda el Stage que se ha creado en la clasee main.
     *
     * @param stage la ventana que se ha creado en el main.
     */
    public void setStage(Stage stage) {
        this.thisStage = stage;
    }

    /**
     * Instancia la ventana SignIn y pone define los parametros necesarios.
     *
     * @param root es el Parent del FXML SignIn
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        thisStage = new Stage();

        //La ventana, es una ventana modal.
        thisStage.setScene(scene);
        thisStage.initModality(Modality.APPLICATION_MODAL);

        //La ventana no es redimensionable.
        thisStage.setResizable(false);

        //Los TextField email (txtEmail) y contraseña (txtPasswd) están habilitados y serán visibles.
        txtEmail.setDisable(false);
        txtEmail.setVisible(true);
        txtPasswd.setDisable(false);
        txtPasswd.setVisible(true);

        //Habrá un TextField (txtShowPasswd) habilitado pero invisible, para mostrar la contraseña cuando se pulse el ToggleButton(tbtnPasswd)
        txtShowPasswd.setDisable(false);
        txtShowPasswd.setVisible(false);

        //El botón Inicio de Sesión (btnInicioSesion) está habilitado.
        btnIniciarSesion.setDisable(false);

        //Los ImageView del email (ivCorreo) y password (ivPasswd) son visibles.
        ivCorreo.setVisible(true);
        ivPasswd.setVisible(true);

        //El foco estará puesto en el campo de email (txtEmail).
        txtEmail.requestFocus();

        //El ToggleButton (tbtnPasswd) utilizado para hacer visible la contraseña, está visible y habilitado (pero no seleccionado).
        tbtnPasswd.setDisable(false);
        tbtnPasswd.setVisible(true);
        tbtnPasswd.setSelected(false);

        //El título de la ventana es “Sign In”.
        thisStage.setTitle("Sign In");

        //El HiperLink (hlSignUp) para moverse a la ventana de registro estará visible.
        hlSignUp.setVisible(true);

        //El Label de información (lblCuenta) estará visible.
        lblCuenta.setVisible(true);

        //La ImageView (ivPanel) estará visible.
        ivPanel.setVisible(true);

        //El botón por defecto sera el de iniciar sesión(btnIniciarSesion).
        btnIniciarSesion.setDefaultButton(true);
        thisStage.setOnCloseRequest(this::handleExitApplication);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleExitApplication(event);
            }
        });

        thisStage.show();
    }

    /**
     * Cuando se pulse en el hyperlink de crear cuenta abre la ventana signUp y
     * cierra esta.
     *
     * @param event
     */
    @FXML
    protected void handleSignUpHyperlinkAction(ActionEvent event) {
        try {
            //Al pulsar sobre el HiperLink nos redirigirá a la ventana de SignUp.

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            Parent root = loader.load();
            SignUpController signUp = loader.getController();
            signUp.setStage(thisStage);
            signUp.initStage(root);
            thisStage.close();

        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cuando se pulse el ToggleButton tbtnPasswd se muestra la contraseña y se
     * cambia la imagen del boton y cuando se vuelve a puslar se oculta la
     * contraseña y la imagen se vuelve a cambiar.
     *
     * @param event
     */
    @FXML
    protected void handleMostrarContraseniaToggleButtonAction(ActionEvent event) {
        if (tbtnPasswd.isSelected()) {
            //Al seleccionar el botón, se hará visible el TextField (txtShowPasswd) con el texto escrito en el PasswordField(txtPasswd)
            //y se hará invisible el PasswordField(txtPasswd). También se cambiará la imagen (ivTbntPasswd) del ToggleButton(tbtnPasswd)

            txtShowPasswd.setText(txtPasswd.getText());
            txtPasswd.setVisible(false);
            txtShowPasswd.setVisible(true);

            Image nuevaImagen = new Image(getClass().getResource("/resources/img/no-ver.png").toExternalForm());
            ivTbntPasswd.setImage(nuevaImagen);
        } else {
            //Al dejar de seleccionar el botón, se hará invisible el TextField (txtShowPasswd) y se hará visible el PasswordField(txtPasswd).
            //También se cambiará la imagen (ivTbntPasswd) del ToggleButton(tbtnPasswd)

            txtPasswd.setVisible(true);
            txtShowPasswd.setVisible(false);
            ivTbntPasswd.setImage(new Image(getClass().getResource("/resources/img/ver.png").toExternalForm()));
        }
    }

    /**
     * Cuando se pulse el boton de iniciar sesion se validaran los campos y si
     * son correctos se iniciara sesion.
     *
     * @param event
     */
    @FXML
    protected void handleIniciarSesionButtonAction(ActionEvent event) {
        try {
            //Validar que los TextField email y contraseña no estén vacíos. Si está vacío alguno de los dos campos, saldrá una ventana informativa
            //con el error. Seguido, saldrá del método del botón.
            if (txtEmail.getText().isEmpty() || txtPasswd.getText().isEmpty()) {
                throw new Exception("Por favor rellene ambos campos");
            }

            //Validar que el máximo número de caracteres en el campo de email sea de 255 caracteres y tenga el patrón de email.Si no es correcto, saldrá
            //una ventana informativa con el error. Seguido, saldrá del método del botón.
            String emailRegex = "^(?=.{1,255}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if (txtEmail.getText().matches(emailRegex)) {
                txtEmail.setStyle("-fx-background-color: #1DFD33");
            } else {
                txtEmail.setStyle("-fx-background-color: #FB3131");
                throw new Exception("El formato del email no es correcto");
            }

            //Validar que el campo de la contraseña tenga un máximo de 255 caracteres y obligatoriamente lleve mayúsculas, minúsculas y números,
            //y que no tenga espacios en blanco. Si no es correcto, saldrá una ventana informativa con el error.  Seguido, saldrá del método del botón.
            String passwdRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,255}$";
            if (txtPasswd.getText().matches(passwdRegex)) {
                txtPasswd.setStyle("-fx-background-color: #1DFD33");
            } else {
                txtPasswd.setStyle("-fx-background-color: #FB3131");
                throw new Exception("La contraseña debe contener:\nUna mayuscula, una minuscula, y un numero");
            }

            //Una vez que los campos de email y contraseña son válidos. Se llama al método getExecuteSignIn de la interfaz (Signable) pasándole un objeto (User)
            //con los valores del nombre de usuario y la password:
            //Si se produce algún error saldrá una ventana informativa con la excepción LoginErrorException que se encontrará en las excepciones creadas en la
            //librería y limpiará esos campos y se cambiará el color del fondo a rojo.
            //Si no se produce error, se cambiara el fondo a color verde y le pasamos el objeto User a la siguiente ventana (Message) y cerramos la actual.
            User user = new User();
            user.setEmail(txtEmail.getText());
            user.setPasswd(txtPasswd.getText());

            user = new SignableSocket().getExecuteSignIn(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Message.fxml"));
            Parent root = loader.load();
            MessageController message = loader.getController();
            message.setStage(thisStage);
            message.initStage(root);
            message.setUser(user);
            thisStage.close();

        } catch (CredentialErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();

        } catch (ServerErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    /**
     * Cuando se pulse la X se preguntara si realmente se quiere cerrar la
     * aplicacion, si dice que si se cerrara la aplicacion.
     *
     * @param event
     */
    @FXML
    private void handleExitApplication(Event event
    ) {
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
}
