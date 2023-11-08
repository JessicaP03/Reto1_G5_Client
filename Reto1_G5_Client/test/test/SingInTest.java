package test;

import controller.SignInController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;

/**
 *
 * @author Jason.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SingInTest extends ApplicationTest {

    /*
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Reto1_G5_Client.class);
    }
     */
    /**
     * Cada vez que se pruebe un test se crea y muesta una ventana de SignIn.
     *
     * @param stage el stage.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            Parent root = loader.load();
            SignInController signIn = loader.getController();
            signIn.setStage(stage);
            signIn.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(SingInTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cuando se termine el metodo no se hace nada.
     */
    @Override
    public void stop() {

    }

    /**
     * Comprueba si los campos son visibles y si estan habilitados.
     */
    @Test
    public void test1_initStage() {
        verifyThat("#txtEmail", isVisible());
        verifyThat("#txtEmail", isEnabled());

        verifyThat("#txtPasswd", isVisible());
        verifyThat("#txtPasswd", isEnabled());

        verifyThat("#txtShowPasswd", isInvisible());
        verifyThat("#txtShowPasswd", isEnabled());

        verifyThat("#btnIniciarSesion", isVisible());
        verifyThat("#btnIniciarSesion", isEnabled());

        verifyThat("#tbtnPasswd", isVisible());
        verifyThat("#tbtnPasswd", isEnabled());
    }

    /**
     * Comprueba si se puede abrir la ventana de SignUp.
     */
    @Test
    public void test2_OpenSignUp() {
        clickOn("#hlSignUp");
        verifyThat("#fondoSignUp", isVisible());
    }

    /**
     *
     * Verifica si se produce un error cuando no se introduce una contraseña.
     */
    @Test
    public void test3_EmailNotFilled() {
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#btnIniciarSesion");
        verifyThat("Por favor rellene ambos campos", isVisible());
    }

    /**
     * Verifica si se produce un error cuando no se introduce una contraseña.
     */
    @Test
    public void test4_PasswdNotFilled() {
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#btnIniciarSesion");
        verifyThat("Por favor rellene ambos campos", isVisible());
    }

    /**
     *
     * Verifica si se produce un error cuando se introduce un email inválido.
     */
    @Test
    public void test5_InvalidEmailFormat() {
        clickOn("#txtEmail");
        write("email");
        clickOn("#txtPasswd");
        write("contraseña");
        clickOn("#btnIniciarSesion");
        verifyThat("El formato del email no es correcto", isVisible());
    }

    /**
     * Verifica si se produce un error cuando se introduce una contraseña
     * inválida.
     */
    @Test
    public void test6_InvalidPasswFormat() {
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtPasswd");
        write("abcd");
        clickOn("#btnIniciarSesion");
        verifyThat("La contraseña debe contener:\nUna mayuscula, una minuscula, y un numero", isVisible());
    }

    /**
     * Verifica si se produce un error cuando la email y la contraseña no
     * coinciden.
     */
    @Test
    public void test7_InvalidCredentials() {
        clickOn("#txtEmail");
        write("esteCorreoNoExiste@gmail.com");
        clickOn("#txtPasswd");
        write("estaContraseñaNoExiste*1234");
        clickOn("#btnIniciarSesion");
        verifyThat("El email: estecorreonoexiste@gmail.com y/o la contraseña: estaContraseñaNoExiste*1234 no son correctas.", isVisible());
    }

    /**
     * Verifica si se abre la ventana de Message cuando se introducen unos datos
     * validos.
     */
    @Test
    public void test8_ValidCredentials() {
        clickOn("#txtEmail");
        write("Jason@gmail.com");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#btnIniciarSesion");
        verifyThat("#fondoMessage", isVisible());
    }
}
