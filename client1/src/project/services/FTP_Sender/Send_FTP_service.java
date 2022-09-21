package project.services.FTP_Sender;

import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;
import project.users.Users;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class Send_FTP_service extends Service {
    public Send_FTP_service() {
        super("Send_FTP");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();

        //path1 , path2 ,filename.UID 2

        String path1=(String) parameters.get("path1");
        parameters.remove("path1");



        Map<String,String> user=new Users().get_port_ip_by_name("server");

        FTP_upload Ftp=new FTP_upload(user.get("IP"),Integer.parseInt(user.get("FTPPort")),path1,(String) parameters.get("fileName"));
        Ftp.send();


        Map object=make_map();

        object=add_map_to_map(object,parameters);

        object.put("service_name","Receive_FTP_server");

        Request request=make_request("json", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);


        if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("error",(String)serviceMessage.get_result().get("error"));

            return servicemessage;

        }


        serviceMessage.add("msg","true");


        return serviceMessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
