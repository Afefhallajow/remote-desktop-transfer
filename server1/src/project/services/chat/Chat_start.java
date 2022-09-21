package project.services.chat;

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

public class Chat_start extends Service {
    public Chat_start() {
        super("Chat_start");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();
        ArrayList<ArrayList<String>> userDB2=new User_Dao().FindByUserID((String) parameters.get("SecondUserID"));

        if( userDB2.size()==1 ){
            servicemessage.add("error", Error.NotFound.toString());
            return servicemessage;
        }

        Map object=make_map();


        object=add_map_to_map(object,parameters);

        object.put("service_name","Receiver_chat_start");

        if(parameters.containsKey("encrypted"))
        {
            object.put("encrypted",parameters.get("encrypted"));
        }

        if(parameters.containsKey("encrypt"))
        {
            object.put("encrypt",parameters.get("encrypt"));
        }

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
