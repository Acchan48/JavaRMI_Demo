/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthouse_client;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import sun.awt.WindowClosingListener;

/**
 *
 * @author Windows 8
 */
public class ClientUI extends JFrame{
    public JButton toggleStatus;
    public Object mutex;
    
    public void ChangeToggleText(){
        if(toggleStatus.getText().equals("Turn On")){
            toggleStatus.setText("Turn Off");
        }
        else
            toggleStatus.setText("Turn On");
    }
    public ClientUI(Object mutex){
        InitializeComponent();
        this.mutex=mutex;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                System.exit(0);
                
            }
        });
    }
    public void InitializeComponent(){
        toggleStatus=new JButton("Turn On");
        FlowLayout mainLayout=new FlowLayout();
        setLayout(mainLayout);
        toggleStatus.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                synchronized(mutex){
                    mutex.notify();
                }
                if(toggleStatus.getText().equals("Turn On"))
                    toggleStatus.setText("Turn Off");
                else
                    toggleStatus.setText("Turn On");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        this.add(toggleStatus);
        this.pack();
    }
    
    
}
