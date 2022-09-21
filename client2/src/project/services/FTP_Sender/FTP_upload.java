package project.services.FTP_Sender;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.SocketException;

public class FTP_upload {


    String serverIP = "127.0.0.1";
    int port = 2221;
    String user = "user";
    String pass = "afef";

    String Client1_path="";
    String fileName="";


    public FTP_upload(String server, int port, String client1_path, String fileName) {
        this.serverIP = server;
        this.port = port;
        Client1_path = client1_path;
        this.fileName = fileName;
    }


    public void send() {
        FTPClient ftpClient = new FTPClient();

        try {

            ftpClient.connect(serverIP,port);
            ftpClient.enterLocalPassiveMode();

            ftpClient.login(user, pass);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(Client1_path+"//"+fileName);

            String firstRemoteFile = fileName;
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}