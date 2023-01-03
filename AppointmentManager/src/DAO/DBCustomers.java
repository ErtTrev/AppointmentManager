package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * @author Eric Trevorrow
 */

/** This class accesses customers database information.*/
public class DBCustomers {

    /**This method returns a list of customers.
     * Obtains all customers from the database and returns the list.
     * @return Returns the list of customers from the database.*/
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> customersList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, countries.Country FROM customers, countries, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String countryName = rs.getString("Country");

                Customers C = new Customers(customerId, customerName, address, postalCode, phoneNumber, divisionId, countryName);
                customersList.add(C);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customersList;
    }

    /**This method adds an customer into the database.
     * Adds a customer into the database.*/
    public static int insert(String customerName, String address, String postalCode,
                             String phoneNumber, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3,postalCode);
        ps.setString(4,phoneNumber);
        ps.setInt(5,divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method updates a customer in the database.
     * Updates a customer in the database.*/
    public static int update(String customerName, String address, String postalCode,
                             String phoneNumber, int divisionId, int customerId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3,postalCode);
        ps.setString(4,phoneNumber);
        ps.setInt(5,divisionId);
        ps.setInt(6, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method deletes a customer in the database.
     * Deletes a customer in the database.
     * @param customerId this is the ID of the customer to be deleted.*/
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
