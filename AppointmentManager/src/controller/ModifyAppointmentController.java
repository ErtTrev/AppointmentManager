package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * @author Eric Trevorrow
 */

/**This class launches the Modify Appointment controller of the project.*/
public class ModifyAppointmentController implements Initializable {
    public AnchorPane appointmentPane;
    public TextField appointmentIDTextBox;
    public TextField titleTextBox;
    public TextField descriptionTextBox;
    public TextField locationTextBox;
    public ComboBox<Contacts> contactComboBox;
    public TextField typeTextBox;
    public DatePicker dateDatepicker;
    public ComboBox<LocalTime> startTimeHour;
    public ComboBox<LocalTime> endTimeHour;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox<Users> userComboBox;
    public ComboBox<Customers> customerComboBox;
    public Label exceptionLabel;
    public DatePicker dateEndDatepicker;
    LocalTime startOfTimeBox = LocalTime.MIDNIGHT;
    LocalTime endOfTimeBox = LocalTime.of(23, 44);
    LocalTime extraMinutes = LocalTime.of(23,45);
    private model.Appointments appointmentToModify = null;

    /**This is the initialize method.
     * This method is used to set the values in the combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactComboBox.setItems(DBContacts.getAllContacts());
        userComboBox.setItems(DBUsers.getAllUsers());
        customerComboBox.setItems((DBCustomers.getAllCustomers()));

        while(startOfTimeBox.isBefore(endOfTimeBox.plusSeconds(1))) {
            startTimeHour.getItems().add(startOfTimeBox);
            endTimeHour.getItems().add(startOfTimeBox);
            startOfTimeBox = startOfTimeBox.plusMinutes(15);
        }
        startTimeHour.getItems().add(extraMinutes);
        endTimeHour.getItems().add(extraMinutes);
    }

    /**This is the change appointment method.
     * This method pulls from the selected data transferred from the Main Controller and populates the Modify Appointment form with the data.*/
    public void changeAppointment(model.Appointments appointmentToModify){

        this.appointmentToModify = appointmentToModify;

        appointmentIDTextBox.setText(String.valueOf(this.appointmentToModify.getAppointmentId()));
        typeTextBox.setText(this.appointmentToModify.getType());
        titleTextBox.setText(this.appointmentToModify.getTitle());
        descriptionTextBox.setText(this.appointmentToModify.getDescription());
        locationTextBox.setText(this.appointmentToModify.getLocation());

        int userId = this.appointmentToModify.getUserId();
        for(Users U : DBUsers.getAllUsers()){
            if(U.getUserId() == userId){
                userComboBox.setValue(U);
            }
        }

        int customerId = this.appointmentToModify.getCustomerId();
        for(Customers C : DBCustomers.getAllCustomers()){
            if(C.getCustomerId() == customerId){
                customerComboBox.setValue(C);
            }
        }

        int contactId = this.appointmentToModify.getContactId();
        for(Contacts Co : DBContacts.getAllContacts()){
            if(Co.getContactId() == contactId){
                contactComboBox.setValue(Co);
            }
        }

        String thisStartDate = this.appointmentToModify.getStartDate();
        String cutStartDate = thisStartDate.substring(0,10);
        DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate thisStartDateTime = LocalDate.parse(cutStartDate, startFormatter);
        dateDatepicker.setValue(thisStartDateTime);

        String thisStartHour = this.appointmentToModify.getStartDate();
        String cutStartTime = thisStartHour.substring(11,16);
        LocalTime thisStartTime = LocalTime.parse(cutStartTime);
        startTimeHour.setValue(thisStartTime);

        String thisEndDate = this.appointmentToModify.getEndDate();
        String cutEndDate = thisEndDate.substring(0,10);
        LocalDate thisEndDateTime = LocalDate.parse(cutEndDate, startFormatter);
        dateEndDatepicker.setValue(thisEndDateTime);

        String thisEndHour = this.appointmentToModify.getEndDate();
        String cutEndTime = thisEndHour.substring(11,16);
        LocalTime thisEndTime = LocalTime.parse(cutEndTime);
        endTimeHour.setValue(thisEndTime);
    }

    /**This method validates if a user ID is present in the user ID field.
     * Validates if a user option is selected.*/
    public boolean userIDValidation(Users userID){
        if(userID == null){
            exceptionLabel.setText("Exception: No user entered");
            return false;
        }
        return true;
    }

    /**This method validates if a customer ID is present in the customer ID field.
     * Validates if a customer option is selected.*/
    public boolean customerIDValidation(Customers customerID){
        if(customerID == null){
            exceptionLabel.setText("Exception: No customer entered");
            return false;
        }
        return true;
    }

    /**This method validates if an appointment title is present in the text field.
     * Validates if an appointment title is entered.*/
    public boolean appointmentTitleValidation(String title){
        if(title == null || title.isEmpty()) {
            exceptionLabel.setText("Exception: No title entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if an appointment description is present in the text field.
     * Validates if an appointment description is entered.*/
    public boolean appointmentDescriptionValidation(String description){
        if(description == null || description.isEmpty()) {
            exceptionLabel.setText("Exception: No description entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if an appointment location is present in the text field.
     * Validates if an appointment location is entered.*/
    public boolean appointmentLocationValidation(String location){
        if(location == null || location.isEmpty()) {
            exceptionLabel.setText("Exception: No location entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if a contact ID is present in the customer ID field.
     * Validates if a contact option is selected.*/
    public boolean contactIDValidation(Contacts contactID){
        if(contactID == null){
            exceptionLabel.setText("Exception: No contact entered");
            return false;
        }
        return true;
    }

    /**This method validates if an appointment type is present in the text field.
     * Validates if an appointment type is entered.*/
    public boolean appointmentTypeValidation(String type){
        if(type == null || type.isEmpty()) {
            exceptionLabel.setText("Exception: No type entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if a start date is present in the start date field.
     * Validates if a start date is selected.*/
    public boolean dateValidation(LocalDate date){
        if(date == null){
            exceptionLabel.setText("Exception: No start date entered");
            return false;
        }
        return true;
    }

    /**This method validates if an end date is present in the end date field.
     * Validates if an end date is selected.*/
    public boolean dateEndValidation(LocalDate endDatepicker) {
        if (endDatepicker == null) {
            exceptionLabel.setText("Exception: No end date entered");
            return false;
        }
        return true;
    }

    /**This method validates if a start time is present in the start time field.
     * Validates if a start time is selected.*/
    public boolean startTimeValidation(LocalTime startTime){
        if(startTime == null){
            exceptionLabel.setText("Exception: No start time entered");
            return false;
        }
        return true;
    }

    /**This method validates if a start date is within business hours.
     * Validates if a start date is within 8am EST and 10pm EST.*/
    public boolean validStartBusinessHours(LocalDateTime startBusinessTime){

        ZonedDateTime zoneDate = startBusinessTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime toEstZoneDate = zoneDate.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime toEstLocalDate = toEstZoneDate.toLocalDateTime();
        ZonedDateTime localToZone = toEstLocalDate.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime ZoneToLocal = localToZone.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime zoneStartDate = ZoneToLocal.toLocalDateTime();
        LocalTime zoneEndToLocalTime = zoneStartDate.toLocalTime();

        if((zoneEndToLocalTime.isBefore(LocalTime.of(8, 00))) || (zoneEndToLocalTime.isAfter(LocalTime.of(22, 00)))){
            exceptionLabel.setText("Start time business hours can only be set between 8am-10pm EST.");
            return false;
        }
        return true;
    }

    /**This method validates if an end time is present in the end time field.
     * Validates if an end time is selected.*/
    public boolean endTimeValidation(LocalTime endTime){
        if(endTime == null){
            exceptionLabel.setText("Exception: No end time entered");
            return false;
        }
        return true;
    }

    /**This method validates if an end date is within business hours.
     * Validates if an end date is within 8am EST and 10pm EST.*/
    public boolean validEndBusinessHours(LocalDateTime endBusinessTime){

        ZonedDateTime zoneDate = endBusinessTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime toEstZoneDate = zoneDate.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime toEstLocalDate = toEstZoneDate.toLocalDateTime();

        ZonedDateTime localToZone = toEstLocalDate.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime ZoneToLocal = localToZone.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime zoneEndDate = ZoneToLocal.toLocalDateTime();
        LocalTime zoneEndToLocalTime = zoneEndDate.toLocalTime();

        if((zoneEndToLocalTime.isBefore(LocalTime.of(8, 00))) || (zoneEndToLocalTime.isAfter(LocalTime.of(22, 00)))){
            exceptionLabel.setText("End time business hours can only be set between 8am-10pm EST.");
            return false;
        }
        return true;
    }

    /**This method validates if a start date is before the end date.
     * Validates whether or not the start time is before the end time.*/
    public boolean startEndComparison(LocalDateTime startTime, LocalDateTime endTime){
        if(startTime.isAfter(endTime)){
            exceptionLabel.setText("Exception: Start time cannot be after end time.");
            return false;
        }
        return true;
    }

    /**This method validates appointment overlap.
     * Validates the entered appointment with all other appointments with the corresponding customer and checks for any overlap, and creates an exception for its own original entry.*/
    public boolean appointmentOverlapVerification(int customerIntID, LocalDate date, LocalTime startTime, LocalTime endTime){
        LocalDateTime startDate = LocalDateTime.of(date, startTime);
        LocalDateTime endDate = LocalDateTime.of(date, endTime);

        for(Appointments A : DBAppointments.getAllAppointments()) {
            if (A.getCustomerId() == customerIntID && (A.getAppointmentId() != this.appointmentToModify.getAppointmentId())) {

                DateTimeFormatter startFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

                LocalDateTime checkedTimeStart = LocalDateTime.parse(A.getStartDate(), startFormatter);
                LocalDateTime checkedTimeEnd = LocalDateTime.parse(A.getEndDate(), startFormatter);

                if((startDate.isAfter(checkedTimeStart) || startDate.isEqual(checkedTimeStart)) && (startDate.isBefore(checkedTimeEnd))){
                    exceptionLabel.setText("Start of appointment overlaps with another for this customer.");
                    return false;
                }
                if(endDate.isAfter(checkedTimeStart) && (endDate.isBefore(checkedTimeEnd) || endDate.isEqual(checkedTimeEnd))){
                    exceptionLabel.setText("End of appointment overlaps with another for this customer.");
                    return false;
                }
                if((startDate.isBefore(checkedTimeStart) || startDate.isEqual(checkedTimeStart)) && (endDate.isAfter(checkedTimeEnd) || endDate.isEqual(checkedTimeEnd))){
                    exceptionLabel.setText("Appointment time overlaps with another for this customer.");
                    return false;
                }
            }
        }
        return true;
    }

    private Stage stageObject;
    private Scene sceneObject;

    /**This method modifies the selected appointment and updates it to the appointment table.
     * Validates if all of the entered fields are valid and updates the modified appointment before it changes to the main controller.*/
    public void saveAction(ActionEvent event) throws IOException, SQLException {

        Users userID = userComboBox.getValue();
        String type = typeTextBox.getText();
        Customers customerID = customerComboBox.getValue();
        String appointmentTitle = titleTextBox.getText();
        String appointmentDescription = descriptionTextBox.getText();
        String appointmentLocation = locationTextBox.getText();
        Contacts contact = contactComboBox.getValue();
        LocalDate date = dateDatepicker.getValue();
        LocalDate endDatepicker = dateEndDatepicker.getValue();
        LocalTime startTime = startTimeHour.getValue();
        LocalTime endTime = endTimeHour.getValue();
        String appointmentID = appointmentIDTextBox.getText();

        if((userIDValidation(userID)) && (customerIDValidation(customerID)) && (appointmentTitleValidation(appointmentTitle))
                && (appointmentDescriptionValidation(appointmentDescription)) && (appointmentLocationValidation(appointmentLocation))
                && (contactIDValidation(contact)) && (appointmentTypeValidation(type)) && (dateValidation(date)) && dateEndValidation(endDatepicker)
                && (startTimeValidation(startTime)) && (endTimeValidation(endTime))){

            int customerIntID = customerComboBox.getValue().getCustomerId();

            LocalDateTime startDate = LocalDateTime.of(date, startTime);
            LocalDateTime endDate = LocalDateTime.of(endDatepicker, endTime);

            if((validStartBusinessHours(startDate)) && (validEndBusinessHours(endDate))
                    && (appointmentOverlapVerification(customerIntID, date, startTime, endTime))
                    && (startEndComparison(startDate, endDate))) {

                int rowsAffected = DBAppointments.update(appointmentTitle, appointmentDescription, appointmentLocation, type,
                        customerID.getCustomerId(), userID.getUserId(), contact.getContactId(),
                        Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), Integer.parseInt(appointmentID));

                if (rowsAffected > 0) {
                    System.out.println("Update Successful!");
                } else {
                    System.out.println("Update failed.");
                }

                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
                sceneObject = new Scene(root);
                stageObject.setScene(sceneObject);
                stageObject.show();
            }
        }
    }

    /**This is the cancel method.
     * Returns the user to the main controller.*/
    public void cancelAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stageObject = (Stage)((Node)event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }
}
