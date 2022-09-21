package project.services;

import project.services.servicemessages.Int_ServiceMessage;
import project.services.servicemessages.ServiceMessage;

import java.util.Map;

public class calculator extends Service {


    public calculator() {
        super("calculator");
    }

    @Override
    public ServiceMessage execute(Map parameters) {
        int result=Integer.parseInt((String)parameters.get("var1"))+Integer.parseInt((String)parameters.get("var2"));

        servicemessage.add("result",result);

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new Int_ServiceMessage();
    }
}
