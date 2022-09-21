package project.responses;

import org.json.JSONObject;
import project.project_info;
import project.services.servicemessages.Int_ServiceMessage;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.util.Map;

public class Json_Response extends Response {




    private String jsonstring;


    public Json_Response() {

        super("json");
    }

    @Override
    public void add(ServiceMessage serviceMessage) {


        jsonstring =(add_map_to_jsonobject(serviceMessage)).toString();

        super.service_response=jsonstring;
    }


    //////object is map
    private JSONObject add_map_to_jsonobject(ServiceMessage serviceMessage){


        JSONObject json=new JSONObject();


        if(serviceMessage.getClass().equals(Int_ServiceMessage.class)){

            Map<String,Integer> map= serviceMessage.get_result();

            map.put("type",1);
            for (Map.Entry<String,Integer> entry : map.entrySet()) {
                json.put(entry.getKey(), entry.getValue());

            }
        }
        else if(serviceMessage.getClass().equals(String_ServiceMessage.class)){

            Map<String,String> map= serviceMessage.get_result();

            map.put("type","string");

            for (Map.Entry<String,String> entry : map.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
        }


        return json;

    }

}
