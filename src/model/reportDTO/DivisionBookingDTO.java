package model.reportDTO;

/**
 * Data transfer object to hold booking by division data to be used for report
 * generation. This class only has getter methods implemented to avoid inadvertent
 * corruption of the data by user.
 *
 * @author Sakae Watanabe
 */
public class DivisionBookingDTO {

  /**
   * Name of the division.
   */
  private String divisionName;

  /**
   * Number of bookings currently attributed to the division.
   */
  private int bookings;

  /**
   * Accepts a string for the name of division and an integer representing the
   * number of bookings currently attributed.
   *
   * @param divisionName Name of division.
   * @param bookings Integer value representing number of bookings for division.
   */
  public DivisionBookingDTO(String divisionName, int bookings) {
    this.divisionName = divisionName;
    this.bookings = bookings;
  }

  /**
   * @return String representing the name of the division.
   */
  public String getDivisionName() {
    return divisionName;
  }

  /**
   * @return Integer value representing number of bookings for division.
   */
  public int getBookings() {
    return bookings;
  }

}
