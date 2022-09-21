package project.transforms;

import project.messages.Message;
import project.requests.Request;

public abstract class Transform {

    Request request;
    Message message;

    public Transform() {
        message=new Message();
    }
    public void setRequest(Request request){
        this.request=request;
    }

    public Message getMessage() {
        return message;
    }

    abstract public void do_transform();

}
