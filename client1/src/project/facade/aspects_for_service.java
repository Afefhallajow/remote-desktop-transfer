package project.facade;

import project.aspects.Aspect;
import project.services.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class aspects_for_service {

    private String service;

    private Map<String, Aspect> aspects;

    public aspects_for_service(Map<String, Aspect> aspects) {
        this.aspects = aspects;
    }

    public void setService(String service) {
        this.service = service;
    }


    public List<String> search(String place){

        List<String>aspetcs_for_service=new ArrayList<>();

        if(!aspects.isEmpty()) {

            if(place.equals("before")){
            for (Map.Entry<String, Aspect> entry : aspects.entrySet()) {

                if (entry.getValue().search_service_in_before(service)) {
                    aspetcs_for_service.add(entry.getKey());
                }

            }

           }

            else {
                for (Map.Entry<String, Aspect> entry : aspects.entrySet()) {

                    if (entry.getValue().search_service_in_after(service)) {
                        aspetcs_for_service.add(entry.getKey());
                    }

                }

            }

        }
        return aspetcs_for_service;
    }
}
