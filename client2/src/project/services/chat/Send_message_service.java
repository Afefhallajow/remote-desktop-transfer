package project.services.chat;

import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class Send_message_service extends Service {
    public Send_message_service() {
        super("Send_message");
    }

    @Override
    public ServiceMessage execute(Map parameters){
        servicemessage=intalize_servicemessage();

        Map object=make_map();


        object=add_map_to_map(object,parameters);

        object.put("service_name","send_message_to_another_client");

        Request request=make_request("json", object);

        ServiceMessage serviceMessage= null;
        try {
            serviceMessage = send_request("socket","server",request);
        } catch (IOException e) {
            servicemessage.add("error","IOException");
        } catch (ClassNotFoundException e) {
            servicemessage.add("error","ClassNotFoundException");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        servicemessage.add("result",serviceMessage.get_result().get("result"));

        return servicemessage;


    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
