package model;

import exceptions.CredentialErrorException;
import exceptions.ServerErrorException;
import exceptions.UserAlreadyExistsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
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
     * @throws UserAlreadyExistsException esta excepción se lanza cuando el
     * usuario ya existe
     * @throws CredentialErrorException gestiona una excepción por si el email y
     * la contraseña no coinciden.
     */
    @Override
    public User getExecuteSignUp(User user) throws ServerErrorException, UserAlreadyExistsException, CredentialErrorException {
        return connectServer(user, MessageType.SIGNUP_REQUEST);
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
     * @throws UserAlreadyExistsException una excepción que gestiona si un
     * usuario ya existe en la base de datos.
     *
     */
    @Override
    public User getExecuteSignIn(User user) throws ServerErrorException, CredentialErrorException, UserAlreadyExistsException {
        return connectServer(user, MessageType.SIGNIN_REQUEST);
    }

    public User connectServer(User user, MessageType messageType) throws ServerErrorException, CredentialErrorException, UserAlreadyExistsException {
        //Escribir, enviar al servidor
        ObjectOutputStream oos = null;

        //Leer desde el servidor
        ObjectInputStream ois = null;

        try {
            //Enviamos el objeto encapsulado al servidor

            //Creamos Socket del cliemte
            Socket skCliente = new Socket(HOST, PUERTO);

            LOGGER.info("El socket se ha creado en " + HOST + ":" + PUERTO);
            LOGGER.info("Se ha conectado con el servidor");

            ois = new ObjectInputStream(skCliente.getInputStream());
            message = (Message) ois.readObject();

            if (message.getMessageType().equals(MessageType.OK_RESPONSE)) {
                oos = new ObjectOutputStream(skCliente.getOutputStream());
                message = new Message();

                //Encapsulamos los objetos user y el tipo de mensaje
                message.setUser(user);
                message.setMessageType(messageType);

                //Escribimos el objeto encapsulado
                oos.writeObject(message);

                //Recibimos el objeto encapsulado del servidor
                ois = new ObjectInputStream(skCliente.getInputStream());
                message = (Message) ois.readObject();
                user = message.getUser();
            }

            //Cerramos los flujos y el soket
            oos.close();
            ois.close();
            skCliente.close();

            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            LOGGER.info("Respuesta del servidor: " + message.getMessageType());
            switch (message.getMessageType()) {
                case OK_RESPONSE:
                    return user;
                case CREDENTIAL_ERROR:
                    throw new CredentialErrorException("El email: " + user.getEmail() + " y/o la contraseña: " + user.getPasswd() + " no son correctas.");
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistsException("El usuario con el email: + " + user.getEmail() + " ya existe");
                case ERROR_RESPONSE:
                    throw new ServerErrorException("Ha ocurrido un error en el servidor");
                case SERVER_ERROR:
                    throw new ServerErrorException("Ha ocurrido un error con el servidor..");

            }

        } catch (IOException | ClassNotFoundException ex) {
            throw new ServerErrorException("Ha ocurrido un error inesperado con el servidor");
        }
        return user;
    }
}
