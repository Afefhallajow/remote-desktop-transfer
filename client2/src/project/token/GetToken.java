package project.token;



import project.keys.manage_asym_keys;

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
import java.util.*;

public class GetToken {
    private static volatile GetToken getToken;
    private static volatile String token="";
    private static volatile Date end_date;

    private GetToken() {
    }

    public static GetToken get_instance(){
        //p will be null if you made instance before
        if (getToken==null){

            synchronized (GetToken.class){
                if (getToken==null){
                    getToken=new GetToken();
                }
            }
        }

        return getToken;
    }


    public synchronized String getTokenString(){
        if(token.equals("")){

            token=get_token_from_file();

            Calendar date = Calendar.getInstance();

            long timeInSecs = date.getTimeInMillis();
            end_date = new Date(timeInSecs + (1 * 60 * 1000));

        }
        else if(end_date.before(new Date())){
            token=get_token_from_file();

            Calendar date = Calendar.getInstance();

            long timeInSecs = date.getTimeInMillis();
            end_date = new Date(timeInSecs + (1 * 60 * 1000));
        }


        return token;
    }

    private String get_token_from_file(){
        String data="";
        try {

            FileInputStream fout = new FileInputStream("data.dat");

            ObjectInputStream oos = new ObjectInputStream(fout);
            Map<String, byte[]> m = (HashMap) oos.readObject();

            data=decrypt(getPrivateKey(),m.get("t1"));
            data=data+decrypt(getPrivateKey(),m.get("t2"));
            data=data+decrypt(getPrivateKey(),m.get("t3"));



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("file not found !!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
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
