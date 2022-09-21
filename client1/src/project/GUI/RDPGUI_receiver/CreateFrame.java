package project.GUI.RDPGUI_receiver;

import project.GUI.GUIHandler;
import project.services.rdp_receiver.CreateFrameHandler;
import project.services.rdp_receiver.SendEvent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

/////didnt use


public class CreateFrame extends Thread{

    String width="",height="";
    private JFrame frame=new JFrame();
    private JDesktopPane desktopPane=new JDesktopPane();
    private JInternalFrame internalFrame= new JInternalFrame("server",true,true,true);
    private  JPanel panel=new JPanel();
    private String  AnotherUser="";
    public CreateFrame(String  AnotherUser,String width, String height){

        CreateFrameHandler.get_instance().setcreateFrame(this);

        this.AnotherUser=AnotherUser;
        this.width=width;
        this.height=height;


        start();
    }
    
    public void drawGUI(){
        frame.add(desktopPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        frame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());
        
        ////error
        internalFrame.getContentPane().add(panel,BorderLayout.CENTER);

        
        internalFrame.setSize(100,100);
        desktopPane.add(internalFrame);
    
        
        try {
            internalFrame.setMaximum(true);
            
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        
        panel.setFocusable(true);
        internalFrame.setVisible(true);

    }
    
    public void run(){

        drawGUI();
        

        draw_received_screen.get_instance(panel);
        new SendEvent(AnotherUser,panel,width,height);
    }

    public void CloseFrame(){
        //frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));

        frame.setVisible(false);
        frame.dispose();
        this.stop();
    }

}