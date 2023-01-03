package controller;

import DAO.DBAppointments;
import DAO.DBContacts;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.FirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Eric Trevorrow
 */

/**This class launches the contact schedule controller of the project.*/
public class ContactScheduleController implements Initializable {
    public AnchorPane contactSchedulePane;
    public TableView contactScheduleTableview;
    public ComboBox<Contacts> chooseContactComboBox;
    public Button closeButton;
    public TableColumn contactAppointmentIDColumn;
    public TableColumn contactCustomerIDColumn;
    public TableColumn contactTitleColumn;
    public TableColumn contactTypeColumn;
    public TableColumn contactDescriptionColumn;
    public TableColumn contactStartDateColumn;
    public TableColumn contactEndDateColumn;

    /**This is the initialize method.
     * This method is used to set the contact values in the combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseContactComboBox.setItems(DBContacts.getAllContacts());
    }

    private Stage stageObject;
    private Scene sceneObject;

    /**This method activates when a contact is selected.
     * Populates the tableview with appointment data that shares a contact ID with the chosen contact.*/
    public void onContactComboChoose(ActionEvent event) {
        int contactId = chooseContactComboBox.getValue().getContactId();
        contactScheduleTableview.getItems().clear();
        for(Appointments A : DBAppointments.getAllAppointments()){
            if(A.getContactId() == contactId){
                contactScheduleTableview.getItems().addAll(FXCollections.observableArrayList(A));
                contactAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                contactDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                contactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                contactStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                contactEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                contactCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
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
