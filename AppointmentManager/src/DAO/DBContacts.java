package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Eric Trevorrow
 */

/** This class accesses contacts database information.*/
public class DBContacts {

    /**This method returns a list of contacts.
     * Obtains all contacts from the database and returns the list.
     * @return Returns the list of contacts from the database.*/
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts U = new Contacts(contactId, contactName, contactEmail);
                contactsList.add(U);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsList;
    }
}
