package project.aspects;

import project.facade.Facade;
import project.keys.manage_asym_keys;
import project.messages.Message;
import project.services.servicemessages.ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {

        Map<String,String> p=new HashMap();

        p.put("MyName","Bassel");

        p.put("encrypt","2");

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("FirstHandShake");

        ServiceMessage serviceMessage=new Facade().execute(msg);

        System.out.println(serviceMessage.get_result());

    }


    private static String getStringPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = new manage_asym_keys().get_public_key();
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");

        return rsaPublicKey;
    }

}
