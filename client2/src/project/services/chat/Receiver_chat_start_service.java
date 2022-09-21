package project.services.chat;

import javafx.application.Platform;
import project.GUI.GUIHandler;
import project.GUI.Main_GUI.MainController;
import project.GUI.chat.*;
import project.facade.Facade;
import project.messages.Message;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Receiver_chat_start_service extends Service {

    public Receiver_chat_start_service() {
        super("Receiver_chat_start");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, AWTException, InvalidKeySpecException, NoSuchAlgorithmException {

        servicemessage=intalize_servicemessage();

        StartChat startConnection = new StartChat((String) parameters.get("FirstUserID"));
        startConnection.setSize(300, 80);
        startConnection.setLocation(500, 300);
        startConnection.setVisible(true);


        boolean loop = true;


        String answer = "";
        while (loop) {
            if (startConnection.getValue().equals("")) {
                sleep(200);
            } else {
                answer = startConnection.getValue();
                loop = false;
            }
        }


        servicemessage.add("msg", answer);


        if (answer.equals("yes")) {
            MainController mainController = GUIHandler.getMainController();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mainController.switchChat((String) parameters.get("FirstUserID"));
                }
            });


            ///////////////////// Get Public of another client from server
            new Thread(() -> {
                try {
                    GetPublicKeyofAnotherUser((String) parameters.get("FirstUserID"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

        }
        else {
            servicemessage.add("error", "client refused");
        }
        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }


    public void GetPublicKeyofAnotherUser(String UID) throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {

        System.out.println(UID);
        ////HandShake
        Map<String,String> p=new HashMap();
        p.put("UID",UID);
        p.put("encrypt","2");

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("GetUserKey");

        ServiceMessage serviceMessage=new Facade().execute(msg);

    }
}