package dao.impl.reportDAOImpl;

import dao.ReportDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.reportDTO.ApptTypeSalesDTO;
import utils.NotificationHandler;
import utils.TextTableBuilder;

/**
 * Report dao for collecting appointments by type and month. Returns a table
 * formatted string for output with the type of appointments in first column
 * followed by count for each month across columns.
 *
 * @author Sakae Watanabe
 */
public class MonthlyReportDAOImpl extends ReportDAO {

  //===========================================================================
  // Data Members & Constructor
  //===========================================================================
  
  /**
   * Query for information on type of appointments with the count per month.
   */
  private final String query = "SELECT a.Type AS Type,"
      + " SUM(IF(a.Month = 'January', a.Count, 0))AS 'Jan',"
      + " SUM(IF(a.Month = 'February', a.Count, 0))AS 'Feb',"
      + " SUM(IF(a.Month = 'March', a.Count, 0))AS 'Mar',"
      + " SUM(IF(a.Month = 'April', a.Count, 0))AS 'Apr',"
      + " SUM(IF(a.Month = 'May', a.Count, 0))AS 'May',"
      + " SUM(IF(a.Month = 'June', a.Count, 0))AS 'Jun',"
      + " SUM(IF(a.Month = 'July', a.Count, 0))AS 'Jul',"
      + " SUM(IF(a.Month = 'August', a.Count, 0)) AS 'Aug',"
      + " SUM(IF(a.Month = 'September', a.Count, 0)) AS 'Sep',"
      + " SUM(IF(a.Month = 'October', a.Count, 0)) AS 'Oct',"
      + " SUM(IF(a.Month = 'November', a.Count, 0))AS 'Nov',"
      + " SUM(IF(a.Month = 'December', a.Count, 0)) AS December"
      + " FROM (SELECT  DATE_FORMAT(Start, '%M') as Month, Type, Count(*) as Count"
      + " FROM appointments\n"
      + " GROUP BY Month, Type\n"
      + " ORDER BY Month DESC) as a\n"
      + " GROUP BY Type";

  /**
   * List for holding type sales dto objects after retrieval from database.
   */
  private List<ApptTypeSalesDTO> typeSales;

  /**
   * Builds report string after data has been retrieved from database.
   */
  private TextTableBuilder<ApptTypeSalesDTO> tableBuilder;

  /**
   * Initializes ArrayList for typeSales and instance of TextTableBuilder.
   */
  public MonthlyReportDAOImpl() {
    this.typeSales = new ArrayList<>();
    this.tableBuilder = new TextTableBuilder<>();
  }

  //===========================================================================
  // Report Construction Area
  //===========================================================================


  /**
   * Report construction for the monthly appointments by type summary. First
   * column is assigned to type, with subsequent columns for months in ascending
   * order.
   */
  @Override
  public void constructReport() {
    buildData();

    if (typeSales.isEmpty()) {
      setReport("--No results to report--");
      return;
    }

    tableBuilder.addColumn("Type", ApptTypeSalesDTO::getType);
    tableBuilder.addColumn("Jan", ApptTypeSalesDTO::getJan);
    tableBuilder.addColumn("Feb", ApptTypeSalesDTO::getFeb);
    tableBuilder.addColumn("Mar", ApptTypeSalesDTO::getMar);
    tableBuilder.addColumn("Apr", ApptTypeSalesDTO::getApr);
    tableBuilder.addColumn("May", ApptTypeSalesDTO::getMay);
    tableBuilder.addColumn("Jun", ApptTypeSalesDTO::getJun);
    tableBuilder.addColumn("Jul", ApptTypeSalesDTO::getJul);
    tableBuilder.addColumn("Aug", ApptTypeSalesDTO::getAug);
    tableBuilder.addColumn("Sep", ApptTypeSalesDTO::getSep);
    tableBuilder.addColumn("Oct", ApptTypeSalesDTO::getOct);
    tableBuilder.addColumn("Nov", ApptTypeSalesDTO::getNov);
    tableBuilder.addColumn("Dec", ApptTypeSalesDTO::getDec);

    setReport(tableBuilder.createString(typeSales));
  }

  /**
   * Prepares and executes the report query before parsing results into dto
   * objects for used for report construction.
   */
  private void buildData() {
    try (PreparedStatement ps = this.conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        typeSales.add(parseDTO(rs));
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Monthly Report", e);
    }
  }


  /**
   * Parse result set data to dto object for appointment type sales reports.
   *
   * @param rs Result set from executed query.
   * @return ApptTypeSalesDTO object containing report data.
   * @throws SQLException thrown when result set unable to get columns.
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
