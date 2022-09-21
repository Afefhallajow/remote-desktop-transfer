package project.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public abstract class Main_Dao {

    protected int VariableNumber;
    protected String Variables;
    protected String TableName;

    private Statement statement;
    private Connection conn;

    private String DRIVER_NAME = "";
    private  String DB_URL = "";
    private  String USER = "";
    private  String PASS = "";


    private static final String DELETE = "DELETE FROM ";
    private static final String FIND = "SELECT * FROM ";
    private static final String INSERT = "INSERT INTO ";
    private static final String UPDATE = "UPDATE ";
    private static final String WHERE = " WHERE ";
    private static final String SET = " SET ";
    private static final String ID = "id = ";
    private static final String NAME = "name = ";
    private static final String VALUES=" VALUES ";



    Main_Dao() throws FileNotFoundException {

        defined_variables();
        Table_Name();


    Map<String,String>data =read_DB_file();

    if(data.get("DB_CONNECTION").equals("mysql")){
        DRIVER_NAME = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://"+data.get("DB_HOST")+":"+data.get("DB_PORT")+"/"+data.get("DB_DATABASE");
        }

        USER=data.get("DB_USERNAME");
        PASS=data.get("DB_PASSWORD");


    }


    private Map<String,String>read_DB_file() throws FileNotFoundException {

        File resourceFile = new File("./src/project/DAO/database_data.txt");


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

        return DB_data;
    }








    public ArrayList<ArrayList<String>>  do_query(String query,List<Object> data) throws SQLException {


        conn = DatabaseConnect();
        PreparedStatement stmt = null;

        stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);


        if(!data.isEmpty()) {
            int t = 0;


            for (int i = 0; i < data.size(); i++) {

                t = i + 1;


                if(data.get(i).getClass().equals(String.class)) {
                    stmt.setString(t, (String) data.get(i));
                }
                else if(data.get(i).getClass().equals(Integer.class)) {
                    stmt.setInt(t,  (Integer) data.get(i));
                }

            }

        }

        //ResultSet result =stmt.executeQuery();;
        stmt.execute();

        ResultSet result=stmt.getResultSet();


            ArrayList<ArrayList<String>> feedback = new ArrayList<ArrayList<String>>();
            ArrayList<String> feed = null;

            feed = new ArrayList<String>();



            if(result!=null) {
                ResultSetMetaData rsm = result.getMetaData();


                if (rsm != null) {

                    for (int y = 1; y <= rsm.getColumnCount(); y++) {

                        feed.add(rsm.getColumnName(y));
                    }
                    feedback.add(feed);

                    while (result.next()) {
                        feed = new ArrayList<String>();
                        for (int i = 1; i <= rsm.getColumnCount(); i++) {

                            feed.add(result.getString(i));
                        }
                        feedback.add(feed);
                    }

                }
            }


        close(stmt);
        close(conn);

        return feedback;
    }





    ///////////////////////////////////////////////

    private Connection DatabaseConnect(){

        try {
            Class.forName(DRIVER_NAME);

            System.out.print("Database Connected");

            return DriverManager.getConnection(
                    DB_URL,USER,PASS);

        } catch (Exception e) {
            System.out.print("Database Not Connected");
        }

        return null;
    }



    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


    /////////////////////////////////////////////////




    public String make_query(String type)
    {
        int variable_at_end=VariableNumber;
        boolean need_brackets=false;
        StringBuilder query= new StringBuilder();


        if (type.equals("insert")){
            need_brackets=true;
            query = new StringBuilder(INSERT + TableName + "(" + Variables + ")" + VALUES);}
        else if (type.equals("deletbyid")){
            query = new StringBuilder(DELETE +TableName+WHERE+ID);
            variable_at_end=1;
        }
        else if (type.equals("deletbyname")){
            query = new StringBuilder(DELETE +TableName+WHERE+NAME);
            variable_at_end=1;
        }
        else if (type.equals("findall")){
            query = new StringBuilder(FIND +TableName);
            variable_at_end=0;
        }
        else if (type.equals("findbyid")){
            query = new StringBuilder(FIND +TableName+WHERE+ID);
            variable_at_end=1;
        }
        else if (type.equals("findbyname")){
            query = new StringBuilder(FIND +TableName+WHERE+NAME);
            variable_at_end=1;
        }
        else if (type.equals("updatebyid")){
            query = new StringBuilder(UPDATE+TableName+SET+make_variables_for_update(Variables) +WHERE+ID);
            variable_at_end=1;
        }

     //   System.out.println(query);
        ////add value
        for (int i=0;i<variable_at_end;i++){
            if(i==0 && need_brackets) query. append("(");

            query.append("?");

            if(i==variable_at_end-1 && need_brackets) query.append(")");
            else if(i<variable_at_end-1) query.append(", ");
        }

        return query.toString();
    }



    private String make_variables_for_update(String variables){
        String NewVariables="";


        if(variables.substring(0,2).contains("ID") || variables.substring(0,2).contains("id") )
        {variables=variables.substring(3);}

        char []mychar=variables.toCharArray();

        for (int i=0;i<mychar.length;i++){
            if(mychar[i]==','){NewVariables=NewVariables+"=? ";}
            NewVariables=NewVariables+mychar[i];
        }
        NewVariables=NewVariables+"=? ";

        return NewVariables;
        }

        /////////////////////////////////


    protected String make_modified_query(String type,String Name,int Id)
    {
        int variable_at_end=VariableNumber;
        boolean need_brackets=false;
        StringBuilder query= new StringBuilder();


        if (type.equals("insert")){
            need_brackets=true;
            query = new StringBuilder(INSERT + TableName + "(" + Variables + ")" + VALUES);}
        else if (type.equals("deletbyid")){
            query = new StringBuilder(DELETE +TableName+WHERE+Id);
            variable_at_end=1;
        }
        else if (type.equals("deletbyname")){
            query = new StringBuilder(DELETE +TableName+WHERE+Name);
            variable_at_end=1;
        }
        else if (type.equals("findall")){
            query = new StringBuilder(FIND +TableName);
            variable_at_end=0;
        }
        else if (type.equals("findbyid")){
            query = new StringBuilder(FIND +TableName+WHERE+Id);
            variable_at_end=1;
        }
        else if (type.equals("findbyname")){
            query = new StringBuilder(FIND +TableName+WHERE+Name);
            variable_at_end=1;
        }
        else if (type.equals("updatebyid")){
            query = new StringBuilder(UPDATE+TableName+SET+make_variables_for_update(Variables) +WHERE+Id);
            variable_at_end=1;
        }

     /*   else if (type.equals("updatebyUID")){
            query = new StringBuilder(UPDATE+TableName+SET+make_variables_for_update(Variables) +WHERE+UID);
            variable_at_end=1;
        }
*/
        //   System.out.println(query);
        ////add value
        for (int i=0;i<variable_at_end;i++){
            if(i==0 && need_brackets) query. append("(");

            query.append("?");

            if(i==variable_at_end-1 && need_brackets) query.append(")");
            else if(i<variable_at_end-1) query.append(", ");
        }

        return query.toString();
    }



    //////////////////////////////////

    protected int get_last_id() throws SQLException {
        String query="SELECT * FROM "+TableName+" ORDER BY ID DESC LIMIT 1 ";

        ArrayList<ArrayList<String>> RESULT=do_query(query,new ArrayList<>());

        if(RESULT.size()==1){return -1;}
        return Integer.parseInt(RESULT.get(1).get(0));
    }



    abstract protected void defined_variables();
    abstract protected void Table_Name();
    abstract public Map Insert(Object object) throws SQLException;
    abstract public void Update(Object object) throws SQLException;
    abstract public void DeleteById(int id) throws SQLException;
    abstract public void DeleteByName(String  name) throws SQLException;
    abstract public ArrayList<ArrayList<String>> FindAll() throws SQLException;
    abstract public ArrayList<ArrayList<String>> FindById(int id) throws SQLException;
    abstract public ArrayList<ArrayList<String>> FindByName(String name) throws SQLException;




}


