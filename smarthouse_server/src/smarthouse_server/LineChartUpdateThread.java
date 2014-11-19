/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_server;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Windows 8
 */
public class LineChartUpdateThread extends Thread{
    private LineChart lineChart;
    private Random rand;
    private int currentRoom;
    public LineChartUpdateThread(LineChart lineChart,int currentRoom){
        this.lineChart=lineChart;
        this.currentRoom=currentRoom;
        rand=new Random();
    }
    public void ChangeCurrentRoom(int currentRoom){
        this.currentRoom=currentRoom;
    }
    @Override
    public void run(){
        
        while(true){
            try {
                sleep(1000);
                //lineChart.GetChart().
                //System.out.println("Current Time: "+Global.currentTime);
                System.out.println("UPDATE");
                synchronized(Global.mutexUpdate){
                    IncrementData(currentRoom);
                    lineChart.RefreshChart();
                    Global.currentTime++;
                    Global.mutexUpdate.notify();
                    
                }
                
                //System.out.println("REFRESH");
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(LineChartUpdateThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void IncrementData(int ID){
        DefaultCategoryDataset tempDataset=lineChart.GetDataSet();
        int colCount=tempDataset.getColumnCount();
        if(colCount<20){
            
            tempDataset.addValue(Global.LightIntensity(ID), "Light", Integer.toString(colCount));
            tempDataset.addValue(Global.Temperature(ID), "Temperature", Integer.toString(colCount));
        }
        else{            
            
            for(int i=0;i<19;i++){
                tempDataset.setValue(tempDataset.getValue("Light", Integer.toString(i+1)), "Light", Integer.toString(i));
                tempDataset.setValue(tempDataset.getValue("Temperature", Integer.toString(i+1)), "Temperature", Integer.toString(i));
            }
            tempDataset.setValue(Global.LightIntensity(ID), "Light", "19");
            tempDataset.setValue(Global.Temperature(ID), "Temperature", "19");
            
        }
        //int limit=Global.roomCount-Global.availableRoom.size();
        for(int i=0;i<Global.roomCount;i++){
            if(Global.livenessStatus[i]==true){
                ServerUI.SetTextField("LIGHT", i, Double.toString(Global.LightIntensity(i)));
                ServerUI.SetTextField("TEMPERATURE", i, Double.toString(Global.Temperature(i)));
            }
        }
        lineChart.SetDefaultDataSet(tempDataset);
            
        
    }
}
