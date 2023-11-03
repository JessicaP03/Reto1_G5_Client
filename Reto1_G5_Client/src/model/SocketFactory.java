package model;

/**
 * Esta clase es la factoría del socket de cliente
 *
 * @author Jessica
 */
public class SocketFactory {

    /**
     * Con este método creamos el método getSocket, el cual llama al
     * ClientSocket y recoge los métodos que hay en este (dependiendo de cual
     * sea llamado en cada ventana correspondiente)
     *
     * @return Signable, se devuelve un nuevo socket de cliente.
     */
    public static ClientSocket getSocket() {
        return new ClientSocket();
    }

}
