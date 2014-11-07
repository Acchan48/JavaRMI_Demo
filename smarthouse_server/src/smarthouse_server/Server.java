/*
Class Main dari server, di class ini, kita membuat dan membind local class yang akan dijadikan remote class.
Class yang akan kita share adalah MathImplement.

 */
package smarthouse_server;

/**
 *
 * @author Acchan48
 */
import java.io.IOException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import smarthouse.interfaces.MathInterface;

public class Server {
    public static void main(String[] argv){
        try{
            if(System.getSecurityManager()==null){
                System.setSecurityManager(new SecurityManager());
            }
            
            Registry registry=LocateRegistry.createRegistry(888);
            
            MathInterface MI=new MathImplement();
            MathInterface stubMI=(MathInterface)UnicastRemoteObject.exportObject(MI,888);
            
            
            registry.rebind("MathClass",stubMI);
            
            System.out.println("Smarthouse server is ready");
        }
        catch(Exception ex){
            System.out.println("Smarthouse server failed: ");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    /*
    Registry adalah apa yang gw sebut sebagai RemoteClassReference.
    Pertama kita buat SecurityManager() built in java untuk security dalam DS
    Kita buat registry di port 888 (nanti client akses getRegistry harus dari port 888)
    Kita buat class yang ingin di remote-kan, yaitu class MathImplement (lets call it MI)
    Kita buat stub class untuk class MI stubMI, stub class berguna untuk marshalling.
    kita register stubMI dengan tag MathClass ke registry, sekarang client bisa memanggil class MathImplement milik Server dengan mengambil MathInterface dari registry port 888 dan tag MathClass
    
    */
    
}
