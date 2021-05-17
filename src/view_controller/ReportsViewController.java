package view_controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.User;

public class ReportsViewController {

  private User user;

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button backButton;

  @FXML
  private TextArea reportText;

  @FXML
  public void initialize() {

  }

  /**
   * Closes the reports screen and returns control to the main view.
   *
   * @param event ActionEvent triggered by back button.
   */
  @FXML
  private void closeReports(ActionEvent event) {
    Stage stage = (Stage) backButton.getScene().getWindow();
    stage.close();
  }
}