package project.services.rdp_receiver;

import javafx.application.Platform;
import javafx.stage.Stage;
import project.GUI.GUIHandler;
import project.GUI.Main_GUI.Start_Main_GUI;
import project.GUI.RDPGUI_receiver.CreateFrame;
import project.GUI.scrollMenu.Start_scroll_GUI;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class End_RDP extends Service {
    public End_RDP() {
        super("End_RDP");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();



        Map object=make_map();

        object.put("service_name","Send_End_RDP");

        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);


        if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("error",(String)serviceMessage.get_result().get("error"));

            return servicemessage;

        }


        CreateFrame createFrame=CreateFrameHandler.get_instance().getcreateFrame();
        createFrame.CloseFrame();
        createFrame.interrupt();

        //////scroll END
//      Start_Main_GUI start_main_gui=new Start_Main_GUI();
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    start_main_gui.setPrivateUID(GUIHandler.get_instance().getUID());
//
//                    Stage stage=new Stage();
//                    start_main_gui.start(stage);
//                    start_main_gui.setUID(GUIHandler.get_instance().getUID());
//                    start_main_gui.setStage(stage);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        return serviceMessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
