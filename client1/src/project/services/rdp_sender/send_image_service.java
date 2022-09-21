package project.services.rdp_sender;

import project.GUI.GUIHandler;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class send_image_service extends Service {
    public send_image_service() {
        super("send_image_service");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, AWTException {
        servicemessage=intalize_servicemessage();

        Map object=make_map();

        object.put("service_name","send_screen_to_another_client");
        object.put("encrypt",GUIHandler.get_instance().getMainController().GetSecurityLevel());
        System.out.println(object.get("encrypt"));
        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object);


        try {

            ServiceMessage serviceMessage=serviceMessage = send_request("socket", "server", request);

            servicemessage.add("result",serviceMessage.get_result().get("result"));

        }
        catch (IOException e){
            servicemessage.add("result","false");


        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return servicemessage;


    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
