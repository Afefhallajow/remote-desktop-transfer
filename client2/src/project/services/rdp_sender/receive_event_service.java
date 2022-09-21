package project.services.rdp_sender;

import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class receive_event_service extends Service {

    public receive_event_service() {
        super("receive_event_service");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();

        ReceiveEvent.get_instance(null).do_event(Integer.parseInt((String) parameters.get("first")),
                (int)Double.parseDouble((String)parameters.get("second")),
                (int)Double.parseDouble((String)parameters.get("third")));

        servicemessage.add("result","done");
        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
