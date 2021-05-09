package dao;

import javafx.collections.ObservableList;
import model.Country;

/**
 * CountryDAO interface specifies methods to be implemented for data
 * retrieval of appointment records. Used for generating list of country
 * data.
 *
 * @author Sakae Watanabe
 */
public interface CountryDAO {

  /**
   * getById returns a list of countries matching the given country id.
   *
   * @return ObservableList of countries matching the given id.
   * @param countryId Integer value representing countryID.
   */
  public ObservableList<Country> getById(int countryId);

  /**
   * @return Observable list of all countries in the database.
   */
  public ObservableList<Country> getAll();

}
