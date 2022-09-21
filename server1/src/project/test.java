package project;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;


import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import project.listeners.FTP_listener;
import project.listeners.Listener;
import project.listeners.Socket_Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public void do_ftp() throws FtpException {
/*

        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

        factory.setPort(2221);

        FtpServer server = serverFactory.createServer();



        ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
        configFactory.setAnonymousLoginEnabled(true);
        serverFactory.setConnectionConfig(configFactory.createConnectionConfig());




        serverFactory.addListener("default", factory.createListener());
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("FTP\\log.txt"));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

        UserManager um = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName("user");
        user.setPassword("afef");
        user.setEnabled(true);
        user.setHomeDirectory("FTP\\Home");
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        try {
            um.save(user);
            server.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
*/
        FtpServerFactory serverFactory = new FtpServerFactory();
    ListenerFactory factory = new ListenerFactory();
    // تعيين منفذ الاستماع
    factory.setPort(2221);
    // استبدال المستمع الافتراضي
    // serverFactory.addListener("afef", factory.createListener());
    // إبدأ الخدمة
    FtpServer server = serverFactory.createServer();



    ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
    configFactory.setAnonymousLoginEnabled(true);
    serverFactory.setConnectionConfig(configFactory.createConnectionConfig());




    serverFactory.addListener("default", factory.createListener());
    PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
    userManagerFactory.setFile(new File(System.getProperty("user.dir") + "\\FTP\\log.txt"));
    serverFactory.setUserManager(userManagerFactory.createUserManager());

    UserManager um = userManagerFactory.createUserManager();
    BaseUser user = new BaseUser();
    user.setName("user");
    user.setPassword("afef");
    user.setEnabled(true);
    user.setHomeDirectory(System.getProperty("user.dir") + "\\FTP\\Home");

    List<Authority> authorities = new ArrayList<Authority>();
    authorities.add(new WritePermission());
    user.setAuthorities(authorities);

    um.save(user);


    server.start();

    }

/*
    public void do_ftp_sssl() throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

// set the port of the listener
        factory.setPort(2221);

// define SSL configuration
        File file=new File("C:\\Users\\TOSHIBA\\Desktop\\NF\\b.jks");
        SslConfigurationFactory ssl = new SslConfigurationFactory();
        ssl.setKeystoreFile(file);
        ssl.setKeystorePassword("password");

// set the SSL configuration for the listener
        factory.setSslConfiguration(ssl.createSslConfiguration());
        factory.setImplicitSsl(true);

// replace the default listener
        serverFactory.addListener("default", factory.createListener());
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("myusers.properties"));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

// start the server
        FtpServer server = serverFactory.createServer();
        server.start();
    }
  */
public static void main(String[] args) throws FtpException, IOException {


            Listener FTPlistener=new FTP_listener(project_info.get_instance().getFTPport());
            FTPlistener.fill_listen_information();

            Thread serverThread = new Thread( FTPlistener );

            serverThread.start();

    }

}
