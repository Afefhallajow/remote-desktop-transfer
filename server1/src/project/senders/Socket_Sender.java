package project.senders;

import org.json.JSONObject;
import project.aspects.Aspect;
import project.facade.Aspects_Map;
import project.facade.aspects_for_service;
import project.messages.Message;
import project.requests.Request;

import project.response_transforms.ResponseTransform;

import project.responses.Response;
import project.services.servicemessages.ServiceMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Socket_Sender extends Sender {

    private Socket server;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private String ip, port;


    private Map<String, Aspect> aspects=new Aspects_Map().get_aspects();
    private project.facade.aspects_for_service aspects_for_service=new aspects_for_service(aspects);

    private String executed_service;

    public Socket_Sender(String ip,String port) {
        this.ip=ip;
        this.port=port;
    }

    @Override
    public void fill_sender_information() throws IOException {
        server = new Socket(ip, Integer.parseInt(port));

        ois = new ObjectInputStream( server.getInputStream() );
        oos = new ObjectOutputStream( server.getOutputStream() );
    }

    @Override
    public ServiceMessage send_request(Request request) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, SQLException {


        JSONObject json;

        if(request.getObjects().getClass().equals(String.class)){

            String jsonstring =(String) request.getObjects();

            json=new JSONObject(jsonstring);

            executed_service=(String) json.get("service_name");

            json.put("sended","1");
            request.changeObject(json.toString());

        }

        aspects_for_service.setService(executed_service);
        List<String> before_aspects_for_this_service=aspects_for_service.search("before");
        List<String> after_aspects_for_this_service=aspects_for_service.search("after");

        System.out.println("**************************************************************************************************************************************************************");
        System.out.println("Sender: start1 : "+request.getObjects());

        if(!before_aspects_for_this_service.isEmpty()  ){
            for (String s:before_aspects_for_this_service) {
                request.changeObject(aspects.get(s).before(request.getObjects()).toString());
            }
        }
        System.out.println("Sender: start2 : "+request.getObjects());

        oos.writeObject(request);

        Response response=(Response) ois.readObject();


        ServiceMessage serviceMessage=transform_response(response);


        oos.close();
        ois.close();
        server.close();

        System.out.println("Sender: end1 : "+serviceMessage.get_result());
        if(!after_aspects_for_this_service.isEmpty()  ){
            for (String s:after_aspects_for_this_service) {
                serviceMessage.setrresult((Map) aspects.get(s).after(serviceMessage.get_result()));
            }
        }

        System.out.println("Sender: end2 :"+serviceMessage.get_result());
        System.out.println("**************************************************************************************************************************************************************");


        if(request.getObjects().getClass().equals(String.class)){

            String jsonstring =(String) request.getObjects();

            json=new JSONObject(jsonstring);

            json.put("sended","0");
            request.changeObject(json.toString());

        }

        return serviceMessage;

    }



}
