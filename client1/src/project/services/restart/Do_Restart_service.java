package project.services.restart;

import project.services.Service;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import java.io.IOException;
import java.util.Map;

public class Do_Restart_service extends Service {
    public Do_Restart_service() {
        super("DoRestart");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();

        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");
      //  System.out.println(operatingSystem);

        if (operatingSystem.contains("Linux") || operatingSystem.contains("Mac OS X")) {
            shutdownCommand = "shutdown -r now";
        }
        else if (operatingSystem.contains("Windows")) {
            shutdownCommand = "shutdown.exe -r -t 0";
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        servicemessage.add("msg","done");

        Runtime.getRuntime().exec(shutdownCommand);


        return servicemessage;

    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }

}
