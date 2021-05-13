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
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.User;
import utils.NotificationHandler;

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
  private List<Control> fieldControls = new ArrayList<>();

  /**
   * Map with status representing if field has been completed.
   */
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
    divisions = divisionDAO.getAll().filtered(d -> true);
    divisionComboBox.setItems(divisions);

    countries = countryDAO.getAll().filtered(c -> true);
    countryComboBox.setItems(countries);

    fieldControls.addAll(Arrays.asList(nameField, addressField, postCode, divisionComboBox,
                                        countryComboBox, phoneNumber));
    fieldControls.forEach(this::checkEmptyField);
  }

  /**
   * Overloaded method initializes user data and isNew flags for the customer
   * record scene and prepares labels.
   *
   * @param user  User currently accessing the application.
   * @param isNew Indicates if we are adding a new customer record.
   */
  public void initCustomerData(boolean isNew, User user) {
    this.user = user;
    this.isNew = isNew;
    this.currentCustomer = null;
    for(Control c : fieldControls) {
      fieldControlStatus.putIfAbsent(c.getId(), false);
    }
    updateLabels();
    System.out.println(user + "\n" + currentCustomer);
  }

  /**
   * Overloaded method initializes user data, isNew flags, and current customer
   * record before calling update labels.
   *
   * @param user User currently accessing the application.
   */
  public void initCustomerData(boolean isNew, User user, Customer customer) {
    this.user = user;
    this.currentCustomer = customer;
    this.isNew = isNew;
    updateLabels();
    for(Control c : fieldControls) {
      fieldControlStatus.putIfAbsent(c.getId(), true);
    }
    System.out.println(user + "\n" + currentCustomer);
  }

  /**
   * Prepares scene labels and text fields for either adding or updating the
   * customer record.
   */
  private void updateLabels() {
    if (isNew) {
      customerFormType.setText("Create New");
      countryComboBox.getSelectionModel().selectFirst();
      filterDivisions();
      divisionComboBox.getSelectionModel().selectFirst();

    } else if (!isNew && currentCustomer != null) {
      customerFormType.setText("Update Existing");
      idField.setText(String.valueOf(currentCustomer.getId()));
      nameField.setText(currentCustomer.getName());
      addressField.setText(currentCustomer.getAddress());
      postCode.setText(currentCustomer.getPostalCode());
      phoneNumber.setText(currentCustomer.getPhoneNumber());

      for (Division d : divisions) {
        if (currentCustomer.getDivisionId().equals(d.getId())) {
          divisionComboBox.getSelectionModel().select(d);
          Division currentDivision = divisionComboBox.getSelectionModel().getSelectedItem();
          for (Country c : countries) {
            if (c.getId() == currentDivision.getCountryID()) {
              countryComboBox.getSelectionModel().select(c);
            }
          }
        }
      }
    }
  }

  //===========================================================================
  // Event Handlers & Helper Methods
  //===========================================================================

  @FXML
  private void saveCustomerHandler(ActionEvent event) throws IOException {
    // TODO Add text field listeners to disable save action if empty.
    // TODO Wrap this block in a confirmation popup

    String name = nameField.getText().strip();
    String address = addressField.getText().strip();
    String postalCode = postCode.getText().strip();
    String phone = phoneNumber.getText().strip();
    int divisionId = divisionComboBox.getSelectionModel().getSelectedItem().getId();

    if (isNew) {
      currentCustomer = new Customer(-1, name, address, postalCode, phone, divisionId);
      currentCustomer.setId(customerDAO.addCustomer(currentCustomer, user));
      if (currentCustomer.getId() > 0){
        NotificationHandler.warningPopup("Add Complete", "Customer\n" +
            currentCustomer + " has been added.");
        loadMainView(event);
      }
    } else {
      // TODO direct existing customer to an update
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

  /**
   * Cancel the current add or update and revert scene to the main view.
   *
   * @param event ActionEvent from user clicking on the cancel button.
   */
  @FXML
  private void cancelHandler(ActionEvent event) throws IOException {
    String message = "Discard changes and return to main?";

    if (NotificationHandler.confirmPopup(event, message)){
      loadMainView(event);

    }
  }


  /**
   * Loads the main view.
   *
   * @param event ActionEvent triggered by save or cancel button handlers.
   */
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
   * Checks if current selection of country combo box is null and will set to
   * matching country id of selected division.
   * 
   * @param event ActionEvent generated by user selecting division from the
   *              division combo box.
   */
  @FXML
  private void divisionComboHandler(ActionEvent event) {
    Division currentDivision = divisionComboBox.getSelectionModel().getSelectedItem();
    Country currentCountry = countryComboBox.getSelectionModel().getSelectedItem();
    if ((currentDivision != null) && (currentCountry == null)){
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
   * @param event ActionEvent generated by user selecting country from the
   *              country combo box.
   */
  @FXML
  private void countryComboHandler(ActionEvent event) {
    filterDivisions();
  }


  /**
   * Filters list of available choices for divisions based on country id.
   */
  private void filterDivisions() {
    Country currentCountry = countryComboBox.getSelectionModel().getSelectedItem();
    Division currentDivision = divisionComboBox.getSelectionModel().getSelectedItem();
    if (currentDivision != null ) {
      if ((currentDivision.getCountryID() != currentCountry.getId())) {
        divisionComboBox.getSelectionModel().clearSelection();
      }
    }
    divisions.setPredicate(d -> d.getCountryID() == currentCountry.getId());
  }

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
          if (((ComboBox) control).getSelectionModel().getSelectedItem() == null) {
            control.getStyleClass().add("combo-box-empty");
            filled = false;
          } else {
            control.getStyleClass().removeIf(style -> style.equals("combo-box-empty"));
          }
        }

      }
      fieldControlStatus.put(control.getId(), filled);
      System.out.println(control);
      if (!okToSave()) {
        // TODO Update this section
        // TODO Test saving a customer from clicking to the save button from the field.
        System.out.println("not ok");
        saveButton.setDisable(true);
      } else {
        System.out.println("ok");
        saveButton.setDisable(false);
      }
    });
  }


  /**
   * Checks the status map and will enable the save button iff all fields have
   * been completed.
   *
   * @return True if all fields have been completed and ok to save/update record.
   */
  private boolean okToSave() {
    boolean ok = true;
    StringBuilder badFields = new StringBuilder();

    for (Map.Entry<String, Boolean> entry : fieldControlStatus.entrySet()) {
      if (!entry.getValue()) {
        badFields.append(" | ").append(entry.getKey()).append(" | ");
        ok = false;
      }
    }
    // TODO Replace this with a notification message with an fxml label
    System.out.println(badFields);
    return ok;
  }

}
