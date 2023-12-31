package test;

import controller.SignUpController;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

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

    /**
     * Test para inicializar
     */
    @Test
    @Ignore
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
    @Ignore
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
    @Ignore
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
     * Metodo para verificar si el email utiliza el patron correcto
     */
    @Ignore
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

    @Ignore
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

    /**
     * Metodo de test para comprobar que la contraseña tiene un patrón correcto,
     * si no lo tiene salta excepción.
     */
    @Ignore
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

    /**
     * Método de test para verificar que las dos contraseñas coinciden, en caso
     * de no coincidir salta mensaje de error.
     */
    @Ignore
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

    /**
     * Metodo de test para verificar que el numero de telefono tiene maximo 9
     * números, si no, saca mensaje de error.
     */
    @Ignore
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

    /**
     * Método de test para verificar que el telefono es númerico, si no lo es,
     * salta mensaje de error.
     */
    @Ignore
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

    /**
     * Método de test para verificar que el ZIP tiene 5 números maximo, en caso
     * de tener mas, salta mensaje de error.
     */
    @Ignore
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

    /**
     * Método de test para verificar que el ZIP es nuúmerico, en caso de que no
     * sea así, sale mensaje de error.
     */
    @Ignore
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

    /**
     * Método para crear aleatoriamente el nombre y el email aleatoriamente.
     *
     * @return cadena
     */
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

    /**
     * Método para crear un número aleatrorio que decide la longitud del nombre
     * y el email.
     *
     * @param minimo
     * @param maximo
     * @return
     */
    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    /**
     * Meétodo de test con validaciones correctas para registrar un usuario.
     *
     */
    @Ignore
    @Test

    public void tests12_SignUp() {
        String cadena = cadenaAleatoria();
        clickOn("#txtNombre");
        write(cadena);
        clickOn("#txtEmail");
        write(cadena + "@gmail.com");
        clickOn("#txtDireccion");
        write("Direccion");
        clickOn("#txtPasswd");
        write("Abcd1234");
        clickOn("#txtPasswd2");
        write("Abcd1234");
        clickOn("#txtTelefono");
        write("123456789");
        clickOn("#txtCodPostal");
        write("48950");

        clickOn("#btnRegistro");
        verifyThat("EL EMAIL: " + cadena.toLowerCase() + "@gmail.com SE HA REGISTRADO CORRECTAMENTE", isVisible());
    }

    /**
     * Meétodo de test con validaciones si el usuario ya existe.
     *
     */
    @Ignore
    @Test
    public void tests13_SignUp() {
        clickOn("#txtNombre");
        write("Jason");
        clickOn("#txtEmail");
        write("jason@gmail.com");
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
        verifyThat("El usuario con el email: jason@gmail.com ya existe", isVisible());

    }

}
