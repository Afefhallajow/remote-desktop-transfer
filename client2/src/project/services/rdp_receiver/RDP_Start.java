package project.services.rdp_receiver;

import project.GUI.GUIHandler;
import project.requests.Request;
import project.services.Service;
import project.GUI.RDPGUI_receiver.*;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class RDP_Start extends Service {
    public RDP_Start() {
        super("RDP_Start");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();

        //my user id is in parameters
        //and user id for client 2


        Map object=make_map();


        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);


        if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("error",(String)serviceMessage.get_result().get("error"));

            return servicemessage;

        }


        serviceMessage.add("msg","true");

        new Thread(new RDPAuthentication((String)parameters.get("FirstUserID"),(String)parameters.get("SecondUserID"))).start();

        return serviceMessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
