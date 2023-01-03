package controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBFirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Eric Trevorrow
 */

//CONTAINS LAMBDA METHOD
/**This class launches the Modify Customer controller of the project.
 * The method customerNameValidation within this class uses the Lambda method, and it's more efficient than it's non-Lambda counterpart
 * because it can share an interface with the same Lambda method used within the AddCustomerController.*/
public class ModifyCustomerController implements Initializable {
    public AnchorPane customerPane;
    public TextField customerIDTextBox;
    public TextField customerNameTextBox;
    public TextField phoneNumberTextBox;
    public TextField addressTextBox;
    public ComboBox<Countries> countryComboBox;
    public ComboBox<FirstLevelDivisions> firstLevelDivisionComboBox;
    public Button saveButton;
    public Button cancelButton;
    public TextField postalCodeTextBox;
    public Label exceptionLabel;
    private model.Customers customerToModify = null;

    /**This is the initialize method.
     * This method is used to set the country values in the country combo box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setItems(DBCountries.getAllCountries());
    }

    /**This is the change customer method.
     * This method pulls from the selected data transferred from the Main Controller and populates the Modify Customer form with the data.*/
    public void changeCustomer(model.Customers customerToModify){

        this.customerToModify = customerToModify;

        customerNameTextBox.setText(this.customerToModify.getCustomerName());
        customerIDTextBox.setText(String.valueOf(this.customerToModify.getCustomerId()));
        phoneNumberTextBox.setText(String.valueOf(this.customerToModify.getPhoneNumber()));
        addressTextBox.setText((String.valueOf(this.customerToModify.getAddress())));
        postalCodeTextBox.setText((String.valueOf(this.customerToModify.getPostalCode())));

        int customerDivisionId = this.customerToModify.getDivisionId();
        for(FirstLevelDivisions D : DBFirstLevelDivisions.getAllFirstLevelDivisions()){
            if(D.getDivisionId() == customerDivisionId){

                firstLevelDivisionComboBox.setValue(null);
                firstLevelDivisionComboBox.getItems().clear();
                int countryId = D.getCountryId();

                for(Countries C : countryComboBox.getItems()){
                    if(countryId == C.getCountryId()){
                        countryComboBox.setValue(C);
                    }
                }

                for(FirstLevelDivisions F : DBFirstLevelDivisions.getAllFirstLevelDivisions()){
                    if(F.getCountryId() == countryId){
                        firstLevelDivisionComboBox.getItems().addAll(FXCollections.observableArrayList(F));
                    }
                }
                firstLevelDivisionComboBox.setValue(D);
            }
        }
    }

    private Stage stageObject;
    private Scene sceneObject;

    /**This is country combo box action method.
     * This method is used to set first level division values based on the country selected from the country combo box.*/
    public void onCountryCombo(ActionEvent event) {
        try {
            firstLevelDivisionComboBox.setValue(null);
            firstLevelDivisionComboBox.getItems().clear();
            int countryId = countryComboBox.getValue().getCountryId();
            for(FirstLevelDivisions D : DBFirstLevelDivisions.getAllFirstLevelDivisions()){
                if(D.getCountryId() == countryId){
                    firstLevelDivisionComboBox.getItems().addAll(FXCollections.observableArrayList(D));
                }
            }
        } catch(NumberFormatException e) {
            return;
        }
    }

    //LAMBDA METHOD
    /**This is the customer name lambda method.
     * This method uses a lambda function to validate whether an entered name passed through it is empty or not.
     * The same lambda can be used between the add screen and modify screen, using the same Java interface.*/
    CustomerNameInterface customerNameValidation = name -> {
        String enteredName = name;
        if(enteredName == null || enteredName.isEmpty()) {
            exceptionLabel.setText("Exception: No name entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    };

    /**This method validates if an phone number is present in the text field.
     * Validates if a phone number is entered.*/
    public boolean phoneNumberValidation(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()) {
            exceptionLabel.setText("Exception: No phone number entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if an address is present in the text field.
     * Validates if an address is entered.*/
    public boolean addressValidation(String address){
        if(address == null || address.isEmpty()) {
            exceptionLabel.setText("Exception: No address entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if a postal code is present in the text field.
     * Validates if a postal code is entered.*/
    public boolean postalCodeValidation(String postalCode){
        if(postalCode == null || postalCode.isEmpty()) {
            exceptionLabel.setText("Exception: No postal code entered");
            return false;
        } else {
            exceptionLabel.setText("");
            return true;
        }
    }

    /**This method validates if a division ID is present in the division combo box.
     * Validates if a first level division ID was selected.*/
    public boolean divisionValidation(){
        if(firstLevelDivisionComboBox.getValue() == null){
            exceptionLabel.setText("Exception: Requires both a country and a division to be selected.");
            return false;
        }
        return true;
    }

    /**This method modifies the selected customer and updates it to the customer table.
     * Validates if all of the entered fields are valid and updates the selected customer before it changes to the main controller.*/
    public void saveAction(ActionEvent event) throws IOException, SQLException {

        String customerName = customerNameTextBox.getText();
        String phoneNumber = phoneNumberTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = postalCodeTextBox.getText();
        FirstLevelDivisions divisionSelected = firstLevelDivisionComboBox.getValue();
        String customerId = customerIDTextBox.getText();

        if((customerNameValidation.customerName(customerName)) && (phoneNumberValidation(phoneNumber)) && (addressValidation(address))
                && (postalCodeValidation(postalCode)) && divisionValidation()){
            int rowsAffected = DBCustomers.update(customerName, address, postalCode, phoneNumber, divisionSelected.getDivisionId(),
            Integer.parseInt(customerId));

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
