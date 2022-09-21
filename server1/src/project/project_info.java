package project;

final public class  project_info {

    private static volatile project_info project_info_instance;


    private static volatile String name="server";
    private static volatile String ip="127.0.0.1";
    //private static volatile String sendport="1212";
    private static volatile String recieveport="5252";
    private static volatile String FTPport="2221";



    private project_info(){}


    public static project_info get_instance(){
        if (project_info_instance==null){

            synchronized (project_info.class){
                if (project_info_instance==null){
                    project_info_instance=new project_info();

                }
            }
        }

        return project_info_instance;
    }




    public static String getIp() {
        return ip;
    }


//    public static String getSendport() {
//        return sendport;
//    }


    public static String getRecieveport() {
        return recieveport;
    }




    public static String getName() {
        return name;
    }


    public static String getFTPport() {
        return FTPport;
    }
}
