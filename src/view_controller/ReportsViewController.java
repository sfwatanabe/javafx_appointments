package view_controller;

import dao.ReportDAO;
import dao.impl.reportDAOImpl.DivisionBookingsDAOImpl;
import dao.impl.reportDAOImpl.MonthlyReportDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.User;

public class ReportsViewController {

  private User user;
  private ReportDAO report;

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button backButton;

  @FXML
  private Label reportTitleLabel;

  @FXML
  private TextArea reportText;

  @FXML
  public void initialize() {

  }


  public void initDataFactory(User user, String reportType) {
    if (reportType.equals("monthlyReportButton")) {
      reportTitleLabel.setText("Appointments by Type");
      report = new MonthlyReportDAOImpl();
    } else if (reportType.equals("contactReportButton")) {
      reportTitleLabel.setText("Contact Schedules");
      // TODO put in the contact schedules DAO impl
      reportText.setText("We hit the contact Report.");
    } else if (reportType.equals("divisionReportButton")) {
      reportTitleLabel.setText("Appointments by Division");
      report = new DivisionBookingsDAOImpl();
    }
    report.constructReport();
    reportText.setText(report.getReport());
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