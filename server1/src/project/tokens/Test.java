package project.tokens;

import com.nimbusds.jose.JOSEException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException {
       // generate_token generate_token=new generate_token();
        //validate_token validate_token=new validate_token();

        //Map para=new HashMap();
        //para.put("Name","bassel1");
        //para.put("Password","lkjlkj");
        //String jwt=generate_token.createJwt(para);
        //System.out.println(jwt);
        Map para2= Extract_token.parseJwt("eyJlbmMiOiJBMTI4R0NNIiwiYWxnIjoiUlNBLU9BRVAifQ.IK6kFNSdRkHZxN4dhXxTQu4UCrPbGj7CayMQihtraIi27y9cb5lK8zCTDGONYw6ec4dnZypU1fkdr9MYXt_UvVwSxVzTeqqErLtF-LE3N6UGD4HQIuxW0nQc9zy2ykssufFCNwZBFF92o_ghQPX4e2pclQ-Stc9HK7nnEUMClQ1hL8_-L8Lvcnw7xOcW9G32jGJBG0dNgtTp9qxc2MfBF8pjvyy28FPNVZPN6jPIR8evpSCV2bEcmiuI41Avaqubtn2sPTgVnjSzMt5WIsW4e_nqgw_c42zCqYyuMxOtgCHuSAj5qIN5BFUmVivjLa0sdDaSwyEKHU0cLP4leuNB_g.UrlSvvmZOPVPA8jV._fq6mvsiQ1yEMtWJDyiJGMlUg0Cr1zi4EAiG2fWSCUCjJvyt51KOGeba_jTUhSr1sjRAvW-amuQXtdHkdn8TqYNhm_i7AA0iPF0QHW5JHs8MShiGpP43UIs20hm49e9xn1i9OfWgwb6KQqMDYdJA241u5X-2nqIc8FfzyQ-br7-Q.a-XX3zARtE1QoQfbwSN25w");
        System.out.println(para2);
    }
}
