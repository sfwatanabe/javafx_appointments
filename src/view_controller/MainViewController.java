package view_controller;

import dao.impl.AppointmentDAOImpl;
import dao.impl.CustomerDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import model.Customer;
import model.User;
import utils.ErrorHandler;

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
   * User currently logged into the application.
   */
  private User user = null;

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
   *
   */

  @FXML
  private RadioButton allRadioButton;

  @FXML
  private ToggleGroup viewByGroup;

  @FXML
  private RadioButton weekRadioButton;

  @FXML
  private RadioButton monthRadioButton;

  @FXML
  private TableView<?> apptTableView;

  @FXML
  private TableColumn<?, ?> apptIDCol;

  @FXML
  private TableColumn<?, ?> apptTitleCol;

  @FXML
  private TableColumn<?, ?> apptDescCol;

  @FXML
  private TableColumn<?, ?> apptLocationCol;

  @FXML
  private TableColumn<?, ?> apptContactCol;

  @FXML
  private TableColumn<?, ?> apptTypeCol;

  @FXML
  private TableColumn<?, ?> apptStartCol;

  @FXML
  private TableColumn<?, ?> apptEndCol;

  @FXML
  private TableColumn<?, ?> apptCustomerIdCol;

  @FXML
  private Button monthlyReportButton;

  @FXML
  private Button contactReportButton;

  @FXML
  private Button divisionReportButton;

  //===========================================================================
  // Scene Initialization
  //===========================================================================

  @FXML
  public void initialize(URL Location, ResourceBundle resources) {

    // TODO Setup cell value factories for customer
    customerIDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
    customerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    customerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
    customerPostalCol.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
    customerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
    customerDivisionCol.setCellValueFactory(cellData -> cellData.getValue().divisionIdProperty());

    customerTableView.setItems(customerDAO.getAll());
    // TODO Setup cell value factories for appointment

    // TODO Setup cellFactory lambda for the appointment timestamps.

    // TODO Setup query appointments before in Appointments DAO

  }

  public void initData(User user) {
    this.user = user;
    userName.setText(user.getName());
    ErrorHandler.warningPopup("APPOINTMENT ALERT", "NEEDS TO GO HERE.");
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
}
