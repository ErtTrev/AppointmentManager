package model;

/**
 * @author Eric Trevorrow
 */

/** This class creates the Users objects.*/
public class Users {
    private int userId;
    private String userName;
    private String password;

    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**This method finds and returns the ID of a user.
     * Gets the ID of a user.
     * @return Returns the user ID.*/
    public int getUserId(){
        return userId;
    }

    /**This method finds and returns the name of a user.
     * Gets the name of a user.
     * @return Returns the user name.*/
    public String getUserName(){
        return userName;
    }

    /**This method finds and returns the password of a user.
     * Gets the password of a user.
     * @return Returns the user password.*/
    public String getPassword() {
        return password;
    }

    /**This method is used to override and format a call for the user ID and name.
     * Gets the ID and name of a user to be used in a combo box.
     * @return Returns the User ID and User Name in a specific format.*/
    @Override
    public String toString(){
        return(userId + ": " + userName);
    }
}