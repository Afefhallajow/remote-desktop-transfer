package project.GUI.scrollMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.GUI.GUIHandler;
import project.facade.Facade;
import project.messages.Message;
import project.services.servicemessages.ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;


public class ControllerScroll {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");


    @FXML
    private Button TransferFilesButton;
    @FXML
    private Button ShutdownButton;
    @FXML
    private Button RestartButton;
    @FXML
    private Button DoTransferButton;
    @FXML
    private Button EndConnection;


    @FXML
    private TextField Path1Field;
    @FXML
    private TextField Path2Field;
    @FXML
    private TextField FileNameField;

    @FXML
    private Text Path1Text;
    @FXML
    private Text Path2Text;
    @FXML
    private Text FileNameText;

    //////////////////////
    String SecondUserID="";

    // Creation of methods which are activated on events in the forms

    @FXML
    private void initialize() {

        (GUIHandler.get_instance()).setControllerScroll(this);

    }


    @FXML
    protected void OnEndConnectionButtonClick() throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {

        Map<String,String> p=new HashMap();
        p.put("SecondUserID",SecondUserID);
        p.put("encrypt",GUIHandler.get_instance().getMainController().GetSecurityLevel());

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("End_RDP");

        ServiceMessage serviceMessage=new Facade().execute(msg);

        Stage stage = (Stage) EndConnection.getScene().getWindow();
        stage.close();

        System.out.println(serviceMessage.get_result());

    }


    @FXML
    protected void onTransferFilesButtonClick() {
        if(TransferFilesButton.getText().equals("Transfer Files")) {
            TransferFilesButton.setText("X");

            RestartButton.setVisible(false);
            ShutdownButton.setVisible(false);
            EndConnection.setVisible(false);
            DoTransferButton.setVisible(true);

            Path1Field.setVisible(true);
            Path2Field.setVisible(true);
            FileNameField.setVisible(true);

            Path1Text.setVisible(true);
            Path2Text.setVisible(true);
            FileNameText.setVisible(true);
        }else {
            TransferFilesButton.setText("Transfer Files");

            RestartButton.setVisible(true);
            ShutdownButton.setVisible(true);
            EndConnection.setVisible(true);
            DoTransferButton.setVisible(false);

            Path1Field.setVisible(false);
            Path2Field.setVisible(false);
            FileNameField.setVisible(false);

            Path1Text.setVisible(false);
            Path2Text.setVisible(false);
            FileNameText.setVisible(false);
        }
    }

    @FXML
    protected void onShutdownButtonClick() throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {

        Map<String,String> p=new HashMap();
        p.put("SecondUserID",SecondUserID);
        p.put("encrypt",GUIHandler.get_instance().getMainController().GetSecurityLevel());

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("Send_shutdown");

        ServiceMessage serviceMessage=new Facade().execute(msg);

        System.out.println(serviceMessage.get_result());

        /////end screen
    }
    @FXML
    protected void onRestartButtonClick() throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {
        Map<String,String> p=new HashMap();
        p.put("SecondUserID",SecondUserID);
        p.put("encrypt",GUIHandler.get_instance().getMainController().GetSecurityLevel());

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("Send_restart");

        ServiceMessage serviceMessage=new Facade().execute(msg);

        //System.out.println(serviceMessage.get_result());

        /////end screen

    }
    @FXML
    protected void onDoTransferButtonClick() throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {
        if (Path1Field.getText().isEmpty()) {
            Path1Field.setStyle(errorStyle);
            Path2Field.setStyle(successStyle);
            FileNameField.setStyle(successStyle);
        }else if (Path2Field.getText().isEmpty()) {
            Path1Field.setStyle(successStyle);
            Path2Field.setStyle(errorStyle);
            FileNameField.setStyle(successStyle);
        }
        else if (FileNameField.getText().isEmpty()) {
            Path1Field.setStyle(successStyle);
            Path2Field.setStyle(successStyle);
            FileNameField.setStyle(errorStyle);
        }
        else {
            Path1Field.setStyle(successStyle);
            Path2Field.setStyle(successStyle);
            FileNameField.setStyle(successStyle);


            Map<String, String> p = new HashMap();
            p.put("SecondUserID", SecondUserID);
            p.put("encrypt",GUIHandler.get_instance().getMainController().GetSecurityLevel());

            String path1 = Path1Field.getText();
            String path2 = Path2Field.getText();

            path1 = path1.replace("\\", "\\\\");
            path2 = path2.replace("\\", "\\\\");

            p.put("path1", path1);
            p.put("path2", path2);
            p.put("fileName", FileNameField.getText());

            Message msg = new Message();

            msg.setParameters(p);

            msg.setService_name("Send_FTP");

            ServiceMessage serviceMessage = new Facade().execute(msg);

            System.out.println(serviceMessage.get_result());
        }
    }



    public void setUID(String UID){
        SecondUserID=UID;
    }

}
