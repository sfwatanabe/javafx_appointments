package model;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Holds information about appointment for customer in the database.
 *
 * @author Sakae Watanabe
 */
public class Appointment {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Integer property representing appointment id number.
   */
  private ObjectProperty<Integer> id = new SimpleObjectProperty();
  /**
   * Integer property representing id number for associated customer.
   */
  private ObjectProperty<Integer> customerId = new SimpleObjectProperty();
  /**
   * String property representing the contact name for appointment.
   */
  private StringProperty contactName = new SimpleStringProperty();
  /**
   * String property representing the appointment's title.
   */
  private StringProperty title = new SimpleStringProperty();
  /**
   * String property representing the appointment's description.
   */
  private StringProperty description = new SimpleStringProperty();
  /**
   * String property representing the appointment's type.
   */
  private StringProperty type = new SimpleStringProperty();
  /**
   * String property representing the appointment's location.
   */
  private StringProperty location = new SimpleStringProperty();
  /**
   * Scheduled starting time for appointment.
   */
  private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty();
  /**
   * Scheduled ending time for appointment.
   */
  private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty();

  //===========================================================================
  // Constructor
  //===========================================================================

  /**
   * Appointment fully parameterized constructor.
   *
   * @param id          Integer representing appointment's id number.
   * @param customerId  Integer representing associated customer's id number.
   * @param contactName String representing associated contacts name.
   * @param title       String representing title for the appointment.
   * @param description String representing description of appointment.
   * @param type        String representing the type of appointment.
   * @param location    String representing the location of the appointment.
   * @param startTime   LocalDateTime representing appointment start time.
   * @param endTime     LocalDateTime representing appointment end time.
   */

  public Appointment(int id, int customerId, String contactName, String title, String description,
      String type, String location, LocalDateTime startTime, LocalDateTime endTime) {
    this.id.set(id);
    this.customerId.set(customerId);
    this.contactName.set(contactName);
    this.title.set(title);
    this.description.set(description);
    this.type.set(type);
    this.location.set(location);
    this.startTime.set(startTime);
    this.endTime.set(endTime);
  }

  //===========================================================================
  // Getter & Setter Methods
  //===========================================================================

  /**
   * @return Appointments id number as Integer.
   */
  public Integer getId() {
    return id.get();
  }

  /**
   * @return ObjectProperty representing the appointment id number.
   */
  public ObjectProperty<Integer> idProperty() {
    return id;
  }

  /**
   * Sets the value for appointment id to the given id value.
   *
   * @param id Integer value for new appointment id.
   */
  public void setId(int id) {
    this.id.set(id);
  }

  /**
   * @return Associated customer id number as Integer.
   */
  public Integer getCustomerId() {
    return customerId.get();
  }

  /**
   * @return ObjectProperty representing the associated customer's id.
   */
  public ObjectProperty<Integer> customerIdProperty() {
    return customerId;
  }

  /**
   * Sets the value for the associated customer id to given customer id value.
   *
   * @param customerId Integer value for customer id.
   */
  public void setCustomerId(int customerId) {
    this.customerId.set(customerId);
  }

  /**
   * @return String representing associated contact name for appointment.
   */
  public String getContactName() {
    return contactName.get();
  }

  /**
   * @return StringProperty representing contact name for appointment.
   */
  public StringProperty contactNameProperty() {
    return contactName;
  }

  /**
   *  Sets the value for the associated contact name to given String value.
   *
   * @param contactName String representing contact name.
   */
  public void setContactName(String contactName) {
    this.contactName.set(contactName);
  }

  /**
   * @return String value representing the title of appointment.
   */
  public String getTitle() {
    return title.get();
  }

  /**
   * @return StringProperty representing the title of appointment.
   */
  public StringProperty titleProperty() {
    return title;
  }

  /**
   * Sets the title of the appointment to the given string value.
   *
   * @param title String representing the title of appointment.
   */
  public void setTitle(String title) {
    this.title.set(title);
  }

  /**
   * @return String value representing description of appointment.
   */
  public String getDescription() {
    return description.get();
  }

  /**
   * @return StringProperty representing description of appointment.
   */
  public StringProperty descriptionProperty() {
    return description;
  }

  /**
   * Sets the description of the appointment to the given string value.
   *
   * @param description String representing the description of appointment.
   */
  public void setDescription(String description) {
    this.description.set(description);
  }

  /**
   * @return String value representing type of appointment.
   */
  public String getType() {
    return type.get();
  }

  /**
   * @return StringProperty representing type of appointment.
   */
  public StringProperty typeProperty() {
    return type;
  }

  /**
   * Sets the type of the appointment to the given string value.
   *
   * @param type String representing the type of appointment.
   */
  public void setType(String type) {
    this.type.set(type);
  }

  /**
   * @return String representing the location of appointment.
   */
  public String getLocation() {
    return location.get();
  }

  /**
   * @return StringProperty representing the location of appointment.
   */
  public StringProperty locationProperty() {
    return location;
  }

  /**
   * Sets the location of the appointment to the given string value.
   *
   * @param location String representing the location of appointment.
   */
  public void setLocation(String location) {
    this.location.set(location);
  }

  /**
   * @return LocalDateTime for the appointment start time.
   */
  public LocalDateTime getStartTime() {
    return startTime.get();
  }

  /**
   * @return ObjectProperty holding the appointment start time.
   */
  public ObjectProperty<LocalDateTime> startTimeProperty() {
    return startTime;
  }

  /**
   * Set the appointment start time to the provided LocalDateTime.
   *
   * @param startTime LocalDateTime representing new start time for appointment.
   */
  public void setStartTime(LocalDateTime startTime) {
    this.startTime.set(startTime);
  }

  /**
   * @return LocalDateTime for the appointment end time.
   */
  public LocalDateTime getEndTime() {
    return endTime.get();
  }

  /**
   * @return ObjectProperty holding the appointment end time.
   */
  public ObjectProperty<LocalDateTime> endTimeProperty() {
    return endTime;
  }

  /**
   * Set the appointment end time to the provided LocalDateTime.
   *
   * @param endTime LocalDateTime representing new end time for appointment.
   */
  public void setEndTime(LocalDateTime endTime) {
    this.endTime.set(endTime);
  }

  //===========================================================================
  // Default Overrides
  //===========================================================================

  /**
   * @return Appointment object information in a string format.
   */
  @Override
  public String toString() {
    return "Appointment{" +
        "id=" + id.get() +
        ", customerId=" + customerId.get() +
        ", contactName=" + contactName.get() +
        ", title=" + title.get() +
        ", description=" + description.get() +
        ", type=" + type.get() +
        ", location=" + location.get() +
        ", startTime=" + startTime.get() +
        ", endTime=" + endTime.get() +
        '}';
  }
}
