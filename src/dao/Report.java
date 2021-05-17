package dao;

import java.sql.Connection;
import utils.DBConnector;

/**
 * Super class for reporting DAO implementations with a basic constructor to
 * initialize database connection. All reports must override local implementations
 * of the constructReport method.
 *
 * @author Sakae Watanabe
 */
public abstract class Report {

  /**
   * Connection to the application database.
   */
  private Connection conn;

  /**
   * Final string representing the output of the report.
   */
  private String report;

  /**
   * Simple constructor for super class grabs connection to the database.
   */
  public Report() {
    this.conn = DBConnector.getConnection();
  }

  /**
   * ConstructReport assembles a report as a formatted string for output.
   */
  public abstract void constructReport();

  /**
   * @return String containing the output of the report.
   */
  public String getReport() {
    return this.report;
  }

  /**
   * @param report Update the report information to the given string.
   */
  private void setReport(String report) {
    this.report = report;
  }
}
