package utils;

import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Contains static methods for displaying common errors to user.
 *
 * @author Sakae Watanabe
 */
public class ErrorHandler {

  //===========================================================================
  // Error Handlers
  //===========================================================================

  /**
   * Displays warning message advising type of issue along with a short message
   * to the user.
   *
   * @param warningName The name of warning being issued to the user.
   * @param message The context of the warning message to be displayed.
   */
  public static void warningPopup(String warningName, String message) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setHeaderText(warningName);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Overloaded method for accepting a list of error messages for display in
   * context text area.
   *
   * @param warningName The name of warning being issued to the user.
   * @param messages List containing messages to be added to context text.
   */
  public static void warningPopup(String warningName, List<String> messages) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setHeaderText(warningName);
    alert.setContentText(String.join("\n", messages));
    alert.showAndWait();
  }

  /**
   * Adds SQLException information to popup dialog.
   *
   * @param daoType The type of DAO in use at time of error.
   * @param  sqlE SQLException thrown for translation to popup dialog.
   */
  public static void sqlPopup(String daoType, SQLException sqlE) {
    Alert alert = new Alert(AlertType.WARNING);
    String messages = "Error code: " + sqlE.getErrorCode() +
                      "\nSQL error: " + sqlE.getSQLState() +
                      "\nError Message: " + sqlE.getMessage();

    alert.setHeaderText("Data Retrieval Error -" + daoType);
    alert.setContentText(messages);
    alert.showAndWait();
  }


}
