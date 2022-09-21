package project.tokens;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import project.keys.manage_asym_keys;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;

public class generate_token {

    public static String createJwt(Map parameters){

        try {


            PublicKey publicKey = getPublicKey();

            JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
            //claimsSet.issuer("https://my-auth-server.com");
            claimsSet.subject("RDS");
            claimsSet.claim("Name",parameters.get("Name"));
            claimsSet.claim("Password",parameters.get("Password"));

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            claimsSet.expirationTime(new Date(timeInSecs + (  1000*60*60*24*7)));
            claimsSet.notBeforeTime(new Date());
            claimsSet.jwtID(UUID.randomUUID().toString());



            // create the JWT header and specify:
            //  RSA-OAEP as the encryption algorithm
            //  128-bit AES/GCM as the encryption method
            JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A128GCM);

            // create the EncryptedJWT object
            EncryptedJWT jwt = new EncryptedJWT(header, claimsSet.build());

            // create an RSA encrypter with the specified public RSA key
            RSAEncrypter encrypter = new RSAEncrypter((RSAPublicKey) publicKey);


            // do the actual encryption
            jwt.encrypt(encrypter);

            // serialize to JWT compact form
            String jwtString = jwt.serialize();

            return jwtString;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }



    private static PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = new manage_asym_keys().get_public_key();
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

}
