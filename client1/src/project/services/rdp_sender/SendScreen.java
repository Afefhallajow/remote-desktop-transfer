package project.services.rdp_sender;

import project.GUI.GUIHandler;
import project.services.servicemessages.ServiceMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SendScreen extends Thread{

    Robot robot=null;
    Rectangle rectangle=null;
    boolean continueLoop=true;
    String AnotherUser="";
    project.services.rdp_sender.send_image_service send_image_service=null;

    public SendScreen(String AnotherUser, Robot robot, Rectangle rectangle) {
        SendScreenHandler.get_instance().setSendScreen(this);

        this.robot = robot;
        this.rectangle = rectangle;
       this.AnotherUser=AnotherUser;
        send_image_service=new send_image_service();
        start();
    }


    public void run(){


        ServiceMessage servicemessage = null;
        int count=5;
        while (continueLoop){

            BufferedImage bufferedImage=robot.createScreenCapture(rectangle);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                ImageIO.write(bufferedImage, "jpg", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = baos.toByteArray();


            String imagestring= Base64.getEncoder().encodeToString(bytes);


            Map<String,String> object=new HashMap<>();
            object.put("image",imagestring);

            object.put("SecondUserID",AnotherUser);

            object.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());
            try {
                servicemessage= send_image_service.execute(object);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            }



            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


             if( servicemessage.get_result().get("result").equals("stop")){
                System.out.println("stop sending screen");
                continueLoop=false;
            }
            else if(servicemessage.get_result().get("result").equals("true") && count<10 ){
                count++;
            }
            else if(servicemessage.get_result().get("result").equals("false") && count>0 ){
                count--;
            }

            if (count==0){
                System.out.println("stop sending screen");
                continueLoop=false;
            }
        }


    }
}
