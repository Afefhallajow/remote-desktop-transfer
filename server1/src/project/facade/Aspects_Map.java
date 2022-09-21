package project.facade;

import project.aspects.*;
import project.services.Hello_Service;
import project.services.Service;

import java.util.*;

public class Aspects_Map {


    private Map<String, Aspect> aspects=new HashMap<String, Aspect>();

    public Aspects_Map() {

        ////////initializes

        Aspect switch_letters=new switch_letters(new ArrayList<String>(Collections.singleton("hello_aspect")),
                                                    new ArrayList<String>(Collections.singleton("hello_aspect"))
                                                    ,false,false,null);


        Aspect forbidden=new forbidden_all(new ArrayList<>(),
                new ArrayList<>()
                ,false,false,null);


        Aspect Decrypt=new Decrypt(new ArrayList<>(),
                new ArrayList<>()
                ,true,true,null);

        Aspect Encrypt=new Encrypt(new ArrayList<>(),
                new ArrayList<>()
                ,true,true,null);


        List<String> expect=new ArrayList<>();

        expect.add("Check_token");
        expect.add("LogIn");
        expect.add("SignUp");

        Aspect check_token=new check_token_aspect(new ArrayList<>(),
                new ArrayList<>()
                ,true,false,expect);
        /////////add to map

//        aspects.put("switch_letters",switch_letters);
  //      aspects.put("forbidden",forbidden);

        aspects.put("Decrypt",Decrypt);
        aspects.put("Encrypt",Encrypt);
        aspects.put("check_token",check_token);
    }


    public Map<String, Aspect> get_aspects() {
        return aspects;
    }

}
