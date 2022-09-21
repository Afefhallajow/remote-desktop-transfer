package project.services.publicKeys;

import com.nimbusds.jose.JOSEException;
import project.DAO.PublicKeys_Dao;
import project.DAO.User_Dao;
import project.errors.Error;
import project.facade.Facade;
import project.keys.AES_Manger;
import project.messages.Message;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;
import project.tokens.generate_token;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    public ServiceMessage execute(Map parameters) throws SQLException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, JOSEException, ParseException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();

        ArrayList<ArrayList<String>> userDB=new PublicKeys_Dao().FindByUserID((String) parameters.get("UID"));


        if(userDB.size()==1){
            Map<String,String> p=new  HashMap();
            p.put("UID",(String) parameters.get("UID"));


            Message msg=new Message();

            msg.setParameters(p);

            msg.setService_name("FirstHandShake");
            ServiceMessage serviceMessage=new Facade().execute(msg);
            if(serviceMessage.get_result().containsKey("error")){
            servicemessage.add("error", serviceMessage.get_result().get("error"));}
            else {
                servicemessage.add("Key",serviceMessage.get_result().get("Key"));
            }
            return servicemessage;
        }

        servicemessage.add("Key",userDB.get(1).get(2));

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}