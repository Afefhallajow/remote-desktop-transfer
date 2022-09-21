package project.DAO;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class User_Dao extends Main_Dao {

    private static AtomicInteger ID_GENERATOR ;

    public User_Dao() throws FileNotFoundException, SQLException {


        ID_GENERATOR=new AtomicInteger((get_last_id())+1);
    }

    @Override
    protected void defined_variables() {

        VariableNumber=10;


        Variables="ID"+ ","+
                "UserID"+ ","+
                "Name"+ ","+
                "Password"+ ","+
                "Email"+ ","+
                "BirthDate"+ ","+
                "Gender"+ ","+
                "IP"+","+
                "RecievePort"+","+
                "CreateDate";

    }

    @Override
    protected void Table_Name() {
        TableName="users";
    }




    @Override
    public Map Insert(Object object) throws SQLException {
        int ID = ID_GENERATOR.getAndIncrement();

//        System.out.println(ID);
        String UID="UID-";

        String temp="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] lang=temp.toCharArray();
        Random random=new Random();

        int k=0;
        for(int i=0;i<10;i++){
            k=random.nextInt(71);
            UID=UID+lang[k];
        }

        //System.out.println(UID);
        Map<String,String> user=(Map<String, String>) object;

        String name=user.get("Name");
        String password=user.get("Password");
        String email=user.get("Email");
        String birthday=user.get("Birthday");
        int Gender = 0;
        if(user.get("Gender").equals("true")) {
            Gender=1;
        }
        String ip=user.get("IP");
        String RecivePort=user.get("RecievePort");


        Date dt = new Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String Time= sdf.format(dt);;


        String query=make_query("insert");

        List<Object> data=new ArrayList<>();
        data.add(ID);
        data.add(UID);
        data.add(name);
        data.add(password);
        data.add(email);
        data.add(birthday);
        data.add(Gender);
        data.add(ip);
        data.add(RecivePort);
        data.add(Time);

        do_query(query,data);



        user.put("Time",Time);
        user.put("UID",UID);

        return user;
    }

    @Override
    public void Update(Object object) throws SQLException {


        Map<String,String> user=(Map<String, String>) object;

        int ID=Integer.parseInt(user.get("ID"));

        ArrayList<String> db_user=null;
        if(!user.containsKey("Name") || !user.containsKey("Password")
                || !user.containsKey("IP") || !user.containsKey("RecivePort")
                 || !user.containsKey("UID")){

            db_user=FindById(ID).get(1);

        }

        String UID="";
        if(!user.containsKey("UID") && db_user!=null ){
            UID=db_user.get(1);
        }
        else {
            UID=user.get("UID");
        }

        String name="";
            if(!user.containsKey("Name") && db_user!=null ){
                name=db_user.get(2);
            }
            else {
                name=user.get("Name");
            }


        String password="";
        if(!user.containsKey("Password") && db_user!=null ){
            password=db_user.get(3);
        }
        else {
            password=user.get("Password");
        }

        String ip="";
        if(!user.containsKey("IP") && db_user!=null ){
            ip=db_user.get(4);
        }
        else {
            ip=user.get("IP");
        }

        String RecivePort="";
        if(!user.containsKey("RecievePort") && db_user!=null ){
            RecivePort=db_user.get(5);
        }
        else {
            RecivePort=user.get("RecievePort");
        }



        String CreateDate=db_user.get(6);

        String query=make_query("updatebyid");



        List<Object> data=new ArrayList<>();

        data.add(UID);
        data.add(name);
        data.add(password);
        data.add(ip);
        data.add(RecivePort);
        data.add(CreateDate);
        data.add(ID);//where

        do_query(query,data);

    }

    @Override
    public void DeleteById(int id) throws SQLException {

        String query=make_query("deletbyid");
        List<Object> data=new ArrayList<Object>();
        data.add(id);
        do_query(query,data);
    }

    @Override
    public void DeleteByName(String name) throws SQLException {

        String query=make_query("deletbyname");
        List<Object> data=new ArrayList<>();
        data.add(name);

        do_query(query,data);

    }

    @Override
    public ArrayList<ArrayList<String>> FindAll() throws SQLException {

        String query=make_query("findall");

        return do_query(query,new ArrayList<>());

    }

    @Override
    public ArrayList<ArrayList<String>> FindById(int id) throws SQLException {

        String query=make_query("findbyid");
        List<Object> data=new ArrayList<Object>();
        data.add(id);
        return do_query(query,data);

    }

    @Override
    public ArrayList<ArrayList<String>> FindByName(String name) throws SQLException {

        String query=make_query("findbyname");
        List<Object> data=new ArrayList<>();
        data.add(name);

        return do_query(query,data);
    }


    public ArrayList<ArrayList<String>> FindByUserID(String UserID) throws SQLException {

        String query=make_modified_query("findbyname","UserID = ",0);
        List<Object> data=new ArrayList<>();
        data.add(UserID);

        return do_query(query,data);
    }


}
