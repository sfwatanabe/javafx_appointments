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
import model.Contact;
import model.Customer;


/**
 * @author Sakae Watanabe
 */

public class AppointmentViewController implements Initializable {

  //===========================================================================
  // Data Members
  //===========================================================================

  // TODO Add scene initializer members
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
  void cancelHandler(ActionEvent event) {

  }

  @FXML
  void saveHandler(ActionEvent event) {

  }
}
