package tests;

import javafx.stage.Stage;
import main.Reto1_G5_Client;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author Jason.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SingInTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        new Reto1_G5_Client();
    }

    @Override
    public void stop() {

    }

    @Test
    public void test1_btnAceptarIsEnabled() {
        clickOn("#txtEmail");
        write("Jason");
        verifyThat("#btn");
    }

    @Test
    public void test2_IncorrectSignIn() {

    }

    @Test
    public void test3_CorrectSignIn() {

    }

}
