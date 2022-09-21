package project.services.handShake;

import project.DAO.PublicKeys_Dao;
import project.keys.manage_asym_keys;
import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.FileNotFoundException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SecondHandShake_service extends Service {


    public SecondHandShake_service() {
        super("SecondHandShake");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws SQLException, FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        servicemessage=intalize_servicemessage();

        if(parameters.containsKey("encrypted"))
        {
            servicemessage.add("encrypted",parameters.get("encrypted"));
        }

        if(parameters.containsKey("encrypt"))
        {
            servicemessage.add("encrypt",parameters.get("encrypt"));
        }

        String UID=(String) parameters.get("UID");
        String Key=(String) parameters.get("Key");

        Map<String,String> user=new HashMap<>();

        user.put("UID",UID);
        user.put("Key",Key);

        new PublicKeys_Dao().Update(user);

        servicemessage.add("Key",getStringPublicKey());

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
