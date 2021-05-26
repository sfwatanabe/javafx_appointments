package view_controller;

import static utils.NotificationHandler.confirmPopup;
import static utils.NotificationHandler.warningPopup;

import dao.impl.AppointmentDAOImpl;
import dao.impl.CustomerDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;
import utils.NotificationHandler;

/**
 * MainViewController handles scene events and logic for the main view.
 *
 * @author Sakae Watanabe
 */

public class MainViewController implements Initializable {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * CustomerDAOImpl used for updating customer records table view.
   */
  private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();

  /**
   * CustomerDAOImpl used for updating appointment records table view.
   */
  private final AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();

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
   * Indicates if this is the first time the scene has been loaded to determine
   * if appointment alert will be shown.
   */
  private static boolean firstLoad = true;

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
  @SuppressWarnings("unused")
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
   * Table column for appointment location information.
   */
  @FXML
  private TableColumn<Appointment, String> apptLocationCol;

  /**
   * Table column for appointment contact name information.
   */
  @FXML
  private TableColumn<Appointment, String> apptContactCol;

  /**
   * Table column for appointment type information.
   */
  @FXML
  private TableColumn<Appointment, String> apptTypeCol;

  /**
   * Table column for appointment start time data.
   */
  @FXML
  private TableColumn<Appointment, LocalDateTime> apptStartCol;

  /**
   * Table column for appointment end time data.
   */
  @FXML
  private TableColumn<Appointment, LocalDateTime> apptEndCol;

  /**
   * Table column for appointment's associated customer id.
   */
  @FXML
  private TableColumn<Appointment, Integer> apptCustomerIdCol;

  /**
   * Button to generate monthly report by appointment type.
   */
  @FXML
  private Button monthlyReportButton;

  /**
   * Button to generate schedule report by contact.
   */
  @FXML
  private Button contactReportButton;

  /**
   * Button to generate report of appointments by division.
   */
  @FXML
  private Button divisionReportButton;

  //===========================================================================
  // Scene Initialization
  //===========================================================================


  /**
   * Initializes the controller for the main view and sets up the table data for
   * both customer and appointment table views.
   *
   * <p>
   *   <strong>DISCUSSION OF {@index LAMBDA_1} </strong> - <blockquote>Lambda expression used for setting cell value
   *                             factories for all table data via property access,
   *                             reducing the necessary code to implement interface.</blockquote>
   *
   * <p>
   *   <strong>DISCUSSION OF {@index LAMBDA_2} </strong> - <blockquote>Lambda expression used for setting cell factory
   *                               for date columns allowing use of method defined
   *                               within class to return table cell data.</blockquote>
   *
   */
  @FXML
  public void initialize(URL Location, ResourceBundle resources) {

    customerIDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    customerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    customerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
    customerPostalCol.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
    customerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
    customerDivisionCol.setCellValueFactory(cellData -> cellData.getValue().divisionIdProperty());

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

    updateCustomersItems();
    updateAppointmentsItems();

    apptTableView.getSortOrder().add(apptStartCol);
    apptTableView.sort();


  }

  /**
   * Updates the list of customer records from the database.
   */
  protected void updateCustomersItems() {
    customers = customerDAO.getAll();
    customerTableView.setItems(customers);
  }

  /**
   * Updated the appointment records in table view from the database.
   */
  private void updateAppointmentsItems() {
    appointmentsFiltered = new FilteredList<>(appointmentDAO.getAll(), p -> true);
    SortedList<Appointment> sortedData = new SortedList<>(appointmentsFiltered);
    sortedData.comparatorProperty().bind(apptTableView.comparatorProperty());
    apptTableView.setItems(sortedData);
  }

  /**
   * Provides cell factory formatting for LocalDateTime cell data.
   *
   * @param <T> object used to populate table view containing a local date time
   *           attribute.
   * @return TableCell with formatted date time string.
   */
  private <T> TableCell<T, LocalDateTime> formatMyDate() {
    return new TableCell<>() {
      @Override
      protected void updateItem(LocalDateTime item, boolean empty) {
        String DATE_FORMATTER = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        super.updateItem(item, empty);
        if (empty) {
          setText(null);
        } else {
          setText(item.format(formatter));
        }
      }
    };
  }

  /**
   * Initializes user data for the scene and makes call to check for upcoming
   * appointments.
   *
   * @param user User currently accessing the application.
   */
  public void initData(User user) {
    MainViewController.user = user;
    userName.setText(user.getName());
    if (firstLoad) {
      checkForUpcoming();
      firstLoad = false;
    }
  }

  /**
   * Checks list of appointment data for any start times that are within 15
   * minutes of user login and provides on screen alert.
   */
  private void checkForUpcoming() {
    List<String> messages = new ArrayList<>();
    LocalDateTime timeLimit = LocalDateTime.now().plusMinutes(15);

    for (Appointment a : appointmentsFiltered) {
      var start = a.getStartTime();
      if ((start.compareTo(timeLimit) <= 0) && (user.getId().equals(a.getUserId()))) {
        if (start.isBefore(LocalDateTime.now()) || start.isEqual(LocalDateTime.now())) {
          messages.add("\nPast Due:\n" + a);
        } else {
          messages.add("\nUpcoming:\n" + a);
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


  /**
   * Loads customer record view and calls appropriate init method for the customer
   * records screen based on adding new customer or updating existing.
   *
   * @param event ActionEvent passed through by the add and update customer buttons
   * @throws IOException if unable to load the customer view fxml.
   */
  @FXML
  private void loadCustomerView(ActionEvent event) throws IOException {
    String buttonId = ((Button) event.getSource()).getId();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view_controller/CustomerView.fxml"));
    Parent parent = loader.load();
    Scene scene = new Scene(parent);
    CustomerViewController controller = loader.getController();

    if (buttonId.equals(addCustomerButton.getId())) {
      controller.initCustomerData(true, user);

    } else if (buttonId.equals(updateCustomerButton.getId())) {
      Customer customer = customerTableView.getSelectionModel().getSelectedItem();
      if (customer != null) {
        controller.initCustomerData(false, user, customer);

      } else {
        warningPopup("No Customer Selected", "Please select a customer.");
        return;
      }
    }


    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }


  /**
   * Handles user request to add a new appointment record and calls scene init
   * method for adding a new appointment from AppointmentViewController class.
   *
   * @param event ActionEvent triggered by user clicking the add appointment button.
   * @throws IOException if unable to load appointment view fxml.
   */
  @FXML
  private void loadApptHandler(ActionEvent event) throws IOException {
    String buttonId = ((Button) event.getSource()).getId();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view_controller/AppointmentView.fxml"));
    Parent parent = loader.load();
    Scene scene = new Scene(parent);
    AppointmentViewController controller = loader.getController();

    if (buttonId.equals(addApptButton.getId())) {
      controller.initAppointmentData(true, user);

    } else if (buttonId.equals(updateApptButton.getId())) {
      Appointment appointment = apptTableView.getSelectionModel().getSelectedItem();
      if (appointment != null) {
        controller.initAppointmentData(false, user, appointment);

      } else {
        warningPopup("No appointment Selected", "Please select a appointment.");
        return;
      }
    }

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }


  /**
   * Handles events from all report buttons to initialize the report scene and
   * populate appropriate reporting data.
   *
   * @param event ActionEvent generated by user clicking one of the report buttons.
   * @throws IOException if unable to load the reports view fxml.
   */
  @FXML
  void loadReports(ActionEvent event) throws IOException {
    String buttonId = ((Control)event.getSource()).getId();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view_controller/ReportsView.fxml"));
    Parent parent = loader.load();
    Scene scene = new Scene(parent);
    Stage stage = new Stage();
    ReportsViewController controller = loader.getController();

    controller.initDataFactory(user, buttonId);

    stage.setScene(scene);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(divisionReportButton.getScene().getWindow());
    stage.show();
  }

  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================

  /**
   * Handles user request to delete customer if selected and will also confirm
   * deletion of associated customer appointments from the system. Provides user
   * with a list of associated appointments that will be removed. Updates both
   * customer and appointment views after transaction has been completed.
   *
   * @param event Event triggered when user clicks on delete customer.
   */
  @FXML
  private void deleteCustomerHandler(ActionEvent event) {
    Customer customer = customerTableView.getSelectionModel().getSelectedItem();

    if (customer != null) {
      int customerId = customer.getId();
      String confirmationMsg = "Delete customer " + customer;
      ObservableList<Appointment> associatedAppointments = appointmentDAO
          .getByCustomerId(customerId);

      if (!associatedAppointments.isEmpty()) {
        confirmationMsg += "\n\nThe following appointments will also be deleted:\n\n" +
            String.join("\n", associatedAppointments.stream().map(Appointment::toString)
                .collect(Collectors.joining("\n")));
      } else {
        confirmationMsg += "\n\nCustomer has no associated appointments.";
      }
      confirmationMsg += "\n\nAre you sure?";

      System.out.println(confirmationMsg);
      if (confirmPopup(event, confirmationMsg)) {

        int appointmentsDeleted = appointmentDAO.deleteAppointmentByCustomer(customer);
        int customerDeleted = customerDAO.deleteCustomer(customer);

        if (customerDeleted > 0) {
          String deleteMsg = "Customer " + customer + " deleted.\n" +
              appointmentsDeleted + " associated appointments were deleted.";
          warningPopup("Delete Complete", deleteMsg);
          updateCustomersItems();
          updateAppointmentsItems();
        }
      }
    } else {
      warningPopup("Delete Failed", "Please select a customer.");
    }
  }


  /**
   * Handles user request to delete selected appointment and updates the contents
   * of the table view.
   *
   * @param event Event triggered when user clicks on delete appointment.
   */
  @FXML
  private void deleteApptHandler(ActionEvent event) {
    Appointment appointment = apptTableView.getSelectionModel().getSelectedItem();

    if (appointment != null) {

      int appointmentId = appointment.getId();
      String msg = "Delete appointment\n" + appointment + "\nAre you sure?";

      if (confirmPopup(event, msg)) {
        int rows = appointmentDAO.deleteAppointment(appointment);

        if (rows > 0) {
          String deleteMsg = "Appointment " + appointmentId + " deleted.";
          warningPopup("Delete Complete", deleteMsg);
          updateAppointmentsItems();
          allRadioButton.setSelected(true);
        }
      }
    } else {
      warningPopup("Delete Failed", "Please select an appointment.");
    }
  }


  /**
   * Exits the application upon user confirmation.
   *
   * @param event ActionEvent triggered by user clicking the exit button.
   */
  @FXML
  private void exitApp(ActionEvent event) {
    if (confirmPopup(event, "Please confirm you would like to exit.")) {
      System.exit(0);
    }
  }

  /**
   * Monitor action events triggered by the appointment view by toggle group. Sets predicate for the
   * filtered list based on selected radio button using lambda expression.
   *
   * <p>
   *   <strong>DISCUSSION OF {@index LAMBDA_3} </strong> -  <blockquote>Filtered list predicates are updated using lambda
   *                               expressions, allowing reduced code for interface.</blockquote>
   *
   *
   *
   * @param event ActionEvent triggered by user selecting radio button.
   */
  @FXML
  void apptViewRadio(@SuppressWarnings("unused") ActionEvent event) {
    if (viewByGroup.getSelectedToggle().equals(allRadioButton)) {
      appointmentsFiltered.setPredicate(a -> true);
    } else if (viewByGroup.getSelectedToggle().equals(weekRadioButton)) {
      appointmentsFiltered
          .setPredicate(a -> a.getStartTime().isBefore(LocalDateTime.now().plusWeeks(1)));
    } else if (viewByGroup.getSelectedToggle().equals(monthRadioButton)) {
      appointmentsFiltered
          .setPredicate(a -> a.getStartTime().isBefore(LocalDateTime.now().plusMonths(1)));
    }

    SortedList<Appointment> sortedAppointments = new SortedList<>(appointmentsFiltered);
    sortedAppointments.comparatorProperty().bind(apptTableView.comparatorProperty());
    apptTableView.setItems(sortedAppointments);
  }

}
