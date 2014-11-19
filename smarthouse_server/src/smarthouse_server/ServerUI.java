/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_server;

/**
 *
 * @author Windows 8
 */
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
public class ServerUI extends JFrame{
    private static JTextField[] lightTF,temperatureTF,doorTF;
    private LineChart lineChart;
    private LineChartUpdateThread updateThread;
    private static JMenuItem[] menuRoom;
    public static void SetTextField(String type,int idx,String value){
        switch(type){
            case "LIGHT":
                lightTF[idx].setText(value);
                break;
            case "TEMPERATURE":
                temperatureTF[idx].setText(value);
                break;
            case "DOOR":
                doorTF[idx].setText(value);
                break;
            default:break;
        }
    }
    
    public ServerUI(){
        InitializeComponents();
        this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent event){
            System.exit(0);
        }
        });
    }
    public static boolean EnableMenuRoom(int idx){
        if(idx>Global.roomCount)
            return false;
        menuRoom[idx].setEnabled(true);
        return true;
    }
    public static boolean DisableMenuRoom(int idx){
        if(idx>Global.roomCount)
            return false;
        menuRoom[idx].setEnabled(false);
        return true;
    }
    private void InitializeComponents(){
        JMenuBar menuBar=new JMenuBar();
        JMenu menuOptMain1=new JMenu("Monitor Room #");
        menuOptMain1.setMnemonic(KeyEvent.VK_R);
        menuRoom=new JMenuItem[Global.roomCount];
        for(int i=0;i<Global.roomCount;i++){
            menuRoom[i]=new JMenuItem("Room # "+(i+1));
            menuRoom[i].addMouseListener(new ChangeLineGraphEvent(i));
            menuRoom[i].setEnabled(false);
            menuOptMain1.add(menuRoom[i]);
        }
        menuBar.add(menuOptMain1);
        
        this.setJMenuBar(menuBar);
        
        JPanel lightPanel=new JPanel();
        JPanel temperaturePanel=new JPanel();
        JPanel doorPanel=new JPanel();
        
        javax.swing.border.Border lineBorder=BorderFactory.createLineBorder(Color.BLACK);
        javax.swing.border.Border lightBorder,temperatureBorder,doorBorder;
        
        lightBorder=BorderFactory.createTitledBorder(lineBorder,"Light Intensity");
        lightPanel.setBorder(lightBorder);
        temperatureBorder=BorderFactory.createTitledBorder(lineBorder,"Temperature Celcius");
        temperaturePanel.setBorder(temperatureBorder);
        doorBorder=BorderFactory.createTitledBorder(lineBorder,"Door Status");
        doorPanel.setBorder(doorBorder);
        
        
        BorderLayout mainLayout=new BorderLayout();
        this.setLayout(mainLayout);
        
        FlowLayout lightLayout=new FlowLayout();
        FlowLayout temperatureLayout=new FlowLayout();
        FlowLayout doorLayout=new FlowLayout();
        
        JTextArea[] lightTA=new JTextArea[Global.roomCount];
        JTextArea[] temperatureTA=new JTextArea[Global.roomCount];
        JTextArea[] doorTA=new JTextArea[Global.roomCount];
        
        lightTF=new JTextField[Global.roomCount];
        temperatureTF=new JTextField[Global.roomCount];
        doorTF=new JTextField[Global.roomCount];
        
        CreateJTAArray(Global.roomCount, lightTA, "#", true);
        CreateJTAArray(Global.roomCount, temperatureTA, "#", true);
        CreateJTAArray(Global.roomCount, doorTA, "#", true);
        
        CreateJTFArray(Global.roomCount,lightTF,15,false);
        CreateJTFArray(Global.roomCount,temperatureTF,15,false);
        CreateJTFArray(Global.roomCount,doorTF,15,false);
        
        
        lightPanel.setLayout(lightLayout);
        temperaturePanel.setLayout(temperatureLayout);
        doorPanel.setLayout(doorLayout);
        
        CreateMonitorPanel(lightPanel,lightTA,lightTF,Global.roomCount);
        CreateMonitorPanel(temperaturePanel,temperatureTA,temperatureTF,Global.roomCount);
        CreateMonitorPanel(doorPanel,doorTA,doorTF,Global.roomCount);
        
        JPanel header=new JPanel(new GridLayout(1,3));
        header.add(lightPanel);
        header.add(temperaturePanel);
        header.add(doorPanel);
        
        this.add(header,BorderLayout.NORTH);
        lineChart= new LineChart();
        
        this.add(lineChart,BorderLayout.CENTER);
        System.out.println("LINE CHART ADDED "+Global.currentTime);
        this.pack();
        
        
    }
    private void CreateJTAArray(int count,JTextArea[] ta,String value,boolean appendIndex){
        for(int i=0;i<count;i++){
            ta[i]=new JTextArea();
            if(appendIndex){
                ta[i].setText(value+i);
            }
            else ta[i].setText(value);
        }
        
    }
    
    private void CreateJTFArray(int count,JTextField[] tf,int textLength,boolean enabled){
        for(int i=0;i<count;i++){
            tf[i]=new JTextField();
            tf[i].setColumns(textLength);
            if(!enabled)
                tf[i].setEnabled(false);
        }
    }
    private boolean CreateMonitorPanel(JPanel panel, JTextArea[] ta, JTextField[] tf,int count){
        if(ta.length!=tf.length)
            return false;
        
        GridLayout TAGrid=new GridLayout(count,1);
        GridLayout TFGrid=new GridLayout(count,1);
        JPanel TAPanel=new JPanel(TAGrid);
        JPanel TFPanel=new JPanel(TFGrid);
        for(int i=0;i<count;i++){
            TAPanel.add(ta[i]);
            TFPanel.add(tf[i]);
        }
        panel.add(TAPanel);
        panel.add(TFPanel);
        return true;
    }
    public void Run(){
        System.out.println("RUNNING THREAD "+Global.currentTime);
        updateThread=new LineChartUpdateThread(lineChart,0);
        updateThread.start();
    }
    private class ChangeLineGraphEvent implements MouseListener{
        private int idx;
        public ChangeLineGraphEvent(int idx){
            this.idx=idx;
            System.out.println("CREATED EVENT FOR "+idx);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("CLICKED");
            try {
                
                lineChart.ChangeRoom(idx);
                updateThread.ChangeCurrentRoom(idx);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("RELEASED");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println("ENTERED");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //System.out.println("EXITED");
        }
        
    }
}
