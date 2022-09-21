package project;

import javafx.application.Application;
import javafx.stage.Stage;
import project.GUI.GUIHandler;
import project.GUI.Login_Signup.Start_Login_GUI;
import project.GUI.Main_GUI.Start_Main_GUI;
import project.facade.Facade;
import project.listeners.Listener;
import project.listeners.Socket_Listener;
import project.messages.Message;
import project.services.servicemessages.ServiceMessage;

import java.awt.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private static int gui=0;
    private static String UID="";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, AWTException, InvalidKeySpecException, NoSuchAlgorithmException {


        Listener socketlistener = new Socket_Listener(project_info.get_instance().getRecieveport());
        socketlistener.fill_listen_information();

        Thread serverThread = new Thread(socketlistener);

        serverThread.start();

        /////////authen
        Message message = new Message();

        message.setService_name("Check_token");


        Map<String,String> para=new HashMap<>();
        para.put("encrypt","2");

        message.setParameters(para);

        ServiceMessage s_msg = new Facade().execute(message);

        if ((s_msg.get_result().get("result").equals("true"))) {
            gui=1;
            UID=(String) s_msg.get_result().get("UID");
        } else {
            gui=0;
        }


        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        if(gui==0) {
            Start_Login_GUI start_login_gui=new Start_Login_GUI();
            start_login_gui.start(primaryStage);
        }else {

            Start_Main_GUI start_main_gui=new Start_Main_GUI();
            start_main_gui.setPrivateUID(UID);
            start_main_gui.start(primaryStage);
            start_main_gui.setUID(UID);

            start_main_gui.setStage(primaryStage);
        }


    }
}
