package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton utility to initialize connection to the database for use by
 * application DAO classes.
 *
 * @author Sakae Watanabe
 */
public class DBConnector {

  //===========================================================================
  // JDBC Connection Information
  //===========================================================================
  private static final String protocol = "jdbc";
  private static final String vendor = ":mysql:";
  private static final String ipAddress = "//wgudb.ucertify.com/WJ08Eq0";
  private static final String timeZone = "?connectionTimeZone=SERVER";

  private static final String jdbcURL = protocol + vendor + ipAddress + timeZone;

  private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  /** Connection object for the utility. */
  private static Connection conn = null;


  /**
   * Static method for initializing connection to the database. Intended to be
   * called only at start of application. Initializes connection to database
   * and returns Connection object. If connection has already been initialized
   * simply returns a reference to the connection.
   *
   * @return Connection object for use with DAO operations.
   */
  public static Connection startConnection() {
    if(conn == null){
      try {
        ConfigProps config = new ConfigProps();
        Class.forName(MYSQL_JDBC_DRIVER);
        conn = DriverManager.getConnection(jdbcURL, config.getUsername(),
            config.getPassword());
        System.out.println("Connection successful.");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) { ;
        e.printStackTrace();
      }
    }
    return conn;
  }

  /**
   * Returns a reference for the database connection. If connection has not yet
   * been initialized will call the startConnection method.
   *
   * @return
   */
  public static Connection getConnection() {
    if(conn == null) {
      startConnection();
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