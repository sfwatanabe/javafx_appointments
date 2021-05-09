package model;

/**
 * Country class extends the Zone abstract class. This class may be extended
 * in the future to add key attributes unique to a country throughout the
 * development process.
 * 
 * @author Sakae Watanabe
 */
public class Country extends Zone {

  //===========================================================================
  // Constructor
  //===========================================================================  
  /**
   * Basic constructor for the Country class.
   *
   * @param id   Integer value for zone id.
   * @param name String value for zone name.
   */
  public Country(int id, String name) {
    super(id, name);
  }

}
