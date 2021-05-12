package dao.impl;

import dao.DivisionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import utils.DBConnector;
import utils.NotificationHandler;

/**
 * Implementation for the DivisionDAO interface.
 *
 * @author Sakae Watanabe
 */
public class DivisionDAOImpl implements DivisionDAO {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Connection instance for accessing application database.
   */
  private Connection conn;

  /**
   * Constructor for DivisionDAOImpl obtains connection reference from the
   * DBConnector class.
   */
  public DivisionDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  //===========================================================================
  // Methods
  //===========================================================================

  @Override
  public ObservableList<Division> getAll() {
    ObservableList<Division> divisions = FXCollections.observableArrayList();
    String allDivisions = "SELECT * FROM first_level_divisions";

    try (PreparedStatement ps = conn.prepareStatement(allDivisions);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        divisions.add(parseDivision(rs));
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Division", e);
    }

    return divisions;
  }

  @Override
  public ObservableList<Division> getByCountry(int countryID) {
    ObservableList<Division> divisions = FXCollections.observableArrayList();
    String byCountryId = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(byCountryId)) {
      ps.setInt(1, countryID);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          divisions.add(parseDivision(rs));
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Division", e);
    }

    return divisions;
  }


  /**
   * Helper method to assist with parsing result set data into Division objects.
   *
   * @param rs Result set from division DAO query.
   * @return Division object with parsed information.
   */
  private Division parseDivision(ResultSet rs) throws SQLException {
    Division division = null;

    int id = rs.getInt("Division_ID");
    String name = rs.getString("Division");
    int countryID = rs.getInt("COUNTRY_ID");
    division = new Division(id, name, countryID);

    return division;
  }
}
