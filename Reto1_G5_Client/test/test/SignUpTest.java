package test;

import controller.SignInController;
import controller.SignUpController;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import static javafx.scene.control.ButtonType.OK;
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
 * Test del sign up.
 *
 * @author Jessica e Ian
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            Parent root = loader.load();
            SignUpController signUp = loader.getController();
            signUp.setStage(stage);
            signUp.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(SingInTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void test1_initStage() {
        verifyThat("#txtNombre", isVisible());
        verifyThat("#txtNombre", isEnabled());

        verifyThat("#txtEmail", isVisible());
        verifyThat("#txtEmail", isEnabled());

        verifyThat("#txtDireccion", isVisible());
        verifyThat("#txtDireccion", isEnabled());

        verifyThat("#txtPasswd", isVisible());
        verifyThat("#txtPasswd", isEnabled());

        verifyThat("#txtPasswd2", isVisible());
        verifyThat("#txtPasswd2", isEnabled());

        verifyThat("#txtTelefono", isVisible());
        verifyThat("#txtTelefono", isEnabled());

        verifyThat("#txtCodPostal", isVisible());
        verifyThat("#txtCodPostal", isEnabled());

    }

    /**
     * Test de comprobación para que los campos esten informados, en caso de que
     * alguno no lo esté, saltará la excepcion.
     */
    @Test
    public void test2_FieldsFilled() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#btnRegistro");

        verifyThat("LOS CAMPOS NO ESTAN INFORMADOS", isVisible());
    }

    /**
     * Test de comprobación para que los campos tengan un maximo de 255
     * caracteres, en caso de que tenga mas, saltará la excepcion.
     */
    @Test
    public void test3_ValidateMaxCharacters() {

        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");

        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");
        clickOn("#btnRegistro");

        verifyThat("EL NUMERO DE CARACTERES MAXIMO ES DE 255", isVisible());
    }

    /**
     * Metodo para verificar si el email utiliza el patron correcto,
     */
    @Test
    public void test4_InvalidEmailFormat() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("NO HAS INTRODUCIDO UN EMAIL CON EL PATRON CORRECTO", isVisible());
    }

    @Test
    public void test5_InvalidPasswFormat() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("NO HAS INTRODUCIDO UNA CONTRASEÑA CON EL PATRON CORRECTO", isVisible());
    }

    @Test
    public void test6_InvalidPassw2Format() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("NO HAS INTRODUCIDO UNA CONTRASEÑA CON EL PATRON CORRECTO", isVisible());
    }

    @Test
    public void test7_InvalidPassw3Format() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*12345");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("LAS CONTRASEÑAS NO COINCIDEN", isVisible());

    }

    @Test
    public void test8_InvalidPhoneFormat() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("12345678998764332");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("EL TELEFONO TIENE QUE TENER 9 NUMEROS MAXIMO", isVisible());

    }

    @Test
    public void test9_InvalidPhone2Format() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("abcdefghi");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("EL TELEFONO TIENE QUE SER NUMERICO", isVisible());

    }

    @Test
    public void tests10_InvalidZIPFormat() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950666666");

        clickOn("#btnRegistro");
        verifyThat("EL CODIGO POSTAL TIENE QUE TENER 5 NUMEROS MAXIMO", isVisible());

    }

    @Test
    public void tests11_InvalidZIP2Format() {
        clickOn("#txtNombre");
        write("Nombre");
        clickOn("#txtEmail");
        write("email@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("aaaaa");

        clickOn("#btnRegistro");
        verifyThat("EL CODIGO POSTAL TIENE QUE SER NUMERICO", isVisible());

    }

    public static String cadenaAleatoria() {
        // El banco de caracteres
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < 8; x++) {
            int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }

    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    @Test
    public void tests12_SignUp() {
        String cadena = cadenaAleatoria();
        clickOn("#txtNombre");
        write("a" + cadena);
        clickOn("#txtEmail");
        write("a" + cadena + "@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd*1234");
        clickOn("#txtPasswd2");
        write("Abcd*1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("USUARIO REGISTRADO", isVisible());

    }

    @Test
    public void tests13_Close() {

        clickOn("#btnVolver");
        verifyThat("¿Seguro que deseas volver?", isVisible());
        clickOn("Aceptar");
        verifyThat("#fondoSignUp", isVisible());

    }

}
