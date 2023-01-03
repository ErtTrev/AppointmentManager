package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;

/**
 * @author Eric Trevorrow
 */

/** This class accesses appointment database information.*/
public class DBAppointments {

    /**This method returns a list of appointments.
     * Obtains all appointments from the database and returns the list.
     * @return Returns the list of appointments from the database.*/
    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");

                Appointments A = new Appointments(appointmentId, title, description, location, type, customerID, userID,
                        contactID, startDate.toLocalDateTime(), endDate.toLocalDateTime());
                appointmentList.add(A);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**This method adds an appointment into the database.
     * Adds an appointment into the database.*/
    public static int insert(String title, String description, String location,
                             String type, int customerID, int userID, int contactID, Timestamp startDate, Timestamp endDate) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Customer_ID, User_ID, Contact_ID, Start, End) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setInt(5,customerID);
        ps.setInt(6,userID);
        ps.setInt(7,contactID);
        ps.setTimestamp(8, startDate);
        ps.setTimestamp(9, endDate);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method updates an appointment in the database.
     * Updates an appointment in the database.*/
    public static int update(String title, String description, String location,
                             String type, int customerID, int userID, int contactID, Timestamp startDate, Timestamp endDate, int appointmentId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, Start = ?, End = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setInt(5,customerID);
        ps.setInt(6,userID);
        ps.setInt(7,contactID);
        ps.setTimestamp(8, startDate);
        ps.setTimestamp(9, endDate);
        ps.setInt(10, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method deletes an appointment in the database.
     * Deletes an appointment in the database.
     * @param appointmentId this is the ID of the appointment to be deleted.*/
    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This method returns a list of appointment titles.
     * Obtains all appointments titles from the database and returns the list.
     * @return Returns the list of appointment titles from the database.*/
    public static ObservableList<String> getAllAppointmentTitles(){
        ObservableList<String> appointmentTitlesList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT DISTINCT Type from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String type = rs.getString("Type");
                appointmentTitlesList.add(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentTitlesList;
    }

    /**This method returns a count of the list of appointments of a specific type in a selected month.
     * Obtains all appointments from the database and returns the a count of distinct types in a specific month.
     * @param selectedType The selected type to search for.
     * @param selectedMonth The selected month to filter results through.*/
    public static String getAppointmentTitlesCount(String selectedType, String selectedMonth){
        ObservableList<String> appointmentTitlesCount = FXCollections.observableArrayList();

        try{
            String sql = "SELECT count(*) from appointments where type = ? and MONTHNAME(start) = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, selectedType);
            ps.setString(2, selectedMonth);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String typeCount = rs.getString("COUNT(*)");

                appointmentTitlesCount.add(typeCount);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentTitlesCount.toString();
    }

    /**This method returns a list of appointments by week.
     * Obtains all appointments from the database for the current week and returns the list.
     * @return Returns the list of appointments for the current week from the database.*/
    public static ObservableList<Appointments> getAllWeeklyAppointments(){
        ObservableList<Appointments> appointmentWeeklyList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(CURRENT_DATE()) AND MONTH(Start) = MONTH(CURRENT_DATE()) AND YEAR(Start) = YEAR(CURRENT_DATE())";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");

                Appointments A = new Appointments(appointmentId, title, description, location, type, customerID, userID,
                        contactID, startDate.toLocalDateTime(), endDate.toLocalDateTime());
                appointmentWeeklyList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentWeeklyList;
    }

    /**This method returns a list of appointments by month.
     * Obtains all appointments from the database for the current month and returns the list.
     * @return Returns the list of appointments for the current month from the database.*/
    public static ObservableList<Appointments> getAllMonthlyAppointments(){
        ObservableList<Appointments> appointmentMonthlyList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURRENT_DATE()) AND YEAR(Start) = YEAR(CURRENT_DATE())";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");

                Appointments A = new Appointments(appointmentId, title, description, location, type, customerID, userID,
                        contactID, startDate.toLocalDateTime(), endDate.toLocalDateTime());
                appointmentMonthlyList.add(A);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentMonthlyList;
    }
}
