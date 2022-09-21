package project.aspects;

import project.requests.Request;
import project.services.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Aspect {


    private boolean before_all_services;
    private boolean after_all_services;

    private List<String> before_services;
    private List<String> after_services;

    private List<String> except;


    private Request request;

    public Aspect(List<String> before_services,List<String> after_services,
                  boolean before_all_services,boolean after_all_services,List<String> except){

        this.before_all_services=before_all_services;
        this.after_all_services=after_all_services;
        this.before_services=before_services;
        this.after_services=after_services;
        this.except=except;
    }

    public boolean search_service_in_before(String service_name){

        if(except!= null && except.contains(service_name)){
            return false;
        }
        if (before_all_services){
            return true;
        }

        if(before_services!=null && before_services.contains(service_name)){
            return true;
        }

        return false;

    }


    public boolean search_service_in_after(String service_name){

        if(except!= null && except.contains(service_name)){
            return false;
        }
        if (after_all_services){
            return true;
        }

        if(after_services!=null && after_services.contains(service_name)){
            return true;
        }

        return false;

    }

    protected Map<String,String> generate_error_map(String errorname){
        Map<String,String> result=new HashMap<>();

        result.put("error",errorname);

        return result;
    }




    abstract public Object before(Object parameters) throws ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException;
    abstract public Object after(Object result) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException;

}
