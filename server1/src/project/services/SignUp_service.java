package project.services;

import project.DAO.User_Dao;
import project.errors.Error;
import project.keys.AES_Manger;
import project.requests.Request;
import project.services.servicemessages.Int_ServiceMessage;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;
import project.tokens.generate_token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp_service extends Service {


    public SignUp_service() {
        super("SignUp");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws FileNotFoundException, SQLException {
        servicemessage=intalize_servicemessage();
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


        ArrayList<ArrayList<String>> userDB=new User_Dao().FindByName((String) parameters.get("Name"));

        if(userDB.size()>1){
            servicemessage.add("error", Error.UserNameUsed.toString());
            return servicemessage;
        }

        String pass=new AES_Manger().encrypt((String) parameters.get("Password"),(String)parameters.get("Birthday"));

        String real_pass=(String) parameters.get("Password");

        parameters.put("Password",pass);

        Map user=new User_Dao().Insert(parameters);

        Map temp=new HashMap();

        temp.put("Name",user.get("Name"));
        temp.put("Password",real_pass);

        String token= generate_token.createJwt(temp);

        user.put("token",token);

        user.put("UID",user.get("UID"));

        user.remove("Password");



        servicemessage.setrresult(user);
        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
