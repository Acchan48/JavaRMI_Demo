/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_server;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smarthouse.interfaces.SmartHouseInterface;
/**
 *
 * @author Windows 8
 */
public class SmartHouseImplement implements  SmartHouseInterface{
    public SmartHouseImplement() throws RemoteException{
        
    }
    
    
    @Override
    public void ReportLightIntensity(int id, double value) {
        Global.SetLightIntensity(id, value);
        
    }

    @Override
    public void ReportTemperature(int id, double celcius) {
        Global.SetTemperature(id, celcius);
        
    }

    @Override
    public void ReportDoorAction(int id, String action) {
        Global.SetDoor(id, action);
        ServerUI.SetTextField("DOOR", id, action);
    }

    @Override
    public int ReportReady(String type) {
        int ret=-1;
        System.out.println("Getting Lock");
        synchronized(Global.connectSem){
            
            try {
                Global.connectSem.acquire();
                System.out.println("LOCK OBTAINED");
                
                System.out.println("Setting Up");
                switch(type){
                    case "ALL":
                        int ID=Global.GetAvailableRoom(false);
                        Global.RemoveAvailableRoom(ID);
                        Global.livenessStatus[ID]=true;
                        System.out.println("LOCK NOTIFIED");
                        Global.connectSem.release();
                        ret=ID;
                        ServerUI.EnableMenuRoom(ID);
                        break;
                    default:Global.connectSem.release();break;
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SmartHouseImplement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ret;
    }

    @Override
    public double GetACTemperature(int id) throws RemoteException {
        double temperature=Global.Temperature(id);
        
        //PROCESS AC
        if(temperature>Global.targetTemperature)
            return temperature-((temperature-Global.targetTemperature)*2);
        else
            return temperature+((Global.targetTemperature-temperature)*2);
        
    }

    @Override
    public double GetLampIntensity(int id) throws RemoteException {
        double light=Global.LightIntensity(id);
        //PROCESS LIGHT
        return light;
    }

    @Override
    public void SendHeartBeat(int id) throws RemoteException {
        Global.livenessStatus[id]=true;
    }

    @Override
    public void ReportStop(int id) throws RemoteException {
        Global.livenessStatus[id]=false;
        ServerUI.DisableMenuRoom(id);
    }
    
}
