package model;

import grupo5.reto1.exceptions.CredentialErrorException;
import grupo5.reto1.exceptions.ServerErrorException;
import grupo5.reto1.exceptions.UserAlreadyExistsException;
import grupo5.reto1.exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import grupo5.reto1.model.Encapsulator;
import grupo5.reto1.model.MessageType;
import grupo5.reto1.model.Signable;
import grupo5.reto1.model.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SignableSocket es el socket de cliente.
 * Dependiendo del método enviado por el controlador, ejecuta 'getExecuteSignIn' o 'getExecuteSignUp'.
 * @author Jessica.
 */
public class SignableSocket implements Signable{

     private static final ResourceBundle RETO1 = ResourceBundle.getBundle("grupo5.reto1.model.Config");
    private static final int PUERTO = Integer.parseInt(RETO1.getString("PORT"));
    private static final String HOST = ResourceBundle.getBundle("grupo5.reto1.model.Config").getString("Ip");
    MessageType messType;
    private Encapsulator encap = null;
    
    /**
     * Conectarse con el 'Server', recibe la información de la ventana de 'SIGN UP'
     * y la ingresa a la base de datos
     * @param user de usuario de tipo Usuario
     * 
     * @return user, de usuario
     * @throws UserAlreadyExitsException esta excepción se lanza cuando el usuario no se encuentra en nuestra base de datos
     * @throws ServerErrorException esta excepción se lanza cuando hay un error en el servidor
     * @throws UserNotFoundException esta excepción se lanza cuando no se encuentra al usuario
     */
    
    
    @Override
    public User getExecuteSignUp(User user) throws UserAlreadyExistsException, UserNotFoundException, ServerErrorException {
        
        MessageType messType;
        //Escribir, enviar al servidor
        ObjectOutputStream oos = null;
        
        //Leer desde el servidor
        ObjectInputStream ois = null;
        
        try {
            //Enviamos el objeto encapsulado al servidor 
            
            //Creamos Socket del cliemte
            Socket skCliente = new Socket(HOST, PUERTO);
            
            oos = new ObjectOutputStream(skCliente.getOutputStream());
            encap = new Encapsulator();
            
            //Encapsulamos los objetos user y el tipo de mensaje
            encap.setUser(user);
            encap.setMessage(MessageType.SIGNUP_REQUEST);
            
            //Escribimos el objeto encapsulado 
            oos.writeObject(encap);
            
            //Recibimos el objeto encalsulado desde el servidor
            ois = new ObjectInputStream(skCliente.getInputStream());
            encap = (Encapsulator) ois.readObject();
            user = encap.getUser();
            
            //Declaramos una variable int (las enumeraciones nos devuelven en valores de int)
            int aaa = encap.getMessage().ordinal();
            oos.close();
            ois.close();
            skCliente.close();
            
            //Dependiendo del mensaje que va a recibir, lanza o escribe un mensaje nuevo
             switch (encap.getMessage()) {
                case OK_RESPONSE:
                    return user;
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistsException("The user already exists");
                case ERROR_RESPONSE:
                    throw new ServerErrorException("An error in the server has ocurred");
             }
            
        } catch (IOException ex) {
             Logger.getLogger(SignableSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(SignableSocket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return user;
        
    }

    
    /**
     * Se conecta con el 'Server', recibe la información de la ventana de 'SIGN IN'
     * y comprueba que el usuario esté en la base de datos
     * @param user de usuario de tipo Usuario
     * @return user, de usuarioç
     * 
     * @throws ServerErrorException  esta excepción se lanza cuando hay un error en el servidor
     * @throws CredentialErrorException   esta excepción se lanza cuando las credenciales en el login son incorrectas
     * @throws   
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
            
            oos = new ObjectOutputStream(skCliente.getOutputStream());
            encap = new Encapsulator();
            
            //Encapsulamos los objetos user y el tipo de mensaje
            encap.setUser(user);
            encap.setMessage(MessageType.SIGNUP_REQUEST);
            
            //Escribimos el objeto encapsulado 
            oos.writeObject(encap);
            
             //Recibimos el objeto encapsulado del servidor
            ois = new ObjectInputStream(skCliente.getInputStream());
            encap = (Encapsulator) ois.readObject();
            user = encap.getUser();
            
            //Declaramos una variable int (las enumeraciones nos devuelven en valores de int)
            int bbb = encap.getMessage().ordinal();
            oos.close();
            ois.close();
            skCliente.close();
            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            switch (encap.getMessage()) {
                case OK_RESPONSE:
                    return user;
                case USER_NOT_FOUND_RESPONSE:
                    throw new CredentialErrorException("The user was not found");
                case ERROR_RESPONSE:
                    throw new ServerErrorException("An error in the server has ocurred");
                case PASSWORD_ERROR_RESPONSE:
                    throw new WrongPasswordException("The password is incorrect");
            }
            
         }catch (Exception ex)  {
             Logger.getLogger(SignableSocket.class.getName()).log(Level.SEVERE, null, ex);
         }
         return user;

    }


}
  