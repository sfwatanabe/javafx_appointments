package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to provide user credential information from config.properties.
 *
 * @author Sakae Watanabe
 */
public class ConfigProps {
  //===========================================================================
  // Class Members
  //===========================================================================

  /** Boolean indicating if properties have been loaded. */
  private static boolean loaded = false;
  /** String representing user name from config.properties. */
  private static String username;
  /** String representing password from config.properties. */
  private static String password;
  /** String for path to config.properties. */
  private static final String propFile = "resources/config/config.properties";

  public ConfigProps() { }

  /**
   * getPropValues opens InputStream with config.properties file to get and set
   * both username and password fields for the instance. This method only called
   * when ConfigProps initialized.
   *
   * @throws IOException if unable to load or close InputStream.
   * @throws FileNotFoundException if unable to load properties file.
   */
  private void getPropValues() throws IOException {
    Properties prop = new Properties();

    try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFile)) {
      if(inputStream != null) {
        prop.load(inputStream);
        username = prop.getProperty("userName");
        password = prop.getProperty("password");
      } else {
        System.out.println("Unable to load from specified path " + propFile);
        throw new FileNotFoundException("Properties File '" + propFile + "' not found.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a string value for the user name.
   *
   * @return String representing user name for login credentials.
   * @throws IOException getPropValues unable to get InputStream.
   */
  public String getUsername() {
    if(!loaded) {
      try {
        getPropValues();
      } catch (IOException e) {
        e.printStackTrace();
        NotificationHandler.warningPopup(e.getMessage(),
            "Unable to get user name.");
      }
    }
    return username;
  }

  /**
   * Returns a string value for the user password.
   *
   * @return String representing user name for login credentials.
   * @throws IOException getPropValues unable to get InputStream.
   */
  public String getPassword() {
    if(!loaded) {
      try {
        getPropValues();
      } catch (IOException e) {
        e.printStackTrace();
        NotificationHandler.warningPopup(e.getMessage(),
            "Unable to get password.");
      }
    }
    return password;
  }
}
