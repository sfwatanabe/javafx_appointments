package view_controller;

import static utils.NotificationHandler.confirmPopup;

import dao.impl.AppointmentDAOImpl;
import dao.impl.CustomerDAOImpl;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import model.Appointment;
import model.Customer;
import model.User;
import utils.NotificationHandler;

public class MainViewController implements Initializable {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * CustomerDAOImpl used for updating customer records table view.
   */
  private CustomerDAOImpl customerDAO = new CustomerDAOImpl();

  /**
   * CustomerDAOImpl used for updating appointment records table view.
   */
  private AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();

  /**
   * Filtered list for holding appointment records in the table view.
   */
  private FilteredList<Appointment> appointmentsFiltered;

  /**
   * Observable list for holding customer records in the table view.
   */
  private ObservableList<Customer> customers = FXCollections.observableArrayList();


  /**
   * User currently logged into the application.
   */
  private static User user = null;

  /**
   * Exit button to leave application.
   */
  @FXML
  private Button exitButton;

  /**
   * Button to add a new customer record.
   */
  @FXML
  private Button addCustomerButton;

  /**
   * Button to update an existing customer record.
   */
  @FXML
  private Button updateCustomerButton;

  /**
   * Button to delete an existing customer record.
   */
  @FXML
  private Button deleteCustomerButton;

  /**
   * Customer record table view.
   */
  @FXML
  private TableView<Customer> customerTableView;

  /**
   * Customer ID column of the table view.
   */
  @FXML
  private TableColumn<Customer, Integer> customerIDCol;

  /**
   * Customer name column of the table view.
   */
  @FXML
  private TableColumn<Customer, String> customerNameCol;

  /**
   * Customer address column of the table view.
   */
  @FXML
  private TableColumn<Customer, String> customerAddressCol;

  /**
   * Customer postal code column of the table view.
   */
  @FXML
  private TableColumn<Customer, String> customerPostalCol;

  /**
   * Customer phone column of the table view.
   */
  @FXML
  private TableColumn<Customer, String> customerPhoneCol;

  /**
   * Customer division id column of the table view.
   */
  @FXML
  private TableColumn<Customer, Integer> customerDivisionCol;

  /**
   * Displays the name of current application user.
   */
  @FXML
  private Label userName;

  /**
   * Button to add new appointment record.
   */
  @FXML
  private Button addApptButton;

  /**
   * Button to update existing appointment record.
   */
  @FXML
  private Button updateApptButton;

  /**
   * Button to delete existing appointment record.
   */
  @FXML
  private Button deleteApptButton;

  /**
   * ToggleGroup for appointment viewing filter buttons.
   */
  @FXML
  private ToggleGroup viewByGroup;

  /**
   * Radio button to view all appointment records.
   */
  @FXML
  private RadioButton allRadioButton;

  /**
   * Radio button to vew appointments within the next week.
   */
  @FXML
  private RadioButton weekRadioButton;

  /**
   * Radio button to view appointments within the next month.
   */
  @FXML
  private RadioButton monthRadioButton;

  /**
   * TableView to display appointment records.
   */
  @FXML
  private TableView<Appointment> apptTableView;

  /**
   * Table column for appointment id.
   */
  @FXML
  private TableColumn<Appointment, Integer> apptIDCol;

  /**
   * Table column for appointment title.
   */
  @FXML
  private TableColumn<Appointment, String> apptTitleCol;

  /**
   * Table column for appointment description.
   */
  @FXML
  private TableColumn<Appointment, String> apptDescCol;

  /**
   *
   */
  @FXML
  private TableColumn<Appointment, String> apptLocationCol;

  @FXML
  private TableColumn<Appointment, String> apptContactCol;

  @FXML
  private TableColumn<Appointment, String> apptTypeCol;

  @FXML
  private TableColumn<Appointment, LocalDateTime> apptStartCol;

  @FXML
  private TableColumn<Appointment, LocalDateTime> apptEndCol;

  @FXML
  private TableColumn<Appointment, Integer> apptCustomerIdCol;

  @FXML
  private Button monthlyReportButton;

  @FXML
  private Button contactReportButton;

  @FXML
  private Button divisionReportButton;

  //===========================================================================
  // Scene Initialization
  //===========================================================================


  // TODO Add javadoc for the initialize -> explain usage of lambda for cellValueFactory
  @FXML
  public void initialize(URL Location, ResourceBundle resources) {
    customers = customerDAO.getAll();
//    appointments = appointmentDAO.getAll();
//    appointments = appointmentDAO.getAll();
    // Use a filtered list
    appointmentsFiltered = new FilteredList<>(appointmentDAO.getAll(), p -> true);

    customerIDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    customerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    customerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
    customerPostalCol.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
    customerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
    customerDivisionCol.setCellValueFactory(cellData -> cellData.getValue().divisionIdProperty());
    customerTableView.setItems(customers);

    apptIDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    apptTitleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    apptDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    apptLocationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
    apptContactCol.setCellValueFactory(cellData -> cellData.getValue().contactNameProperty());
    apptTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    apptStartCol.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
    apptStartCol.setCellFactory(cellData -> formatMyDate());
    apptEndCol.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
    apptEndCol.setCellFactory(cellData -> formatMyDate());
    apptCustomerIdCol.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());

    apptTableView.setItems(appointmentsFiltered);
    // TODO Setup query appointments before in Appointments DAO
    // ADD A listener to the toggle group.

  }


  /**
   * Sets up cell factory formatting for LocalDateTime cell data.
   *
   * @param <T, LocalDateTime> Object type used to populate the table view using LocalDateTime
   *            attribute.
   * @return TableCell with formatted date time string.
   */
  private <T> TableCell<T, LocalDateTime> formatMyDate() {
    return new TableCell<T, LocalDateTime>() {
      @Override
      protected void updateItem(LocalDateTime item, boolean empty) {
        String DATE_FORMATTER = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(String.format(item.format(formatter)));
        }
      }
    };
  }

  /**
   * Initializes user data for the scene and makes call to check for upcoming appointments.
   *
   * @param user User currently accessing the application.
   */
  public void initData(User user) {
    this.user = user;
    userName.setText(user.getName());
    checkForUpcoming();
  }

  /**
   * Checks list of appointment data for any start times that are within 15 minutes of user login
   * and provides on screen alert.
   */
  private void checkForUpcoming() {
    List<String> messages = new ArrayList<>();
    LocalDateTime timeLimit = LocalDateTime.now().plusMinutes(15);

    for (Appointment a : appointmentsFiltered) {
      var start = a.getStartTime();
      if (start.isBefore(timeLimit) && (user.getId().equals(a.getUserId()))) {
        if (start.isBefore(LocalDateTime.now()) || start.isEqual(LocalDateTime.now())) {
          messages.add("Past Due - " + a);
        } else {
          messages.add("Upcoming - " + a);
        }
      }
    }

    if (messages.size() > 0) {
      NotificationHandler.warningPopup("Appointments for user: " + user.getName(), messages);
    } else {
      String allClear = "No appointments within 15 minutes.";
      NotificationHandler.warningPopup("Appointments for user: " + user.getName(), allClear);
    }
  }

  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================
  @FXML
  private void addApptHandler(ActionEvent event) {

  }

  @FXML
  private void addCustomerHandler(ActionEvent event) {

  }

  @FXML
  private void deleteApptHandler(ActionEvent event) {

  }

  @FXML
  private void deleteCustomerHandler(ActionEvent event) {

  }

  @FXML
  private void updateApptHandler(ActionEvent event) {

  }

  @FXML
  private void updateCustomerHandler(ActionEvent event) {

  }

  /**
   * Exits the application.
   *
   * @param event ActionEvent triggered by user clicking the exit button.
   */
  @FXML
  private void exitApp(ActionEvent event) {
    if(confirmPopup(event, "Please confirm you would like to exit.")) {
      System.exit(0);
    }
  }

  /**
   * Monitor action events triggered by the appointment view by toggle group. Sets predicate for the
   * filtered list based on selected radio button using lambda function.
   *
   * @param event ActionEvent triggered by user selecting radio button.
   */
  @FXML
  void apptViewRadio(ActionEvent event) {
    if (viewByGroup.getSelectedToggle().equals(allRadioButton)) {
      appointmentsFiltered.setPredicate(a -> true);
    } else if (viewByGroup.getSelectedToggle().equals(weekRadioButton)) {
      appointmentsFiltered
          .setPredicate(a -> a.getStartTime().isBefore(LocalDateTime.now().plusDays(7)));
    } else if (viewByGroup.getSelectedToggle().equals(monthRadioButton)) {
      appointmentsFiltered
          .setPredicate(a -> a.getStartTime().isBefore(LocalDateTime.now().plusMonths(1)));
    }
  }

}
