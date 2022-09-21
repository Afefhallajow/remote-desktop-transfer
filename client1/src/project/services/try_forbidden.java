package project.services;

import project.requests.Request;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class try_forbidden extends Service {
    public try_forbidden() {
        super("try_forbidden");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        Map object=make_map();

        Request request=make_request("simple", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);

        if(serviceMessage.get_result().containsKey("error")){

             servicemessage.add("msg",(String)serviceMessage.get_result().get("error"));

        }
        else {
            servicemessage.add("msg", serviceMessage.get_result().get("msg"));
        }

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
