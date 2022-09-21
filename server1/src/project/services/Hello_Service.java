package project.services;


import project.requests.Request;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Hello_Service extends Service {

    public Hello_Service() {
        super("hello");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        //servicemessage.add("msg","hello world !!");

        Map object=make_map();


        Request request=make_request("simple",object) ;

        ServiceMessage serviceMessage=send_request("socket","user2",request);

        servicemessage.add("msg",serviceMessage.get_result().get("msg"));



        return servicemessage;


    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
