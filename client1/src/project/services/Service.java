package project.services;

import project.keys.manage_asym_keys;
import project.requests.Json_Request;
import project.requests.Request;
import project.requests.Simple_Request;
import project.senders.Sender;
import project.senders.Socket_Sender;
import project.services.servicemessages.ServiceMessage;
import project.users.Users;

import javax.crypto.Cipher;
import java.awt.*;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public abstract class Service {

    private String name;
    protected ServiceMessage servicemessage=intalize_servicemessage();

    public Service(String name) {
        this.name = name;
    }

    public String get_name(){
        return name;
    }

    protected Map<String,String>make_map(){
        Map<String,String> map=new HashMap<>();
        map.put("service_name",name);

        return map;
    }

    protected Map<String,String>add_map_to_map(Map<String,String> map1,Map<String,String> map2){

        for (Map.Entry<String, String> entry : map2.entrySet()) {

            map1.put(entry.getKey(), entry.getValue());
        }
        return map1;
    }


    protected Request make_request(String request_type,Object object){
        Request request = null;

        if(request_type.equals("json")){
            request =new Json_Request();
        }
        else if(request_type.equals("simple")){
            request =new Simple_Request();
        }



        request.add(object);

        return request;
    }


    protected ServiceMessage send_request(String sender_type,String receiver_name,Request request) throws IOException, ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        Sender sender=null;
        Map<String,String> user=new Users().get_port_ip_by_name(receiver_name);
        if (sender_type.equals("socket")){
            sender=new Socket_Sender(user.get("IP"),user.get("RecievePort"));
        }
        sender.fill_sender_information();

        ServiceMessage serviceMessage=sender.send_request(request);

        return serviceMessage;

    }


    private PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = new manage_asym_keys().get_public_key();
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

    protected byte[] encrypt( String text) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Key pubkey=getPublicKey();
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.ENCRYPT_MODE, pubkey);
            return rsa.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    abstract public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException, AWTException, InvalidKeySpecException, NoSuchAlgorithmException;

    abstract public ServiceMessage intalize_servicemessage();



}
