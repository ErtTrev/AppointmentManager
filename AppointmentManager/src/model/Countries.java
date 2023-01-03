package model;

/**
 * @author Eric Trevorrow
 */

/** This class creates the Countries objects.*/
public class Countries {
    private int countryId;
    private String countryName;

    public Countries(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**This method finds and returns the ID of a country.
     * Gets the ID of the country.
     * @return Returns the Country ID.*/
    public int getCountryId(){ return countryId; }

    /**This method finds and returns the name of a country.
     * Gets the name of the country
     * @return Returns the Country Name.*/
    public String getCountryName(){
        return countryName;
    }

    /**This method is used to override and format a call for the country name.
     * Gets the name of a country to be used in a combo box.
     * @return Returns the Country Name.*/
    @Override
    public String toString(){
        return(countryName);
    }

}


