/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author poker
 */
public class LoginErrorException extends Exception {

    /**
     * Creates a new instance of <code>LoginErrorException</code> without detail
     * message.
     */
    public LoginErrorException() {
    }

    /**
     * Constructs an instance of <code>LoginErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginErrorException(String msg) {
        super(msg);
    }
}
