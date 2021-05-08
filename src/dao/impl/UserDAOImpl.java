package dao.impl;

import dao.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import utils.DBConnector;

/**
 * Implementation of the UserDAO interface for accessing user data.
 *
 * @author Sakae Watanabe
 */
public class UserDAOImpl implements UserDAO {

  //===========================================================================
  // Data Members
  //===========================================================================
  /** Connection instance for accessing application database. */
  private Connection conn;

  /** Query select statement for lookup of user data by user name. */
  private String byUserName = "SELECT User_ID, User_Name, Password " +
                            "FROM users WHERE " +
                            "User_Name = ?";

  /**
   * Constructor for UserDAOImpl obtains connection reference from the
   * DBConnector class.
   */
  public UserDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  /**
   * Attempts to retrieve user by name from database connection with prepared
   * query statement using try with resource blocks.
   *
   * @param userName String value of user name to be queried.
   * @return User object reference or null if not found.
   * @throws SQLException if unable to prepare statement.
   */
  @Override
  public User getUserByName(String userName) {
    User user = null;

    try (PreparedStatement ps = conn.prepareStatement(byUserName))
    {
      ps.setString(1, userName);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          int id = rs.getInt("User_ID");
          String name = rs.getString("User_Name");
          String password = rs.getString("Password");
          user = new User(id, name, password);
        }
      }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
  }
}
