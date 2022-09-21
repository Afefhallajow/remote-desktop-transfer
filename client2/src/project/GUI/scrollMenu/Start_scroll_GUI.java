package project.GUI.scrollMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.GUI.GUIHandler;

import java.io.File;
import java.net.URL;

public class Start_scroll_GUI extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/project/GUI/scrollMenu/Scroll.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Options");
        primaryStage.setScene(new Scene(root, 160, 300));
        primaryStage.show();


    }


    public void setUID(String UID) {

        ControllerScroll ControllerScroll= GUIHandler.get_instance().getControllerScroll();

        ControllerScroll.setUID(UID);
    }




}
