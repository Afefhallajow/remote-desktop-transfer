package project.GUI;

import project.GUI.Login_Signup.Controller;
import project.GUI.Main_GUI.MainController;
import project.GUI.scrollMenu.ControllerScroll;

import java.util.HashMap;

public class GUIHandler {
    private static volatile GUIHandler guiHandler;
    private static volatile MainController mainController;
    private static volatile Controller controller;
    private static volatile ControllerScroll scrollController;

    private GUIHandler(){}



    public static GUIHandler get_instance(){
        if (guiHandler==null){

            synchronized (GUIHandler.class){
                if (guiHandler==null){
                    guiHandler=new GUIHandler();

                }
            }
        }

        return guiHandler;
    }



    public static MainController getMainController() {

        synchronized (GUIHandler.class) {
            return mainController;
        }
    }

    public static void setMainController(MainController mainController) {
        synchronized (GUIHandler.class) {
            GUIHandler.mainController = mainController;
        }
    }

    public static Controller getController() {
        synchronized (GUIHandler.class) {
            return controller;
        }
    }

    public static void setController(Controller controller) {
        synchronized (GUIHandler.class) {
            GUIHandler.controller = controller;
        }
    }




    public static ControllerScroll getControllerScroll() {

        synchronized (GUIHandler.class) {
            return scrollController;
        }
    }

    public static void setControllerScroll(ControllerScroll ControllerScroll) {
        synchronized (GUIHandler.class) {
            GUIHandler.scrollController = ControllerScroll;
        }
    }


}
