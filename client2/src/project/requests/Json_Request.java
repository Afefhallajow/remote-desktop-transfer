package project.requests;

import org.json.JSONObject;
import project.project_info;
import project.token.GetToken;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class Json_Request extends Request {




    private String jsonstring;


    public Json_Request() {

        super("json");
    }

    @Override
    public void add(Object object) {


            jsonstring = (add_map_to_jsonobject(object)).toString();

        objects=jsonstring;
    }


    //////object is map
    private JSONObject add_map_to_jsonobject(Object object){
        Map<String,String>map =(Map)object;

        JSONObject json=new JSONObject();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }

        //json.put("jwt_token", GetToken.get_instance().getTokenString());


        return json;
    }




}
