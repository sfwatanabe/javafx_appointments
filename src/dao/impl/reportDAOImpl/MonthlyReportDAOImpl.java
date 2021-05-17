package dao.impl.reportDAOImpl;

import dao.ReportDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.reportDTO.ApptTypeSalesDTO;
import utils.TextTableBuilder;

/**
 * Report dao for collecting appointments by type and month. Returns a table
 * formatted string for output with the type of appointments in first column
 * followed by count for each month across columns.
 *
 * @author Sakae Watanabe
 */
public class MonthlyReportDAOImpl extends ReportDAO {

  /**
   * Query for information on type of appointments with the count per month.
   */
  private final String query = "SELECT a.Type as Type,"
      + " SUM(CASE WHEN a.Month = 'January' THEN a.Count ELSE 0 END) AS 'Jan',"
      + " SUM(CASE WHEN a.Month = 'February' THEN a.Count ELSE 0 END) AS 'Feb',"
      + " SUM(CASE WHEN a.Month = 'March' THEN a.Count ELSE 0 END) AS 'Mar',"
      + " SUM(CASE WHEN a.Month = 'April' THEN a.Count ELSE 0 END) AS 'Apr',"
      + " SUM(CASE WHEN a.Month = 'May' THEN a.Count ELSE 0 END) AS 'May',"
      + " SUM(CASE WHEN a.Month = 'June' THEN a.Count ELSE 0 END) AS 'Jun',"
      + " SUM(CASE WHEN a.Month = 'July' THEN a.Count ELSE 0 END) AS 'Jul',"
      + " SUM(CASE WHEN a.Month = 'August' THEN a.Count ELSE 0 END) AS 'Aug',"
      + " SUM(CASE WHEN a.Month = 'September' THEN a.Count ELSE 0 END) AS 'Sep',"
      + " SUM(CASE WHEN a.Month = 'October' THEN a.Count ELSE 0 END) AS 'Oct',"
      + " SUM(CASE WHEN a.Month = 'November' THEN a.Count ELSE 0 END) AS 'Nov',"
      + " SUM(CASE WHEN a.Month = 'December' THEN a.Count ELSE 0 END) AS December"
      + " FROM (SELECT  DATE_FORMAT(Start, '%M') as Month, Type, Count(*) as Count"
      + " FROM appointments"
      + " GROUP BY Month, Type"
      + " ORDER BY Month DESC) as a"
      + " GROUP BY Type";

  /**
   * List for holding type sales dto objects after retrieval from database.
   */
  private List<ApptTypeSalesDTO> typeSales;

  /**
   * Builds report string after data has been retrieved from database.
   */
  private TextTableBuilder<ApptTypeSalesDTO> tableBuilder;

  public MonthlyReportDAOImpl() {
    this.typeSales = new ArrayList<>();
    this.tableBuilder = new TextTableBuilder<>();
  }

  @Override
  public void constructReport() {
    // GET THE DATA // Check if we have anything
    // If we don't - just return --No Results--, otherwise continue.

    // PUT THE DATA TOGETHER - > we call the builder here

    // SET THE STRING
  }


  private void buildData() {
    try (PreparedStatement ps = this.conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        typeSales.add(parseDTO(rs));
      }

    } catch (SQLException e) {

    }
  }


  /**
   * Parse result set data into a dto object for appointment type sales reports.
   *
   * @param rs Result set from
   * @return ApptTypeSalesDTO object containing report data.
   */
  private ApptTypeSalesDTO parseDTO(ResultSet rs) throws SQLException {
    ApptTypeSalesDTO dto = null;
    ArrayList<Integer> months = new ArrayList<>();

    String type = rs.getString("Type");
    months.add(rs.getInt("Jan"));
    months.add(rs.getInt("Feb"));
    months.add(rs.getInt("Mar"));
    months.add(rs.getInt("Apr"));
    months.add(rs.getInt("May"));
    months.add(rs.getInt("Jun"));
    months.add(rs.getInt("Jul"));
    months.add(rs.getInt("Aug"));
    months.add(rs.getInt("Sep"));
    months.add(rs.getInt("Oct"));
    months.add(rs.getInt("Nov"));
    months.add(rs.getInt("December"));

    dto = new ApptTypeSalesDTO(type, months);

    return dto;
  }


}
