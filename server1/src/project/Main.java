package project;

import org.apache.ftpserver.ftplet.FtpException;
import project.errors.Error;
import project.facade.Facade;
import project.listeners.FTP_listener;
import project.listeners.Listener;
import project.listeners.Socket_Listener;
import project.messages.Message;
import project.services.servicemessages.ServiceMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, FtpException {

/*
        Message message=new Message();
        message.setService_name("hello");
        message.setParameters(new HashMap());

        ServiceMessage s_msg=new Facade().execute(message);

        String msg=(String) s_msg.get_result().get("msg");

        System.out.println(msg);
*/



        Listener socketlistener=new Socket_Listener(project_info.get_instance().getRecieveport());
        socketlistener.fill_listen_information();

        Thread serverThread = new Thread( socketlistener );

        serverThread.start();


        ////////////////////////// FTP
        Listener FTPlistener=new FTP_listener(project_info.get_instance().getFTPport());
        FTPlistener.fill_listen_information();

        Thread serverThread2 = new Thread( FTPlistener );

        serverThread2.start();


    }


}
