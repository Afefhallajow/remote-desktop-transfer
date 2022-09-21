package project.services.publicKeys;



import project.errors.Error;
import project.facade.Facade;
import project.messages.Message;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetUserKey extends Service {


    public GetUserKey() {
        super("GetUserKey");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws  IOException, InvalidKeySpecException, NoSuchAlgorithmException,  ClassNotFoundException {
        servicemessage=intalize_servicemessage();


        Map object=make_map();


        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        try {
                ServiceMessage serviceMessage = send_request("socket", "server", request);

                servicemessage.setrresult(serviceMessage.get_result());

                if(serviceMessage.get_result().containsKey("Key")){
                    try {

                        FileOutputStream fout = new FileOutputStream(parameters.get("UID")+".dat");
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        Map<String, String> m = new HashMap<>();
                        String data = (String) serviceMessage.get_result().get("Key");

                        m.put("Key", data);

                        oos.writeObject(m);
                        oos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        }catch (IOException e){
            servicemessage.add("error", Error.serverUnReachable.toString());

        }


        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

    }
