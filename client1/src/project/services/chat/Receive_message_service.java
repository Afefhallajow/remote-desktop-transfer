package project.services.chat;

import javafx.application.Platform;
import project.GUI.GUIHandler;
import project.GUI.Main_GUI.MainController;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.util.Map;

public class Receive_message_service extends Service {

    public Receive_message_service() {
        super("Receive_message");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();



            MainController mainController= GUIHandler.getMainController();

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    mainController.receive_message((String) parameters.get("message"));
                }});

            return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}

