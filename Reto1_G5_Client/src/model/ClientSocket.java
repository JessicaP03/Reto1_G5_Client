package model;

import exceptions.CredentialErrorException;
import exceptions.ServerErrorException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClientSocket es el socket de cliente. Dependiendo del método enviado por el
 * controlador, ejecuta 'getExecuteSignIn' o 'getExecuteSignUp'.
 *
 * @author Jessica.
 */
public class ClientSocket implements Signable {

    final private Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());

    private static final ResourceBundle RETO1 = ResourceBundle.getBundle("model.Config");
    private static final int PUERTO = Integer.parseInt(RETO1.getString("PORT"));
    private static final String HOST = ResourceBundle.getBundle("model.Config").getString("Ip");
    private MessageType messType;
    private Message message = null;

    /**
     * Conectarse con el 'Server', recibe la información de la ventana de 'SIGN
     * UP' y la ingresa a la base de datos
     *
     * @param user de usuario de tipo Usuario
     *
     * @return user, de usuario
     * @throws ServerErrorException esta excepción se lanza cuando hay un error
     * en el servidor
     * @throws UserNotFoundException esta excepción se lanza cuando no se
     * encuentra al usuario
     * @throws UserAlreadyExistsException esta excepción se lanza cuando el
     * usuario ya existe
     */
    @Override
    public User getExecuteSignUp(User user) throws UserNotFoundException, ServerErrorException, UserAlreadyExistsException {

        MessageType messType;
        //Escribir, enviar al servidor
        ObjectOutputStream oos = null;

        //Leer desde el servidor
        ObjectInputStream ois = null;

        try {

            //Creamos Socket del cliemte
            Socket skCliente = new Socket(HOST, PUERTO);
            LOGGER.info("El soket se ha creado en " + HOST + ":" + PUERTO);
            LOGGER.info("Se ha conectado con el servidor");

            oos = new ObjectOutputStream(skCliente.getOutputStream());
            message = new Message();

            //Encapsulamos los objetos user y el tipo de mensaje
            message.setUser(user);
            message.setMessageType(MessageType.SIGNUP_REQUEST);

            //Escribimos el objeto encapsulado
            oos.writeObject(message);

            //Recibimos el objeto encalsulado desde el servidor
            ois = new ObjectInputStream(skCliente.getInputStream());
            message = (Message) ois.readObject();
            user = message.getUser();

            //Declaramos una variable int (las enumeraciones nos devuelven en valores de int)
            int aaa = message.getMessageType().ordinal();
            oos.close();
            ois.close();
            skCliente.close();

            //Dependiendo del mensaje que va a recibir, lanza o escribe un mensaje nuevo
            LOGGER.info("Respuesta del servidor: " + message.getMessageType());
            switch (message.getMessageType()) {
                case OK_RESPONSE:
                    return user;
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistsException("El usuario ya existe.");
                case ERROR_RESPONSE:
                    throw new ServerErrorException("Ha occurrido un error en el servidor.");
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;

    }

    /**
     * Se conecta con el 'Server', recibe la información de la ventana de 'SIGN
     * IN' y comprueba que el usuario esté en la base de datos
     *
     * @param user de usuario de tipo Usuario
     * @return user, de usuario
     *
     * @throws ServerErrorException esta excepción se lanza cuando hay un error
     * en el servidor
     * @throws CredentialErrorException esta excepción se lanza cuando las
     * credenciales en el login son incorrectas
     *
     */
    @Override
    public User getExecuteSignIn(User user) throws ServerErrorException, CredentialErrorException {

        MessageType messType;
        //Escribir, enviar al servidor
        ObjectOutputStream oos = null;

        //Leer desde el servidor
        ObjectInputStream ois = null;

        try {
            //Enviamos el objeto encapsulado al servidor

            //Creamos Socket del cliemte
            Socket skCliente = new Socket(HOST, PUERTO);

            LOGGER.info("El soket se ha creado en " + HOST + ":" + PUERTO);
            LOGGER.info("Se ha conectado con el servidor");

            oos = new ObjectOutputStream(skCliente.getOutputStream());
            message = new Message();

            //Encapsulamos los objetos user y el tipo de mensaje
            message.setUser(user);
            message.setMessageType(MessageType.SIGNIN_REQUEST);

            //Escribimos el objeto encapsulado
            oos.writeObject(message);

            //Recibimos el objeto encapsulado del servidor
            ois = new ObjectInputStream(skCliente.getInputStream());
            message = (Message) ois.readObject();
            user = message.getUser();

            //Declaramos una variable int (las enumeraciones nos devuelven en valores de int)
            int bbb = message.getMessageType().ordinal();
            oos.close();
            ois.close();
            skCliente.close();

            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            LOGGER.info("Respuesta del servidor: " + message.getMessageType());
            switch (message.getMessageType()) {
                case OK_RESPONSE:
                    return user;
                case USER_NOT_FOUND_RESPONSE:
                    throw new CredentialErrorException("Error en las credenciales. Comprueba que introduce correctamente los datos.");
                case ERROR_RESPONSE:
                    throw new ServerErrorException("Ha ocurrido un error en el servidor");
            }

        } catch (Exception ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
