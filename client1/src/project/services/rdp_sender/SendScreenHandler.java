package project.services.rdp_sender;

import project.GUI.GUIHandler;
import project.GUI.Login_Signup.Controller;
import project.GUI.Main_GUI.MainController;
import project.GUI.scrollMenu.ControllerScroll;

public class SendScreenHandler {
    private static volatile SendScreen sendScreen;
    private static volatile SendScreenHandler sendScreenHandler;


    private SendScreenHandler(){}



    public static SendScreenHandler get_instance(){
        if (sendScreenHandler==null){

            synchronized (GUIHandler.class){
                if (sendScreenHandler==null){
                    sendScreenHandler=new SendScreenHandler();

                }
            }
        }

        return sendScreenHandler;
    }



    public static SendScreen getSendScreen() {

        synchronized (SendScreenHandler.class) {
            return sendScreen;
        }
    }

    public static void setSendScreen(SendScreen sendScreen1) {
        synchronized (SendScreenHandler.class) {
            SendScreenHandler.sendScreen = sendScreen1;
        }
    }


}
