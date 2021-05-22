package dao.impl.reportDAOImpl;

import dao.ReportDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.reportDTO.DivisionBookingDTO;
import utils.NotificationHandler;
import utils.TextTableBuilder;

/**
 * Report dao for collecting report data to provide a list of the top performing
 * divisions based on booking count. Results are ordered by the booking count.
 *
 * @author Sakae Watanabe
 */
public class DivisionBookingsDAOImpl extends ReportDAO {


  //===========================================================================
  // Data Members & Constructor
  //===========================================================================

  /**
   * Query string for collecting results of top performing division bookings.
   * Report requires two joins to access the division name in a human readable
   * format. Useful for determining where staffing needs may be most urgent, top
   * performing regions, or other localized insights.
   */
  private final String query = "SELECT d.Division as Division, COUNT(*) as Bookings"
      + " FROM appointments"
      + " JOIN customers c on appointments.Customer_ID = c.Customer_ID"
      + " JOIN first_level_divisions as d ON c.Division_ID = d.Division_ID"
      + " GROUP BY d.Division"
      + " ORDER BY Bookings DESC";

  /**
   * List for holding division booking dto objects after retrieval from database.
   */
  private final List<DivisionBookingDTO> divisionBookings;

  /**
   * Builds report string after data has been retrieved from database.
   */
  private final TextTableBuilder<DivisionBookingDTO> tableBuilder;

  public DivisionBookingsDAOImpl() {
    this.divisionBookings = new ArrayList<>();
    this.tableBuilder = new TextTableBuilder<>();
  }

  //===========================================================================
  // Report Construction Area
  //===========================================================================


  /**
   * Report construction for the bookings by division summary report consists of
   * assigning name and bookings columns.
   */

  @Override
  public void constructReport() {
    // Build the data
    buildData();
    // Check for empty
    if (divisionBookings.isEmpty()) {
      setReport("--No results to report--");
      return;
    }
    // Set the columns
    tableBuilder.addColumn("Division", DivisionBookingDTO::getDivisionName);
    tableBuilder.addColumn("Division", DivisionBookingDTO::getBookings);
    // Build the report
    setReport(tableBuilder.createString(divisionBookings));
  }

  /**
   * Prepares and executes report query before parsing results into dto objects
   * to be used for report construction.
   */
  private void buildData() {
    try (PreparedStatement ps = this.conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        divisionBookings.add(parseDTO(rs));
      }
    } catch(SQLException e) {
      NotificationHandler.sqlPopup("Division-Booking Report", e);
    }
  }

  /**
   * Parse result set data to dto object for division booking sales reports.
   *
   * @param rs Result set from executed query.
   * @return DivisionBookingDTO object containing report data.
   */
  private DivisionBookingDTO parseDTO(ResultSet rs) throws SQLException {
    DivisionBookingDTO dto = null;

    String name = rs.getString("Division");
    int bookings = rs.getInt("Bookings");
    dto = new DivisionBookingDTO(name, bookings);

    return dto;
  }

}
