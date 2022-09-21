package project.services.rdp_sender;

import java.awt.*;

public class ReceiveEvent {


    private static volatile Robot robot ;


    private static volatile ReceiveEvent receiveEvent;

    private ReceiveEvent(Robot robot1) {

        this.robot = robot1;
    }


    public static ReceiveEvent get_instance(Robot robot){

        //for the first time robot
        //       then null

        if (receiveEvent ==null){

            synchronized (ReceiveEvent.class){
                if (receiveEvent ==null){
                    receiveEvent =new ReceiveEvent(robot);

                }
            }
        }
        return receiveEvent;

    }


    public synchronized void do_event(int first,int second,int third) {



        // if third is not existed it will be 0

                int command = first;
                switch (command) {

                    case -1:
                        robot.mousePress(second);
                        break;
                    case -2:
                        robot.mouseRelease(second);
                        break;
                    case -3:
                        robot.keyPress(second);
                        break;
                    case -4:
                        robot.keyRelease(second);
                        break;
                    case -5:
                        robot.mouseMove(second, third);
                        break;

            }


    }


}
