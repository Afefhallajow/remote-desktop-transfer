package project.responses;

import project.services.servicemessages.ServiceMessage;

import java.io.Serializable;

public abstract class Response implements Serializable {

    //private String myname;
    private String type;
    protected Object service_response;


    public Response( String type) {
   //     this.myname=myname;
        this.type = type;
    }

   // public String getMyname() { return myname; }

    public String getType() {
        return type;
    }

    public Object getService_response() {
        return service_response;
    }

    abstract public void add(ServiceMessage serviceMessage);

}
