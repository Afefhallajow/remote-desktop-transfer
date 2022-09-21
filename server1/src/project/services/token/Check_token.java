package project.services.token;

import com.nimbusds.jose.JOSEException;
import project.DAO.User_Dao;
import project.errors.Error;
import project.keys.AES_Manger;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;
import project.tokens.Extract_token;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Check_token extends Service {
    public Check_token() {
        super("Check_token");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {

        servicemessage=intalize_servicemessage();

        System.out.println(parameters);
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


        String data=(String)parameters.get("token");


        Map para2= Extract_token.parseJwt(data);


      //  System.out.println(para2);

        if(((Date)para2.get("exp") ).before(new Date())){

            servicemessage.add("error", Error.Expired.toString());
            servicemessage.add("result","false");
            return servicemessage;
        }


        ArrayList<ArrayList<String>> userDB=new User_Dao().FindByName((String) para2.get("Name"));

        if(userDB.size()==1){
            servicemessage.add("error", Error.NotFound.toString());
            servicemessage.add("result","false");
            return servicemessage;
        }

        ArrayList<String> user0=userDB.get(0);
        ArrayList<String> user=userDB.get(1);

        String pass=new AES_Manger().decrypt((String) user.get(3),(String)user.get(5));



        if(!pass.equals(para2.get("Password"))){
            servicemessage.add("error", Error.AuthenticateError.toString());
            servicemessage.add("result","false");
            return servicemessage;
        }



        for(int i=0;i<user0.size()-1;i++){
            if(user0.get(i).equals("UserID"))
            {
                servicemessage.add("UID",user.get(i));
                break;
            }
        }



        servicemessage.add("result","true");


        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }


}
