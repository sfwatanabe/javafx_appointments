package view_controller;

import dao.impl.AppointmentDAOImpl;
import dao.impl.ContactDAOImpl;
import dao.impl.CustomerDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.BusinessHours;
import utils.ControlValidation;
import utils.NotificationHandler;


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
   * AppointmentDAO implementation for data retrieval.
   */

  private final AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();

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
    fieldControls.forEach(c -> ControlValidation
        .checkEmptySelections(c, fieldControlStatus, emptyWarning, saveButton));

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
    fieldControlStatus.replaceAll((k, v) -> v = false);
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
    fieldControlStatus.replaceAll((k, v) -> v = true);
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

      startTime.setValue(currentAppointment.getLocalStartTime());
      endTime.setValue(currentAppointment.getLocalEndTime());
    }

  }
  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================

  @FXML
  private void saveHandler(ActionEvent event) throws IOException {
    // Check if they want to save first
    List<String> conflictMessages = new ArrayList<String>();

    if (NotificationHandler.confirmPopup(event, "Save changes ?")) {
      LocalDateTime start = LocalDateTime.of(datePicker.getValue(), startTime.getValue());
      LocalDateTime end = LocalDateTime.of(datePicker.getValue(), endTime.getValue());
      var conflictList = appointmentDAO.getBetween(start, end);
      //TODO put in the appointments between check here and only parse the appointment if we're good
      if (conflictList.isEmpty()){
        int customerId = customerCombo.getValue().getId();
        int contactId = contactCombo.getValue().getId();
        String contactName = contactCombo.getValue().getName();
        String title = titleField.getText().strip();
        String description = descriptionField.getText().strip();
        String type = typeField.getText().strip();
        String location = locationField.getText().strip();
        // New appointment
        if (isNew) {
          currentAppointment = new Appointment(-1, customerId, contactId, contactName, user.getId(),
              title, description, type, location, start, end);
          currentAppointment.setId(appointmentDAO.addAppointment(currentAppointment, user));

          if (currentAppointment.getId() > 0) {
            NotificationHandler.warningPopup("Add Complete", "Appointment\n" +
                currentAppointment + " has been added.");
            loadMainView(event);
          }
        }
        // Update appointment


      } else {
        conflictMessages = conflictList.stream().map(Appointment::toString).collect(Collectors.toList());
        NotificationHandler.warningPopup("Scheduling Overlaps", conflictMessages);
      }

    }
  }

  @FXML
  private void checkEndTime(ActionEvent event) {

  }

  @FXML
  private void checkStartTime(ActionEvent event) {

  }

  /**
   * Cancel the current add or update operation and revert scene to the main view.
   *
   * @param event ActionEvent from user clicking on the cancel button.
   */
  @FXML
  private void cancelHandler(ActionEvent event) throws IOException {
    String message = "Discard changes and return to main?";

    if (NotificationHandler.confirmPopup(event, message)) {
      loadMainView(event);

    }
  }


  /**
   * Loads the main view and initializes user data.
   *
   * @param event ActionEvent triggered by save or cancel button handlers.
   */
  @SuppressWarnings("DuplicatedCode")
  private void loadMainView(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view_controller/MainView.fxml"));
    Parent parent = loader.load();
    Scene scene = new Scene(parent);

    MainViewController controller = loader.getController();
    controller.initData(user);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

}
