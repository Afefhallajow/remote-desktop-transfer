package project.services.handShake;

import project.DAO.PublicKeys_Dao;
import project.errors.Error;
import project.keys.manage_asym_keys;
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
import java.util.HashMap;
import java.util.Map;

public class FirstHandShake_service extends Service {


    public FirstHandShake_service() {
        super("FirstHandShake");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();
        //  String UID=(String) parameters.get("UID");


        Map object=make_map();

        object.put("service_name","SecondHandShake");

        parameters.put("Key",getStringPublicKey());

        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        try {
            ServiceMessage serviceMessage = send_request("socket", (String) parameters.get("UID"), request);

            if (serviceMessage.get_result().containsKey("error")) {

                servicemessage.add("error", (String) serviceMessage.get_result().get("error"));

            } else {


                String UID=(String) parameters.get("UID");
                String Key=(String) serviceMessage.get_result().get("Key");

                Map<String,String> user=new HashMap<>();

                user.put("UID",UID);
                user.put("Key",Key);

                new PublicKeys_Dao().Update(user);

                servicemessage.add("Key",Key);

            }
        }catch (IOException | SQLException e){
            servicemessage.add("error", Error.ClintUnReachable.toString());

        }

        return servicemessage;
    }


    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }



    private static String getStringPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = new manage_asym_keys().get_public_key();
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");

        return rsaPublicKey;
    }
}
