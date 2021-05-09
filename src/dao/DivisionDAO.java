package dao;

import javafx.collections.ObservableList;
import model.Division;

/**
 * DivisionDAO interface specifies methods to be implemented for data retrieval of appointment
 * records. Used for generating lists of division data.
 *
 * @author Sakae Watanabe
 */
public interface DivisionDAO {

  /**
   * @return Observable list of all divisions in the database.
   */
  public ObservableList<Division> getAll();

  /**
   * getByCountry queries customer data for divisions assigned to country id.
   *
   * @param countryID Integer value of country id number.
   * @return ObservableList of division data assigned to country id.
   */
  public ObservableList<Division> getByCountry(int countryID);
}
