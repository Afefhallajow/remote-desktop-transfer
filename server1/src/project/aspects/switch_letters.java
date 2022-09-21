package project.aspects;

import project.services.Service;

import java.util.List;
import java.util.Map;

public class switch_letters extends Aspect {

    public switch_letters(List<String> before_services,List<String> after_services,
                          boolean before_all_services,boolean after_all_services,List<String> except){

        super(before_services,after_services,before_all_services,after_all_services,except);

    }

    @Override
    public Object before(Object parameters) {
    /*        String s=(String) parameters.get("msg");

            char[] c=s.toCharArray();


        for (int i=0;i<c.length;i++){


            if(c[i]=='q'){
                c[i]='h';
            }
            else if(c[i]=='p'){
                c[i]='l';
            }

        }
            parameters.put("msg",String.valueOf(c));
            return parameters;
  */return 0;      }

    @Override
    public Object after(Object result) {
    /*    String s=(String) result.get("msg");

        char[] c=s.toCharArray();

        for (int i=0;i<c.length;i++){
            if(c[i]=='h'){
                c[i]='q';
            }
            else if(c[i]=='l'){
                c[i]='p';
            }
        }
        result.put("msg",String.valueOf(c));
        return result;
    }*/return 0;
}}
