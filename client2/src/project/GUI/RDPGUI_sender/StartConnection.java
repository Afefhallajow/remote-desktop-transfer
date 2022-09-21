package project.GUI.RDPGUI_sender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartConnection extends JFrame implements ActionListener
{


    String UserID="";
    String value="";
    JButton Yes,No;
    JPanel pan1;
    JLabel label,label1;
    String width="",height="";
    //JTextField text;

    public StartConnection(String UserID){

        this.UserID=UserID;

        label =new JLabel();
        label.setText("want to connect");

        label1 =new JLabel();
        label1.setText("            "+UserID);



        this.setLayout(new BorderLayout());

        Yes=new JButton("yes");
        No=new JButton("no");


        pan1=new JPanel(new GridLayout(2,1));
        pan1.add(label1);
        pan1.add(label);
        pan1.add(Yes);
        pan1.add(No);
        add(pan1,BorderLayout.CENTER);
        Yes.addActionListener(this);
        No.addActionListener(this);

        setTitle("RDP");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked=(JButton)e.getSource();

        value=clicked.getText();
        System.out.println(value);

        dispose();

    }
    public String getValue(){
        return value;
    }

}
