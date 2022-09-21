package project.facade;

import project.services.*;
import project.services.FTP_Receiver.Receive_FTP_service;
import project.services.FTP_Sender.Send_FTP_service;
import project.services.chat.*;
import project.services.handShake.FirstHandShake_service;
import project.services.handShake.SecondHandShake_service;
import project.services.publicKeys.GetUserKey;
import project.services.rdp_receiver.End_RDP;
import project.services.rdp_receiver.RDP_Start;
import project.services.rdp_receiver.ReceiveScreen;
import project.services.rdp_sender.*;

import project.services.restart.Do_Restart_service;
import project.services.restart.Send_restart;
import project.services.shutdown.Do_ShutDown_service;
import project.services.shutdown.Send_shutdown;
import project.services.tokens_services.Check_token;

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
        Service receiveScreen=new ReceiveScreen();

        Service receiver_rdp_start=new Receiver_RDP_Start();
        Service receiver_rdp_authentication=new Receiver_RDPAuthentication();
        Service send_image_service=new send_image_service();
        Service receive_event_service=new receive_event_service();

        Service chat_start=new Chat_start_service();

        Service Send_message=new Send_message_service();

        Service Receive_message=new Receive_message_service();

        Service receiver_chat_start=new Receiver_chat_start_service();

        Service chat_end=new Chat_end_service();

        Service receiver_chat_end=new Receiver_chat_end_service();

        Service check_token=new Check_token();


        Service FirstHandShake=new FirstHandShake_service();
        Service SecondHandShake=new SecondHandShake_service();

        Service GetUserKey=new GetUserKey();

        Service SendFTP=new Send_FTP_service();

        Service ReceiveFTP=new Receive_FTP_service();

        Service SendShutDown=new Send_shutdown();
        Service DoShutDown=new Do_ShutDown_service();


        Service SendRestart=new Send_restart();
        Service DoRestart=new Do_Restart_service();

        Service end_rdp=new End_RDP();
        Service receiver_end_rdp=new Receiver_End_RDP();
        /////////add to map
        services.put("hello",hello_service);
        services.put("hello_aspect",hello_service_with_aspect);
        services.put("calculator",calculator);
        services.put("try_forbidden",try_forbidden);
        services.put(SignUp.get_name(),SignUp);
        services.put(LogIn.get_name(),LogIn);

        services.put(rdp_start.get_name(),rdp_start);
        services.put(receiveScreen.get_name(),receiveScreen);
        services.put(receiver_rdp_start.get_name(),receiver_rdp_start);
        services.put(receiver_rdp_authentication.get_name(),receiver_rdp_authentication);
        services.put(send_image_service.get_name(),send_image_service);
        services.put(receive_event_service.get_name(),receive_event_service);

        services.put(chat_start.get_name(),chat_start);

        services.put(Send_message.get_name(),Send_message);

        services.put(Receive_message.get_name(),Receive_message);

        services.put(receiver_chat_start.get_name(),receiver_chat_start);

        services.put(chat_end.get_name(),chat_end);

        services.put(receiver_chat_end.get_name(),receiver_chat_end);

        services.put(check_token.get_name(),check_token);

        services.put(FirstHandShake.get_name(),FirstHandShake);

        services.put(SecondHandShake.get_name(),SecondHandShake);

        services.put(GetUserKey.get_name(),GetUserKey);

        services.put(SendFTP.get_name(),SendFTP);

        services.put(ReceiveFTP.get_name(),ReceiveFTP);

        services.put(SendShutDown.get_name(),SendShutDown);

        services.put(DoShutDown.get_name(),DoShutDown);

        services.put(SendRestart.get_name(),SendRestart);

        services.put(DoRestart.get_name(),DoRestart);
        services.put(receiver_end_rdp.get_name(),receiver_end_rdp);

        services.put(end_rdp.get_name(),end_rdp);
    }


    Map<String, Service> get_services() {
        return services;
    }


}
