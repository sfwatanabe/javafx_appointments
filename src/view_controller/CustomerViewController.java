package view_controller;

import dao.impl.CountryDAOImpl;
import dao.impl.CustomerDAOImpl;
import dao.impl.DivisionDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.Division;
import model.User;

public class CustomerViewController implements Initializable {

  /**
   * User currently editing the customer record.
   */
  private User user;

  /**
   * Indicates if we are adding a new user.
   */
  private boolean isNew;

  /**
   * Holds current customer record when updating customer.
   */
  private Customer currentCustomer = null;

  /**
   * CustomerDAOImpl used for adding or updating customer record.
   */
  private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();

  /**
   * CountryDAOImpl used for updating country combo box data..
   */
  private final CountryDAOImpl countryDAO = new CountryDAOImpl();

  /**
   * Filtered list for holding country records for the combo box.
   */
  private FilteredList<Country> countries;

  /**
   * DivisionDAOImpl used for updating division combo box data..
   */
  private final DivisionDAOImpl divisionDAO = new DivisionDAOImpl();

  /**
   * Filtered list for holding division records for the combo box.
   */
  private FilteredList<Division> divisions;

  /**
   * Customer name information.
   */
  @FXML
  private TextField nameField;

  /**
   * Customer address information.
   */
  @FXML
  private TextField addressField;

  /**
   * Customer postal code information.
   */
  @FXML
  private TextField postCode;

  /**
   * Customer phone number.
   */
  @FXML
  private TextField phoneNumber;

  /**
   * Customer assigned division information.
   */
  @FXML
  private ComboBox<Division> divisionComboBox;

  /**
   * Customer assigned division information.
   */
  @FXML
  private ComboBox<Country> countryComboBox;

  /**
   * Saves current customer record changes.
   */
  @FXML
  private Button saveButton;

  /**
   * Cancel the current update or new customer record.
   */
  @FXML
  private Button cancelButton;

  /**
   * Displays whether user is adding or updating customer.
   */
  @FXML
  private Label customerFormType;

  //===========================================================================
  // Scene Initialization
  //===========================================================================

  @FXML
  public void initialize(URL Location, ResourceBundle resources) {

  }

  /**
   * Overloaded method initializes user data and isNew flags for the customer
   * record scene and prepares labels.
   *
   * @param user User currently accessing the application.
   */
  public void initCustomerData(boolean isNew, User user) {
    this.user = user;
    this.isNew = isNew;

    updateLabels();
  }

  /**
   * Prepares scene labels and text fields for either adding or updating the
   * customer record.
   */
  private void updateLabels() {
    if (isNew) {
      customerFormType.setText("Create New");
    }
  }

  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================

  @FXML
  private void saveCustomerHandler(ActionEvent event) {

  }

  @FXML
  private void cancelHandler(ActionEvent event) {

  }

  @FXML
  private void filterCountries(ActionEvent event) {

  }

  @FXML
  private void filterDivisions(ActionEvent event) {

  }

}
