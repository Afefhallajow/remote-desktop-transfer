package project.services.rdp;

import project.DAO.Channel_Dao;
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

public class RDP_Start extends Service {
    public RDP_Start() {
        super("RDP_Start");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();

        ArrayList<ArrayList<String>> userDB1=new User_Dao().FindByUserID((String) parameters.get("FirstUserID"));
        ArrayList<ArrayList<String>> userDB2=new User_Dao().FindByUserID((String) parameters.get("SecondUserID"));

        if(userDB1.size()==1 || userDB2.size()==1 ){
            servicemessage.add("error", Error.NotFound.toString());
            return servicemessage;
        }

        ArrayList<String> u1=userDB1.get(1);
        ArrayList<String> u2=userDB2.get(1);

        /*
        ArrayList<ArrayList<String>> channel=new Channel_Dao().FindByFirstUserID(Integer.parseInt(u1.get(0)));


        if(channel.size()==1 ){
            servicemessage.add("error", Error.NotFoundChannel.toString());
            return servicemessage;
        }

        if(!channel.get(1).get(2).equals(u2.get(0))){
            servicemessage.add("error", Error.ChannelError.toString());
            return servicemessage;
        }

        servicemessage.add("channel msg", "channel is ok");
*/

        Map object=make_map();


        object=add_map_to_map(object,parameters);

        object.put("service_name","Receiver_RDP_Start");
        Request request=make_request("json", object) ;

        try {
            ServiceMessage serviceMessage  = send_request("socket", (String) parameters.get("SecondUserID"), request);


            System.out.println(serviceMessage.get_result().get("msg"));

            if (serviceMessage.get_result().get("msg").equals("yes")) {
                servicemessage.add("client msg", "client 2 accepted");

            } else {
                servicemessage.add("error", Error.ClientRefused.toString());

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
