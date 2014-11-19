/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_client;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smarthouse.interfaces.SmartHouseInterface;

/**
 *
 * @author Windows 8
 */
public class HeartBeatClientThread extends Thread{
    private int ID;
    private SmartHouseInterface SHI;
    private boolean running;
    public HeartBeatClientThread(int ID,SmartHouseInterface SHI){
        this.ID=ID;
        this.SHI=SHI;
        
    }
    public void run(){
        running=true;
        while(running){
            try {
                sleep(1000);
                SHI.SendHeartBeat(ID);
            } catch (InterruptedException ex) {
                Logger.getLogger(HeartBeatClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(HeartBeatClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public void stopRun(){
        running=false;
    }
}
