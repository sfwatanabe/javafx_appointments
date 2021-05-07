package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Basic person model implementation. Person has both a name and id for
 * the purposes of our application.
 *
 * @author Sakae Watanabe
 */
public abstract class Person {

  //===========================================================================
  // Data Members
  //===========================================================================
  /** SimpleObjectProperty to hold Customer ID number. */
  private ObjectProperty<Integer> id = new SimpleObjectProperty();

  /** StringProperty to hold Customer name. */
  private StringProperty name = new SimpleStringProperty();

  /** Basic constructor for Person class. */
  public Person(int id, String name) {
    this.id.set(id);
    this.name.set(name);
  }

  /** Returns integer value of customer id. */
  public Integer getId() {
    return id.get();
  }

  /** Returns ObjectProperty representing value of customer id. */
  public ObjectProperty<Integer> getIdProperty() {
    return id;
  }

  /**
   * Sets the value of customer id to the given integer.
   *
   * @param id Integer value representing the customer id.
   */
  public void setId(Integer id) {
    this.id.set(id);
  }

  /** Returns string value of customer name. */
  public String getName() {
    return name.get();
  }

  /** Returns StringProperty representing value of customer name. */
  public StringProperty getNameProperty() {
    return name;
  }

  /**
   * Sets the value of customer name to the given string value.
   *
   * @param name String value representing the customer name.
   */
  public void setName(String name) {
    this.name.set(name);
  }
}
