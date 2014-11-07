/*
Class Main dari client, di class ini, kita memanggil object MathImplement milik server menggunakan entry dari RemoteClassReference atau Registry

 */
package smarthouse_client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIServerSocketFactory;
import javax.net.ServerSocketFactory;
import smarthouse.interfaces.MathInterface;

/**
 *
 * @author Acchan48
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args.length<1){
            System.out.println("MUST ENTER ARGUMENT. ARGUMENT 1 : SERVER IP");
            System.exit(-1);
        }
        System.out.println("CONNECTING TO "+args[0]);
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }
        
        
        try{
            
            Registry registry=LocateRegistry.getRegistry(args[0],888);
            MathInterface MI=(MathInterface)registry.lookup("MathClass");
            System.out.println("5 operator 7 :");
            System.out.println("Add "+MI.add(5, 7));
            System.out.println("Deduct "+MI.deduct(5, 7));
            System.out.println("Multiply "+MI.multiply(5, 7));
            System.out.println("Divide "+MI.divide(5, 7));
        }
        catch(Exception ex){
            System.out.println("Client error: ");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    /*
    GetRegistry di port 888 dan dari host args[0] (host server)
    Lookup class di registry dengan tag MathClass. 
    Interface=ClassYangImplementInterface is a valid assignment, karena sebuah class C yang implement Interface I pasti meng-implement semua fungsi yang didefinisikan di I.
    Panggil implementasi fungsi remote class di server.
    */
}
