package project.GUI.RDPGUI_receiver;

import javax.swing.*;
import java.awt.*;

public class draw_received_screen {

    private static volatile draw_received_screen drawReceivedScreen;
    private static volatile JPanel panel;

    private draw_received_screen(JPanel p) {
        this.panel=p;
    }

    public static draw_received_screen get_instance(JPanel p){
        //p will be null if you made instance before
        if (drawReceivedScreen==null){

            synchronized (draw_received_screen.class){
                if (drawReceivedScreen==null){
                    drawReceivedScreen=new draw_received_screen(p);
                }
            }
        }

        return drawReceivedScreen;
    }


    public synchronized boolean draw_on_panel(Image image){

        Image image1=image.getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_FAST);

        Graphics graphics=panel.getGraphics();

        if(image1!=null && panel!=null && graphics!=null) {
            graphics.drawImage(image1, 0, 0, panel.getWidth(), panel.getHeight(), panel);
        }
        else return false;

        return true;

    }
}
