package project.facade;

import project.aspects.Aspect;
import project.messages.Message;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//////record
public class Facade {

    private Map<String, Service> services=new Services_Map().get_services();
    private Map<String, Aspect> aspects=new Aspects_Map().get_aspects();
    private aspects_for_service aspects_for_service=new aspects_for_service(aspects);

    private String executed_service;

    public ServiceMessage execute(Message message) throws IOException, ClassNotFoundException, InterruptedException, AWTException, InvalidKeySpecException, NoSuchAlgorithmException {
        executed_service=message.getService_name();

        aspects_for_service.setService(executed_service);
        List<String> before_aspects_for_this_service=aspects_for_service.search("before");
        List<String> after_aspects_for_this_service=aspects_for_service.search("after");


        if(message.getParameters().containsKey("sended")){


            Map<String,String> result=message.getParameters();
            result.put("sended","0");
            message.setParameters(result);

        }

        System.out.println("########################################################################################################################################################");
        System.out.println("service : "+executed_service);
        System.out.println("Facade: start1 : "+message.getParameters());


        if(!before_aspects_for_this_service.isEmpty() && message.getParameters()!=null ){
            for (String s:before_aspects_for_this_service) {
                message.setParameters((Map) aspects.get(s).before((Map) message.getParameters()));
            }
        }


        if(message.getParameters().containsKey("error")){
            ServiceMessage service_result=new String_ServiceMessage();
            Map<String,String> result=new HashMap<>();
            result.put("error",(String) message.getParameters().get("error"));
            service_result.setrresult(result);
            return service_result;
        }

        System.out.println("Facade: start2 : "+message.getParameters());

        ServiceMessage service_result=services.get(executed_service).execute(message.getParameters());

        System.out.println("Facade: end1 : "+service_result.get_result());


        if(!after_aspects_for_this_service.isEmpty() && service_result.get_result()!=null ){
            for (String s:after_aspects_for_this_service) {
                service_result.setrresult((Map) aspects.get(s).after(service_result.get_result()));
            }
        }

        System.out.println("Facade: end2 : "+service_result.get_result());

        System.out.println("########################################################################################################################################################");

        if(service_result.get_result().containsKey("sended")){
            Map<String,String> result=service_result.get_result();
            result.put("sended","1");
            service_result.setrresult(result);
        }

        return service_result;
    }

}
