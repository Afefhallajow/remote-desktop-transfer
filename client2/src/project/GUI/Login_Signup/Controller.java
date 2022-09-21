package project.GUI.Login_Signup;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.GUI.GUIHandler;
import project.GUI.Main_GUI.Start_Main_GUI;
import project.facade.Facade;
import project.messages.Message;
import project.project_info;
import project.services.servicemessages.ServiceMessage;

import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Controller {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls
    @FXML
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private TextField loginPasswordPasswordField;
    @FXML
    private TextField signUpUsernameTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private TextField signUpPasswordPasswordField;
    @FXML
    private TextField signUpRepeatPasswordPasswordField;
    @FXML
    private DatePicker signUpDateDatePicker;
    @FXML
    private RadioButton MaleButton;
    @FXML
    private RadioButton FemaleButton;

    @FXML
    private ToggleGroup Gender=new ToggleGroup();


    //////////////////////////////////////

    int count_signup=0;
    Date limitdate_signup=new Date();

    int count_login=0;
    Date limitdate_login=new Date();

    ///////////////////////////////////


    // Creation of methods which are activated on events in the forms

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
        MaleButton.setToggleGroup(Gender);

        FemaleButton.setToggleGroup(Gender);

        Gender.setUserData(Gender.getSelectedToggle().toString());

    }

    @FXML
    protected void onLoginButtonClick() {
        if (loginUsernameTextField.getText().isEmpty()) {
            invalidLoginCredentials.setText("The Username field is required!");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");


            loginPasswordPasswordField.setStyle(successStyle);
            loginUsernameTextField.setStyle(errorStyle);

        }
        else if(loginPasswordPasswordField.getText().isEmpty()){

            invalidLoginCredentials.setText("The Password field is required!");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");


            loginUsernameTextField.setStyle(successStyle);
            loginPasswordPasswordField.setStyle(errorStyle);


        }
        else if(loginUsernameTextField.getText().contains("!") ||loginUsernameTextField.getText().contains("#")
                ||loginUsernameTextField.getText().contains("$")||loginUsernameTextField.getText().contains("%")
                ||loginUsernameTextField.getText().contains("^")||loginUsernameTextField.getText().contains("&")
                ||loginUsernameTextField.getText().contains("*")||loginUsernameTextField.getText().contains("|")
                ||loginUsernameTextField.getText().contains("'")||loginUsernameTextField.getText().contains(";")
                ||loginUsernameTextField.getText().contains("@")||loginUsernameTextField.getText().contains("/")
                ||loginUsernameTextField.getText().contains("[")||loginUsernameTextField.getText().contains("]")
                ||loginUsernameTextField.getText().contains("{")||loginUsernameTextField.getText().contains("}")
                ||loginUsernameTextField.getText().contains("`")||loginUsernameTextField.getText().contains(">")
                ||loginUsernameTextField.getText().contains("<")||loginUsernameTextField.getText().contains("?")
                ||loginUsernameTextField.getText().contains("=")||loginUsernameTextField.getText().contains("-")

        ) {

            invalidLoginCredentials.setText("the Username field can't contain ' -=?!@#$%^&*;'`/[]{}<> ' ");

            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");


            loginPasswordPasswordField.setStyle(successStyle);
            loginUsernameTextField.setStyle(errorStyle);

        }

        else if(loginPasswordPasswordField.getText().contains("!") ||loginPasswordPasswordField.getText().contains("#")
                ||loginPasswordPasswordField.getText().contains("$")||loginPasswordPasswordField.getText().contains("%")
                ||loginPasswordPasswordField.getText().contains("^")||loginPasswordPasswordField.getText().contains("&")
                ||loginPasswordPasswordField.getText().contains("*")||loginPasswordPasswordField.getText().contains("|")
                ||loginPasswordPasswordField.getText().contains("'")||loginPasswordPasswordField.getText().contains(";")
                ||loginPasswordPasswordField.getText().contains("@")||loginPasswordPasswordField.getText().contains("/")
                ||loginPasswordPasswordField.getText().contains("[")||loginPasswordPasswordField.getText().contains("]")
                ||loginPasswordPasswordField.getText().contains("{")||loginPasswordPasswordField.getText().contains("}")
                ||loginPasswordPasswordField.getText().contains("`")||loginPasswordPasswordField.getText().contains(">")
                ||loginPasswordPasswordField.getText().contains("<")||loginPasswordPasswordField.getText().contains("?")
                ||loginPasswordPasswordField.getText().contains("=")||loginPasswordPasswordField.getText().contains("-")

        ) {

            invalidLoginCredentials.setText("the Password field can't contain ' -=?!@#$%^&*;'`/[]{}<> ' ");

            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");


            loginPasswordPasswordField.setStyle(errorStyle);
            loginUsernameTextField.setStyle(successStyle);

        }
        else {
            invalidLoginCredentials.setStyle(successMessage);
            invalidLoginCredentials.setText("");
            loginUsernameTextField.setStyle(successStyle);
            loginPasswordPasswordField.setStyle(successStyle);
            invalidSignupCredentials.setText("");
            invalidSignupCredentials.setStyle(successMessage);

            if(count_login<10){


            Message message = new Message();
            message.setService_name("LogIn");

            Map user = new HashMap();
            user.put("Name", loginUsernameTextField.getText());
            user.put("Password", loginPasswordPasswordField.getText());
            user.put("encrypt","2");

            message.setParameters(user);


            try {
                ServiceMessage s_msg = new Facade().execute(message);

                if (s_msg.get_result().containsKey("error")){
                    invalidLoginCredentials.setText((String) s_msg.get_result().get("error"));

                    invalidLoginCredentials.setStyle(errorMessage);

                    if(((String) s_msg.get_result().get("error")).equals("AuthenticateError") || ((String) s_msg.get_result().get("error")).equals("NotFound")) {
                        count_login++;

                    if (count_login >= 10) {
                        Calendar date = Calendar.getInstance();

                        long timeInSecs = date.getTimeInMillis();
                        limitdate_login = new Date(timeInSecs + (2 * 60 * 1000));

                    }
                   }
                }
                else {

                    /// login is ok
                    invalidLoginCredentials.setText("Login Successful!");

                    invalidLoginCredentials.setStyle(successMessage);

                    start_Main_GUI((String) s_msg.get_result().get("UID"));
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            }
            else {


                if(limitdate_login.before(new Date())){
                    count_login=0;
                }

                invalidLoginCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("You have done too many failure log in , you must wait a while ");

            }
    }

    }

    @FXML
    protected void onSignUpButtonClick() {


            if (signUpUsernameTextField.getText().isEmpty()) {

                invalidSignupCredentials.setText("Please fill in Username field!");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");



                signUpEmailTextField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpUsernameTextField.setStyle(errorStyle);

            } else if (signUpEmailTextField.getText().isEmpty()) {

                invalidSignupCredentials.setText("Please fill in Email field!");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");


                signUpUsernameTextField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpEmailTextField.setStyle(errorStyle);
            } else if (signUpPasswordPasswordField.getText().isEmpty()) {

                invalidSignupCredentials.setText("Please fill in Password field!");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");


                signUpUsernameTextField.setStyle(successStyle);
                signUpEmailTextField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpPasswordPasswordField.setStyle(errorStyle);
            } else if (signUpRepeatPasswordPasswordField.getText().isEmpty()) {

                invalidSignupCredentials.setText("Please fill in Repeat Password field!");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");

                signUpUsernameTextField.setStyle(successStyle);
                signUpEmailTextField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            }
            else if(signUpDateDatePicker.getValue()==null){

                invalidSignupCredentials.setText("Please fill in Date of Birth field!");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");

                signUpUsernameTextField.setStyle(successStyle);
                signUpEmailTextField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);

                signUpDateDatePicker.setStyle(errorStyle);

            }

            else if (signUpUsernameTextField.getText().length()<5) {

                invalidSignupCredentials.setText("Username can't be less than 5 letter !");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");


                signUpEmailTextField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpUsernameTextField.setStyle(errorStyle);
            }
            else if (!signUpEmailTextField.getText().contains("@") || !signUpEmailTextField.getText().contains(".")

                    ||signUpEmailTextField.getText().substring(0,signUpEmailTextField.getText().indexOf('@')).length()<1
                    ||signUpEmailTextField.getText().substring(signUpEmailTextField.getText().indexOf('@')+1,signUpEmailTextField.getText().indexOf('.')).length()<1
                    ||signUpEmailTextField.getText().substring(signUpEmailTextField.getText().indexOf('.')+1).length()<1) {

                invalidSignupCredentials.setText("The Email form must be --@--.--");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");

                signUpUsernameTextField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpEmailTextField.setStyle(errorStyle);
            }

            else if(signUpUsernameTextField.getText().contains("!") ||signUpUsernameTextField.getText().contains("#")
                    ||signUpUsernameTextField.getText().contains("$")||signUpUsernameTextField.getText().contains("%")
                    ||signUpUsernameTextField.getText().contains("^")||signUpUsernameTextField.getText().contains("&")
                    ||signUpUsernameTextField.getText().contains("*")||signUpUsernameTextField.getText().contains("|")
                    ||signUpUsernameTextField.getText().contains("'")||signUpUsernameTextField.getText().contains(";")
                    ||signUpUsernameTextField.getText().contains("@")||signUpUsernameTextField.getText().contains("/")
                    ||signUpUsernameTextField.getText().contains("[")||signUpUsernameTextField.getText().contains("]")
                    ||signUpUsernameTextField.getText().contains("{")||signUpUsernameTextField.getText().contains("}")
                    ||signUpUsernameTextField.getText().contains("`")||signUpUsernameTextField.getText().contains(">")
                    ||signUpUsernameTextField.getText().contains("<")||signUpUsernameTextField.getText().contains("?")
                    ||signUpUsernameTextField.getText().contains("=")||signUpUsernameTextField.getText().contains("-")

            ){

                invalidSignupCredentials.setText("the Username field can't contain ' -=?!@#$%^&*;'`/[]{}<> ' ");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");


                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpEmailTextField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);


                signUpUsernameTextField.setStyle(errorStyle);
            }
            else if(signUpEmailTextField.getText().contains("!") ||signUpEmailTextField.getText().contains("#")
                    ||signUpEmailTextField.getText().contains("$")||signUpEmailTextField.getText().contains("%")
                    ||signUpEmailTextField.getText().contains("^")||signUpEmailTextField.getText().contains("&")
                    ||signUpEmailTextField.getText().contains("*")||signUpEmailTextField.getText().contains("|")
                    ||signUpEmailTextField.getText().contains("'")||signUpEmailTextField.getText().contains(";")
                    ||signUpEmailTextField.getText().contains("/")||signUpEmailTextField.getText().contains("`")
                    ||signUpEmailTextField.getText().contains("[")||signUpEmailTextField.getText().contains("]")
                    ||signUpEmailTextField.getText().contains("{")||signUpEmailTextField.getText().contains("}")
                    ||signUpEmailTextField.getText().contains("<")||signUpEmailTextField.getText().contains(">")
                    ||signUpEmailTextField.getText().contains("?")||signUpEmailTextField.getText().contains("-")
                    ||signUpEmailTextField.getText().contains("=")
                    ||(signUpEmailTextField.getText().substring(0,signUpEmailTextField.getText().indexOf('@')-1).contains("@"))
                    ||(signUpEmailTextField.getText().substring(signUpEmailTextField.getText().indexOf('@')+1).contains("@"))
            ){

                invalidSignupCredentials.setText("the Email field can't contain ' -=?!#$%^&*;'`/[]{}<> ' ( one @ ) ");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");

                signUpUsernameTextField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpEmailTextField.setStyle(errorStyle);

            }
            else if(signUpPasswordPasswordField.getText().contains("!") ||signUpPasswordPasswordField.getText().contains("#")
                    ||signUpPasswordPasswordField.getText().contains("$")||signUpPasswordPasswordField.getText().contains("%")
                    ||signUpPasswordPasswordField.getText().contains("^")||signUpPasswordPasswordField.getText().contains("&")
                    ||signUpPasswordPasswordField.getText().contains("*")||signUpPasswordPasswordField.getText().contains("|")
                    ||signUpPasswordPasswordField.getText().contains("'")||signUpPasswordPasswordField.getText().contains(";")
                    ||signUpPasswordPasswordField.getText().contains("@")||signUpPasswordPasswordField.getText().contains("/")
                    ||signUpPasswordPasswordField.getText().contains("[")||signUpPasswordPasswordField.getText().contains("]")
                    ||signUpPasswordPasswordField.getText().contains("{")||signUpPasswordPasswordField.getText().contains("}")
                    ||signUpPasswordPasswordField.getText().contains("`")||signUpPasswordPasswordField.getText().contains(">")
                    ||signUpPasswordPasswordField.getText().contains("<")||signUpPasswordPasswordField.getText().contains("?")
                    ||signUpPasswordPasswordField.getText().contains("-")||signUpPasswordPasswordField.getText().contains("=")
            ){

                invalidSignupCredentials.setText("the Password field can't contain ' -=?!@#$%^&*;'`/[]{}<>? ' ");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");


                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpEmailTextField.setStyle(successStyle);
                signUpUsernameTextField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);


                signUpPasswordPasswordField.setStyle(errorStyle);
            }
            else if (signUpPasswordPasswordField.getText().length()<8) {

                invalidSignupCredentials.setText("Weak Password ! ( At least 8 letter )");
                invalidSignupCredentials.setStyle(errorMessage);
                invalidLoginCredentials.setText("");

                signUpUsernameTextField.setStyle(successStyle);
                signUpEmailTextField.setStyle(successStyle);
                signUpRepeatPasswordPasswordField.setStyle(successStyle);
                signUpDateDatePicker.setStyle(successStyle);

                signUpPasswordPasswordField.setStyle(errorStyle);
            }
            else if (signUpRepeatPasswordPasswordField.getText().equals(signUpPasswordPasswordField.getText())) {

            signUpUsernameTextField.setStyle(successStyle);
            signUpEmailTextField.setStyle(successStyle);
            signUpPasswordPasswordField.setStyle(successStyle);
            signUpRepeatPasswordPasswordField.setStyle(successStyle);
            signUpDateDatePicker.setStyle(successStyle);
            invalidLoginCredentials.setText("");
            invalidLoginCredentials.setStyle(successMessage);

                if(count_signup < 3) {


                    Message message = new Message();
                    message.setService_name("SignUp");

                    Map user = new HashMap();
                    user.put("Name", signUpUsernameTextField.getText());
                    user.put("Password", signUpPasswordPasswordField.getText());
                    user.put("encrypt","2");
                    boolean male = true;
                    if (Gender.getSelectedToggle() != null) {
                        RadioButton selectedRadioButton = (RadioButton) Gender.getSelectedToggle();
                        String toogleGroupValue = selectedRadioButton.getText();
                        if ((toogleGroupValue.equals("Female"))) {
                            male = false;
                        }

                    }

                    if (male) {
                        user.put("Gender", "true");
                    } else {
                        user.put("Gender", "false");
                    }
                    user.put("Email", signUpEmailTextField.getText());
                    user.put("Birthday", signUpDateDatePicker.getValue().toString());
                    user.put("IP", project_info.get_instance().getIp());
                    user.put("RecievePort",project_info.get_instance().getRecieveport());

                    message.setParameters(user);

                    try {
                        ServiceMessage s_msg = new Facade().execute(message);


                        if (s_msg.get_result().containsKey("error")) {
                            invalidSignupCredentials.setStyle(errorMessage);
                            invalidSignupCredentials.setText((String) s_msg.get_result().get("error"));

                            if(s_msg.get_result().get("error").equals("UserNameUsed")){
                                signUpUsernameTextField.setStyle(errorStyle);
                                invalidSignupCredentials.setText("Your name has been used, please try another name");
                            }
                            count_signup++;
                            if (count_signup >= 3) {
                                Calendar date = Calendar.getInstance();

                                long timeInSecs = date.getTimeInMillis();
                                limitdate_signup = new Date(timeInSecs + (1 * 60 * 1000));
                            }

                        } else {
                            /// sign up is ok
                            invalidSignupCredentials.setStyle(successMessage);
                            invalidSignupCredentials.setText("You are set!");
                            start_Main_GUI((String) s_msg.get_result().get("UID"));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    if(limitdate_signup.before(new Date())){
                        count_signup=0;
                    }

                    invalidSignupCredentials.setStyle(errorMessage);
                    invalidSignupCredentials.setText("You have done too many sign up , you must wait a while ");

                }


            } else {

            invalidSignupCredentials.setText("The Passwords don't match!");
            signUpUsernameTextField.setStyle(successStyle);
            signUpEmailTextField.setStyle(successStyle);
            signUpDateDatePicker.setStyle(successStyle);

            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            invalidLoginCredentials.setText("");
        }
    }


    private void start_Main_GUI(String UID){

        onCancelButtonClick();

        Start_Main_GUI start_main_gui=new Start_Main_GUI();
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    start_main_gui.setPrivateUID(UID);

                    Stage stage=new Stage();
                    start_main_gui.start(stage);
                    start_main_gui.setUID(UID);
                    start_main_gui.setStage(stage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //new Start_Main_GUI().Main_GUI(UID);
    }

}
