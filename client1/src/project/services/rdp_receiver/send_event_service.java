package project.services.rdp_receiver;

import project.GUI.GUIHandler;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class send_event_service extends Service {
    public send_event_service() {
        super("send_event_service");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();


        Map object=make_map();

        object.put("service_name","send_event_to_another_client");
        if(parameters.containsKey("encrypted"))
        {
            object.put("encrypted",parameters.get("encrypted"));
        }

        if(parameters.containsKey("encrypt"))
        {
            object.put("encrypt",parameters.get("encrypt"));
        }
        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object);

        ServiceMessage serviceMessage=send_request("socket","server",request);

        servicemessage.add("result",serviceMessage.get_result().get("result"));


        return servicemessage;


    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
