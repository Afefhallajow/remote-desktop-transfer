package project.services.chat;

import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;

public class send_message_to_another_client extends Service {
    public send_message_to_another_client() {
        super("send_message_to_another_client");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();

        Map object=make_map();

        object.put("service_name","Receive_message");

        if(parameters.containsKey("encrypted"))
        {
            object.put("encrypted",parameters.get("encrypted"));
        }

        if(parameters.containsKey("encrypt"))
        {
            object.put("encrypt",parameters.get("encrypt"));
        }





        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        try {

            System.out.println("id= "+(String) parameters.get("SecondUserID"));
            ServiceMessage serviceMessage  = send_request("socket", (String) parameters.get("SecondUserID"), request);

            if (serviceMessage.get_result().containsKey("error")) {

                servicemessage.add("error", "false");

            } else {
                servicemessage.add("result", "true");
            }

        }catch (IOException e){
            servicemessage.add("error", "IOException");

            System.out.println("user 2 didn't receive message ");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(parameters.containsKey("encrypted"))
        {
            servicemessage.add("encrypted",parameters.get("encrypted"));
        }

        if(parameters.containsKey("encrypt"))
        {
            servicemessage.add("encrypt",parameters.get("encrypt"));
        }

        if(parameters.containsKey("sended"))
        {
            servicemessage.add("sended",parameters.get("sended"));
        }
        servicemessage.add("UID",(String) parameters.get("FirstUserID"));

        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}

