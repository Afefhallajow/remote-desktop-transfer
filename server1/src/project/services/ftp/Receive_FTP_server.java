package project.services.ftp;

import project.DAO.User_Dao;
import project.errors.Error;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Receive_FTP_server extends Service {
    public Receive_FTP_server() {
        super("Receive_FTP_server");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();

        Map object=make_map();

        object=add_map_to_map(object,parameters);

        object.put("service_name","Receive_FTP");

        Request request=make_request("json", object) ;

        System.out.println((String) parameters.get("SecondUserID"));

        try {

            ServiceMessage serviceMessage  = send_request("socket", (String) parameters.get("SecondUserID"), request);

            if (serviceMessage.get_result().containsKey("error")) {

                servicemessage.add("error", "client refused");

            } else {
                servicemessage.add("msg", "true");
            }

        }catch (IOException e){

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
