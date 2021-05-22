package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
  private final ObjectProperty<Integer> id = new SimpleObjectProperty<>();
  /**
   * Integer property representing id number for associated customer.
   */
  private final ObjectProperty<Integer> customerId = new SimpleObjectProperty<>();
  /**
   * Integer property representing the contact id for appointment.
   */
  private final ObjectProperty<Integer> contactId = new SimpleObjectProperty<>();

  /**
   * Integer property representing the contact id for appointment.
   */
  private final ObjectProperty<Integer> userId = new SimpleObjectProperty<>();

  /**
   * String property representing the contact name for appointment.
   */
  private final StringProperty contactName = new SimpleStringProperty();

  /**
   * String property representing the appointment's title.
   */
  private final StringProperty title = new SimpleStringProperty();
  /**
   * String property representing the appointment's description.
   */
  private final StringProperty description = new SimpleStringProperty();
  /**
   * String property representing the appointment's type.
   */
  private final StringProperty type = new SimpleStringProperty();
  /**
   * String property representing the appointment's location.
   */
  private final StringProperty location = new SimpleStringProperty();
  /**
   * Scheduled starting time for appointment.
   */
  private final ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
  /**
   * Scheduled ending time for appointment.
   */
  private final ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

  //===========================================================================
  // Constructor
  //===========================================================================

  /**
   * Appointment fully parameterized constructor.
   *
   * @param id          Integer representing appointment's id number.
   * @param customerId  Integer representing associated customer's id number.
   * @param contactId   Integer representing associated contact's id number.
   * @param contactName String representing associated contact's name.
   * @param userId      Integer value representing userID assigned to appointment.
   * @param title       String representing title for the appointment.
   * @param description String representing description of appointment.
   * @param type        String representing the type of appointment.
   * @param location    String representing the location of the appointment.
   * @param startTime   LocalDateTime representing appointment start time.
   * @param endTime     LocalDateTime representing appointment end time.
   */

  public Appointment(int id, int customerId, int contactId, String contactName, int userId,
      String title, String description, String type, String location, LocalDateTime startTime,
      LocalDateTime endTime) {
    this.id.set(id);
    this.customerId.set(customerId);
    this.contactId.set(contactId);
    this.contactName.set(contactName);
    this.userId.set(userId);
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
   * @return Associated contact id number as Integer.
   */
  public Integer getContactId() {
    return contactId.get();
  }

  /**
   * @return ObjectProperty representing the contact id number.
   */
  public ObjectProperty<Integer> contactIdProperty() {
    return contactId;
  }

  /**
   * Sets the value for the associated contact id to given integer value.
   *
   * @param contactId Integer value for contact id.
   */
  public void setContactId(int contactId) {
    this.contactId.set(contactId);
  }

  /**
   * @return String value representing associated contact's name.
   */
  public String getContactName() {
    return contactName.get();
  }

  /**
   * @return String property containing the associated contact's name value.
   */
  public StringProperty contactNameProperty() {
    return contactName;
  }

  /**
   * Sets the associated contact name to the given string value.
   *
   * @param contactName String value representing associated contact's name.
   */
  public void setContactName(String contactName) {
    this.contactName.set(contactName);
  }

  /**
   * @return Associated user id number as Integer.
   */
  public Integer getUserId() {
    return userId.get();
  }

  /**
   * @return ObjectProperty representing the user id number.
   */
  public ObjectProperty<Integer> userIdProperty() {
    return userId;
  }

  /**
   * Sets the value for the associated user id to given integer value.
   *
   * @param userId Integer value for user id.
   */
  public void setUserId(int userId) {
    this.userId.set(userId);
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
   * @return Local starting time in 24hr format rounded to minutes.
   */
  public LocalTime getLocalStartTime() {
    return startTime.get().toLocalTime().truncatedTo(ChronoUnit.MINUTES);
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
   * @return Local end time in 24hr format rounded to minutes.
   */
  public LocalTime getLocalEndTime() {
    return endTime.get().toLocalTime().truncatedTo(ChronoUnit.MINUTES);
  }

  /**
   * @return String of the local date time for appointment start.
   */
  public String getStartLocalString() {
    return endTime.get().toLocalDate() + " " + getLocalEndTime();
  }

  /**
   * @return String of the local date time for appointment end.
   */
  public String getEndLocalString() {
    return startTime.get().toLocalDate() + " " + getLocalStartTime();
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
    return "Appt ID: " + id.get() + " Customer: " + getCustomerId() +
        "\nType: " + type.get() +
        "\nStart: " + Timestamp.valueOf(startTime.get()) +
        "\nEnd: " + Timestamp.valueOf(endTime.get()) + "\n";
  }
}
