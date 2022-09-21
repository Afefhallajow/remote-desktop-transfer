package project.services.rdp_sender;

import project.errors.Error;
import project.facade.Facade;
import project.messages.Message;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class Receiver_RDPAuthentication extends Service {
    public Receiver_RDPAuthentication() {
        super("Receiver_RDPAuthentication");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, AWTException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();

        String Password=(String) parameters.get("Password");
        String FirstUserID=(String) parameters.get("FirstUserID");


        password_checker passwordChecker= password_checker.get_instance();
        boolean valid=passwordChecker.check_password(FirstUserID,Password);

        if(!valid){
            servicemessage.add("verification", Error.AuthenticateError.toString());
        }else{

            GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice graphicsDevice=graphicsEnvironment.getDefaultScreenDevice();
            Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
            String width=""+dimension.getWidth();
            String height=""+dimension.getHeight();
            Rectangle rectangle=new Rectangle(dimension);
            Robot robot=new Robot(graphicsDevice);

            servicemessage.add("verification", "valid");

            servicemessage.add("width",width);
            servicemessage.add("height",height);

            new SendScreen((String) parameters.get("FirstUserID"),robot,rectangle);
            ReceiveEvent.get_instance(robot);

            ///////////////////// Get Public of another client from server
            new Thread(()->{
                try {
                    GetPublicKeyofAnotherUser((String) parameters.get("FirstUserID"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

    public void GetPublicKeyofAnotherUser(String UID) throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {
        ////HandShake
        Map<String,String> p=new HashMap();
        p.put("UID",UID);
        p.put("encrypt","2");

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("GetUserKey");

        ServiceMessage serviceMessage=new Facade().execute(msg);

    }
}
