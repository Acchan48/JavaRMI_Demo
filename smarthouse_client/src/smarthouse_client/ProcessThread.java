/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_client;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import smarthouse.interfaces.SmartHouseInterface;

/**
 *
 * @author Windows 8
 */
public class ProcessThread extends Thread{
    private SmartHouseInterface SHI;
    private int ID;
    private double light,temperature;
    private boolean door,running;
    public ProcessThread(int ID,SmartHouseInterface SHI){
        this.SHI=SHI;
        this.ID=ID;
    }
    @Override
    public void run(){
        running=true;
        Random rand=new Random();
        double lamp,AC,light,temperature;
        try {
            light=rand.nextDouble()*30;
            temperature=rand.nextDouble()*30;
            SHI.ReportLightIntensity(ID, light);
            SHI.ReportTemperature(ID, temperature);
            SHI.ReportDoorAction(ID, "CLOSED");
            System.out.println("RUNNING");
            while(running){
                lamp=SHI.GetLampIntensity(ID);
                AC=SHI.GetACTemperature(ID);
                
                //process AC temperature with current temperature in 1 sec
                //process lamp intensity with current light intensity
                if(AC>temperature){
                    temperature+=(AC/10);
                }
                else if(AC<temperature){
                    temperature-=(AC/10);
                }
                
                SHI.ReportLightIntensity(ID, light);
                SHI.ReportTemperature(ID, temperature);
                //System.out.println("NEW LIGHT: "+light);
                System.out.println("NEW TEMP : "+temperature);
                sleep(1000);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ProcessThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void stopRun(){
        running=false;
    }
}
