/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_server;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author Windows 8
 */
public class Global {
    private static double[] lightIntensity,temperature;
    private static int[] door;
    public static int roomCount=5;
    public static int roomReady;
    public static int currentTime;
    public static int sequence;
    public static final Semaphore connectSem=new Semaphore(1, true);
    public static final Object mutex=new Object();
    public static Object mutexUpdate;
    public static ArrayList<Integer> availableRoom;
    public static boolean[] livenessStatus;
    public static double targetTemperature=20;
    public static void Initiate(){
        roomReady=0;
        lightIntensity=new double[roomCount];
        temperature=new double[roomCount];
        door=new int[roomCount];
        availableRoom=new ArrayList<Integer>();
        livenessStatus=new boolean[roomCount];
        for(int i=0;i<roomCount;i++){
            availableRoom.add(i);
            livenessStatus[i]=false;
        }
        
        mutexUpdate=new Object();
        sequence=0;
        
    }
    public static double LightIntensity(int idx){
        return lightIntensity[idx];
    }
    public static double Temperature(int idx){
        return temperature[idx];
    }
    public static void SetLightIntensity(int idx,double value){
        lightIntensity[idx]=value;
    }
    public static void SetTemperature(int idx,double celcius){
        temperature[idx]=celcius;
    }
    public static void SetDoor(int idx,String action){
        switch(action){
            case "OPEN":door[idx]=1;break;
            case "CLOSED":door[idx]=0;break;
            default:break;
        }
    }
    public synchronized static int GetAvailableRoom(boolean remove){
        if(availableRoom.isEmpty())
            return -1;
        int ID=availableRoom.get(0);
        return ID;
    }
    public synchronized static void RemoveAvailableRoom(int ID){
        availableRoom.remove(availableRoom.indexOf(ID));
    }
    
            
}
