/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse.interfaces;

/**
 *
 * @author Acchan48
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface SmartHouseInterface extends Remote{
    public int ReportReady(String type) throws RemoteException;
    public void ReportLightIntensity(int id,double value) throws RemoteException;
    public void ReportTemperature(int id,double celcius) throws RemoteException;
    public void ReportDoorAction(int id,String action) throws RemoteException;
    public double GetACTemperature(int id) throws RemoteException;
    public double GetLampIntensity(int id) throws RemoteException;
    public void SendHeartBeat(int id) throws RemoteException;
    public void ReportStop(int id) throws RemoteException;
}
