package project.aspects;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class check_token_aspect extends Aspect {
    public check_token_aspect(List<String> before_services, List<String> after_services, boolean before_all_services, boolean after_all_services, List<String> except) {
        super(before_services, after_services, before_all_services, after_all_services, except);
    }

    @Override
    public Object before(Object parameters) {

        System.out.println("check token !! ");

        HashMap<String, String> p;
        JSONObject jsonObject;


        if(parameters.getClass().equals(Map.class) || parameters.getClass().equals(HashMap.class)) {


            p = (HashMap<String, String>) parameters;


            if(p.containsKey("jwt_token") ){
               p.remove("jwt_token");

            }
            else if(p.containsKey("token") ){
                p.remove("token");

            }
        return p;
        }
        else if(parameters.getClass().equals(String.class)){

            String jsonstring =(String) parameters;

            jsonObject=new JSONObject(jsonstring);


            if(jsonObject.has("jwt_token") ){
                jsonObject.remove("jwt_token");


            }
            else if(jsonObject.has("token") ){
                jsonObject.remove("token");


            }

            return jsonObject;

        }

        return parameters;
    }

    @Override
    public Object after(Object parameters) {
    return parameters;
    }







}
