package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Eric Trevorrow
 */

/** This class accesses users database information.*/
public class DBUsers {

    /**This method returns a list of users.
     * Obtains all users from the database and returns the list.
     * @return Returns the list of users from the database.*/
    public static ObservableList<Users> getAllUsers(){
        ObservableList<Users> userList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users U = new Users(userId, userName, password);
                userList.add(U);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
