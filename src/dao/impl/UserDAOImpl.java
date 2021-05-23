package dao.impl;

import dao.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBConnector;
import utils.NotificationHandler;

/**
 * Implementation of the UserDAO interface for accessing user data.
 *
 * @author Sakae Watanabe
 */
public class UserDAOImpl implements UserDAO {

  //===========================================================================
  // Data Members
  //===========================================================================
  /**
   * Connection instance for accessing application database.
   */
  private final Connection conn;

  /**
   * Constructor for UserDAOImpl obtains connection reference from the
   * DBConnector class.
   */
  public UserDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  //===========================================================================
  // Methods
  //===========================================================================
  
  @Override
  public ObservableList<User> getAll() {
    ObservableList<User> users = FXCollections.observableArrayList();
    String allUsers = "SELECT * FROM users";

    try (PreparedStatement ps = conn.prepareStatement(allUsers);
        ResultSet rs = ps.executeQuery()) {
      while(rs.next()){
        users.add(parseUser(rs));
      }
    } catch(SQLException e) {
      NotificationHandler.sqlPopup("User", e);
    }

    return users;
  }

  @Override
  public User getUserByName(String userName) {
    User user = null;
    String byUserName = "SELECT User_ID, User_Name, Password " +
        "FROM users WHERE " +
        "User_Name = ?";

    try (PreparedStatement ps = conn.prepareStatement(byUserName)) {
      ps.setString(1, userName);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          user = parseUser(rs);
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("User", e);
    }
    return user;
  }

  /**
   * Helper method to assist with parsing result set data into User objects.
   *
   * @param rs Result set from user DAO query.
   * @return User object with parsed information.
   * @throws SQLException if error parsing column from result set.
   */
  private User parseUser(ResultSet rs) throws SQLException {
    User user;

    int id = rs.getInt("User_ID");
    String name = rs.getString("User_Name");
    String password = rs.getString("Password");
    user = new User(id, name, password);

    return user;
  }


}
