package project;

import project.services.rdp_sender.password_checker;

import java.util.HashMap;

final public class  project_info {

    private static volatile project_info project_info_instance;

    private static volatile String ip="127.0.0.1";
    //private static volatile String sendport="1111";
    private static volatile String recieveport="6666";

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


}
