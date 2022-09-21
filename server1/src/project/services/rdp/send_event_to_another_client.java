package project.services.rdp;

import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;

public class send_event_to_another_client extends Service {
    public send_event_to_another_client() {
        super("send_event_to_another_client");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {

        servicemessage=intalize_servicemessage();

        Map object=make_map();

        object.put("service_name","receive_event_service");



        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        try {


            ServiceMessage serviceMessage  = send_request("socket", (String) parameters.get("SecondUserID"), request);

            if (serviceMessage.get_result().containsKey("error")) {

                servicemessage.add("result", "false");

            } else {
                servicemessage.add("result", "true");
            }

        }catch (IOException e){
            servicemessage.add("result", "false");

            System.out.println("user 2 didn't receive event ");
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

