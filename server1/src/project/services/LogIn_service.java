package project.services;

import project.DAO.User_Dao;
import project.errors.Error;
import project.keys.AES_Manger;
import project.requests.Request;
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

public class LogIn_service extends Service {


    public LogIn_service() {
        super("LogIn");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws SQLException, FileNotFoundException {
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

        if(userDB.size()==1){
            servicemessage.add("error", Error.NotFound.toString());
            return servicemessage;
        }

        ArrayList<String> user0=userDB.get(0);
        ArrayList<String> user=userDB.get(1);

        String pass=new AES_Manger().decrypt((String) user.get(3),(String)user.get(5));

        if(!pass.equals(parameters.get("Password"))){
            servicemessage.add("error", Error.AuthenticateError.toString());
            return servicemessage;
        }

        Map result=makeusermap(userDB.get(0),user);

        String real_pass=(String) parameters.get("Password");

        result.put("Password",pass);

        Map temp=new HashMap();

        temp.put("Name",result.get("Name"));
        temp.put("Password",real_pass);

        String token= generate_token.createJwt(temp);

        result.put("token",token);

        result.remove("Password");

        for(int i=0;i<user0.size()-1;i++){
            if(user0.get(i).equals("UserID")){
            result.put("UID",user.get(i));
            break;
            }
        }



        servicemessage.setrresult(result);



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

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }


    private Map makeusermap(List names,List user){
        Map result=new HashMap();

        for(int i=0;i<names.size();i++){
        result.put(names.get(i),user.get(i));
        }
        return result;
    }
}
