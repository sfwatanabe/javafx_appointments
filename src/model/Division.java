package model;

/**
 * The Division class extends the abstract Zone class. Divisions must have an
 * associated country.This class may be extended in the future to add key
 * attributes unique to a country throughout the development process.
 *
 * @author Sakae Watanabe
 */
public class Division extends Zone {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Integer value representing assigned country id.
   */
  private int countryID;

  //===========================================================================
  // Constructor
  //===========================================================================

  /**
   * Division fully parameterized constructor.
   *
   * @param id        Integer value for division id number.
   * @param name      String value for division name.
   * @param countryID Integer value for assigned country id number.
   */
  public Division(int id, String name, int countryID) {
    super(id, name);
    this.countryID = countryID;
  }

  //===========================================================================
  // Getter & Setter Methods
  //===========================================================================

  /**
   * @return Integer value representing assigned country id number.
   */
  public int getCountryID() {
    return countryID;
  }

  /**
   * Set the assigned country id number to the given integer.
   *
   * @param countryID Integer value representing assigned country id number.
   */
  public void setCountryID(int countryID) {
    this.countryID = countryID;
  }
}
