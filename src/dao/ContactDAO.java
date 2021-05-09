package dao;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * ContactDAO interface specifies methods to be implemented for data
 * retrieval of appointment records. Used for generating list of contact
 * data.
 *
 * @author Sakae Watanabe
 */
public interface ContactDAO {

  /**
   * @return Observable list of all contacts in the database.
   */
  public ObservableList<Contact> getAll();
}
