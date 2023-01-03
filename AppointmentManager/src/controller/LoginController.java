package controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
import DAO.DBFirstLevelDivisions;
import DAO.DBUsers;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.FirstLevelDivisions;
import model.Users;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @author Eric Trevorrow
 */

/**This class launches the login controller of the project.*/
public class LoginController implements Initializable {
    public Button loginButton;
    public Button loginCloseButton;
    public AnchorPane loginPane;
    public TextField usernameTextBox;
    public PasswordField passwordTextBox;
    public Label incorrectLabel;
    public Label currentZoneIDLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label loginScreenTitle;

    /**This is the initialize method.
     * This method is used to get the zoneID of the user, as well as to set text to French if the locale is set to French.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentZoneIDLabel.setText(ZoneId.systemDefault().getId());

        ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")){
            System.out.println(rb.getString("bundlelanguage"));
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            loginScreenTitle.setText(rb.getString("logintitle"));
            loginButton.setText(rb.getString("loginbutton"));
            loginCloseButton.setText(rb.getString("loginexit"));
            usernameTextBox.setPromptText(rb.getString("usernameprompt"));
            passwordTextBox.setPromptText(rb.getString("passwordprompt"));
        }
        if(Locale.getDefault().getLanguage().equals("en")){
            System.out.println(rb.getString("bundlelanguage"));
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            loginScreenTitle.setText(rb.getString("logintitle"));
            loginButton.setText(rb.getString("loginbutton"));
            loginCloseButton.setText(rb.getString("loginexit"));
            usernameTextBox.setPromptText(rb.getString("usernameprompt"));
            passwordTextBox.setPromptText(rb.getString("passwordprompt"));
        }
    }

    /**This is the appointment alert method.
     * This method checks through the appointments in the database and notifies the user if there is an appointment in the next 15 minutes of them logging in.*/
    public boolean appointmentAlert(){
        for(Appointments A : DBAppointments.getAllAppointments()) {

            DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            LocalDateTime thisStartDateTime = LocalDateTime.parse(A.getStartDate(), startFormatter);
            LocalDateTime currentTime = LocalDateTime.now();

            long timeDifference = (ChronoUnit.MINUTES.between(currentTime, thisStartDateTime) + 1);

            if((timeDifference > 0) && (timeDifference <= 15)) {

                ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

                if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")) {
                    Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
                    alertObject.setTitle(rb.getString("alertitle"));
                    alertObject.setHeaderText(rb.getString("alertheader"));
                    alertObject.setContentText(rb.getString("alertcontent1") + " " + A.getAppointmentId() + ": " + A.getTitle() + " " + rb.getString("alertcontent2") + " " + timeDifference + " " + rb.getString("alertcontent3") + " " + A.getStartDate() + "!");

                    if (alertObject.showAndWait().get() == ButtonType.OK) {
                        //ignore
                    }
                } else if(Locale.getDefault().getLanguage().equals("en")){
                        Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
                    alertObject.setTitle(rb.getString("alertitle"));
                    alertObject.setHeaderText(rb.getString("alertheader"));
                    alertObject.setContentText(rb.getString("alertcontent1") + " " + A.getAppointmentId() + ": " + A.getTitle() + " " + rb.getString("alertcontent2") + " " + timeDifference + " " + rb.getString("alertcontent3") + " " + A.getStartDate() + "!");

                        if (alertObject.showAndWait().get() == ButtonType.OK) {
                            //ignore
                        }
                    }
                return true;
            }
        }
        return false;
    }

    /**This is the successful login tracker method.
     * This method is used to track login attempts and append the text file if they are successful.*/
    public boolean loginTrackerSuccess(String username, String password) throws IOException {
        for (Users U : DBUsers.getAllUsers()) {
            if ((username.equals(U.getUserName())) && ((password.equals(U.getPassword())))) {

                String filename = "login_activity.txt", item;
                DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                LocalDateTime currentTime = LocalDateTime.now();
                String currentTimeFormatted = currentTime.format(startFormatter);

                FileWriter fwriter = new FileWriter(filename, true);

                PrintWriter outputFile = new PrintWriter(fwriter);

                outputFile.println("User " + username + " successfully logged in at " + currentTimeFormatted);
                outputFile.close();
                return true;
            }
        }
        return false;
    }

    /**This is the failed login tracker method.
     * This method is used to track login attempts and append the text file if they are failed.*/
    public void loginTrackerFailed(String username) throws IOException {
        String filename = "login_activity.txt", item;

        DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        String currentTimeFormatted = currentTime.format(startFormatter);

        FileWriter fwriter = new FileWriter(filename, true);

        PrintWriter outputFile = new PrintWriter(fwriter);

        outputFile.println("User " + username + " failed to log in at " + currentTimeFormatted);
        outputFile.close();
    }

    private Stage stageObject;
    private Scene sceneObject;

    /**This is the login action method.
     * This method is called when the user attempts to log in, and attempts to verify whether the entered username and password allow it.
     * If there is a successful log in, it transfers the user to the main controller.*/
    public void loginAction(ActionEvent event) throws IOException{
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();

        for (Users U : DBUsers.getAllUsers()) {
            if ((username.equals(U.getUserName())) && ((password.equals(U.getPassword())))) {
                if (appointmentAlert() == false) {

                    ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

                    if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")) {
                        Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
                        alertObject.setTitle(rb.getString("noalerttitle"));
                        alertObject.setHeaderText(rb.getString("noalertheader"));
                        alertObject.setContentText(" ");

                        if (alertObject.showAndWait().get() == ButtonType.OK) {
                                    //ignore
                        }
                    } else if(Locale.getDefault().getLanguage().equals("en")){
                        Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
                        alertObject.setTitle(rb.getString("noalerttitle"));
                        alertObject.setHeaderText(rb.getString("noalertheader"));
                        alertObject.setContentText(" ");

                        if (alertObject.showAndWait().get() == ButtonType.OK) {
                                    //ignore
                        }
                    }
                }

                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
                sceneObject = new Scene(root);
                stageObject.setScene(sceneObject);
                stageObject.show();

            } else {

                ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

                if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")){
                    incorrectLabel.setText(rb.getString("wronguserpass"));
                } else if(Locale.getDefault().getLanguage().equals("en")){
                    incorrectLabel.setText(rb.getString("wronguserpass"));
                }
            }

        }

        if(password.isEmpty()) {

            ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")) {
                incorrectLabel.setText(rb.getString("nopassword"));
            } else if(Locale.getDefault().getLanguage().equals("en")){
                incorrectLabel.setText(rb.getString("nopassword"));
            }
        }

        if(username.isEmpty()) {

            ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")) {
                incorrectLabel.setText(rb.getString("nousername"));
            } else if(Locale.getDefault().getLanguage().equals("en")){
                incorrectLabel.setText(rb.getString("nousername"));
            }
        }

        if(!loginTrackerSuccess(username, password)){
            loginTrackerFailed(username);
        }

    }

    /**This is the login close method.
     * Asks the user if they wish to exit the program and exits if the user clicks OK.*/
    public void loginClose(ActionEvent actionEvent) {

        ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")) {
            Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
            alertObject.setTitle(rb.getString("alertexittitle"));
            alertObject.setHeaderText(rb.getString("alertexitheader"));
            alertObject.setContentText(rb.getString("alertexitcontent"));

            if(alertObject.showAndWait().get() == ButtonType.OK) {
                stageObject = (Stage) loginPane.getScene().getWindow();
                stageObject.close();
            }
        } else if(Locale.getDefault().getLanguage().equals("en")){
            Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
            alertObject.setTitle(rb.getString("alertexittitle"));
            alertObject.setHeaderText(rb.getString("alertexitheader"));
            alertObject.setContentText(rb.getString("alertexitcontent"));

            if (alertObject.showAndWait().get() == ButtonType.OK) {
                stageObject = (Stage) loginPane.getScene().getWindow();
                stageObject.close();
            }
        }
    }
}
