package project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ftpserver.ftplet.FtpException;
import project.GUI.scrollMenu.Start_scroll_GUI;
import project.facade.Facade;
import project.messages.Message;
import project.services.servicemessages.ServiceMessage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;


public class test extends Application {

    public static void main(String[] args) throws Exception {


        launch(args);


        /*

        String server = "127.0.0.1";
        int port = 2221;
        String user = "user";
        String pass = "afef";

        FTPClient ftpClient = new FTPClient();

        try {

            ftpClient.connect(server,2221);
            ftpClient.enterLocalPassiveMode();

            ftpClient.login(user, pass);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("C:\\Users\\TOSHIBA\\Desktop\\New folder\\HI.txt");

            String firstRemoteFile = "test.txt";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }

 // APPROACH #2: uploads second file using an OutputStream
 File secondLocalFile = new File("C:\\Users\\HP\\Desktop\\New folder\\a.txt");
 String secondRemoteFile = "Projects.zip";
 inputStream = new FileInputStream(secondLocalFile);

 System.out.println("Start uploading second file");
 OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
 byte[] bytesIn = new byte[4096];
 int read = 0;

 while ((read = inputStream.read(bytesIn)) != -1) {
 outputStream.write(bytesIn, 0, read);
 }
           inputStream.close();
            //      outputStream.close();

            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The second file is uploaded successfully.");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
*/


        ////SHUTDOWN
//        Map<String,String> p=new HashMap();
//        p.put("SecondUserID","UID-tvSbz1aKz1");
//
//
//        Message msg=new Message();
//
//        msg.setParameters(p);
//
//        msg.setService_name("Send_shutdown");
//
//        ServiceMessage serviceMessage=new Facade().execute(msg);
//
//        System.out.println(serviceMessage.get_result());
//
//
/*
  ////FTP

  Map<String,String> p=new HashMap();
        p.put("SecondUserID","UID-tvSbz1aKz1");
        p.put("path1","C:\\Users\\TOSHIBA\\Desktop\\New folder");
        p.put("path2","C:\\Users\\TOSHIBA\\Desktop\\NF");
        p.put("fileName","HI.txt");

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("Send_FTP");

        ServiceMessage serviceMessage=new Facade().execute(msg);

        System.out.println(serviceMessage.get_result());
  */  }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Start_scroll_GUI start_scroll_gui=new Start_scroll_GUI();

        start_scroll_gui.start(primaryStage);
        start_scroll_gui.setUID("UID-3izW2l70F7");

    }
}
