package project.users;

import project.DAO.User_Dao;
import sun.rmi.log.LogInputStream;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {



    public Map<String,String> get_port_ip_by_UID(String UserID) throws FileNotFoundException, SQLException {
        ArrayList<ArrayList<String>> userDB1= new User_Dao().FindByUserID(UserID);
        if(userDB1.size()==1){return null;}

        ArrayList<String> u0=userDB1.get(0);
        ArrayList<String> u1=userDB1.get(1);
        Map<String,String> result=new HashMap<>();

        for(int i=0;i<u0.size()-1;i++){
            if(u0.get(i).equals("RecievePort")||u0.get(i).equals("IP")){
                result.put(u0.get(i),u1.get(i));

            }
        }

       return result;
    }





}
