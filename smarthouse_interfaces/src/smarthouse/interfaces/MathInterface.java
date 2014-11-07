/*
Ini interface yang bakal dipake sama server ataupun client.
Seperti yang dijelasin di readme.txt, di RMI DS ada yang namanya RemoteClassReference yang adalah list dari accessible class dan method-method dari server.
Client menggunakan ini buat tau class dan method class apa aja yang tersedia, bisa dipanggil oleh client.
Server menggunakan ini untuk diimplementasikan interfacenya.
 */
package smarthouse.interfaces;

/**
 *
 * @author Acchan48
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface MathInterface extends Remote {//mendemokan simple math interface
    public int add(int a,int b) throws RemoteException;
    public int deduct(int a,int b) throws RemoteException;
    public int multiply(int a,int b) throws RemoteException;
    public int divide(int a,int b) throws RemoteException;
}
