package project.response_transforms;

import org.json.JSONObject;
import project.responses.Json_Response;
import project.responses.Response;
import project.responses.Simple_Response;
import project.services.servicemessages.Int_ServiceMessage;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResponseTransform {


    public ServiceMessage get_servicemassge(Response response){


        if(response.getClass().equals(Simple_Response.class)){

            return (ServiceMessage)response.getService_response();

        }

        else if(response.getClass().equals(Json_Response.class)){

            String jsonstring =(String) response.getService_response();

            JSONObject json=new JSONObject(jsonstring);

            ServiceMessage serviceMessage=null;

             if(json.get("type").getClass().equals(String.class)) {

                json.remove("type");

                serviceMessage=new String_ServiceMessage();

                Map<String, String> data = new HashMap<>();

                for (Iterator<String> it = json.keys(); it.hasNext(); ) {

                    String s = it.next();
                    data.put(s, (String) json.get(s));
                }

                serviceMessage.setrresult(data);
            }
            else if(json.get("type").getClass().equals(Integer.class)) {

                json.remove("type");

                serviceMessage=new Int_ServiceMessage();

                Map<String, Integer> data = new HashMap<>();

                for (Iterator<String> it = json.keys(); it.hasNext(); ) {

                    String s = it.next();
                    data.put(s, (Integer) json.get(s));
                }
                serviceMessage.setrresult(data);
            }

            return serviceMessage;
        }

        return null;
    }

}
