package project.GUI.Main_GUI;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
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
import java.util.*;
import java.util.List;


public class MainController {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls

    @FXML
    private Label invalidStartCredentials;
    @FXML
    private TextField UserIDField;

    @FXML
    private RadioButton NoSecurityButton;
    @FXML
    private RadioButton SecurityButton;
    @FXML
    private RadioButton ExtraSecurityButton;
    @FXML
    private ToggleGroup Security = new ToggleGroup();

    @FXML
    private Text UserIDText;

    @FXML
    private Button StartConnectionButton;

    @FXML
    private TextField SecurityField;

    @FXML
    private Line SecurityLine;


    @FXML
    private TextField MyIDField;

    @FXML
    private ScrollPane ChatPane;

    @FXML
    private VBox ChatBox;

    @FXML
    private TextField ChatUserIDField;

    @FXML
    private Button ChatConnectButton;

    @FXML
    private Button ChatConnect_toAnotherButton;

    @FXML
    private TextField ChatMessageField;

    @FXML
    private Button ChatMessageSendButton;

    @FXML
    private Label ChatLabel;


    int count_connect = 0;

    Date limitdate_connect = new Date();


    int count_Chat = 0;

    Date limitdate_Chat = new Date();

    boolean connectChat = false;

    private List<TextField> messages = new ArrayList<>();

    @FXML
    private void initialize() {
        NoSecurityButton.setToggleGroup(Security);

        SecurityButton.setToggleGroup(Security);

        ExtraSecurityButton.setToggleGroup(Security);

        Security.setUserData(Security.getSelectedToggle().toString());

        ChatPane.setContent(ChatBox);

        (GUIHandler.get_instance()).setMainController(this);

    }


    @FXML
    protected void onStartConnectionButtonClick() throws InterruptedException {
        if (UserIDField.getText().isEmpty()) {
            invalidStartCredentials.setText("The User ID field is required!");
            invalidStartCredentials.setStyle(errorMessage);
            UserIDField.setStyle(errorStyle);


        } else {

            if (count_connect < 10) {

                if(MyIDField.getText().equals(UserIDField.getText())){
                    invalidStartCredentials.setText("you entered your id");

                    invalidStartCredentials.setStyle(errorMessage);

                }
                else {

                    invalidStartCredentials.setText("Trying to connect , please wait ...");
                    invalidStartCredentials.setStyle(successMessage);
                    UserIDField.setStyle(successStyle);

                /*
                int s = 0;

                if (Security.getSelectedToggle() != null) {
                    RadioButton selectedRadioButton = (RadioButton) Security.getSelectedToggle();
                    String toogleGroupValue = selectedRadioButton.getText();
                    if ((toogleGroupValue.contains("extra"))) {
                        s=2;
                    }
                    else if(toogleGroupValue.contains("medium")){
                        s=1;
                    }
                    else {
                        s=0;
                    }

                }
                */


                    Message message = new Message();
                    message.setService_name("RDP_Start");

                    Map user = new HashMap();
                    user.put("FirstUserID", MyIDField.getText());
                    user.put("SecondUserID", UserIDField.getText());

                    user.put("encrypt", "2");

                    message.setParameters(user);


                    try {
                        ServiceMessage s_msg = new Facade().execute(message);

                        //System.out.println(s_msg.get_result());

                        if (s_msg.get_result().containsKey("error")) {
                            invalidStartCredentials.setText((String) s_msg.get_result().get("error"));

                            invalidStartCredentials.setStyle(errorMessage);

                            count_connect++;

                            if (count_connect >= 10) {
                                Calendar date = Calendar.getInstance();

                                long timeInSecs = date.getTimeInMillis();
                                limitdate_connect = new Date(timeInSecs + (2 * 60 * 1000));

                            }

                        } else {

                            ///////////////////// Get Public of another client from server
                            GetPublicKeyofAnotherUser(UserIDField.getText());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            } else {


                if (limitdate_connect.before(new Date())) {
                    count_connect = 0;
                }

                invalidStartCredentials.setStyle(errorMessage);
                invalidStartCredentials.setText("You have done too many failure log in , you must wait a while ");

            }

        }
    }


    /////////////////////////


    @FXML
    protected void onChatConnectButtonClick() throws InterruptedException {
        if (ChatUserIDField.getText().isEmpty()) {
            ChatLabel.setText("The User ID field is required!");
            ChatLabel.setStyle(errorMessage);
            ChatUserIDField.setStyle(errorStyle);


        } else {

            if (count_Chat < 10) {

                if(MyIDField.getText().equals(ChatUserIDField.getText())){
                    ChatLabel.setText("you entered your id");

                    ChatLabel.setStyle(errorMessage);

                }
                else {

                    ChatLabel.setText("Trying to connect , please wait ...");
                    ChatLabel.setStyle(successMessage);
                    ChatUserIDField.setStyle(successStyle);


                    Message message = new Message();
                    message.setService_name("Chat_start");

                    Map user = new HashMap();
                    user.put("FirstUserID", MyIDField.getText());
                    user.put("SecondUserID", ChatUserIDField.getText());
                    user.put("encrypt", "2");

                    message.setParameters(user);


                    try {
                        ServiceMessage s_msg = new Facade().execute(message);


                        if (s_msg.get_result().containsKey("error")) {
                            ChatLabel.setText((String) s_msg.get_result().get("error"));

                            ChatLabel.setStyle(errorMessage);

                            count_Chat++;

                            if (count_Chat >= 10) {
                                Calendar date = Calendar.getInstance();

                                long timeInSecs = date.getTimeInMillis();
                                limitdate_Chat = new Date(timeInSecs + (2 * 60 * 1000));

                            }

                        } else {

                            ChatLabel.setText("Online");

                            ChatLabel.setStyle(successMessage);

                            ChatConnect_toAnotherButton.setVisible(true);
                            ChatConnectButton.setVisible(false);

                            ChatUserIDField.setEditable(false);


                            ChatBox.getChildren().removeAll(messages);

                            messages = new ArrayList<>();

                            connectChat = true;


                            ///////////////////// Get Public of another client from server
                            GetPublicKeyofAnotherUser(ChatUserIDField.getText());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            } else {


                if (limitdate_Chat.before(new Date())) {
                    count_connect = 0;
                }

                ChatLabel.setStyle(errorMessage);
                ChatLabel.setText("You have done too many failure connect , you must wait a while ");

            }

        }
    }


    //////


    @FXML
    protected void onChatConnect_toAnotherButtonClick() throws InterruptedException {




        Message message = new Message();
        message.setService_name("Chat_end");

        Map user = new HashMap();
        user.put("FirstUserID", MyIDField.getText());
        user.put("SecondUserID", ChatUserIDField.getText());
        user.put("encrypt","2");

        message.setParameters(user);


        try {
            ServiceMessage s_msg = new Facade().execute(message);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        } catch(IOException e)
        {
            e.printStackTrace();
        } catch(ClassNotFoundException | AWTException e)
        {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        ChatLabel.setText("");

        ChatLabel.setStyle(successMessage);

        ChatConnect_toAnotherButton.setVisible(false);
        ChatConnectButton.setVisible(true);

        ChatUserIDField.setText("");
        ChatUserIDField.setEditable(true);


        ChatBox.getChildren().removeAll(messages);

        messages = new ArrayList<>();

        connectChat = false;


    }

    ///////////////////////////////////////














    @FXML
    protected void onChatSendMessageButtonClick() throws InterruptedException {
        ChatMessageField.setStyle(successStyle);

        if(connectChat) {
            if (ChatMessageField.getText().isEmpty()) {

                ChatMessageField.setStyle(errorStyle);


            } else {

                Message message = new Message();
                message.setService_name("Send_message");

                Map user = new HashMap();

                user.put("FirstUserID", MyIDField.getText());
                user.put("SecondUserID", ChatUserIDField.getText());
                user.put("message", ChatMessageField.getText());
                user.put("encrypt",GetSecurityLevel());

                message.setParameters(user);

//                System.out.println("id="+UserIDField.getText());

                try {
                    ServiceMessage s_msg = new Facade().execute(message);

                    //System.out.println(s_msg.get_result());

                    if (s_msg.get_result().containsKey("error")) {

                        ChatMessageField.setStyle(errorStyle);
                    } else {


                        messages.add(new TextField(ChatMessageField.getText()));
                        messages.get(messages.size() - 1).setEditable(false);


                        messages.get(messages.size() - 1).setStyle("-fx-control-inner-background: #0B9CDA ;" +
                                "textFill: #f7f7f7 ;" +
                                "prefHeight: 39.0 ;");


                        ChatBox.getChildren().add(messages.get(messages.size() - 1));

                        ChatMessageField.setText("");
                        ChatMessageField.setStyle(successStyle);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException | AWTException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        }
        else {
            ChatLabel.setText("no connection");
            ChatLabel.setStyle(errorMessage);
        }
    }








    @FXML
    protected void onStartRDPButtonClick() {
        ////start
        UserIDText.setVisible(true);
        UserIDField.setVisible(true);
        invalidStartCredentials.setVisible(true);
        StartConnectionButton.setVisible(true);


        ////security
        SecurityField.setVisible(false);
        NoSecurityButton.setVisible(false);
        SecurityButton.setVisible(false);
        ExtraSecurityButton.setVisible(false);
        SecurityLine.setVisible(false);


        ////chat
        ChatPane.setVisible(false);
        ChatBox.setVisible(false);
        ChatUserIDField.setStyle(successStyle);
        ChatUserIDField.setVisible(false);
        ChatConnectButton.setVisible(false);
        ChatMessageField.setVisible(false);
        ChatMessageField.setStyle(successStyle);
        ChatMessageSendButton.setVisible(false);
        if(ChatLabel.getText().equals("no connection")){ChatLabel.setText("");}
        ChatLabel.setVisible(false);
        ChatConnect_toAnotherButton.setVisible(false);

    }

    @FXML
    protected void onSettingsButtonClick() {
        ////start
        UserIDText.setVisible(false);
        UserIDField.setVisible(false);
        invalidStartCredentials.setVisible(false);
        StartConnectionButton.setVisible(false);
        invalidStartCredentials.setText("");
        UserIDField.setStyle(successStyle);


        ////security
        SecurityField.setVisible(true);
        NoSecurityButton.setVisible(true);
        SecurityButton.setVisible(true);
        ExtraSecurityButton.setVisible(true);
        SecurityLine.setVisible(true);


        ////chat
        ChatPane.setVisible(false);
        ChatBox.setVisible(false);
        ChatUserIDField.setStyle(successStyle);
        ChatUserIDField.setVisible(false);
        ChatConnectButton.setVisible(false);
        ChatMessageField.setVisible(false);
        ChatMessageField.setStyle(successStyle);
        ChatMessageSendButton.setVisible(false);
        if(ChatLabel.getText().equals("no connection")){ChatLabel.setText("");}
        ChatLabel.setVisible(false);
        ChatConnect_toAnotherButton.setVisible(false);

    }

    @FXML
    protected void onChatButtonClick() {

        ////start
        UserIDText.setVisible(false);
        UserIDField.setVisible(false);
        invalidStartCredentials.setVisible(false);
        StartConnectionButton.setVisible(false);
        invalidStartCredentials.setText("");
        UserIDField.setStyle(successStyle);

        ////security
        SecurityField.setVisible(false);
        NoSecurityButton.setVisible(false);
        SecurityButton.setVisible(false);
        ExtraSecurityButton.setVisible(false);
        SecurityLine.setVisible(false);


        ////chat
        ChatPane.setVisible(true);
        ChatBox.setVisible(true);
        ChatUserIDField.setVisible(true);
        ChatMessageField.setVisible(true);
        ChatMessageSendButton.setVisible(true);
        ChatLabel.setVisible(true);

        if(!connectChat){
            ChatConnectButton.setVisible(true);
            ChatConnect_toAnotherButton.setVisible(false);
        }
        else {
            ChatConnectButton.setVisible(false);
            ChatConnect_toAnotherButton.setVisible(true);
        }
    }


    public void switchChat(String FirstUserID){

        ChatBox.getChildren().removeAll(messages);

        messages=new ArrayList<>();

        onChatButtonClick();



        ChatLabel.setText("Online");

        ChatLabel.setStyle(successMessage);

        ChatConnect_toAnotherButton.setVisible(true);
        ChatConnectButton.setVisible(false);

        ChatUserIDField.setText(FirstUserID);
        ChatUserIDField.setEditable(false);


        connectChat=true;

    }

    public void receive_message(String message){

        messages.add(new TextField(message));
        messages.get(messages.size()-1).setEditable(false);

        messages.get(messages.size()-1).setAlignment(Pos.CENTER_RIGHT);
        messages.get(messages.size()-1).setStyle("-fx-control-inner-background: #0B9CDA ;" +
                "textFill: #f7f7f7 ;"+
                "prefHeight: 39.0 ;");

        ChatBox.getChildren().add(messages.get(messages.size()-1));

    }



    public void EndChat(){

        ChatLabel.setText("Offline");

        ChatLabel.setStyle(errorMessage);

        ChatConnect_toAnotherButton.setVisible(false);

        ChatConnectButton.setVisible(true);

        ChatUserIDField.setEditable(true);

        connectChat=false;

    }


    public void setUID(String UID){
        MyIDField.setText(UID);
    }


    public void GetPublicKeyofAnotherUser(String UID) throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException, AWTException, ClassNotFoundException {
        ////HandShake
        Map<String,String> p=new HashMap();
        p.put("UID",UID);
        p.put("encrypt","2");

        Message msg=new Message();

        msg.setParameters(p);

        msg.setService_name("GetUserKey");

        ServiceMessage serviceMessage=new Facade().execute(msg);

    }



    public String GetSecurityLevel(){

        if (Security.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) Security.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
            if ((toogleGroupValue.contains("Without security"))) {
                return String.valueOf("1");
            }
            else if((toogleGroupValue.equals("medium security"))){
                return String.valueOf("2");
            }
            else {
                return String.valueOf("3");
            }
        }

        return "1";
    }


}

