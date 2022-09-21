package project.aspects;

import org.json.JSONObject;
import project.keys.manage_asym_keys;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

////////////////// 2 Decrypt using server public key
////////////////// 3 Decrypt using my private key
public class Decrypt extends Aspect{
    public Decrypt(List<String> before_services, List<String> after_services, boolean before_all_services, boolean after_all_services, List<String> except) {
        super(before_services, after_services, before_all_services, after_all_services, except);
    }

    @Override
    public Object before(Object parameters) throws ClassNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        System.out.println("decrypt before ");
        JSONObject jsonObject;
        HashMap<String, String> p;

        String token1="",token2="",token3="";
        String jwt1 = "", jwt2 = "", jwt3 = "";
        String key1 = "", key2 = "", key3 = "";

        if (parameters.getClass().equals(Map.class) || parameters.getClass().equals(HashMap.class)) {


            p = (HashMap<String, String>) parameters;

            Map<String, String> endresult = new HashMap<>();

            if (!p.containsKey("sended")
                    || !p.get("sended").equals("0")
                    ||!p.containsKey("encrypted")
                    || !p.get("encrypted").equals("1")) {

                return p;
            } else {

                if (p.get("encrypt").equals("1")) {
                    return p;
                }
                else if (p.get("encrypt").equals("2") || p.get("encrypt").equals("3")) {


                    String key = "", value = "";
                    PrivateKey MyKey = getMyPrivateKey();

                    String encryptedKey = "";

                    for (Map.Entry<String, String> entry : p.entrySet()) {

                        if (entry.getKey().equals("image")||entry.getKey().equals("encrypt") || entry.getKey().equals("encrypted")
                                ||entry.getKey().equals("sended") || entry.getKey().equals("service_name")
                                ||entry.getKey().equals("SecondUserID")) {
                            endresult.put(entry.getKey(), entry.getValue());
                        } else {
                            encryptedKey = entry.getKey();

                            encryptedKey = decrypt(MyKey, Base64.getDecoder().decode(encryptedKey));

                            if (encryptedKey.equals("jwt_token1")) {
                                jwt1 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("jwt_token2")) {
                                jwt2 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("jwt_token3")) {
                                jwt3 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }
                            else if (encryptedKey.equals("token1")) {
                                token1 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }
                            else if (encryptedKey.equals("token2")) {
                                token2 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }
                            else if (encryptedKey.equals("token3")) {
                                token3 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }else if (encryptedKey.equals("key1")) {
                                key1 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("key2")) {
                                key2 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("key3")) {
                                key3 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else {

                                endresult.put(encryptedKey, decrypt(MyKey, Base64.getDecoder().decode(entry.getValue())));
                            }
                        }
                    }

                    if (!key1.equals("")) {
                        endresult.put("Key", key1 + key2 + key3);
                    }
                    if (!jwt1.equals("")) {
                        endresult.put("jwt_token", jwt1 + jwt2 + jwt3);
                    }
                    if (!token1.equals("")) {
                        endresult.put("token", token1 + token2 + token3);
                    }

                }
            }

            endresult.put("encrypted", "0");
            return endresult;





        }else if (parameters.getClass().equals(String.class)) {

            String jsonstring = (String) parameters;

            jsonObject = new JSONObject(jsonstring);


            JSONObject Jsonresult = new JSONObject();


            if (!jsonObject.has("sended")
                    || !jsonObject.get("sended").equals("0")
                    ||!jsonObject.has("encrypted")
                    || !jsonObject.get("encrypted").equals("1")) {

                return jsonObject;

            } else {

                if (jsonObject.get("encrypt").equals("1")) {
                    return jsonObject;

                } else if (jsonObject.get("encrypt").equals("2") || jsonObject.get("encrypt").equals("3")) {




                    String key = "", value = "";
                    PrivateKey MyKey = getMyPrivateKey();

                    String encryptedKey="";

                    for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                        String s = it.next();

                        if(s.equals("encrypt") || s.equals("encrypted")
                                ||s.equals("service_name") || s.equals("sended") ){
                            Jsonresult.put(s,jsonObject.get(s));
                        }else {
                            encryptedKey=s;
                            encryptedKey = decrypt(MyKey,Base64.getDecoder().decode(encryptedKey));

                            if (encryptedKey.equals("jwt_token1")) {
                                jwt1 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("jwt_token2")) {
                                jwt2 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("jwt_token3")) {
                                jwt3 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("token1")) {
                                token1 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("token2")) {
                                token2 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("token3")) {
                                token3 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("key1")) {
                                key1 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("key2")) {
                                key2 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("key3")) {
                                key3 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            }
                            else {

                                Jsonresult.put(encryptedKey, decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s))));
                            }
                        }
                    }

                    if(!key1.equals("")){
                        Jsonresult.put("Key",key1+key2+key3);
                    }
                    if(!jwt1.equals("")){
                        Jsonresult.put("jwt_token",jwt1+jwt2+jwt3);
                    }

                    if(!token1.equals("")){
                        Jsonresult.put("token",token1+token2+token3);
                    }

                }
            }

            Jsonresult.put("encrypted","0");
            return Jsonresult;
        }

        return parameters;
    }


    @Override
    public Object after(Object result) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException {

        System.out.println("decrypt after ");
        JSONObject jsonObject;
        HashMap<String, String> p;

        String token1="",token2="",token3="";
        String jwt1 = "", jwt2 = "", jwt3 = "";
        String key1 = "", key2 = "", key3 = "";
        String image1="",image2="",image3="";

        if (result.getClass().equals(Map.class) || result.getClass().equals(HashMap.class)) {


            p = (HashMap<String, String>) result;

            Map<String, String> endresult = new HashMap<>();

            if (!p.containsKey("sended")
                    || p.get("sended").equals("0")
                    ||!p.containsKey("encrypted")
                    || !p.get("encrypted").equals("1")) {

                return p;
            } else {

                if (p.get("encrypt").equals("1")) {
                    return p;
                }
                else if (p.get("encrypt").equals("2") || p.get("encrypt").equals("3")) {




                    String key = "", value = "";
                    PrivateKey MyKey = getMyPrivateKey();

                    String encryptedKey = "";

                    for (Map.Entry<String, String> entry : p.entrySet()) {

                        if (entry.getKey().equals("image") ||
                                entry.getKey().equals("encrypt") || entry.getKey().equals("encrypted")
                                ||entry.getKey().equals("sended") || entry.getKey().equals("service_name")) {
                            endresult.put(entry.getKey(), entry.getValue());
                        } else {
                            encryptedKey = entry.getKey();
                            encryptedKey = decrypt(MyKey, Base64.getDecoder().decode(encryptedKey));

                            if (encryptedKey.equals("jwt_token1")) {
                                jwt1 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("jwt_token2")) {
                                jwt2 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("jwt_token3")) {
                                jwt3 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }
                            else if (encryptedKey.equals("token1")) {
                                token1 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }
                            else if (encryptedKey.equals("token2")) {
                                token2 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }
                            else if (encryptedKey.equals("token3")) {
                                token3 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }else if (encryptedKey.equals("key1")) {
                                key1 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("key2")) {
                                key2 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            } else if (encryptedKey.equals("key3")) {
                                key3 = decrypt(MyKey, Base64.getDecoder().decode(entry.getValue()));
                            }

                            else {

                                endresult.put(encryptedKey, decrypt(MyKey, Base64.getDecoder().decode(entry.getValue())));
                            }
                        }
                    }

                    if (!key1.equals("")) {
                        endresult.put("Key", key1 + key2 + key3);
                    }
                    if (!jwt1.equals("")) {
                        endresult.put("jwt_token", jwt1 + jwt2 + jwt3);
                    }
                    if (!token1.equals("")) {
                        endresult.put("token", token1 + token2 + token3);
                    }

                }
            }

            endresult.put("encrypted", "0");
            return endresult;





        }else if (result.getClass().equals(String.class)) {

            String jsonstring = (String) result;

            jsonObject = new JSONObject(jsonstring);


            JSONObject Jsonresult = new JSONObject();


            if (!jsonObject.has("sended")
                    || jsonObject.get("sended").equals("0")
                    ||!jsonObject.has("encrypted")
                    || !jsonObject.get("encrypted").equals("1")) {

                return jsonObject;

            } else {

                if (jsonObject.get("encrypt").equals("1")) {
                    return jsonObject;

                } else if (jsonObject.get("encrypt").equals("2") || jsonObject.get("encrypt").equals("3")) {




                    String key = "", value = "";
                    PrivateKey MyKey = getMyPrivateKey();

                    String encryptedKey="";

                    for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                        String s = it.next();

                        if(       s.equals("image")
                                ||s.equals("encrypt") || s.equals("encrypted")
                                ||s.equals("service_name") || s.equals("sended") ){
                            Jsonresult.put(s,jsonObject.get(s));
                        }else {
                            encryptedKey=s;
                            encryptedKey = decrypt(MyKey,Base64.getDecoder().decode(encryptedKey));

                            if (encryptedKey.equals("jwt_token1")) {
                                jwt1 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("jwt_token2")) {
                                jwt2 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("jwt_token3")) {
                                jwt3 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("token1")) {
                                token1 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("token2")) {
                                token2 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("token3")) {
                                token3 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("key1")) {
                                key1 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("key2")) {
                                key2 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            } else if (encryptedKey.equals("key3")) {
                                key3 = decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s)));
                            }
                            else {

                                Jsonresult.put(encryptedKey, decrypt(MyKey,Base64.getDecoder().decode((String) jsonObject.get(s))));
                            }
                        }
                    }

                    if(!key1.equals("")){
                        Jsonresult.put("Key",key1+key2+key3);
                    }
                    if(!jwt1.equals("")){
                        Jsonresult.put("jwt_token",jwt1+jwt2+jwt3);
                    }

                    if(!token1.equals("")){
                        Jsonresult.put("token",token1+token2+token3);
                    }

                }
            }

            Jsonresult.put("encrypted","0");
            return Jsonresult;
        }

        return result;
            }


            /////////////////////////////////////////////////







    private String decrypt(Key pubkey,byte[] Text) throws InvalidKeySpecException, ClassNotFoundException, NoSuchAlgorithmException, IOException {

        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.DECRYPT_MODE, pubkey);
            byte[] utf8 = rsa.doFinal(Text);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



    private PrivateKey getMyPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {


        String rsaPrivateKey = new manage_asym_keys().get_private_key();

        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;

    }




    }


