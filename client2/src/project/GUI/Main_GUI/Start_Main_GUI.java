package project.GUI.Main_GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.GUI.GUIHandler;
import project.facade.Facade;
import project.listeners.Listener;
import project.listeners.Socket_Listener;
import project.messages.Message;
import project.project_info;
import project.services.servicemessages.ServiceMessage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Start_Main_GUI extends Application {

    private String UID="";
    @Override
    public void start(Stage primaryStage) throws Exception{


        ////HandShake
        Map<String,String> p=new HashMap();
        p.put("UID",UID);
        p.put("encrypt","1");

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("FirstHandShake");

        ServiceMessage serviceMessage=new Facade().execute(msg);


        //////////////////// GUI
        URL url = new File("src/project/GUI/Main_GUI/maingui.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("RSD");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }


    public void setUID(String UID) {

        MainController mainController=GUIHandler.get_instance().getMainController();

        mainController.setUID(UID);

    }

    public void setStage(Stage stage) {

        MainController mainController=GUIHandler.get_instance().getMainController();

        mainController.setStage(stage);

    }

    public void setPrivateUID(String UID) {

    this.UID=UID;
    }


}
