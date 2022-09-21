package project.GUI.Login_Signup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.facade.Facade;
import project.messages.Message;
import project.services.servicemessages.ServiceMessage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Start_Login_GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        ////////////////////////////////GUI
        URL url = new File("src/project/GUI/Login_Signup/sample.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("login");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }



}
