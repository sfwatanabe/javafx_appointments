package dao.impl;

import dao.CountryDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnector;
import utils.NotificationHandler;

/**
 * Implementation for the CountryDAO interface.
 *
 * @author Sakae Watanabe
 */
public class CountryDAOImpl implements CountryDAO {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Connection instance for accessing application database.
   */
  private Connection conn;

  /**
   * Constructor for CountryDAOImpl obtains connection reference from the
   * DBConnector class.
   */
  public CountryDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  //===========================================================================
  // Methods
  //===========================================================================

  @Override
  public ObservableList<Country> getAll() {
    ObservableList<Country> countries = FXCollections.observableArrayList();
    String allCountries = "SELECT * FROM countries";

    try (PreparedStatement ps = conn.prepareStatement(allCountries);
          ResultSet rs = ps.executeQuery()) {
        while(rs.next()) {
          countries.add(parseCountry(rs));
        }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Country", e);
    }
    return countries;
  }

  @Override
  public ObservableList<Country> getById(int countryId) {
    ObservableList<Country> countries = FXCollections.observableArrayList();
    String byId = "SELECT * FROM countries WHERE Country_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(byId)) {
      ps.setInt(1, countryId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          countries.add(parseCountry(rs));
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Country", e);
    }

    return countries;
  }

  /**
   * Helper method to assist with parsing result set data into Country objects.
   *
   * @param rs Result set from country DAO query.
   * @return Country object with parsed information.
   */
  private Country parseCountry(ResultSet rs) throws SQLException {
    Country country = null;

    int id = rs.getInt("Country_ID");
    String name = rs.getString("Country");
    country = new Country(id, name);

    return country;
  }

}
