package model;

import grupo5.reto1.model.Signable;

/**
 * Instantiate the interface methods that run on 'SignableSocket' to the Controller
 * @author Jessica.
 */
public class SignableFactory {
    
     /**
     * Con este método creamos el método getSocket, el cual llama al
     * SignableSocket y recoge los métodos que hay en este (dependiendo de cual
     * sea llamado en cada ventana correspondiente)
     * @return sign
     */

     public Signable getSocket() {
        Signable sign;
        sign = new SignableSocket();
        return sign;
    }
    
}
