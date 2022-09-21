package project.services.servicemessages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//////////***********
public abstract class ServiceMessage implements Serializable {
    protected Map result;

    public Map get_result()
    {
        return result;
    }

    public void setrresult(Map new_result)
    {
        result=new_result;
    }


    abstract public void add(Object key,Object value);
}
