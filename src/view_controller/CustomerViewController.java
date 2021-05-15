package view_controller;

import dao.impl.CountryDAOImpl;
import dao.impl.CustomerDAOImpl;
import dao.impl.DivisionDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.User;
import utils.ControlValidation;
import utils.NotificationHandler;


/**
 * Handles logic and data collection for the customer record screen.
 *
 * @author Sakae Watanabe
 */
public class CustomerViewController implements Initializable {

  /**
   * User currently editing the customer record.
   */
  private User user;

  /**
   * Indicates if we are adding a new customer.
   */
  private boolean isNew;

  /**
   * Holds current customer record when updating customer.
   */
  private Customer currentCustomer;

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
   * Customer id information.
   */
  @FXML
  private TextField idField;

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
  @SuppressWarnings("unused")
  @FXML
  private Button cancelButton;

  /**
   * Displays whether user is adding or updating customer.
   */
  @FXML
  private Label customerFormType;

  /**
   * Displays warning messages to user regarding empty fields that need to be completed before able
   * to save/update record.
   */
  @FXML
  private Label emptyWarning;

  //===========================================================================
  // Scene Initialization
  //===========================================================================

  // TODO fill in javadoc comment for customer initialize.
  @FXML
  public void initialize(URL Location, ResourceBundle resources) {
    divisions = divisionDAO.getAll().filtered(d -> true);
    divisionComboBox.setItems(divisions);

    countries = countryDAO.getAll().filtered(c -> true);
    countryComboBox.setItems(countries);

    fieldControls.addAll(Arrays.asList(nameField, addressField, postCode, divisionComboBox,
        countryComboBox, phoneNumber));
//    fieldControls.forEach(this::checkEmptyField);
    fieldControls.forEach(c -> ControlValidation
        .checkEmptySelections(c, fieldControlStatus, emptyWarning, saveButton));
  }

  /**
   * Overloaded method initializes user data and isNew flags for the customer record scene and
   * prepares labels.
   *
   * @param user  User currently accessing the application.
   * @param isNew Indicates if we are adding a new customer record.
   */
  public void initCustomerData(boolean isNew, User user) {
    this.user = user;
    this.isNew = isNew;
    this.currentCustomer = null;
    fieldControlStatus.replaceAll((k, v) -> v = false);
    updateCustomerLabels();
  }

  /**
   * Overloaded method initializes user data, isNew flags, and current customer record before
   * calling update labels.
   *
   * @param user User currently accessing the application.
   */
  public void initCustomerData(boolean isNew, User user, Customer customer) {
    this.user = user;
    this.currentCustomer = customer;
    this.isNew = isNew;
    updateCustomerLabels();
    fieldControlStatus.replaceAll((k, v) -> v = true);
  }

  /**
   * Prepares scene labels and text fields for either adding or updating the customer record.
   */
  private void updateCustomerLabels() {
    if (isNew) {
      customerFormType.setText("Create New");
      countryComboBox.getSelectionModel().selectFirst();
      filterDivisions();
      divisionComboBox.getSelectionModel().selectFirst();

    } else //noinspection ConstantConditions
      if (!isNew && currentCustomer != null) {
        customerFormType.setText("Update Existing");
        idField.setText(String.valueOf(currentCustomer.getId()));
        nameField.setText(currentCustomer.getName());
        addressField.setText(currentCustomer.getAddress());
        postCode.setText(currentCustomer.getPostalCode());
        phoneNumber.setText(currentCustomer.getPhoneNumber());

        for (Division d : divisions) {
          if (currentCustomer.getDivisionId().equals(d.getId())) {
            for (Country c : countries) {
              if (c.getId() == d.getCountryID()) {
                countryComboBox.setValue(c);
                filterDivisions();
                divisionComboBox.setValue(d);
                return;
              }
            }
          }
        }

      }
  }

  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================


  /**
   * Attempts to save the new customer record or update the current record being browsed.
   *
   * @param event ActionEvent triggered by user clicking on the save button.
   * @throws IOException Exception from failure to load the main scene.
   */
  @FXML
  private void saveCustomerHandler(ActionEvent event) throws IOException {

    if (NotificationHandler.confirmPopup(event, "Save changes to record?")) {

      String name = nameField.getText().strip();
      String address = addressField.getText().strip();
      String postalCode = postCode.getText().strip();
      String phone = phoneNumber.getText().strip();
      int divisionId = divisionComboBox.getSelectionModel().getSelectedItem().getId();

      if (isNew) {
        currentCustomer = new Customer(-1, name, address, postalCode, phone, divisionId);
        currentCustomer.setId(customerDAO.addCustomer(currentCustomer, user));
        if (currentCustomer.getId() > 0) {
          NotificationHandler.warningPopup("Add Complete", "Customer\n" +
              currentCustomer + " has been added.");
          loadMainView(event);
        }
      } else {
        currentCustomer.setName(name);
        currentCustomer.setAddress(address);
        currentCustomer.setPostalCode(postalCode);
        currentCustomer.setPhoneNumber(phone);
        currentCustomer.setDivisionId(divisionId);
        int rowsAffected = customerDAO.updateCustomer(currentCustomer, user);
        if (rowsAffected > 0) {
          NotificationHandler.warningPopup("Update Complete", "Customer\n" +
              currentCustomer + " has been updated.");
          loadMainView(event);
        }
      }
    }
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
    stage.show();
  }

  /**
   * Checks if current selection of country combo box is null and will set to matching country id of
   * selected division.
   *
   * @param event ActionEvent generated by user selecting division from the division combo box.
   */
  @FXML
  private void divisionComboHandler(@SuppressWarnings("unused") ActionEvent event) {
    Division currentDivision = divisionComboBox.getSelectionModel().getSelectedItem();
    Country currentCountry = countryComboBox.getSelectionModel().getSelectedItem();
    if ((currentDivision != null) && (currentCountry == null)) {
      for (Country c : countries) {
        if (currentDivision.getCountryID() == c.getId()) {
          countryComboBox.getSelectionModel().select(c);
        }
      }
    }
  }

  /**
   * Handles event generated by a new selection of the country combo box.
   *
   * @param event ActionEvent generated by user selecting country from the country combo box.
   */
  @FXML
  private void countryComboHandler(@SuppressWarnings("unused") ActionEvent event) {
    filterDivisions();
  }


  /**
   * Filters list of available choices for divisions based on country selection.
   */
  private void filterDivisions() {
    Country currentCountry = countryComboBox.getSelectionModel().getSelectedItem();
    Division currentDivision = divisionComboBox.getSelectionModel().getSelectedItem();
    if (currentDivision != null) {
      if ((currentDivision.getCountryID() != currentCountry.getId())) {
        divisionComboBox.getSelectionModel().clearSelection();
      }
    }
    divisions.setPredicate(d -> d.getCountryID() == currentCountry.getId());
  }

}
