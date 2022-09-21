package project.services.rdp_receiver;

import project.GUI.GUIHandler;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class SendEvent implements KeyListener, MouseListener, MouseMotionListener {


    private JPanel panel=null;
    String width="",height="";
    double w,h;

    String AnotherUser="";
    public SendEvent(String AnotherUser, JPanel panel, String width, String height) {
        this.AnotherUser=AnotherUser;
        this.panel = panel;
        this.width = width;
        this.height = height;


        w=Double.valueOf(width.trim()).doubleValue();
        h=Double.valueOf(height.trim()).doubleValue();

        panel.addKeyListener(this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);


    }

    public void mouseDragged(MouseEvent e){

    }

    public void mouseMoved(MouseEvent e){

        double xScale=(double) (w/panel.getWidth());
        double yScale=(double) (h/panel.getHeight());

        Map<String,String> para=new HashMap<>();
        para.put("first",String.valueOf(Commands.MOVE_MOUSE.getAbbrev()));
        para.put("second",String.valueOf(e.getX()*xScale));
        para.put("third",String.valueOf(e.getY()*yScale));
        para.put("SecondUserID",AnotherUser);
        para.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());
        try {
            new send_event_service().execute(para);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e){

    }


    public void mousePressed(MouseEvent e){


        int button=e.getButton();
        int xbutton=16;

        if(button==3){
            xbutton=4;
        }

        Map<String,String> para=new HashMap<>();
        para.put("first",String.valueOf(Commands.PRESS_MOUSE.getAbbrev()));
        para.put("second",String.valueOf(xbutton));
        para.put("third",String.valueOf(0));
        para.put("SecondUserID",AnotherUser);
        para.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());

        try {
            new send_event_service().execute(para);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int button=e.getButton();
        int xbutton=16;

        if(button==3){
            xbutton=4;
        }


        Map<String,String> para=new HashMap<>();
        para.put("first",String.valueOf(Commands.RELEASW_MOUSE.getAbbrev()));
        para.put("second",String.valueOf(xbutton));
        para.put("third",String.valueOf(0));
        para.put("SecondUserID",AnotherUser);
        para.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());
        try {
            new send_event_service().execute(para);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

    /////error
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {



        Map<String,String> para=new HashMap<>();
        para.put("first",String.valueOf(Commands.PRESS_KEY.getAbbrev()));
        para.put("second",String.valueOf(e.getKeyCode()));
        para.put("third",String.valueOf(0));
        para.put("SecondUserID",AnotherUser);
        para.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());
        try {
            new send_event_service().execute(para);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {




        Map<String,String> para=new HashMap<>();
        para.put("first",String.valueOf(Commands.RELEASE_KEY.getAbbrev()));
        para.put("second",String.valueOf(e.getKeyCode()));
        para.put("third",String.valueOf(0));
        para.put("SecondUserID",AnotherUser);
        para.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());
        try {
            new send_event_service().execute(para);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

}