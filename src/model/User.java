package model;

/**
 * Users accounts are stored on the server for accessing the database. New user
 * accounts may only be added by server admin.
 *
 * @author Sakae Watanabe
 */
public class User extends Person {
  //===========================================================================
  // Data Members
  //===========================================================================
  /** String representing the user password. */
  private String password;

  /**
   * Constructor for the user class. Users must have a password for accessing
   * the system functionality.
   *
   * @param id Integer representing the user id.
   * @param name String representing the user name.
   * @param password String representing the user password.
   */
  public User(int id, String name, String password) {
    super(id, name);
    this.password = password;
  }

  //===========================================================================
  // Getter & Setter Methods
  //===========================================================================
  /**
   * @return String value representing the user password.
   */
  public String getPassword() {
    return password;
  }

}