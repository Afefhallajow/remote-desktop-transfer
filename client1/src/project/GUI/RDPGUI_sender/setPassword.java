package project.GUI.RDPGUI_sender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class setPassword extends JFrame implements ActionListener {


    String value="";
    JButton submit;
    JPanel panel;
    JTextField text1,text2;

    JLabel label,label1,label2;


    public setPassword(){
        label =new JLabel();

        label1  =new JLabel();
        label1.setText("set password");

        text1=new JTextField(15);

        this.setLayout(new BorderLayout());

        submit=new JButton("Submit");

        panel=new JPanel(new GridLayout(2,1));

        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(submit);

        add(panel,BorderLayout.CENTER);

        submit.addActionListener(this);

        setTitle("setting password for client");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        value=text1.getText();
        dispose();

//        new InitConnection(Integer.parseInt(port),value1);
    }

    public String getValue(){
        return value;
    }

}
