package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representation of company contact data for use in application. Intended for data access only,
 * will not have setter methods.
 *
 * @author Sakae Watanabe
 */
public class Contact extends Person {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * String representing contact email address.
   */
  private StringProperty email = new SimpleStringProperty();

  //===========================================================================
  // Constructor
  //===========================================================================

  /**
   * Contact fully parameterized constructor.
   *
   * @param id    Integer value for contact id number.
   * @param name  String value for contact name.
   * @param email String value for contact email address.
   */
  public Contact(int id, String name, String email) {
    super(id, name);
    this.email.set(email);
  }

  //===========================================================================
  // Getter & Setter Methods
  //===========================================================================


  /**
   * @return String value representing contact email address.
   */
  public String getEmail() {
    return email.get();
  }

  /**
   * @return StringProperty representing contact email address.
   */
  public StringProperty emailProperty() {
    return email;
  }

  //===========================================================================
  // Default Overrides
  //===========================================================================

  /**
   * @return Contact object information in a string format.
   */
  @Override
  public String toString() {
    return super.getId() + "-" + super.getName();
  }
}
