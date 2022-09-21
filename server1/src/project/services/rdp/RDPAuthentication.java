package project.services.rdp;

import project.errors.Error;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Map;

public class RDPAuthentication extends Service {
    public RDPAuthentication() {
        super("RDPAuthentication");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();

        Map object=make_map();


        object.put("service_name","Receiver_RDPAuthentication");


        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        try {
            ServiceMessage serviceMessage  = send_request("socket", (String) parameters.get("SecondUserID"), request);

            if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("error",(String)serviceMessage.get_result().get("error"));

        }
        else {
            servicemessage.add("verification",(String)serviceMessage.get_result().get("verification"));
            servicemessage.add("width",(String)serviceMessage.get_result().get("width"));
            servicemessage.add("height",(String)serviceMessage.get_result().get("height"));

        }
    }catch (IOException e){
        servicemessage.add("error", Error.ClintUnReachable.toString());

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
