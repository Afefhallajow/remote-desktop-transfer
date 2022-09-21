package project.services.rdp_receiver;

import javafx.application.Platform;
import javafx.stage.Stage;
import project.GUI.GUIHandler;
import project.GUI.RDPGUI_receiver.CreateFrame;
import project.GUI.RDPGUI_receiver.RdpAuthentication;
import project.GUI.RDPGUI_receiver.WrongAuthentication;
import project.GUI.scrollMenu.Start_scroll_GUI;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class RDPAuthentication extends Service implements Runnable {


    private String FirstUserID="";
    private String SecondUserID="";


    public RDPAuthentication(String FirstUserID,String SecondUserID) {
        super("RDPAuthentication");
        this.SecondUserID=SecondUserID;
        this.FirstUserID=FirstUserID;
    }

    @Override
    public void run() {

        Map<String,String> para=new HashMap<>();
        para.put("FirstUserID",FirstUserID);
        para.put("SecondUserID",SecondUserID);
        para.put("encrypt", GUIHandler.get_instance().getMainController().GetSecurityLevel());
        try {

            execute(para);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {

        //////////////////////
        servicemessage=intalize_servicemessage();

        RdpAuthentication frame1=new RdpAuthentication();
        frame1.setSize(300,80);
        frame1.setLocation(500,300);
        frame1.setVisible(true);


        /////////////////////////////////////////////

        Map object=make_map();
        object=make_map();
        boolean loop=true;
        String Password="";

        while (loop){
            if(frame1.getID().equals("")){
                sleep(200);
            }else {
                Password=frame1.getID();
                loop=false;
            }
        }

        Map<String,String> variable=new HashMap<>();

        variable.put("Password",Password);
        variable.put("FirstUserID",(String) parameters.get("FirstUserID"));
        variable.put("SecondUserID",(String) parameters.get("SecondUserID"));
        if(parameters.containsKey("encrypted"))
        {
            variable.put("encrypted",(String)parameters.get("encrypted"));
        }

        if(parameters.containsKey("encrypt"))
        {
            variable.put("encrypt",(String)parameters.get("encrypt"));
        }

        object=add_map_to_map(object,variable);

        Request request=make_request("json", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);


        if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("msg",(String)serviceMessage.get_result().get("error"));

            return servicemessage;

        }


        String verify=(String) serviceMessage.get_result().get("verification");



        if (!verify.equals("valid")){
            servicemessage.add("msg","please enter valid password");


            WrongAuthentication frame2=new WrongAuthentication();
            frame2.setSize(300,80);
            frame2.setLocation(500,300);
            frame2.setVisible(true);

            return servicemessage;

        }


        ///////////////

        String width=(String) serviceMessage.get_result().get("width")
                ,height=(String) serviceMessage.get_result().get("height");;

        CreateFrame abc=new CreateFrame((String) parameters.get("SecondUserID"),width,height);


        //////scroll
        Start_scroll_GUI start_scroll_gui=new Start_scroll_GUI();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    start_scroll_gui.start(new Stage());
                    start_scroll_gui.setUID((String) parameters.get("SecondUserID"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        ////////////////////////////////////////////
        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
