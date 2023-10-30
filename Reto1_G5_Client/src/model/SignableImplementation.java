package model;

import grupo5.reto1.exceptions.CredentialErrorException;
import grupo5.reto1.exceptions.ServerErrorException;
import grupo5.reto1.exceptions.UserAlreadyExistsException;
import grupo5.reto1.exceptions.UserNotFoundException;
import grupo5.reto1.model.Signable;
import grupo5.reto1.model.User;

/**
 *
 * @author Jason.
 */
public class SignableImplementation implements Signable {

    @Override
    public User getExecuteSignUp(User user) throws UserAlreadyExistsException, UserNotFoundException, ServerErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getExecuteSignIn(User user) throws ServerErrorException, CredentialErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
