package view_controller;

import dao.impl.ContactDAOImpl;
import dao.impl.CustomerDAOImpl;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.BusinessHours;


/**
 * Handles logic and data collection for the appointment record screen.
 *
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
  private ObservableList<Contact> contacts = contactDAO.getAll();

  /**
   * List of customers for use in the customer combo box.
   */
  private ObservableList<Customer> customers = customerDAO.getAll();

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
   * List containing the field controls for the form.
   */
  @SuppressWarnings("FieldMayBeFinal")
  private List<Control> fieldControls = new ArrayList<>();

  /**
   * Map with status representing if field has been completed.
   */
  @SuppressWarnings("FieldMayBeFinal")
  private Map<String, Boolean> fieldControlStatus = new HashMap<>();

  /**
   * Label for alerting user that fields have not been completed.
   */
  @FXML
  private Label emptyWarning;

  //===========================================================================
  // Scene Initialization
  //===========================================================================

  // TODO fill in javadoc comment for customer initialize.
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Setup combo boxes for contact and customer
    contacts = contactDAO.getAll();
    customers = customerDAO.getAll();

    startTimes = BusinessHours.getStartTimes().filtered(s -> true);
    endTimes = BusinessHours.getEndTimes().filtered(e -> true);

    contactCombo.setItems(contacts);
    customerCombo.setItems(customers);
    startTime.setItems(startTimes);
    endTime.setItems(endTimes);

    //TODO setup time lists

    // Add controls to the fieldControls list
    fieldControls.addAll(Arrays.asList(titleField, locationField, typeField, contactCombo,
        customerCombo, startTime, endTime, datePicker, descriptionField));

  }

  /**
   * Overloaded method for initializing user data and isNew flags for the appointment record
   * scene,preparing appropriate scene labels as well.
   *
   * @param isNew Indicates if we are adding a new appointment record.
   * @param user  User currently accessing the application.
   */

  public void initAppointmentData(boolean isNew, User user) {
    this.user = user;
    this.isNew = isNew;
    this.currentAppointment = null;
    updateAppointmentLabels();
    for (Control c : fieldControls) {
      fieldControlStatus.putIfAbsent(c.getId(), false);
    }
  }

  /**
   * Overloaded method for initializing user data and isNew flags for the appointment record
   * scene,preparing appropriate scene labels as well.
   *
   * @param isNew       Indicates if we are adding a new appointment record.
   * @param appointment Appointment record to open for updating.
   * @param user        User currently accessing the application.
   */

  public void initAppointmentData(boolean isNew, User user, Appointment appointment) {
    this.user = user;
    this.isNew = isNew;
    this.currentAppointment = appointment;
    updateAppointmentLabels();
    for (Control c : fieldControls) {
      fieldControlStatus.putIfAbsent(c.getId(), false);
    }
  }


  /**
   * Prepares scene labels and text fields for either adding or updating the appointment record.
   */
  private void updateAppointmentLabels() {
    if (isNew) {
      appointmentType.setText("Create New");
      datePicker.setValue(LocalDate.now());

    } else if (!isNew && currentAppointment != null) {
      appointmentType.setText("Update Existing");
      idField.setText(String.valueOf(currentAppointment.getId()));
      titleField.setText(currentAppointment.getTitle());
      locationField.setText(currentAppointment.getLocation());
      typeField.setText(currentAppointment.getType());
      datePicker.setValue(currentAppointment.getStartTime().toLocalDate());
      descriptionField.setText(currentAppointment.getDescription());

      // TODO Clean this up later and combine refs.
      for (Contact c : contacts) {
        if (currentAppointment.getContactId().equals(c.getId())) {
          contactCombo.setValue(c);
          break;
        }
      }
      for (Customer c : customers) {
        if (currentAppointment.getCustomerId().equals(c.getId())) {
          customerCombo.setValue(c);
          break;
        }
      }
      for (LocalTime lt : startTimes) {
        if (currentAppointment.getStartTime().toLocalTime().equals(lt)) {
          startTime.setValue(lt);
          break;
        }
      }
      for (LocalTime lt : endTimes) {
        if (currentAppointment.getEndTime().toLocalTime().equals(lt)) {
          endTime.setValue(lt);
          break;
        }
      }


    }

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

  // TODO move this into utils?

  /**
   * Adds listeners to data collection fields that check if input is present in the field. If no
   * input present in the field it will be bordered in red and flagged as false in the field status
   * map.
   *
   * @param control Data collection control on form used to update customer data.
   */
  private void checkEmptyField(Control control) {
    control.focusedProperty().addListener((observable, oldValue, newValue) -> {
      boolean filled = true;

      if (!newValue) {
        if (control instanceof TextField) {
          if (((TextField) control).getText().isBlank()) {
            control.getStyleClass().add("empty-form-field");
            filled = false;
          } else {
            control.getStyleClass().removeIf(style -> style.equals("empty-form-field"));
          }

        } else if (control instanceof ComboBox) {
          //noinspection rawtypes
          if (((ComboBox) control).getSelectionModel().getSelectedItem() == null) {
            control.getStyleClass().add("combo-box-empty");
            filled = false;
          } else {
            control.getStyleClass().removeIf(style -> style.equals("combo-box-empty"));
          }
        }

      }

      if (fieldControlStatus.containsValue(false)) {
        saveButton.setDisable(true);
        emptyWarning.setText("MUST COMPLETE ALL FIELDS BEFORE SAVING");
      } else {
        emptyWarning.setText("");
        Tooltip goodToGo = new Tooltip("Click to save changes.");
        saveButton.setDisable(false);
        saveButton.setTooltip(goodToGo);
      }
    });
  }

}
