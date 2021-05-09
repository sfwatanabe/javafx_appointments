package dao.impl;

import dao.CustomerDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import utils.DBConnector;
import utils.ErrorHandler;

/**
 * Implementation of the CustomerDAO interface for accessing customer data.
 *
 * @author Sakae Watanabe
 */
public class CustomerDAOImpl implements CustomerDAO {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Connection instance for accessing application database.
   */
  private Connection conn;

  /**
   * Constructor for CustomerDAOImpl obtains connection reference from the DBConnector class.
   */
  public CustomerDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  //===========================================================================
  // Methods
  //===========================================================================

  @Override
  public Customer getById(int id) {
    Customer customer = null;
    String queryById = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID"
        + " FROM customers WHERE Customer_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(queryById)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          customer = parseCustomer(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  @Override
  public ObservableList<Customer> getAll() {
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    String queryAll = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID"
        + " FROM customers";

    try (PreparedStatement ps = conn.prepareStatement(queryAll);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        customers.add(parseCustomer(rs));
      }
    } catch (SQLException e) {
      ErrorHandler.sqlPopup("Customer", e);
    }

    return customers;
  }

  @Override
  public int addCustomer(Customer customer, User user) {
    // TODO implement addCustomer and return customer id of record
    return 0;
  }

  @Override
  public int deleteCustomer(Customer customer, User user) {
    // TODO Must implement delete appointment by customer first
    return 0;
  }

  @Override
  public int updateCustomer(Customer customer, User user) {
    // TODO implement updateCustomer and return customer id of record
    return 0;
  }

  /**
   * Helper method to assist with parsing result set data into Customer objects.
   *
   * @param rs Result set from customer DAO query.
   * @return Customer object with parsed information.
   */
  private Customer parseCustomer(ResultSet rs) throws SQLException {
    Customer customer = null;

    int customerId = rs.getInt("Customer_ID");
    String customerName = rs.getString("Customer_Name");
    String address = rs.getString("Address");
    String postalCode = rs.getString("Postal_Code");
    String phoneNumber = rs.getString("Phone");
    int divisionId = rs.getInt("Division_ID");
    customer = new Customer(customerId, customerName, address, postalCode,
        phoneNumber, divisionId);

    return customer;
  }
}
