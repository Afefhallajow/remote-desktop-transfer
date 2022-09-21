package project.listeners;

import project.facade.Facade;
import project.messages.Message;
import project.requests.Request;
import project.responses.Json_Response;
import project.responses.Response;
import project.responses.Simple_Response;
import project.services.servicemessages.ServiceMessage;
import project.transforms.Json_Transform;
import project.transforms.Simple_Transform;
import project.transforms.Transform;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class Listener implements Runnable{
    private Transform simple_transform=null;
    private Transform json_transform=null;

    private Facade facade=new Facade();


    protected Message transform_message(Request request){

        if(request.getType().equals("simple")){
            if(simple_transform==null){simple_transform=new Simple_Transform(); }

            simple_transform.setRequest(request);
            simple_transform.do_transform();

            return simple_transform.getMessage();
        }
        else if(request.getType().equals("json")){
            if(json_transform==null){json_transform=new Json_Transform(); }


            json_transform.setRequest(request);
            json_transform.do_transform();

            return json_transform.getMessage();
        }


        return null;
    }

    protected ServiceMessage call_facade(Message msg) throws IOException, ClassNotFoundException, InterruptedException, AWTException, InvalidKeySpecException, NoSuchAlgorithmException {

        return facade.execute(msg);
    }


    protected Response make_response(String type, ServiceMessage serviceMessage){

        Response response=null;
        if(type.equals("simple")){
            response=new Simple_Response();
        }
        else if(type.equals("json")){
            response=new Json_Response();
        }

        response.add(serviceMessage);

        return response;
    }


    abstract public void fill_listen_information() throws IOException;

}
