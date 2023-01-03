package controller;

/**
 * @author Eric Trevorrow
 */

/**This is the interface for the Customer Name lambda method.
 * This interface is used for the lambda method that verifies an entered name in the Add Customer and Modify Customer controllers.*/
public interface CustomerNameInterface {
    Boolean customerName(String name);
}
