package project.senders;

import project.requests.Request;
import project.response_transforms.ResponseTransform;
import project.responses.Response;
import project.services.servicemessages.ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public abstract class Sender {

    protected ServiceMessage transform_response(Response response){
        return new ResponseTransform().get_servicemassge(response);
    }

    abstract public void fill_sender_information() throws IOException;
    abstract public ServiceMessage send_request(Request request) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, SQLException;



}
