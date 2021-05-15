package view_controller;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
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

  // TODO Add initData methods for the add and update methods
  @FXML
  private Label appointmentType;

  @FXML
  private TextField idField;

  @FXML
  private TextField titleField;

  @FXML
  private TextField locationField;

  @FXML
  private TextField typeField;

  @FXML
  private ComboBox<Contact> contactCombo;

  @FXML
  private ComboBox<Customer> customerCombo;

  @FXML
  private ComboBox<LocalTime> startTime;

  @FXML
  private ComboBox<LocalTime> endTime;

  @FXML
  private DatePicker datePicker;

  @FXML
  private TextField descriptionField;

  @FXML
  private Button saveButton;

  @FXML
  private Button cancelButton;

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
