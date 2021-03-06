package view_controller;

import dao.impl.AppointmentDAOImpl;
import dao.impl.ContactDAOImpl;
import dao.impl.CustomerDAOImpl;
import dao.impl.UserDAOImpl;
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
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.Duration;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Person;
import model.User;
import utils.BusinessHours;
import utils.ControlValidation;
import utils.NotificationHandler;


/**
 * Handles logic and data collection for the appointment record screen.
 *
 * @author Sakae Watanabe
 */

public class AppointmentViewController {

  //===========================================================================
  // Data Members
  //===========================================================================

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
   * UserDAO implementation for data retrieval.
   */
  private final UserDAOImpl userDAO = new UserDAOImpl();

  /**
   * List of contacts for use in the contact combo box.
   */
  private ObservableList<Contact> contacts;

  /**
   * List of customers for use in the customer combo box.
   */
  private ObservableList<Customer> customers;

  /**
   * List of users for use in user combo box.
   */
  private ObservableList<User> users;

  /**
   * FilteredList of starting times for appointments.
   */
  private FilteredList<LocalTime> startTimes;

  /**
   * FilteredList of ending times for appointments.
   */
  private FilteredList<LocalTime> endTimes;

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

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
   * Contains list of available users to assign to appointment.
   */
  @FXML
  private ComboBox<User> userCombo;

  /**
   * Date picker for appointment start date.
   */
  @FXML
  private DatePicker startDate;

  /**
   * Date picker for appointment end date.
   */
  @FXML
  private DatePicker endDate;

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

  /**
   * Label for alerting user that date needs to be fixed.
   */
  @FXML
  private Label dateWarning;

  //===========================================================================
  // Scene Initialization
  //===========================================================================

  /**
   * Initialize values for combo boxes, start times, end times, and add empty
   * field listeners to all controls that require data completion.
   *
   * <p>
   * <strong>DISCUSSION OF {@index LAMBDA_5}</strong> - <blockquote>Lambda used to add focus listeners to all field controls
   *                        for scene that require data collection. Reduced necessary
   *                        lines of code for implementation using this consumer
   *                        interface. Same method as implemented in Customer View
   *                        Controller.</blockquote>
   */
  @FXML
  public void initialize() {
    contacts = contactDAO.getAll();
    customers = customerDAO.getAll();
    users = userDAO.getAll();

    startTimes = BusinessHours.getStartTimes().filtered(s -> true);
    endTimes = BusinessHours.getEndTimes().filtered(e -> true);

    contactCombo.setItems(contacts);
    customerCombo.setItems(customers);
    userCombo.setItems(users);
    startTime.setItems(startTimes);
    endTime.setItems(endTimes);

    fieldControls.addAll(Arrays.asList(titleField, locationField, typeField, contactCombo,
        customerCombo, userCombo, startTime, endTime, startDate, endTime, endDate, descriptionField));
    fieldControls.forEach(c -> ControlValidation
        .checkEmptySelections(c, fieldControlStatus, emptyWarning, saveButton));

  }

  /**
   * Overloaded method for initializes user data and isNew flag for appointment
   * record scene,preparing appropriate scene labels as well.
   *
   * <p>
   *   <strong>DISCUSSION OF {@index LAMBDA_6}</strong> -
   *   <blockquote>
   *     Use of lambda function to initialize the field status map that indicates
   *     if the field has been completed. The BiFunction interface of replaceAll
   *     allows better code readability and removes the need for an iterative loop.
   *   </blockquote>
   *
   * @param isNew Indicates if we are adding a new appointment record.
   * @param user  User currently accessing the application.
   */

  public void initAppointmentData(boolean isNew, User user) {
    this.user = user;
    this.isNew = isNew;
    this.currentAppointment = null;
    updateAppointmentLabels();
  }

  /**
   * Overloaded method for initializes user data and isNew flag for appointment
   * record scene,preparing appropriate scene labels as well.
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
  }


  /**
   * Prepares scene labels and text fields for adding or updating appointments.
   */
  private void updateAppointmentLabels() {
    if (isNew) {
      fieldControlStatus.replaceAll((k, v) -> v = false);
      appointmentType.setText("Create New");
      startDate.setValue(LocalDate.now());
      userCombo.setValue(user);

    } else if (!isNew && currentAppointment != null) {
      appointmentType.setText("Update Existing");
      idField.setText(String.valueOf(currentAppointment.getId()));
      titleField.setText(currentAppointment.getTitle());
      locationField.setText(currentAppointment.getLocation());
      typeField.setText(currentAppointment.getType());
      startDate.setValue(currentAppointment.getStartTime().toLocalDate());
      endDate.setValue(currentAppointment.getEndTime().toLocalDate());
      descriptionField.setText(currentAppointment.getDescription());

      contactCombo.setValue((Contact)findPersonById(contacts, currentAppointment.getContactId()));
      customerCombo.setValue((Customer)findPersonById(customers, currentAppointment.getCustomerId()));
      userCombo.setValue((User)findPersonById(users, currentAppointment.getUserId()));

      startTime.setValue(currentAppointment.getLocalStartTime());
      endTime.setValue(currentAppointment.getLocalEndTime());

      fieldControlStatus.replaceAll((k, v) -> v = true);
    }

  }
  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================

  /**
   * Attempts to save the new appointment record or update the existing record
   * currently in use. Start times must be no earlier than opening of business
   * and end times must be within close of business hours range. The BusinessHours
   * utility helps convert the local date time selections to the business hours
   * time zone for confirmation if appointments are within the proper range.
   *
   * @param event ActionEvent triggered by user clicking on the save button.
   * @throws IOException Exception from failure to load the main scene.
   */

  @FXML
  private void saveHandler(ActionEvent event) throws IOException {
    List<String> conflictMessages = new ArrayList<>();

    if (NotificationHandler.confirmPopup(event, "Save changes ?")) {
      boolean ready = true;
      LocalDateTime start = LocalDateTime.of(startDate.getValue(), startTime.getValue());
      LocalDateTime end = LocalDateTime.of(endDate.getValue(), endTime.getValue());

      if (!start.isBefore(end)){
        conflictMessages.add("Start must be before end date & time.");
        ready = false;
      }
      if (!BusinessHours.insideShift(start, end)) {
        conflictMessages.add(
            "\nOutside of business hours:"
            + "\n\nStart and end must be within same shift:\n"
            + BusinessHours.getLocalBusinessHours()
            +" (08:00 - 22:00 EST)"
        );
        ready = false;
      }

      if (ready) {
        var apptId = isNew ? -1 : currentAppointment.getId();
        var conflictList = appointmentDAO.getBetween(start, end, apptId);

        if (conflictList.isEmpty()) {
          int customerId = customerCombo.getValue().getId();
          int contactId = contactCombo.getValue().getId();
          int userId = userCombo.getValue().getId();
          String contactName = contactCombo.getValue().getName();
          String title = titleField.getText().strip();
          String description = descriptionField.getText().strip();
          String type = typeField.getText().strip();
          String location = locationField.getText().strip();

          if (isNew) {
            currentAppointment = new Appointment(apptId, customerId, contactId, contactName,
                userId, title, description, type, location, start, end);
            currentAppointment.setId(appointmentDAO.addAppointment(currentAppointment, user));

            if (currentAppointment.getId() > 0) {
              NotificationHandler.warningPopup("Add Complete", "Appointment\n" +
                  currentAppointment + " has been added.");
              loadMainView(event);
            }
          } else {
            currentAppointment.setTitle(title);
            currentAppointment.setLocation(location);
            currentAppointment.setType(type);
            currentAppointment.setStartTime(start);
            currentAppointment.setEndTime(end);
            currentAppointment.setContactId(contactId);
            currentAppointment.setContactName(contactName);
            currentAppointment.setCustomerId(customerId);
            currentAppointment.setUserId(userId);
            int rowsAffected = appointmentDAO.updateAppointment(currentAppointment, user);
            if (rowsAffected > 0) {
              NotificationHandler.warningPopup("Update Complete", "Appointment\n" +
                  currentAppointment + " has been updated.");
              loadMainView(event);
            }
          }
        } else {
          conflictMessages = conflictList.stream().map(Appointment::toString)
              .collect(Collectors.toList());
          NotificationHandler.warningPopup("Scheduling Overlap", conflictMessages);
        }
      } else {
          PauseTransition dateWarningPause = new PauseTransition(Duration.seconds(10));
          dateWarning.setVisible(true);
          dateWarning.setText("Check start/end times");
          dateWarningPause.setOnFinished(done -> dateWarning.setVisible(false));
          dateWarningPause.play();

          startTime.requestFocus();
          NotificationHandler.warningPopup("Start/End Time Alert", conflictMessages);
      }
    }
  }

  /**
   * Cancel the current add or update operation and revert scene to the main view.
   *
   * @param event ActionEvent from user clicking on the cancel button.
   * @throws IOException if unable to load fxml for main view.
   */
  @FXML
  private void cancelHandler(ActionEvent event) throws IOException {
    String message = "Discard changes and return to main?";
    if (NotificationHandler.confirmPopup(event, message)) {
      loadMainView(event);

    }
  }


  /**
   * Close the current scene and refresh the data in the main view.
   *
   * @param event ActionEvent triggered by save or cancel button handlers.
   * @throws IOException if unable to load main view fxml.
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
    stage.show();
  }


  /**
   * Binary search implementation to find person by id in a list of sorted persons.
   *
   * @param persons List of Person type objects that we can search by id.
   * @param id ID number of the person we would like to find.
   * @return Person object representing the matching contact from the list or null.
   */
  private Person findPersonById(ObservableList<? extends Person> persons, int id) {
    int low = 0;
    int high = persons.size() - 1;
    int idx = -1;

    while (low <= high) {
      int mid = (low + high) / 2;
      if (persons.get(mid).getId() < id) {
        low = mid + 1;
      } else if (persons.get(mid).getId() > id) {
        high = mid - 1;
      } else if (persons.get(mid).getId() == id) {
          idx = mid;
          break;
      }
    }
    if (idx >= 0) {
      return persons.get(idx);
    }
    return null;
  }

}
