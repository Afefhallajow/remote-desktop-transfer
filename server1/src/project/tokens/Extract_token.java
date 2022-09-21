package project.tokens;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;

import project.keys.manage_asym_keys;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Map;

public class Extract_token {

    public static Map<String, Object> parseJwt(String jwtString) throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException {

        PrivateKey privateKey = getPrivateKey();
        EncryptedJWT jwt = EncryptedJWT.parse(jwtString);


        // create a decrypter with the specified private RSA key
        RSADecrypter decrypter = new RSADecrypter((RSAPrivateKey)privateKey);

        // do the decryption
        jwt.decrypt(decrypter);


        return jwt.getJWTClaimsSet().getClaims();
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
