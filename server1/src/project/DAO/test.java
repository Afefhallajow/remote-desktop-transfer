package project.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public  class test {

    Statement statement;


    public  void DatabaseConnect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/passmanger","root","");
            statement = conn.createStatement();
            System.out.print("Database Connected");
        } catch (Exception e) {
            System.out.print("Database Not Connected");
        }
    }




    //for inserting data
    public void insert(){
        try{
            String insertquery = "INSERT INTO `tablename`(`field1`, `field2`) VALUES ('value1', 'value2'";
            statement.executeUpdate(insertquery);
            System.out.print("Inserted");
        } catch(Exception e){
            System.out.print("Not Inserted");
        }
    }

    //for viewing data
    public void view(){
        try {
            String insertquery = "";
            ResultSet result = statement.executeQuery("SELECT * FROM `user`");
            System.out.println("f");
            if(result.next()){
                System.out.println("Value " + result.getString(2));
                System.out.println("Value " + result.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    //to update data
    public void update(){
        try {
            String insertquery = "UPDATE `table_name` set `field`='value',`field2`='value2' WHERE field = 'value'";
            statement.executeUpdate(insertquery);
            System.out.println("Updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //to delete data
    public void delete(){
        try {
            String insertquery = "DELETE FROM `table_name` WHERE field = 'value'";
            statement.executeUpdate(insertquery);
            System.out.println("Deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);


    public static void main(String[] args) throws FileNotFoundException, SQLException {



        Main_Dao main_dao=new User_Dao();

        System.out.println(main_dao.make_query("findall"));
        Map<String,String> data=new HashMap<>();

        //data.put("ID","0");
        data.put("FirstUserID","0");

        data.put("SecondUserID","1");


        //main_dao.Insert(data);
        //main_dao.Update(data);

        //System.out.println(((Channel_Dao) main_dao).FindByFirstUserID(0));
        //main_dao.DeleteById(0);

      //  ((Channel_Dao) main_dao).end_channel(0);
        /*
        test t1=new test();

        t1.DatabaseConnect();

        t1.view();
*/

/*

        File resourceFile = new File("./src/project/DAO/database_data.txt");

        System.out.println(resourceFile.getAbsolutePath());

        int i=0;
        Map<String,String>DB_data=new HashMap<>();

        Scanner myReader = new Scanner(resourceFile);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

            i= data.indexOf('=');


            if(data.contains("DB_CONNECTION")){  DB_data.put("DB_CONNECTION",data.substring(i+1)); }
            else if (data.contains("DB_HOST")){DB_data.put("DB_HOST",data.substring(i+1));}
            else if (data.contains("DB_PORT")){DB_data.put("DB_PORT",data.substring(i+1));}
            else if (data.contains("DB_DATABASE")){DB_data.put("DB_DATABASE",data.substring(i+1));}
            else if (data.contains("DB_USERNAME")){DB_data.put("DB_USERNAME",data.substring(i+1));}
            else if (data.contains("DB_PASSWORD")){DB_data.put("DB_PASSWORD",data.substring(i+1));}


        }

        System.out.println(DB_data.get("DB_CONNECTION"));
        myReader.close();

*/
  //      Main_Dao main_dao=new User_Dao();


        //main_dao.Insert(new HashMap<>());

        //int result = stmt.executeUpdate("insert into student(Id,name,number) values('1','rachel','45')");
/*
        String UPDATE = "UPDATE user SET name, tel, passwd";
        UPDATE=main_dao.make_query("insert");
*/
/*
        List<Object> l=new ArrayList<Object>();


        l.add(21);
        l.add("deqdddr");
        l.add("eee");

        main_dao.do_query(INSERT, (ArrayList<Object>) l);




        ArrayList<ArrayList<String>> a=main_dao.do_query("SELECT * FROM `user`",new ArrayList<>());
        System.out.println(a);

        //INSERT INTO `pass`(`id`, `password`, `description`, `address`, `username`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5])
        //main_dao.view("pass");
  */





    }








}
