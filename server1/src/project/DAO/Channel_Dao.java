package project.DAO;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Channel_Dao extends Main_Dao {

    private static AtomicInteger ID_GENERATOR ;

    public Channel_Dao() throws FileNotFoundException, SQLException {


        ID_GENERATOR=new AtomicInteger((get_last_id())+1);
    }

    @Override
    protected void defined_variables() {

        VariableNumber=5;


        Variables="ID"+ ","+
                "FirstUserID"+ ","+
                "SecondUserID"+ ","+
                "TimeStart"+ ","+
                "TimeEnd";

    }

    @Override
    protected void Table_Name() {
        TableName="channels";
    }




    @Override
    public Map Insert(Object object) throws SQLException {
        int ID = ID_GENERATOR.getAndIncrement();



        Map<String,String> Channel=(Map<String, String>) object;

        int FirstUserId=Integer.parseInt(Channel.get("FirstUserID"));
        int  SecondUserId=Integer.parseInt(Channel.get("SecondUserID"));


        Date dt = new Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String TimeStart= sdf.format(dt);;

        String TimeEnd=sdf.format(dt);

        String query=make_query("insert");

        List<Object> data=new ArrayList<>();
        data.add(ID);
        data.add(FirstUserId);
        data.add(SecondUserId);
        data.add(TimeStart);
        data.add(TimeEnd);


        do_query(query,data);


        Channel.put("TimeStart",TimeStart);

        return Channel;
    }

    @Override
    public void Update(Object object) throws SQLException {


        Map<String,String> Channel=(Map<String, String>) object;

        int ID=Integer.parseInt(Channel.get("ID"));

        ArrayList<String> db_channel=FindById(ID).get(1);



            int FirstUserID;
        if(!Channel.containsKey("FirstUserID") && db_channel!=null ){
            FirstUserID=Integer.parseInt(db_channel.get(1));
        }
        else {
            FirstUserID=Integer.parseInt(Channel.get("FirstUserID"));
        }

        int SecondUserID=0;
        if(!Channel.containsKey("SecondUserID") && db_channel!=null ){
            SecondUserID=Integer.parseInt(db_channel.get(2));
        }
        else {
            SecondUserID=Integer.parseInt(Channel.get("SecondUserID"));
        }


        String TimeStart=db_channel.get(3);



        String TimeEnd="";
        if(!Channel.containsKey("TimeEnd") && db_channel!=null ){
            TimeEnd=db_channel.get(4);
        }
        else {
            TimeEnd=Channel.get("TimeEnd");
        }


        String query=make_query("updatebyid");

        List<Object> data=new ArrayList<>();

        data.add(FirstUserID);
        data.add(SecondUserID);
        data.add(TimeStart);
        data.add(TimeEnd);
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
/*
        String query=make_query("deletbyname");
        List<Object> data=new ArrayList<>();
        data.add(name);

        do_query(query,data);
*/
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

        /*
        String query=make_query("findbyname");
        List<Object> data=new ArrayList<>();
        data.add(name);

        return do_query(query,data);
    */
    return null;
    }




    ///////////////////////////////
    public ArrayList<ArrayList<String>> FindByFirstUserID(int FirstUserID) throws SQLException {

        String query=make_modified_query("findbyname","FirstUserID = ",0);
        List<Object> data=new ArrayList<>();
        data.add(FirstUserID);

        return do_query(query,data);

    }


    public ArrayList<ArrayList<String>> FindBySecondUserID(int SecondUserID) throws SQLException {

        String query=make_modified_query("findbyname","SecondUserID = ",0);
        List<Object> data=new ArrayList<>();
        data.add(SecondUserID);

        return do_query(query,data);
    }


    public void end_channel(int id) throws SQLException {

        Date dt = new Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String TimeEnd= sdf.format(dt);;

        Map<String,String> data=new HashMap<>();
        data.put("ID",String.valueOf(id));
        data.put("TimeEnd",TimeEnd);

        Update(data);
    }

}
