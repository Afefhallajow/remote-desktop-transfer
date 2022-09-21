package project.services;

import project.errors.Error;
import project.requests.Request;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class SignUp_service extends Service {


    public SignUp_service() {
        super("SignUp");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException {
        servicemessage=intalize_servicemessage();

        Map object=make_map();

        object=add_map_to_map(object,parameters);

        Request request=make_request("json", object) ;

        try {

        ServiceMessage serviceMessage=send_request("socket","server",request);

        if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("error",(String)serviceMessage.get_result().get("error"));

        }
        else {
            servicemessage.setrresult(serviceMessage.get_result());

            try {

                FileOutputStream fout = new FileOutputStream("data.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                Map<String,byte[]> m=new HashMap<>();
                String data= (String) serviceMessage.get_result().get("token");

                m.put("t1", encrypt(data.substring(0,data.length()/3)));
                m.put("t2", encrypt(data.substring(data.length()/3,data.length()*2/3)));
                m.put("t3", encrypt(data.substring(data.length()*2/3)));

                oos.writeObject(m);
                oos.close();
            }
            catch (Exception e) { e.printStackTrace(); }
        }

    }catch (IOException e){
        servicemessage.add("error", Error.serverUnReachable.toString());

    } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }



}
