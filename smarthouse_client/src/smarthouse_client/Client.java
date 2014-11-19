/*
Class Main dari client, di class ini, kita memanggil object MathImplement milik server menggunakan entry dari RemoteClassReference atau Registry

 */
package smarthouse_client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import smarthouse.interfaces.SmartHouseInterface;
/**
 *
 * @author Acchan48
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static final Object mutex=new Object();
    public static void main(String[] args) {
        // TODO code application logic here
        ClientUI clientUI=new ClientUI(mutex);
        HeartBeatClientThread heartbeat;
        ProcessThread processThread;
        if(args.length<1){
            System.out.println("MUST ENTER ARGUMENT. ARGUMENT 1 : SERVER IP");
            System.exit(-1);
        }
        //Scanner in=new Scanner(System.in);
        
        
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }
        
        
        try{
            Registry registry=LocateRegistry.getRegistry(args[0],999);
            SmartHouseInterface SHI=(SmartHouseInterface)registry.lookup("SHIClass");
            System.out.println("INTERFACE OBTAINED "+args[0]);
            clientUI.setVisible(true);
            while(true){
                synchronized(mutex){
                    mutex.wait();
                    System.out.println("REPORTING TO SERVER");
                    int ID=SHI.ReportReady("ALL");
                    heartbeat=new HeartBeatClientThread(ID, SHI);
                    heartbeat.start();
                    System.out.println("ID OBTAINED : "+ID);
                    processThread=new ProcessThread(ID,SHI);
                    processThread.start();
                    mutex.wait();
                    System.out.println("STOPPING COMMUNICATION");
                    heartbeat.stopRun();
                    processThread.stopRun();
                    SHI.ReportStop(ID);
                }
            }
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
