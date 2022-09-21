package project.requests;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class Request implements Serializable {

   // private String myname;
    private String type;
    protected Object objects;


    public Request(String type) {
     //   this.myname=myname;
        this.type = type;
    }

    //public String getMyname() { return myname; }

    public String getType() {
        return type;
    }

    public Object getObjects() {
        return objects;
    }

    public void changeObject(Object o){objects=o;}

    abstract public void add(Object object);

}
