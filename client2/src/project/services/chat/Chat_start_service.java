package project.services.chat;

import project.requests.Request;
import project.services.Service;
import project.services.rdp_receiver.RDPAuthentication;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class Chat_start_service  extends Service {
    public Chat_start_service() {
        super("Chat_start");
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


        return serviceMessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
