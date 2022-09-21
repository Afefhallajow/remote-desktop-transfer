package project.DAO;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PublicKeys_Dao extends Main_Dao {

    private static AtomicInteger ID_GENERATOR ;

    public PublicKeys_Dao() throws FileNotFoundException, SQLException {
        ID_GENERATOR=new AtomicInteger((get_last_id())+1);
    }

    @Override
    protected void defined_variables() {
        VariableNumber=3;


        Variables= "id"+ ","+
                   "UID"+ ","
                    +"TheKey";
    }

    @Override
    protected void Table_Name() {
        TableName="publickeys";
    }

    @Override
    public Map Insert(Object object) throws SQLException {
        int ID = ID_GENERATOR.getAndIncrement();
        Map<String,String> user=(Map<String, String>) object;


        String UID=user.get("UID");
        String key=user.get("Key");

        String query=make_query("insert");

        List<Object> data=new ArrayList<>();

        data.add(ID);
        data.add(UID);
        data.add(key);


        do_query(query,data);

        return user;

    }

    @Override
    public void Update(Object object) throws SQLException {

        Map<String,String> user=(Map<String, String>) object;

        String UID=(String) user.get("UID");

        ArrayList<ArrayList<String>> db_all_user=FindByUserID(UID);

        System.out.println(db_all_user.size());
        if(db_all_user.size()<=1){
            Insert(object);
        }
        else {
            ArrayList<String> db_user = db_all_user.get(1);

            String query=make_query("updatebyid");

            List<Object> data=new ArrayList<>();

            int ID=Integer.parseInt(db_user.get(0));

        data.add(UID);
        data.add((String) user.get("Key"));
        data.add(ID);//where


        do_query(query,data);






        }









    }

    @Override
    public void DeleteById(int id) throws SQLException {

    }

    @Override
    public void DeleteByName(String name) throws SQLException {

    }

    @Override
    public ArrayList<ArrayList<String>> FindAll() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> FindById(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> FindByName(String name) throws SQLException {
        return null;
    }

    public ArrayList<ArrayList<String>> FindByUserID(String UserID) throws SQLException {

        String query=make_modified_query("findbyname","UID = ",0);
        List<Object> data=new ArrayList<>();
        data.add(UserID);

        return do_query(query,data);
    }

}