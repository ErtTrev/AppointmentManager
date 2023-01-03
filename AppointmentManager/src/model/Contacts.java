package model;

/**
 * @author Eric Trevorrow
 */

/** This class creates the Contacts objects.*/
public class Contacts {
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**This method finds and returns the ID of a contact.
     * Gets the ID of the contact.
     * @return Returns the Contact ID.*/
    public int getContactId(){
        return contactId;
    }

    /**This method finds and returns the name of a contact.
     * Gets the name of the contact.
     * @return Returns the Contact Name.*/
    public String getContactName(){
        return contactName;
    }

    /**This method finds and returns the Email of a contact.
     * Gets the email of the contact.
     * @return Returns the Contact Email.*/
    public String getContactEmail(){
        return contactEmail;
    }

    /**This method is used to override and format a call for the contact ID and contact name.
     * Gets the ID and name of a contact to be used in a combo box.
     * @return Returns the Contact ID and Contact Name in a specific format.*/
    @Override
    public String toString(){
        return(contactId + ": " + contactName);
    }

}

