package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton utility to initialize connection to the database for DAO classes.
 *
 * @author Sakae Watanabe
 */
public class DBConnector {

  //===========================================================================
  // JDBC Connection Information
  //===========================================================================

  /**
   * Connection protocol settings for MySQL database connection.
   */
  private static final String protocol = "jdbc";
  private static final String vendor = ":mysql:";
  private static final String timeZone = "?connectionTimeZone=SERVER";
  private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

  /** Connection object for the utility. */
  private static Connection conn = null;


  /**
   * Static method for initializing connection to the database. Intended to be
   * called only at start of application. Initializes connection to database
   * and returns Connection object. If connection has already been initialized
   * simply returns a reference to the connection.
   *
   * @throws ClassNotFoundException if unable to locate the JDBC driver.
   * @throws SQLException if unable to establish connection to database.
   */
  public static void startConnection() throws ClassNotFoundException, SQLException{
    if(conn == null){
      ConfigProps config = new ConfigProps();

      String jdbcURL = protocol + vendor + config.getDbUrl() + timeZone;

      Class.forName(MYSQL_JDBC_DRIVER);
      conn = DriverManager.getConnection(jdbcURL, config.getUsername(),
          config.getPassword());
      System.out.println("Connection successful.");
    }
  }

  /**
   * Returns a reference for the database connection. If connection has not yet
   * been initialized will call the startConnection method.
   *
   * @return Connection object to the application database.
   */
  public static Connection getConnection() {
    if(conn == null) {
      try {
        startConnection();
      } catch (ClassNotFoundException e) {
        NotificationHandler.warningPopup("Database Connector", e.getMessage());
        e.printStackTrace();
      } catch (SQLException e) {
        NotificationHandler.sqlPopup("Database Connector", e);
        e.printStackTrace();
      }
    }
    return conn;
  }


  /**
   * Attempts to close the active database connection. Method should be called
   * when user is finished with application.
   */
  public static void closeConnection() {
    try {
      conn.close();
      System.out.println("Connection has been closed.");
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }


}
