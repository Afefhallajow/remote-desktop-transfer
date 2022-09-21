package project.services.rdp_receiver;

import project.GUI.GUIHandler;
import project.GUI.RDPGUI_receiver.CreateFrame;
import project.services.rdp_sender.SendScreen;

public class CreateFrameHandler {
    private static volatile CreateFrame createFrame;
    private static volatile CreateFrameHandler createFrameHandler;


    private CreateFrameHandler(){}



    public static CreateFrameHandler get_instance(){
        if (createFrameHandler==null){

            synchronized (CreateFrameHandler.class){
                if (createFrameHandler==null){
                    createFrameHandler=new CreateFrameHandler();

                }
            }
        }

        return createFrameHandler;
    }



    public static CreateFrame getcreateFrame() {

        synchronized (CreateFrameHandler.class) {
            return createFrame;
        }
    }

    public static void setcreateFrame(CreateFrame createFrame1) {
        synchronized (CreateFrame.class) {
            CreateFrameHandler.createFrame = createFrame1;
        }
    }

}
