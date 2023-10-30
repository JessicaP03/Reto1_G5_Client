package model;

import exceptions.CredentialErrorException;
import exceptions.ServerErrorException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;

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
