package project.services.rdp_sender;

import project.GUI.RDPGUI_sender.StartConnection;
import project.GUI.RDPGUI_sender.setPassword;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Receiver_RDP_Start extends Service {

    public Receiver_RDP_Start() {
        super("Receiver_RDP_Start");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();


        StartConnection startConnection=new StartConnection((String) parameters.get("FirstUserID"));
        startConnection.setSize(300,80);
        startConnection.setLocation(500,300);
        startConnection.setVisible(true);


        boolean loop=true;


        String answer="";
        while (loop){
            if(startConnection.getValue().equals("")){
                sleep(200);
            }else {
                answer=startConnection.getValue();
                loop=false;
            }
        }


        servicemessage.add("msg",answer);


        if(answer.equals("yes")) {
            setPassword frame1 = new setPassword();
            frame1.setSize(300, 80);
            frame1.setLocation(500, 300);
            frame1.setVisible(true);


            answer="";
            loop=true;


            while (loop){
                if(frame1.getValue().equals("")){
                    sleep(200);
                }else {
                    answer=frame1.getValue();
                    loop=false;
                }
            }


            password_checker passwordChecker= password_checker.get_instance();
            passwordChecker.add_password((String) parameters.get("FirstUserID"),answer);

        }



        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
