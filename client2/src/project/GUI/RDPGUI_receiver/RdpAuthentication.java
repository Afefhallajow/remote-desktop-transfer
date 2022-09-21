package project.GUI.RDPGUI_receiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RdpAuthentication extends JFrame implements ActionListener
{


	 String value="";
    JButton submit;
    JPanel pan1;
    JLabel label,label1;
    String width="",height="";
    JTextField text;

   public RdpAuthentication(){

       label =new JLabel();
       label.setText("");

       label1 =new JLabel();
       label1.setText("password");

       text=new JTextField(15);

       this.setLayout(new BorderLayout());

       submit=new JButton("check");

       pan1=new JPanel(new GridLayout(2,1));
       pan1.add(label1);
       pan1.add(label);
       pan1.add(text);
       pan1.add(submit);
       add(pan1,BorderLayout.CENTER);
       submit.addActionListener(this);

       setTitle("Enter password");
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        value=text.getText();

        dispose();

    }
    public String getID(){
       return value;
    }

}