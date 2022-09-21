package project.messages;

import java.util.HashMap;
import java.util.Map;

///////////INTERNEL MESSAGE
public class Message {
   private String service_name;
   private Map parameters=new HashMap();

    public Message() {
    }


    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

}
