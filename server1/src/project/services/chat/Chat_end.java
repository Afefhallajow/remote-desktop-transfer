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

public class Chat_end extends Service {
    public Chat_end() {
        super("Chat_end");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();
        Map object=make_map();


        object=add_map_to_map(object,parameters);

        object.put("service_name","Receiver_chat_end");

        Request request=make_request("json", object) ;

        try {


            ServiceMessage serviceMessage  = send_request("socket", (String) parameters.get("SecondUserID"), request);

            if (serviceMessage.get_result().containsKey("error")) {

                servicemessage.add("error", "client refused");

            } else {
                servicemessage.add("msg", "true");
            }

        }catch (IOException e){
            servicemessage.add("result", "Offline");

            System.out.println("user 2 didn't receive  ");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
