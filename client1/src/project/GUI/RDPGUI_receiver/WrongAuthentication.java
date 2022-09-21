package project.GUI.RDPGUI_receiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WrongAuthentication extends JFrame
{


    JPanel pan1;
    JLabel label;
    String width="",height="";
    //JTextField text;

   public WrongAuthentication(){

        label =new JLabel();
        label.setText("wrong password !! ");


        this.setLayout(new BorderLayout());


        pan1=new JPanel(new GridLayout(2,1));
        pan1.add(label);

        add(pan1,BorderLayout.CENTER);

        setTitle("wrong password ");
    }


}