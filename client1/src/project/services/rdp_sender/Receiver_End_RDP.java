package project.services.rdp_sender;

import project.GUI.RDPGUI_sender.StartConnection;
import project.GUI.RDPGUI_sender.setPassword;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Receiver_End_RDP extends Service {

    public Receiver_End_RDP() {
        super("Receiver_End_RDP");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();

        SendScreen sendScreen= SendScreenHandler.getSendScreen();
        sendScreen.interrupt();

//                sendScreen.interrupt();
//        if(sendScreen.isInterrupted()){
//            System.out.println("end is done !!! ");
//        }
//        ;


        servicemessage.add("msg","done");

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
