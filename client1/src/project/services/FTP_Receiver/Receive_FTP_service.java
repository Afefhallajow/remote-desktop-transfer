package project.services.FTP_Receiver;

import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;
import project.users.Users;

import java.io.IOException;
import java.util.Map;

public class Receive_FTP_service extends Service {

    public Receive_FTP_service() {
        super("Receive_FTP");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();

        Map<String,String> user=new Users().get_port_ip_by_name("server");

        FTP_download ftp_download=new FTP_download(user.get("IP"),Integer.parseInt(user.get("FTPPort")),(String) parameters.get("path2"),(String) parameters.get("fileName"));
        ftp_download.receive();

        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
