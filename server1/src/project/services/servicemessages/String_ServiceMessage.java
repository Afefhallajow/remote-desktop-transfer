package project.services.servicemessages;

import java.util.HashMap;
import java.util.Map;

public class String_ServiceMessage extends ServiceMessage {


    public String_ServiceMessage(){
        result=new HashMap<String,String>();
    }



    @Override
    public void add(Object key, Object value) {
        add_String((String) key,(String)value);
    }



    private void add_String(String key,String value){
        result.put(key,value);
    }
}
