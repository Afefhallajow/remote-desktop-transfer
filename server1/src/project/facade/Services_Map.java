package project.facade;

import project.services.*;
import project.services.chat.Chat_end;
import project.services.chat.Chat_start;
import project.services.chat.send_message_to_another_client;
import project.services.ftp.Receive_FTP_server;
import project.services.handShake.FirstHandShake_service;
import project.services.handShake.SecondHandShake_service;
import project.services.publicKeys.GetUserKey;
import project.services.rdp.*;
import project.services.restart.send_restart_2client;
import project.services.shutdown.send_shutdown_2client;
import project.services.token.Check_token;

import java.util.HashMap;
import java.util.Map;

public class Services_Map {

    private Map<String, Service> services=new HashMap<String, Service>();

    public Services_Map() {

        ////////initializes
        Service hello_service=new Hello_Service();
        Service hello_service_with_aspect=new Hello_Service_with_Aspect();
        Service calculator=new calculator();
        Service try_forbidden=new try_forbidden();
        Service SignUp=new SignUp_service();
        Service LogIn=new LogIn_service();
        Service rdp_start=new RDP_Start();
        Service rdp_authentication=new RDPAuthentication();

        Service send_screen_to_another_client=new send_screen_to_another_client();
        Service send_event_to_another_client=new send_event_to_another_client();

        Service chat_start=new Chat_start();

        Service send_message_to_another_client=new send_message_to_another_client();

        Service chat_end=new Chat_end();

        Service check_token=new Check_token();

        Service FirstHandShake=new FirstHandShake_service();
        Service SecondHandShake=new SecondHandShake_service();

        Service GetUserKey =new GetUserKey();

        Service ReceiveFTP=new Receive_FTP_server();

        Service SendShutDown=new send_shutdown_2client();
        Service SendRestart=new send_restart_2client();

        Service send_end_rdp=new Send_End_RDP();

        /////////add to map
        services.put("hello",hello_service);
        services.put("hello_aspect",hello_service_with_aspect);
        services.put("calculator",calculator);
        services.put("try_forbidden",try_forbidden);
        services.put("SignUp",SignUp);
        services.put("LogIn",LogIn);
        services.put(rdp_start.get_name(),rdp_start);
        services.put(rdp_authentication.get_name(),rdp_authentication);
        services.put(send_screen_to_another_client.get_name(),send_screen_to_another_client);
        services.put(send_event_to_another_client.get_name(),send_event_to_another_client);
        services.put(chat_start.get_name(),chat_start);
        services.put(send_message_to_another_client.get_name(),send_message_to_another_client);
        services.put(chat_end.get_name(),chat_end);
        services.put(check_token.get_name(),check_token);
        services.put(SecondHandShake.get_name(),SecondHandShake);
        services.put(FirstHandShake.get_name(),FirstHandShake);

        services.put(GetUserKey.get_name(),GetUserKey);

        services.put(ReceiveFTP.get_name(),ReceiveFTP);

        services.put(SendShutDown.get_name(),SendShutDown);
        services.put(SendRestart.get_name(),SendRestart);

        services.put(send_end_rdp.get_name(),send_end_rdp);
    }


    Map<String, Service> get_services() {
        return services;
    }


}
