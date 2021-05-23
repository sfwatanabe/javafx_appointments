package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Builder class to produce table style string output for the given object type.
 * <p>
 * adapted from <a href="https://stackoverflow.com/a/29054406/14795728"> @Marco13,
 * StackOverflow</a>
 *
 * @author Sakae Watanabe
 */
public class TextTableBuilder<T> {

  //===========================================================================
  // Data Members
  //===========================================================================
  /**
   * Holds list of column names for the text table headers.
   */
  private List<String> columnNames;

  /**
   * Holds functions for mapping row data to string output for the table.
   */
  private List<Function<? super T, String>> stringFunctions;

  /**
   * Constructor assigns new array lists for data members to accept new items.
   */
  public TextTableBuilder() {
    columnNames = new ArrayList<>();
    stringFunctions = new ArrayList<>();
  }

  /**
   * Adds column heading and cell value factory method for attributes added to
   * table. Method will be called on table objects to fill data values.
   *
   * @param columnName    String name of the column we wish to add to the table.
   * @param fieldFunction Method taking object of type T and returning a value
   *                      to be inserted into the table.
   */
  public void addColumn(String columnName, Function<? super T, ?> fieldFunction) {
    columnNames.add(columnName);
    stringFunctions.add((p) -> (String.valueOf(fieldFunction.apply(p))));
  }


  /**
   * Calculates the maximum width required for the given column index multiplied
   * by the specified scale factor. Result is returned as integer value.
   *
   * @param column   Index of the current column we're passing elements into.
   * @param elements Elements that will be passed into the column.
   * @return Integer number representing the width needed for the column.
   */
  private int computeMaxWidth(int column, Iterable<? extends T> elements) {
    int maxWidth = columnNames.get(column)
        .length();
    double scaleFactor = 1.0;

    Function<? super T, String> f = stringFunctions
        .get(column);
    for (T element : elements) {
      String s = f.apply(element);
      maxWidth = Math.max(maxWidth, s.length());
    }

    return (int) (maxWidth * scaleFactor);
  }


  /**
   * Adds padding to the left hand side of the string to fit to the column width.
   *
   * @param cellValue String to be inspected for padding.
   * @param padChar   Character to insert as padding.
   * @param length    Length needed to meet for cell.
   * @return String cellValue with padding to fit the length of the cell.
   */
  private static String padLeft(String cellValue, char padChar, int length) {
    while (cellValue.length() < length) {
      cellValue = String.format("%s%s", padChar, cellValue);
    }
    return cellValue;
  }

  /**
   * Create list of column widths for the table formatting.
   *
   * @param elements Elements for the current column.
   * @return List of integers representing the width for each column.
   */
  private List<Integer> computeColumnWidths(Iterable<? extends T> elements) {
    List<Integer> columnWidths = new ArrayList<>();
    for (int i = 0; i < columnNames.size(); i++) {
      int maxWidth = computeMaxWidth(i, elements);
      columnWidths.add(maxWidth);
    }

    return columnWidths;
  }

  /**
   * Formats result string using the given list of elements. Column headers &amp;
   * separator formatted before row data appended to string builder. Returns
   * formatted string for output.
   *
   * @param elements Iterable containing the elements with our table data.
   * @return Formatted string result with table style.
   */
  public String createString(Iterable<? extends T> elements) {
    List<Integer> columnWidths = computeColumnWidths(elements);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < columnNames.size(); i++) {
      if (i > 0) {
        sb.append("  |  ");
      }
      String format = "%" + columnWidths.get(i) + "s";
      sb.append(String.format(format, columnNames.get(i)));
    }
    sb.append("\n");

    for (int i = 0; i < columnNames.size(); i++) {
      if (i > 0) {
        sb.append("--+--");
      }
      sb.append(padLeft("", '-', columnWidths.get(i)));
    }

    sb.append("\n");

    for (T element : elements) {
      for (int i = 0; i < columnNames.size(); i++) {
        if (i > 0) {
          sb.append("  |  ");
        }
        String format = "%" + columnWidths.get(i) + "s";
        Function<? super T, String> f = stringFunctions.get(i);
        String s = f.apply(element);
        sb.append(String.format(format, s));
      }
      sb.append("\n");
    }
    return sb.toString();

  }
}
