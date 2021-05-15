package view_controller;

import dao.impl.ContactDAOImpl;
import dao.impl.CustomerDAOImpl;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;


/**
 * @author Sakae Watanabe
 */

public class AppointmentViewController implements Initializable {

  //===========================================================================
  // Data Members
  //===========================================================================

  // TODO Add scene initializer members
  /**
   * User currently editing the appointment record.
   */
  private User user;

  /**
   * Indicates if we are adding a new appointment.
   */
  private boolean isNew;

  /**
   * Holds current appointment record when updating.
   */
  private Appointment currentAppointment;


  /**
   * ContactDAO implementation for data retrieval.
   */
  private final ContactDAOImpl contactDAO = new ContactDAOImpl();

  /**
   * CustomerDAO implementation for data retrieval.
   */
  private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();

  /**
   * List of contacts for use in the contact combo box.
   */
  private final ObservableList<Contact> contacts = contactDAO.getAll();

  /**
   * List of customers for use in the customer combo box.
   */
  private final ObservableList<Customer> customers = customerDAO.getAll();

  /**
   * FilteredList of starting times for appointments.
   */
  private FilteredList<LocalTime> startTimes;

  /**
   * FilteredList of ending times for appointments.
   */
  private FilteredList<LocalTime> endTimes;

  /**
   * Indicates type of appointment record operation in process.
   */
  @FXML
  private Label appointmentType;

  /**
   * Database id key for the current appointment record.
   */
  @FXML
  private TextField idField;

  /**
   * Title for the current appointment record.
   */
  @FXML
  private TextField titleField;

  /**
   * Location for the current appointment record.
   */
  @FXML
  private TextField locationField;

  /**
   * Type of the appointment record.
   */
  @FXML
  private TextField typeField;

  /**
   * ComboBox containing list of choices for contacts.
   */
  @FXML
  private ComboBox<Contact> contactCombo;

  /**
   * ComboBox containing list of choices for customers.
   */
  @FXML
  private ComboBox<Customer> customerCombo;

  /**
   * Contains list of available appointment starting time choices in local time.
   */
  @FXML
  private ComboBox<LocalTime> startTime;

  /**
   * Contains list of available appointment ending time choices in local time.
   */
  @FXML
  private ComboBox<LocalTime> endTime;

  /**
   * Date picker for appointment date.
   */
  @FXML
  private DatePicker datePicker;

  /**
   * Extended description for the appointment record.
   */
  @FXML
  private TextField descriptionField;

  /**
   * Button to confirm save of new/updated customer record.
   */
  @FXML
  private Button saveButton;

  /**
   * Button to cancel current appointment record operation.
   */
  @FXML
  private Button cancelButton;

  /**
   * Label for alerting user that fields have not been completed.
   */
  @FXML
  private Label emptyWarning;

  //===========================================================================
  // Scene Initialization
  //===========================================================================


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    
  }

  
  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================


  @FXML
  private void checkEndTime(ActionEvent event) {

  }

  @FXML
  private void checkStartTime(ActionEvent event) {

  }

  @FXML
  private void cancelHandler(ActionEvent event) {

  }

  @FXML
  private void saveHandler(ActionEvent event) {

  }
}
