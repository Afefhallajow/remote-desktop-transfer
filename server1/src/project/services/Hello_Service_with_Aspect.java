package project.services;

import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.util.Map;

public class Hello_Service_with_Aspect extends Service {

    public Hello_Service_with_Aspect() {
        super("hello_aspect");
    }

    @Override
    public ServiceMessage execute(Map parameters) {
        String msg=(String) parameters.get("msg");
        servicemessage.add("msg",msg+", hello world from server!!");

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
