package project.services;

import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.util.Map;

public class try_forbidden extends Service {
    public try_forbidden() {
        super("try_forbidden");
    }

    @Override
    public ServiceMessage execute(Map parameters)  {
        servicemessage.add("msg","you are in service !!");

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
