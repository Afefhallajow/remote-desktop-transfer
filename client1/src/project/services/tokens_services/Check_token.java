package project.services.tokens_services;

import project.keys.manage_asym_keys;
import project.requests.Request;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import project.errors.Error;
public class Check_token extends Service {
    public Check_token() {
        super("Check_token");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();

        // parameters empty

        String data="";

        try {
            FileInputStream fout = new FileInputStream("data.dat");
            ObjectInputStream oos = new ObjectInputStream(fout);
            Map<String, byte[]> m = (HashMap) oos.readObject();

             data = decrypt(getPrivateKey(), m.get("t1"));
            data = data + decrypt(getPrivateKey(), m.get("t2"));
            data = data + decrypt(getPrivateKey(), m.get("t3"));

        } catch (NoSuchAlgorithmException | InvalidKeySpecException  | FileNotFoundException e) {
            servicemessage.add("result","false");
            servicemessage.add("error",Error.FileNotFound.toString());
            return servicemessage;
        }


        Map object=make_map();

        object=add_map_to_map(object,parameters);

        object.put("token",data);

        Request request=make_request("json", object) ;

        ServiceMessage serviceMessage=send_request("socket","server",request);


        if(serviceMessage.get_result().containsKey("error")){

            servicemessage.add("error",serviceMessage.get_result().get("error"));
            servicemessage.add("result","false");
            return servicemessage;

        }


        servicemessage.add("UID",serviceMessage.get_result().get("UID"));
        servicemessage.add("result","true");


        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }



    private static String decrypt(Key decryptionKey, byte[] buffer) {
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.DECRYPT_MODE, decryptionKey);
            byte[] utf8 = rsa.doFinal(buffer);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPrivateKey = new manage_asym_keys().get_private_key();

        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;
    }

}
