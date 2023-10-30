package test;

import controller.SignInController;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.Reto1_G5_Client;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

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

    @Override
    public void stop() {
        // System.exit(0);
    }

    @Test
    public void test1_initStage() {
        verifyThat("#txtEmail", isVisible());
        verifyThat("#txtEmail", isEnabled());

        verifyThat("#txtPasswd", isVisible());
        verifyThat("#txtPasswd", isEnabled());

        // verifyThat("#txtShowPasswd", isVisible());
        verifyThat("#txtShowPasswd", isEnabled());

        verifyThat("#btnIniciarSesion", isVisible());
        verifyThat("#btnIniciarSesion", isEnabled());

        verifyThat("#tbtnPasswd", isVisible());
        verifyThat("#tbtnPasswd", isEnabled());
    }

    @Test
    public void test2_OpenSignUp() {
        clickOn("#hlSignUp");
    }

    @Test
    public void test3_EmailNotFilled() {
        clickOn("#txtEmail");
        write("email@gmail.com");

    }

    @Test
    public void test4_PasswdNotFilled() {
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#btnIniciarSesion");
    }

    @Test
    public void test5_InvalidEmailFormat() {
        clickOn("#txtEmail");
        write("email");
        clickOn("#txtPasswd");
        write("contraseña");
        clickOn("#btnIniciarSesion");
    }

    @Test
    public void test6_InvalidPasswFormat() {
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtPasswd");
        write("abcd");
        clickOn("#btnIniciarSesion");
    }

    @Test
    public void test7_InvalidCredentials() {
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#btnIniciarSesion");
    }

    @Test
    public void test8_ValidCredentials() {
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#btnIniciarSesion");
    }

}
