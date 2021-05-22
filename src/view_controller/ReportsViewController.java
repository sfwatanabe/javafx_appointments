package view_controller;

import dao.ReportDAO;
import dao.impl.reportDAOImpl.ContactScheduleDAOImpl;
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

  /**
   * User currently logged in to application.
   */
  private User user;

  /**
   * Report DAO item for constructing and updating report data.
   */
  private ReportDAO report;

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  /**
   * Allows user to close the reports window and transfer control back to main.
   */
  @FXML
  private Button backButton;

  /**
   * Label indicating which report is currently displayed.
   */
  @FXML
  private Label reportTitleLabel;

  /**
   * TextArea containing the output for the chosen report.
   */
  @FXML
  private TextArea reportText;

  @FXML
  public void initialize() {

  }


  /**
   * Initializes the data displayed for the reports view based on the button
   * clicked to launch the scene, calling the appropriate report construction.
   *
   * @param user User currently using the application.
   * @param reportType Button-id from main-view that was clicked to load scene.
   */
  public void initDataFactory(User user, String reportType) {
    if (reportType.equals("monthlyReportButton")) {
      reportTitleLabel.setText("Appointments by Type");
      report = new MonthlyReportDAOImpl();
    } else if (reportType.equals("contactReportButton")) {
      reportTitleLabel.setText("Contact Schedules");
      report = new ContactScheduleDAOImpl();
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