package project.services.handShake;

import project.keys.manage_asym_keys;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class SecondHandShake_service extends Service {


    public SecondHandShake_service() {
        super("SecondHandShake");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {

        servicemessage=intalize_servicemessage();

      //  String UID=(String) parameters.get("UID");


        String Key=getStringPublicKey();

        String serverKey=(String) parameters.get("Key");
        try{
            FileOutputStream fout = new FileOutputStream("ServerKey.dat");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    Map<String, String> m = new HashMap<>();
                    String data = serverKey;

                    m.put("Key", data);

                    oos.writeObject(m);
                    oos.close();


        }catch (Exception e) {
            e.printStackTrace();
        }


        servicemessage.add("Key",Key);

        return servicemessage;
    }


    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }



    private static String getStringPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = new manage_asym_keys().get_public_key();
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");

        return rsaPublicKey;
    }


}
