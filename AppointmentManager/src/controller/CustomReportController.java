package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Eric Trevorrow
 */

/**This class launches the custom report controller of the project, which groups customers by country.*/
public class CustomReportController implements Initializable {
    public AnchorPane customReportPane;
    public TableView customReportTableview;
    public ComboBox<Countries> chooseCountryDropBox;
    public Button closeButton;
    public TableColumn customerIDColumn;
    public TableColumn customerNameColumn;
    public TableColumn divisionIDColumn;
    public TableColumn PhoneNumberColumn;

    /**This is the initialize method.
     * This method is used to set the country values in the combo box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseCountryDropBox.setItems(DBCountries.getAllCountries());
    }

    private Stage stageObject;
    private Scene sceneObject;

    /**This is the method called when you select a country.
     * This method takes selected the country and searches through the database, returning a tableview of the customers who are in the selected country.*/
    public void onChooseCustomerCountry(ActionEvent event) {
        int countryId = chooseCountryDropBox.getValue().getCountryId();
        customReportTableview.getItems().clear();

        for(FirstLevelDivisions D : DBFirstLevelDivisions.getAllFirstLevelDivisions()) {
            if (D.getCountryId() == countryId) {
                for (Customers C : DBCustomers.getAllCustomers()) {
                    if(C.getDivisionId() == D.getDivisionId()) {
                        customReportTableview.getItems().addAll(FXCollections.observableArrayList(C));
                        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                        PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
                   }
                }
            }
        }
    }

    /**This is the cancel method.
     * Returns the user to the main controller.*/
    public void cancelAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stageObject = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneObject = new Scene(root);
        stageObject.setScene(sceneObject);
        stageObject.show();
    }
}
