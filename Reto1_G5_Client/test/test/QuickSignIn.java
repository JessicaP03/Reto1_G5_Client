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
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Jason.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuickSignIn extends ApplicationTest {

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
        clickOn("#txtEmail");
        write("Jason@gmail.com");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#btnIniciarSesion");
    }
}
