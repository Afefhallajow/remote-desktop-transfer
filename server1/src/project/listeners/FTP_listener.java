package project.listeners;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import project.project_info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FTP_listener extends Listener {

    private String  port;
    FtpServer server;
    UserManager um ;
    BaseUser user ;

    public FTP_listener(String port) {

        this.port=port;
    }

    @Override
    public void fill_listen_information() throws IOException, FtpException {


        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

        factory.setPort(Integer.parseInt(project_info.get_instance().getFTPport()));

         server = serverFactory.createServer();



        ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
        configFactory.setAnonymousLoginEnabled(true);
        serverFactory.setConnectionConfig(configFactory.createConnectionConfig());



        String mypath=System.getProperty("user.dir");
        int index=mypath.indexOf("server");
        mypath=mypath.substring(0,index-1);



        serverFactory.addListener("default", factory.createListener());
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File(mypath + "\\FTP\\log.txt"));
        serverFactory.setUserManager(userManagerFactory.createUserManager());

         um = userManagerFactory.createUserManager();
         user = new BaseUser();
        user.setName("user");
        user.setPassword("afef");
        user.setEnabled(true);

        user.setHomeDirectory(mypath + "\\FTP\\Home");

        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);


    }

    @Override
    public void run() {



        try {
            um.save(user);
            server.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }







    }
}
