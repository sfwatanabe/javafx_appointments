package view_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

  private ResourceBundle resources;

  @FXML
  private Label userNameLabel;

  @FXML
  private TextField userNameField;

  @FXML
  private Label passwordLabel;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;

  @FXML
  private Label loginZone;

  @FXML
  private Label loginErrorMessage;

  @FXML
  void verifyLogin(ActionEvent event) {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resources = resources;
  }
}