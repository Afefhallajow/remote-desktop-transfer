package project.transforms;

import project.messages.Message;
import project.requests.Request;

import java.io.IOException;

public abstract class Transform {

    protected Request request;
    protected Message message;

    public Transform() {
        message=new Message();
    }
    public void setRequest(Request request){
        this.request=request;
    }

    public Message getMessage() {
        return message;
    }

    abstract public void do_transform() throws IOException;

}
