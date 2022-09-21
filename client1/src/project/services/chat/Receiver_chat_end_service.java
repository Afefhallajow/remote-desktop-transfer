package project.services.chat;

import javafx.application.Platform;
import project.GUI.GUIHandler;
import project.GUI.Main_GUI.MainController;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.util.Map;

public class Receiver_chat_end_service extends Service {
    public Receiver_chat_end_service() {
        super("Receiver_chat_end");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();
        MainController mainController= GUIHandler.getMainController();
        Platform.runLater(new Runnable() {
            @Override public void run() {
                mainController.EndChat();
            }});


        servicemessage.add("result","true");

        return servicemessage;



    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
