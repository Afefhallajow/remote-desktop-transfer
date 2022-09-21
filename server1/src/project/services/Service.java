package project.services;

import com.nimbusds.jose.JOSEException;
import project.requests.Json_Request;
import project.requests.Request;
import project.requests.Simple_Request;
import project.senders.Sender;
import project.senders.Socket_Sender;
import project.services.servicemessages.ServiceMessage;
import project.users.Users;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public abstract class Service {


    private String name;
    protected ServiceMessage servicemessage=intalize_servicemessage();

    public Service(String name) {
        this.name = name;
    }

    public String get_name(){
        return name;
    }


    protected Map<String,String>make_map(){
        Map<String,String> map=new HashMap<>();
        map.put("service_name",name);

        return map;
    }

    protected Request make_request(String request_type,Object object){
        Request request = null;

        if(request_type.equals("json")){
            request =new Json_Request();
        }
        else if(request_type.equals("simple")){
            request =new Simple_Request();
        }



        request.add(object);

        return request;
    }

    protected ServiceMessage send_request(String sender_type,String receiver_UID,Request request) throws IOException, ClassNotFoundException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        Sender sender=null;


        Map<String,String> user=new Users().get_port_ip_by_UID(receiver_UID);

        if (sender_type.equals("socket")){

            sender=new Socket_Sender(user.get("IP"),user.get("RecievePort"));
        }

        sender.fill_sender_information();

        ServiceMessage serviceMessage=sender.send_request(request);

        return serviceMessage;

    }
    protected Map<String,String>add_map_to_map(Map<String,String> map1,Map<String,String> map2){

        for (Map.Entry<String, String> entry : map2.entrySet()) {

            map1.put(entry.getKey(), entry.getValue());
        }
        return map1;
    }

/*
call_dao(){

}
 */



    abstract public ServiceMessage execute(Map parameters) throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException;

    abstract public ServiceMessage intalize_servicemessage();



}
