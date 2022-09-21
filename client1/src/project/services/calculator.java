package project.services;

import project.requests.Request;
import project.services.servicemessages.Int_ServiceMessage;
import project.services.servicemessages.ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class calculator extends Service {


    public calculator() {
        super("calculator");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {


        Map object=make_map();

        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);

        servicemessage.add("result",serviceMessage.get_result().get("result"));

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new Int_ServiceMessage();
    }
}
