package project.services.servicemessages;

import java.util.HashMap;

public class Int_ServiceMessage extends ServiceMessage {


    public Int_ServiceMessage(){
        result=new HashMap<String,Integer>();
    }



    @Override
    public void add(Object key, Object value) {
        add_Int((String) key,(int)value);
    }



    private void add_Int(String key,int value){
        result.put(key,value);
    }
}
