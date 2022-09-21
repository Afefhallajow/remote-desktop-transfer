package project.aspects;

import org.json.JSONObject;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class Encrypt extends Aspect {
    public Encrypt(List<String> before_services, List<String> after_services, boolean before_all_services, boolean after_all_services, List<String> except) {
        super(before_services, after_services, before_all_services, after_all_services, except);
    }

    @Override
    public Object before(Object parameters) throws ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        HashMap<String, String> p;
        JSONObject jsonObject;


        if(parameters.getClass().equals(Map.class) || parameters.getClass().equals(HashMap.class)) {


             p = (HashMap<String, String>) parameters;

            Map <String,String> result=new HashMap<>();

            if(!p.containsKey("sended") || p.get("sended").equals("0")){

                return p;

            }
            else {

                if(p.get("encrypt").equals("1")){
                    return p;
                }
                else if (p.get("encrypt").equals("2")){


                    String key="",value="";
                    PublicKey ServerKey=getServerPublicKey();

                    for (Map.Entry<String, String> entry : p.entrySet()) {

                        if(entry.getKey().equals("encrypt")){
                            result.put(entry.getKey(),entry.getValue());
                        }   else {
                            key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, entry.getKey()));
                            value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, entry.getValue()));

                            result.put(key, value);

                        }
                    }
                }
                else if (p.get("encrypt").equals("3")){

                    String key="",value="";

                    PublicKey ClientKey=null;
                    if (p.containsKey("SecondUserID")) {
                        ClientKey = getClientPublicKey(p.get("SecondUserID"));
                    }
                    else if(p.containsKey("UID"))
                    {
                        ClientKey = getClientPublicKey(p.get("UID"));
                    }

                    PublicKey ServerKey=getServerPublicKey();

                    for (Map.Entry<String, String> entry : p.entrySet()) {

                        if(entry.getKey().equals("encrypt")){
                            result.put(entry.getKey(),entry.getValue());
                        }
                        else if(entry.getKey().equals("SecondUserID")){
                            key = entry.getKey();
                            value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey,entry.getValue()));

                        }else {
                            key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey,entry.getKey()));
                            value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey,entry.getValue()));
                        }

                        result.put(key, value);
                    }
                }

                return result;





            }
        }
        else if(parameters.getClass().equals(String.class)){

            String jsonstring =(String) parameters;

             jsonObject=new JSONObject(jsonstring);


            JSONObject result=new JSONObject();


            if(!jsonObject.has("sended") || jsonObject.get("sended").equals("0")){

                return jsonObject;

            }
            else {

                if(jsonObject.get("encrypt").equals("1")){
                    return jsonObject;
                }
                else if (jsonObject.get("encrypt").equals("2")){

                    String key="",value="";
                    PublicKey ServerKey=getServerPublicKey();

                    for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                        String s = it.next();

                        if(s.equals("image") || s.equals("encrypted")|| s.equals("encrypt") || s.equals("service_name") ||s.equals("sended")){
                            result.put(s,jsonObject.get(s));
                        } else {
                            if(s.equals("Key")){
                                String temp=(String) jsonObject.get(s);
                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key1"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));
                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey,  "key2"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey,temp.substring( temp.length() / 3,temp.length() * 2 / 3)));
                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key3" ));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey,temp.substring(temp.length() * 2/ 3)));
                                result.put(key, value);

                            }
                            else if(s.equals("token")){
                                String temp=(String) jsonObject.get(s);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "token1"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));

                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "token2"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring( temp.length() / 3,temp.length() * 2 / 3)));
                                result.put(key, value);

                                key =  Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "token3"));
                                value =Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() * 2/ 3)));
                                result.put(key, value);


                            }
                            else if(s.equals("jwt_token")){
                                String temp=(String) jsonObject.get(s);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token1"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));

                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token2"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring( temp.length() / 3,temp.length() * 2 / 3)));
                                result.put(key, value);

                                key =  Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token3"));
                                value =Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() * 2/ 3)));
                                result.put(key, value);


                            }

                            else {
                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, s));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, (String) jsonObject.get(s)));

                                result.put(key, value);
                            }
                        }
                    }
                    result.put("encrypted","1");
                }
                else if (jsonObject.get("encrypt").equals("3")){

                        String key="",value="";

                        PublicKey ClientKey=null;

                        if (jsonObject.has("SecondUserID")) {
                            ClientKey = getClientPublicKey((String) jsonObject.get("SecondUserID"));
                        }
                        else if(jsonObject.has("UID"))
                        {
                            ClientKey = getClientPublicKey((String) jsonObject.get("UID"));
                        }

                        PublicKey ServerKey=getServerPublicKey();

                        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                            String s = it.next();


                            if(s.equals("image") ||s.equals("encrypted") ||s.equals("encrypt") || s.equals("service_name") ||s.equals("sended")){
                                result.put(s,jsonObject.get(s));
                            }else if(s.equals("SecondUserID")) {
                                key = s;
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, (String) jsonObject.get(s)));
                                result.put(key, value);
                            }else {
                                if(s.equals("Key")){
                                    String temp=(String) jsonObject.get(s);
                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "key1"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring(0, temp.length() / 3)));
                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey,  "key2"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey,temp.substring( temp.length() / 3,temp.length() * 2 / 3)));
                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "key3" ));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey,temp.substring(temp.length() * 2/ 3)));
                                    result.put(key, value);

                                }
                                else if(s.equals("token")){
                                    String temp=(String) jsonObject.get(s);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "token1"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring(0, temp.length() / 3)));

                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "token2"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring( temp.length() / 3,temp.length() * 2 / 3)));
                                    result.put(key, value);

                                    key =  Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "token3"));
                                    value =Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring(temp.length() * 2/ 3)));
                                    result.put(key, value);


                                }
                                else if(s.equals("jwt_token")){
                                    String temp=(String) jsonObject.get(s);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "jwt_token1"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring(0, temp.length() / 3)));

                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "jwt_token2"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring( temp.length() / 3,temp.length() * 2 / 3)));
                                    result.put(key, value);

                                    key =  Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, "jwt_token3"));
                                    value =Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, temp.substring(temp.length() * 2/ 3)));
                                    result.put(key, value);


                                }


                                else {
                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, s));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ClientKey, (String) jsonObject.get(s)));

                                    result.put(key, value);
                                }
                            }
                        }
                        result.put("encrypted","1");
                    }


                    return result;
            }



        }


        return 0;
    }

    @Override
    public Object after(Object result) {
        return result;
    }





    private byte[] encrypt_by_public_key(PublicKey pubkey, String plainText) throws InvalidKeySpecException, ClassNotFoundException, NoSuchAlgorithmException, IOException {

        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.ENCRYPT_MODE, pubkey);
            return rsa.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private PublicKey getServerPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException {
        FileInputStream fout = new FileInputStream("ServerKey.dat");
        ObjectInputStream ois = new ObjectInputStream(fout);
        HashMap<String, String> m =(HashMap<String, String>) ois.readObject();

        String data = (String) m.get("Key");

        String rsaPublicKey = data;

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }






    private PublicKey getClientPublicKey(String UID) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException {
        FileInputStream fout = new FileInputStream(UID+".dat");
        ObjectInputStream ois = new ObjectInputStream(fout);
        HashMap<String, String> m =(HashMap<String, String>) ois.readObject();
        String data = (String) m.get("Key");



        String rsaPublicKey = data;
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

}
