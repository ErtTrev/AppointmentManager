package controller;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * @author Eric Trevorrow
 */

/**This class starts the program.*/
public class Main extends Application {

    /** This is the start method. This loads the login controller.*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));

        ResourceBundle rb = ResourceBundle.getBundle("controller/LanguageBundle",Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("fr_FR")){
            primaryStage.setTitle(rb.getString("title"));
        }
        if(Locale.getDefault().getLanguage().equals("en")){
            primaryStage.setTitle(rb.getString("title"));
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /** This is the main method. This is the first method that gets called when the program is launched. The Javadocs are located in the Javadoc folder.*/
    public static void main(String[] args) {
        System.out.println(Locale.getDefault());
        //Locale.setDefault(new Locale("fr"));
        //Locale.setDefault((new Locale("en")));
        //TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(ZoneId.systemDefault());

        JDBC.makeConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
