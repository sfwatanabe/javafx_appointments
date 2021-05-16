package utils;

import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * Contains methods for checking control states and field data.
 *
 * @author Sakae Watanabe
 */
public class ControlValidation {

  /**
   * Adds listener to data collection control that check if input is present in
   * field, sets warning message, and disables button. If no input present in
   * the field border changed to red and flagged as false in field status map.
   *
   * @param control Data collection control on form that must contain input.
   * @param statusMap hash map contains key from control-id, boolean indicating if
   *                  fields are true if filled, false if blank.
   * @param warning Label on scene to be updated with the empty warning text.
   * @param button Button that will be disabled if empty fields are present.
   *
   */
  public static void checkEmptySelections(Control control, Map<String, Boolean> statusMap,
                                              Label warning, Button button) {
    control.focusedProperty().addListener((observable, oldValue, newValue) -> {
      Tooltip goodToGo = new Tooltip("Click to save changes.");
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
          //noinspection rawtypes
          if (((ComboBox) control).getSelectionModel().getSelectedItem() == null) {
            control.getStyleClass().add("combo-box-empty");
            filled = false;
          } else {
            control.getStyleClass().removeIf(style -> style.equals("combo-box-empty"));
          }
        }

      }
      statusMap.put(control.getId(), filled);

      if (statusMap.containsValue(false)) {
        button.setDisable(true);
        warning.setText("Please complete all fields to save.");

      } else {
        warning.setText("");
        button.setDisable(false);
        button.setTooltip(goodToGo);
      }
    });
  }

}
