package controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBFirstLevelDivisions;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author Eric Trevorrow
 */

/**This class launches the customer appointment type and month report controller of the project.*/
public class CustomerAppointmentController implements Initializable {
    public AnchorPane customerAppointmentTypePane;
    public ComboBox<String> monthComboBox;
    public ComboBox typeComboBox;
    public Button closeButton;
    public Button totalButton;
    public Label totalLabel;
    public Label exceptionLabel;

    /**This is the initialize method.
     * This method is used to set the month and type values in the combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> monthsOfYear = FXCollections.observableArrayList();
        monthsOfYear.add("January");
        monthsOfYear.add("February");
        monthsOfYear.add("March");
        monthsOfYear.add("April");
        monthsOfYear.add("May");
        monthsOfYear.add("June");
        monthsOfYear.add("July");
        monthsOfYear.add("August");
        monthsOfYear.add("September");
        monthsOfYear.add("October");
        monthsOfYear.add("November");
        monthsOfYear.add("December");

        monthComboBox.setItems(monthsOfYear);
        typeComboBox.setItems(DBAppointments.getAllAppointmentTitles());

        }

    private Stage stageObject;
    private Scene sceneObject;

    /**This method validates if an month is present in the combo box.
     * Validates if a month is selected.*/
    public boolean monthVerification(){
        if(monthComboBox.getValue() == null){
            exceptionLabel.setText("Exception: No month entered");
            return false;
        }
        return true;
    }

    /**This method validates if a type is present in the combo box.
     * Validates if a type is selected.*/
    public boolean typeVerification(){
            if (typeComboBox.getValue() == null) {
                exceptionLabel.setText("Exception: No type entered");
                return false;
            }
            return true;
    }

    /**This is the method called when you select a month and type and generate the report.
     * This method takes the type and month and searches through the database, returning a total count of that distinctive type in a month.*/
    public void findTotalAction(ActionEvent event) throws SQLException {
        try {

            if ((monthVerification()) && (typeVerification())) {
                String chosenType = typeComboBox.getValue().toString();
                String chosenMonth = monthComboBox.getValue();
                String totalAmount = DBAppointments.getAppointmentTitlesCount(chosenType, chosenMonth);

                exceptionLabel.setText("");
                totalLabel.setText("Total amount of " + chosenType + " in " + chosenMonth + " is: " + totalAmount.substring(1, 2));

            }
        }catch(Exception e){
            //ignore
        }
    }

    /**This is the close method.
     * Returns the user to the main controller.*/
    public void closeButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stageObject = (Stage)((Node)event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }
}
