package model;

/**
 * @author Eric Trevorrow
 */

/** This class creates the First Level Divisions objects.*/
public class FirstLevelDivisions {
    private int divisionId;
    private String divisionName;
    private int countryId;

    public FirstLevelDivisions(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**This method finds and returns the ID of a country.
     * Gets the ID of the country.
     * @return Returns the Country ID from the First Level Division list.*/
    public int getCountryId(){
        return countryId;
    }

    /**This method finds and returns the name of a first level division.
     * Gets the name of a first level division.
     * @return Returns the division name.*/
    public String getDivisionName(){
        return divisionName;
    }

    /**This method finds and returns the ID of a first level division.
     * Gets the ID of a first level division.
     * @return Returns the division ID.*/
    public int getDivisionId() {
        return divisionId;
    }

    /**This method is used to override and format a call for the first level division ID and name.
     * Gets the ID and name of a first level division to be used in a combo box.
     * @return Returns the First Level Division ID and First Level Division Name in a specific format.*/
    @Override
    public String toString(){
        return("#" + Integer.toString(divisionId) + " " + divisionName);
    }
}
