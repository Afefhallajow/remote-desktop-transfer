package project.transforms;

import org.json.JSONObject;
import project.requests.Request;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Json_Transform extends Transform{

    public Json_Transform() {
        super();
    }

    @Override
    public void do_transform() {
         String jsonstring =(String) request.getObjects();

        JSONObject json=new JSONObject(jsonstring);

        message.setService_name((String) json.remove("service_name"));

        Map data= data = new HashMap<String, String>();

            for (Iterator<String> it = json.keys(); it.hasNext(); ) {

                String s = it.next();
                data.put(s, (String) json.get(s));
            }

        message.setParameters(data);

    }
}
