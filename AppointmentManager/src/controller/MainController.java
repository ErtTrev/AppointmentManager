package controller;

import DAO.DBAppointments;
import DAO.DBCountries;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * @author Eric Trevorrow
 */

/**This class launches the main controller of the project.*/
public class MainController implements Initializable {
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button logOffButton;
    public Button reportCustomerButton;
    public Button reportContactButton;
    public Button reportCustomButton;
    public RadioButton viewAllRadioButton;
    public RadioButton monthlyRadioButton;
    public RadioButton weeklyRadioButton;
    public ToggleGroup appointmentRadioGrouping;
    public TableView customerTable;
    public TableView appointmentTable;
    public AnchorPane mainPane;
    public TableColumn aTableApptIDColumn;
    public TableColumn aTableTitleColumn;
    public TableColumn aTableDescriptionColumn;
    public TableColumn aTableLocationColumn;
    public TableColumn aTableContactColumn;
    public TableColumn aTableTypeColumn;
    public TableColumn aTableStartDateColumn;
    public TableColumn aTableEndDateColumn;
    public TableColumn aTableCustomerIDColumn;
    public TableColumn aTableUserIDColumn;
    public TableColumn cTableCustomerIDColumn;
    public TableColumn cTableCustomerNameColumn;
    public TableColumn cTablePhoneNumberColumn;
    public TableColumn cTableAddressColumn;
    public TableColumn cTablePostalCodeColumn;
    public TableColumn cTableDivisionIDColumn;
    public Label customerExceptionLabel;
    public Label appointmentExceptionLabel;
    public TableColumn cTableDivisionCountryColumn;

    /**This is the initialize method.
     * This method is used to set the tableview values in the tableviews.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTable.setItems(DBAppointments.getAllAppointments());
        aTableApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aTableTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        aTableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        aTableLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        aTableContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aTableTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        aTableStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        aTableEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        aTableCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        aTableUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        customerTable.setItems(DBCustomers.getAllCustomers());
        cTableCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cTableCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cTablePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        cTableAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cTablePostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cTableDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        cTableDivisionCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }

    private Stage stageObject;
    private Scene sceneObject;

    /**This method sets a weekly tableview of appointments if the Weekly button is selected.
     * If the radio button for Weekly is checked, the tableview loads the data for appointments of the week.*/
    public void onWeeklyRadioSelect(ActionEvent event) {
        appointmentTable.getItems().clear();
        appointmentTable.setItems(DBAppointments.getAllWeeklyAppointments());
        aTableApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aTableTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        aTableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        aTableLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        aTableContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aTableTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        aTableStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        aTableEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        aTableCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        aTableUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**This method sets a monthly tableview of appointments if the Monthly button is selected.
     * If the radio button for Monthly is checked, the tableview loads the data for appointments of the month.*/
    public void onMonthlyRadioSelect(ActionEvent event) {
        appointmentTable.getItems().clear();
        appointmentTable.setItems(DBAppointments.getAllMonthlyAppointments());
        aTableApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aTableTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        aTableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        aTableLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        aTableContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aTableTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        aTableStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        aTableEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        aTableCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        aTableUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**This method sets a tableview of all appointments if the View All button is selected.
     * If the radio button for View All is checked, the tableview loads the data for every appointment in the database.*/
    public void onViewAllRadioSelect(ActionEvent event) {
        appointmentTable.getItems().clear();
        appointmentTable.setItems(DBAppointments.getAllAppointments());
        aTableApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aTableTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        aTableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        aTableLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        aTableContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aTableTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        aTableStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        aTableEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        aTableCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        aTableUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**This method switches to the Add Appointment menu.
     * Initializes and switches scenes to the Add Appointment view.*/
    public void addAppointmentAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stageObject = (Stage)((Node)event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }


    /**This method switches to the Modify Appointment menu.
     * Initializes and switches scenes to the Modify Appointment view and transfers over the data to populate the form.*/
    public void modifyAppointmentAction(ActionEvent event) throws IOException {

        if(appointmentTable.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointment.fxml"));
            Parent root = loader.load();

            ModifyAppointmentController appointmentToModify = loader.getController();
            appointmentToModify.changeAppointment((Appointments) appointmentTable.getSelectionModel().getSelectedItem());

            stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
            sceneObject = new Scene(root);
            stageObject.setScene(sceneObject);
            stageObject.show();
        } else {
            appointmentExceptionLabel.setText("No appointment selected to modify.");
        }
    }

    //CONTAINS LAMBDA METHOD
    /**This is the delete appointment method.
     * This method deletes a selected appointment from the tableview and database.
     * This contains a Lambda method that outputs the text for a deleted appointment, and looks cleaner than the method used in the delete customer method.*/
    public void deleteAppointmentAction(ActionEvent actionEvent) throws SQLException {
        if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
            Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
            alertObject.setTitle("Delete appointment");
            alertObject.setHeaderText("Delete");
            alertObject.setContentText("Do you want to delete the selected appointment?");

            if(alertObject.showAndWait().get() == ButtonType.OK) {

                stageObject = (Stage) mainPane.getScene().getWindow();
                int aID = (int) aTableApptIDColumn.getCellData(appointmentTable.getSelectionModel().getSelectedItem());
                String aType = (String) aTableTypeColumn.getCellData(appointmentTable.getSelectionModel().getSelectedItem());
                DBAppointments.delete(aID);
                ObservableList<Appointments> A = DBAppointments.getAllAppointments();

                //LAMBDA METHOD
                DeleteInterface deleteMessage = (chosenID, chosenType) -> appointmentExceptionLabel.setText("Appointment " + chosenID + " of type " + chosenType + " deleted.");
                deleteMessage.displayExceptionMessage(aID, aType);

                appointmentTable.setItems(A);
                appointmentTable.refresh();
            }
        } else {
         appointmentExceptionLabel.setText("No appointment selected to delete.");
        }
    }

    /**This method switches to the Add Customer menu.
     * Initializes and switches scenes to the add customer view.*/
    public void addCustomerAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stageObject = (Stage)((Node)event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }

    /**This method switches to the Modify Customer menu.
     * Initializes and switches scenes to the modify customer view and transfers over the data to populate the form.*/
    public void modifyCustomerAction(ActionEvent event) throws IOException {

        if(customerTable.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomer.fxml"));
            Parent root = loader.load();

            ModifyCustomerController customerToModify = loader.getController();

            customerToModify.changeCustomer((Customers) customerTable.getSelectionModel().getSelectedItem());

            stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
            sceneObject = new Scene(root);
            stageObject.setScene(sceneObject);
            stageObject.show();
        } else {
            customerExceptionLabel.setText("No customer selected to modify.");
        }
    }

    /**This method checks if a customer can be deleted.
     * Combs through the appointments to find a matching customer ID, and if it finds a match, returns a false value.*/
    public boolean deleteVerification(int cID){
        for (Appointments A : DBAppointments.getAllAppointments()) {
            for(Customers C : DBCustomers.getAllCustomers()){
                if (A.getCustomerId() == cID && C.getCustomerId() == A.getCustomerId()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**This is the delete customer method.
    * This method deletes a selected customer from the tableview and database.*/
    public void deleteCustomerAction(ActionEvent actionEvent) throws SQLException {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
            alertObject.setTitle("Delete customer");
            alertObject.setHeaderText("Delete");
            alertObject.setContentText("Do you want to delete the selected customer?");

            if(alertObject.showAndWait().get() == ButtonType.OK) {

                stageObject = (Stage) mainPane.getScene().getWindow();
                int cID = (int) cTableCustomerIDColumn.getCellData(customerTable.getSelectionModel().getSelectedItem());
                String cName = (String) cTableCustomerNameColumn.getCellData(customerTable.getSelectionModel().getSelectedItem());

                if(deleteVerification(cID)){

                    DBCustomers.delete(cID);
                    ObservableList<Customers> C = DBCustomers.getAllCustomers();
                    customerExceptionLabel.setText("Customer " + cID + ": " + cName + " deleted.");
                    customerTable.setItems(C);
                    customerTable.refresh();

                } else {
                    customerExceptionLabel.setText("Customer " + cID + " cannot be deleted. Has an appointment.");
                }
            }
        } else {
            customerExceptionLabel.setText("No customer selected to delete.");
        }
    }

    /**This method switches to the customer appointment report menu.
     * Initializes and switches scenes to the Customer Appointment view.*/
    public void customerAppointmentAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerAppointment.fxml"));
        stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }

    /**This method switches to the contact schedule report menu.
     * Initializes and switches scenes to the Contact Schedule view.*/
    public void contactScheduleAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactSchedule.fxml"));
        stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }

    /**This method switches to the Custom Report menu.
     * Initializes and switches scenes to the customers by country view.*/
    public void customReportAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomReport.fxml"));
        stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }

    /**This is the log off method.
     * Asks the user if they wish to exit the program and exits if the user clicks OK.*/
    public void logOffAction(ActionEvent actionEvent) {
        Alert alertObject = new Alert(Alert.AlertType.CONFIRMATION);
        alertObject.setTitle("Exit");
        alertObject.setHeaderText("You are about to exit.");
        alertObject.setContentText("Do you want to close the program?");

        if (alertObject.showAndWait().get() == ButtonType.OK) {

            stageObject = (Stage) mainPane.getScene().getWindow();
            stageObject.close();
        }
    }
}
