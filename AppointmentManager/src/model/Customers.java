package model;

/**
 * @author Eric Trevorrow
 */

/** This class creates the Customers objects.*/
public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private String countryName;


    public Customers(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId, String countryName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.countryName = countryName;
    }

    /**This method finds and returns the ID of a customer.
     * Gets the ID of the customer.
     * @return Returns the customer ID.*/
    public int getCustomerId() {
        return customerId;
    }

    /**This method finds and returns the name of a customer.
     * Gets the name of the customer.
     * @return Returns the Customer name.*/
    public String getCustomerName() {
        return customerName;
    }

    /**This method finds and returns the address of a customer.
     * Gets the address of the customer.
     * @return Returns the Customer address.*/
    public String getAddress() {
        return address;
    }

    /**This method finds and returns the postal code of a customer.
     * Gets the postal code of a customer.
     * @return Returns the Customer postal code.*/
    public String getPostalCode(){
        return postalCode;
    }

    /**This method finds and returns the phone number of a customer.
     * Gets the phone number of a customer.
     * @return Returns the Customer phone number.*/
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**This method finds and returns the division ID of a customer.
     * Gets the division ID of a customer.
     * @return Returns the Customer first level division ID.*/
    public int getDivisionId() {
        return divisionId;
    }

    /**This method finds and returns the country of a customer.
     * Gets the country of a customer.
     * @return Returns the Customer country.*/
    public String getCountryName(){ return countryName;}

    /**This method is used to override and format a call for the Customer ID and name.
     * Gets the ID and name of a Customer to be used in a combo box.
     * @return Returns the Customer ID and Customer Name in a specific format.*/
    @Override
    public String toString(){
        return(customerId + ": " + customerName);
    }
}
