package dao;

import model.User;

/**
 * UserDAO interface specifies methods to be implemented for data retrieval.
 *
 * @author Sakae Watanabe
 */
public interface UserDAO {

  /**
   * getUserByName will be used to get user data matching supplied user name.
   *
   * @param userName User name to use in query.
   * @return User object representing matching record.
   */
  public User getUserByName(String userName);
}
