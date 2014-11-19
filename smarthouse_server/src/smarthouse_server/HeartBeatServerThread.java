/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Windows 8
 */
public class HeartBeatServerThread extends Thread{
    @Override
    public void run(){
        while(true){
            try {
                sleep(3000);
                for(int i=0;i<Global.roomCount;i++){
                    if(Global.availableRoom.indexOf(i)==-1){
                        if(!Global.livenessStatus[i]){
                            Global.availableRoom.add(i);
                            System.out.println("ROOM "+i+" is now available");
                        }
                        
                        
                        Global.livenessStatus[i]=false;
                        
                        
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HeartBeatServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
