package project.aspects;

import org.json.JSONObject;
import project.DAO.PublicKeys_Dao;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.*;

public class Encrypt extends Aspect {
    public Encrypt(List<String> before_services, List<String> after_services, boolean before_all_services, boolean after_all_services, List<String> except) {
        super(before_services, after_services, before_all_services, after_all_services, except);
    }

    @Override
    public Object before(Object parameters) throws ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, SQLException {


        HashMap<String, String> p;
        JSONObject jsonObject;


        if(parameters.getClass().equals(Map.class) || parameters.getClass().equals(HashMap.class)) {
            System.out.println("map");

            p = (HashMap<String, String>) parameters;

            Map <String,String> result=new HashMap<>();

            if(!p.containsKey("sended") || p.get("sended").equals("0")
                    || !p.containsKey("encrypted") || !p.get("encrypted").equals("0")){
                return p;
            }
            else {

                if(p.get("encrypt").equals("1")){
                    return p;
                }
                else if (p.get("encrypt").equals("2") ){


                    String key="",value="";
                    PublicKey ServerKey=getClientPublicKey(p.get("UID"));
                    for (Map.Entry<String, String> entry : p.entrySet()) {

                        if (entry.getKey().equals("image") || entry.getKey().equals("encrypt") || entry.getKey().equals("service_name")
                                ||entry.getKey().equals("sended") || entry.getKey().equals("encrypted")) {
                            result.put(entry.getKey(), entry.getValue());
                        } else {
                            if (entry.getKey().equals("Key")) {
                                String temp = entry.getValue();
                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key1"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));
                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key2"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() / 3, temp.length() * 2 / 3)));
                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key3"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() * 2 / 3)));
                                result.put(key, value);


                            }else if(entry.getKey().equals("token")){
                                String temp=(String) entry.getValue();

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
                            else if (entry.getKey().equals("jwt_token")) {
                                String temp = entry.getValue();
                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token1"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));
                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token2"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() / 3, temp.length() * 2 / 3)));
                                result.put(key, value);

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token3"));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() * 2 / 3)));
                                result.put(key, value);


                            } else {

                                key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, entry.getKey()));
                                value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, entry.getValue()));

                                result.put(key, value);
                            }
                        }
                    }
                }

                else if (p.get("encrypt").equals("3")){

                    ////no thin'  to do

                }
                result.put("encrypted","1");

                return result;



            }
        }
        else if(parameters.getClass().equals(String.class)){

            String jsonstring =(String) parameters;

            jsonObject=new JSONObject(jsonstring);


            JSONObject result=new JSONObject();


            if(!jsonObject.has("sended") || jsonObject.get("sended").equals("0")
                    || !jsonObject.has("encrypted") || !jsonObject.get("encrypted").equals("0")){

                return jsonObject;

            }
            else {

                if(jsonObject.get("encrypt").equals("1")){
                    return jsonObject;
                }
                else if (jsonObject.get("encrypt").equals("2")){


                    String key="",value="";
                    PublicKey ServerKey=null;
                    if(jsonObject.has("UID")) {
                        ServerKey = getClientPublicKey((String) jsonObject.get("UID"));
                    }
                    else if(jsonObject.has("SecondUserID")) {
                        ServerKey= getClientPublicKey((String) jsonObject.get("SecondUserID"));
                    }
                    for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                        String s = it.next();

                        if(s.equals("image") ||s.equals("encrypt") ||s.equals("encrypted")||s.equals("sended")  || s.equals("service_name")){
                            result.put(s,jsonObject.get(s));
                        }   else {
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


                            }else if(s.equals("token")){
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
                }
                else if (jsonObject.get("encrypt").equals("3")){

                    result=jsonObject;

                    PublicKey ServerKey=null;

                    if(jsonObject.has("UID")) {
                        ServerKey = getClientPublicKey((String) jsonObject.get("UID"));
                        result.put("UID",Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, (String) jsonObject.get("UID"))));
                    }
                    else if(jsonObject.has("SecondUserID")) {
                        ServerKey= getClientPublicKey((String) jsonObject.get("SecondUserID"));
                        result.put("SecondUserID",Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, (String) jsonObject.get("SecondUserID"))));
                    }


                    /////////no thin' to do
                }
                result.put("encrypted","1");

                return result;
            }



        }

        return 0;
    }

    @Override
    public Object after(Object parameters) throws ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, SQLException {
        HashMap<String, String> p;
        JSONObject jsonObject;


        if(parameters.getClass().equals(Map.class) || parameters.getClass().equals(HashMap.class)) {
            System.out.println("map");

            p = (HashMap<String, String>) parameters;

            Map <String,String> result=new HashMap<>();


                if(!p.containsKey("sended")  || !p.get("sended").equals("0")
                        || !p.containsKey("encrypted") || !p.get("encrypted").equals("0")){
                return p;
            }
            else {

                if(p.get("encrypt").equals("1")){
                    return p;
                }
                else if (p.get("encrypt").equals("2") ){


                    String key="",value="";
                    PublicKey ServerKey=getClientPublicKey(p.get("UID"));
                    for (Map.Entry<String, String> entry : p.entrySet()) {

                            if (entry.getKey().equals("image")||entry.getKey().equals("encrypt") || entry.getKey().equals("service_name")
                            ||entry.getKey().equals("sended") || entry.getKey().equals("encrypted")) {
                                result.put(entry.getKey(), entry.getValue());
                            } else {
                                if (entry.getKey().equals("Key")) {
                                    String temp = entry.getValue();
                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key1"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));
                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key2"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() / 3, temp.length() * 2 / 3)));
                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "key3"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() * 2 / 3)));
                                    result.put(key, value);


                                }else if(entry.getKey().equals("token")){
                                    String temp=(String) entry.getValue();

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
                                else if (entry.getKey().equals("jwt_token")) {
                                    String temp = entry.getValue();
                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token1"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(0, temp.length() / 3)));
                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token2"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() / 3, temp.length() * 2 / 3)));
                                    result.put(key, value);

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, "jwt_token3"));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, temp.substring(temp.length() * 2 / 3)));
                                    result.put(key, value);


                                } else {

                                    key = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, entry.getKey()));
                                    value = Base64.getEncoder().encodeToString(encrypt_by_public_key(ServerKey, entry.getValue()));

                                    result.put(key, value);
                                }
                            }
                        }
                    }

                else if (p.get("encrypt").equals("3")){

                    ////no thin'  to do

                }
                result.put("encrypted","1");

                return result;



            }
        }
        else if(parameters.getClass().equals(String.class)){

            String jsonstring =(String) parameters;

            jsonObject=new JSONObject(jsonstring);


            JSONObject result=new JSONObject();



            if(!jsonObject.has("sended") || !jsonObject.get("sended").equals("0")
                    || !jsonObject.has("encrypted") || !jsonObject.get("encrypted").equals("0")){

                return jsonObject;

            }
            else {

                if(jsonObject.get("encrypt").equals("1")){
                    return jsonObject;
                }
                else if (jsonObject.get("encrypt").equals("2")){


                    String key="",value="";
                    PublicKey ServerKey=getClientPublicKey((String) jsonObject.get("UID"));

                    for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                        String s = it.next();

                        if(s.equals("image")||s.equals("encrypt") ||s.equals("encrypted")||s.equals("sended")  || s.equals("service_name")){
                            result.put(s,jsonObject.get(s));
                        }   else {
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


                            }else if(s.equals("token")){
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
                }
                else if (jsonObject.get("encrypt").equals("3")){

                    result=jsonObject;
                    /////////no thin' to do
                }
                result.put("encrypted","1");

                return result;
            }



        }

return 0;
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


    private PublicKey getClientPublicKey(String UID) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException, SQLException {

        ArrayList<ArrayList<String>> userDB=new PublicKeys_Dao().FindByUserID(UID);

        String rsaPublicKey = (String) userDB.get(1).get(2);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }





/*
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
*/
}
