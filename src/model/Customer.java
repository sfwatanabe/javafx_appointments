package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representation of customer data for use in application. Customers are stored
 * in the company database and used in booking appointments.
 *
 * @author Sakae Watanabe
 */
public class Customer extends Person {

  //===========================================================================
  // Data Members
  //===========================================================================
  /** String representing customer street address information. */
  private final StringProperty address = new SimpleStringProperty();
  /** String representing customer postal code information. */
  private final StringProperty postalCode = new SimpleStringProperty();
  /** String representing customer phone number information. */
  private final StringProperty phoneNumber = new SimpleStringProperty();
  /** Integer representing the customer division id. */
  private final ObjectProperty<Integer> divisionId = new SimpleObjectProperty<>();

  //===========================================================================
  // Constructor
  //===========================================================================

  /**
   * Customer fully parameterized constructor.
   *
   * @param id Integer representing customer id.
   * @param name String representing customer name.
   * @param address String representing customer street address.
   * @param postalCode String representing customer postal code.
   * @param phoneNumber String value representing customer phone number.
   * @param divisionId Integer value representing customer division id.
   */
  public Customer(int id, String name, String address, String postalCode,
      String phoneNumber, int divisionId) {

    super(id, name);
    this.address.set(address);
    this.postalCode.set(postalCode);
    this.phoneNumber.set(phoneNumber);
    this.divisionId.set(divisionId);
  }

  /**
   * No Arg constructor for the Customer class initializes empty strings and
   * negative id numbers.
   */
  public Customer() {

    super();
    this.address.set("");
    this.postalCode.set("");
    this.phoneNumber.set("");
    this.divisionId.set(-1);
  }


  //===========================================================================
  // Getter & Setter Methods
  //===========================================================================
  /**
   * @return String representing customer street address.
   */
  public String getAddress() {
    return address.get();
  }

  /**
   * @return StringProperty representing customer street address.
   */
  public StringProperty addressProperty() {
    return address;
  }

  /**
   * Set customer postal code to supplied string value.
   *
   * @param address String representing the new postal code value.
   */
  public void setAddress(String address) {
    this.address.set(address);
  }

  /**
   * @return String representing customer postal code.
   */
  public String getPostalCode() {
    return postalCode.get();
  }

  /**
   * @return StringProperty representing customer postal code.
   */
  public StringProperty postalCodeProperty() {
    return postalCode;
  }

  /**
   * Set customer postal code to supplied string value.
   *
   * @param postalCode String value representing new postal code.
   */
  public void setPostalCode(String postalCode) {
    this.postalCode.set(postalCode);
  }

  /**
   * @return String value representing customer phone number.
   */
  public String getPhoneNumber() {
    return phoneNumber.get();
  }

  /**
   * @return StringProperty representing customer phone number.
   */
  public StringProperty phoneNumberProperty() {
    return phoneNumber;
  }

  /**
   * Set customer phone number to supplied string value.
   *
   * @param phoneNumber String representing new phone number value.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber.set(phoneNumber);
  }

  /**
   * @return Integer representing customer division id.
   */
  public Integer getDivisionId() {
    return divisionId.get();
  }

  /**
   * @return ObjectProperty with integer value representing customer division id.
   */
  public ObjectProperty<Integer> divisionIdProperty() {
    return divisionId;
  }

  /**
   * Set divisionId to supplied integer value.
   *
   * @param divisionId Integer value to set division id to.
   */
  public void setDivisionId(Integer divisionId) {
    this.divisionId.set(divisionId);
  }

  //===========================================================================
  // Default Overrides
  //===========================================================================
  /**
   * @return Customer object information in a string format.
   */
  @Override
  public String toString() {
    return super.getId() + "-" + super.getName();
  }
}
