package project.services.rdp_receiver;

import project.services.Service;
import project.GUI.RDPGUI_receiver.draw_received_screen;
import project.services.servicemessages.ServiceMessage;
import project.services.servicemessages.String_ServiceMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class ReceiveScreen extends Service {
    public ReceiveScreen() {
        super("ReceiveScreen");
    }

    @Override
    public ServiceMessage execute(Map parameters) throws IOException, ClassNotFoundException, InterruptedException {
        servicemessage=intalize_servicemessage();

        String image=(String) parameters.get("image");
        byte [] imagebytes= Base64.getDecoder().decode(image);



//        ByteArrayInputStream bis = new ByteArrayInputStream(imagebytes);
//        Image bImage2 = ImageIO.read(bis);
//
//        ImageIO.write(bImage2, "jpg", new File("output.jpg") );

        ByteArrayInputStream bis = new ByteArrayInputStream(imagebytes);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("output.jpg") );


        boolean result=draw_received_screen.get_instance(null).draw_on_panel((Image) bImage2);

        if(result==false){

            servicemessage.add("result", "stop");
        }else {
            servicemessage.add("result", "true");
        }
        return servicemessage;
    }

    @Override
    public ServiceMessage intalize_servicemessage() {
        return new String_ServiceMessage();
    }
}
