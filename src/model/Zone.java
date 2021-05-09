package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains information associated with Zones.
 *
 * @author Sakae Watanabe
 */
public abstract class Zone {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * ObjectProperty for holding zone id number.
   */
  private ObjectProperty<Integer> id = new SimpleObjectProperty();

  /**
   * StringProperty to hold zone name.
   */
  private StringProperty name = new SimpleStringProperty();

  //===========================================================================
  // Constructor
  //===========================================================================

  /**
   * Basic constructor for the Zone class.
   *
   * @param id Integer value for zone id.
   * @param name String value for zone name.
   */
  public Zone(int id, String name) {
    this.id.set(id);
    this.name.set(name);
  }

  //===========================================================================
  // Getter & Setter Methods
  //===========================================================================

  /**
   * @return Integer value representing zone id.
   */
  public Integer getId() {
    return id.get();
  }

  /**
   * @return IntegerProperty containing zone id value.
   */
  public ObjectProperty<Integer> idProperty() {
    return id;
  }

  /**
   * Sets the value of zone id to the given integer.
   *
   * @param id Integer value representing the zone id.
   */
  public void setId(Integer id) {
    this.id.set(id);
  }

  /**
   * @return String value representing the zone name.
   */
  public String getName() {
    return name.get();
  }

  /**
   * @return StringProperty containing the zone name value.
   */
  public StringProperty nameProperty() {
    return name;
  }

  /**
   * Sets the value of zone name to the given string.
   *
   * @param name String value representing the zone name.
   */
  public void setName(String name) {
    this.name.set(name);
  }

  //===========================================================================
  // Default Overrides
  //===========================================================================


  /**
   * @return Zone information in a user readable format.
   */
  @Override
  public String toString() {
    return id.get() + "-" + name.get();
  }
}
