package project.services;

import project.requests.Request;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class Hello_Service_with_Aspect extends Service {

    public Hello_Service_with_Aspect() {
        super("hello_aspect");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {

        Map object=make_map();

        add_map_to_map(object,parameters);

        Request request=make_request("simple", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);

        servicemessage.add("msg",serviceMessage.get_result().get("msg"));

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
