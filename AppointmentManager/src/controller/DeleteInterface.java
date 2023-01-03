package controller;

/**
 * @author Eric Trevorrow
 */

/**This is the interface for the delete appointment action lambda method.
 * This interface is used in the deleteAppointmentAction method in the Main controller, and is cleaner to write than the alternative used in the deleteCustomerAction method.*/
public interface DeleteInterface {
    void displayExceptionMessage(int chosenID, String chosenType);
}
