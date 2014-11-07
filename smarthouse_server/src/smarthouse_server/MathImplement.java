/*
Class ini mengimplementasikan method-method di RemoteClassReference . 
Saat client memanggil function dari remote class, isi dari function itu didefinisikan disini
*/
package smarthouse_server;

/**
 *
 * @author Acchan48
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import smarthouse.interfaces.MathInterface;
public class MathImplement implements MathInterface{//implements RemoteClassReference

    /**
     * @param args the command line arguments
     */
    public MathImplement() throws RemoteException{};
    
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }

    @Override
    public int deduct(int a, int b) throws RemoteException {
        return a-b;
    }

    @Override
    public int multiply(int a, int b) throws RemoteException {
        return a*b;
    }

    @Override
    public int divide(int a, int b) throws RemoteException {
        return a/b;
    }
    
}
