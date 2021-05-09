package dao.impl;

import dao.ContactDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utils.DBConnector;
import utils.ErrorHandler;

/**
 * Implementation for the ContactDAO interface.
 *
 * @author Sakae Watanabe
 */
public class ContactDAOImpl implements ContactDAO {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Connection instance for accessing application database.
   */
  private Connection conn;

  /**
   * Query select statement for lookup of all contact data.
   */
  private String allContacts = "SELECT * FROM contacts";

  /**
   * Constructor for ContactDAOImpl obtains connection reference from the DBConnector class.
   */
  public ContactDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  //===========================================================================
  // Methods
  //===========================================================================


  @Override
  public ObservableList<Contact> getAll() {
    ObservableList<Contact> contacts = FXCollections.observableArrayList();

    try (PreparedStatement ps = conn.prepareStatement(allContacts);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        contacts.add(parseContact(rs));
      }

    } catch (SQLException e) {
      ErrorHandler.sqlPopup("Contact", e);
    }

    return contacts;
  }


  /**
   * Helper method to assist with parsing result set data into Contact objects.
   *
   * @param rs Result set from contact DAO query.
   * @return Contact object with parsed information.
   */
  private Contact parseContact(ResultSet rs) throws SQLException {
    Contact contact = null;

    int contactId = rs.getInt("Contact_ID");
    String name = rs.getString("Contact_Name");
    String email = rs.getString("Email");
    contact = new Contact(contactId, name, email);

    return contact;
  }

}
