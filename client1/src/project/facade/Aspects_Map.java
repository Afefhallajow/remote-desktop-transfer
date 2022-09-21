package project.facade;

import project.aspects.Aspect;
import project.aspects.Decrypt;
import project.aspects.Encrypt;
import project.aspects.switch_letters;
import project.services.Hello_Service;
import project.services.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Aspects_Map {

    private Map<String, Aspect> aspects=new HashMap<String, Aspect>();

    public Aspects_Map() {

        ////////initializes

        Aspect switch_letters=new switch_letters(new ArrayList<String>(Collections.singleton("hello_aspect")),
                                                    new ArrayList<String>(Collections.singleton("hello_aspect"))
                                                    ,false,false,null);




        Aspect Encrypt=new Encrypt(new ArrayList<>(),
                new ArrayList<>()
                ,true,true,null);

        Aspect Decrypt=new Decrypt(new ArrayList<>(),
                new ArrayList<>()
                ,true,true,null);



        /////////add to map

        //aspects.put("switch_letters",switch_letters);
        aspects.put("Encrypt",Encrypt);
        aspects.put("Decrypt",Decrypt);


    }


    public Map<String, Aspect> get_aspects() {
        return aspects;
    }

}
