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
import utils.NotificationHandler;

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
  private final Connection conn;

  /**
   * Constructor for CustomerDAOImpl obtains connection reference from the
   * DBConnector class.
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
      NotificationHandler.sqlPopup("Customer", e);
    }

    return customers;
  }


  @Override
  public int addCustomer(Customer customer, User user) {
    int newCustomerId = 0;
    int rowsAffected;
    String newCustomer =
        "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date,"
            + " Created_By, Last_Update, Last_Updated_By, Division_ID)"
            + " VALUES(?, ?, ?,?,NOW(),?,NOW(),?,?)";

    try (PreparedStatement ps = conn
        .prepareStatement(newCustomer, PreparedStatement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, customer.getName());
      ps.setString(2, customer.getAddress());
      ps.setString(3, customer.getPostalCode());
      ps.setString(4, customer.getPhoneNumber());
      ps.setString(5, user.getName());
      ps.setString(6, user.getName());
      ps.setInt(7, customer.getDivisionId());
      rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Create user failed, no rows affected.");
      }
      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          newCustomerId = keys.getInt(1);
        } else {
          throw new SQLException("Create user failed, no id key returned.");
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Customer-Add", e);
    }

    return newCustomerId;
  }


  @Override
  public int updateCustomer(Customer customer, User user) {
    int rowsAffected = 0;
    String updateCustomer = "UPDATE customers "
        + " SET Customer_Name = ?,"
        + " Address = ?,"
        + " Postal_Code = ?,"
        + " Phone = ?,"
        + " Division_ID = ?,"
        + " Last_Update = NOW(),"
        + " Last_Updated_By = ?"
        + "WHERE Customer_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(updateCustomer)) {
      ps.setString(1, customer.getName());
      ps.setString(2, customer.getAddress());
      ps.setString(3, customer.getPostalCode());
      ps.setString(4, customer.getPhoneNumber());
      ps.setInt(5, customer.getDivisionId());
      ps.setString(6, user.getName());
      ps.setInt(7, customer.getId());

      rowsAffected = ps.executeUpdate();

      if (rowsAffected == 0) {
        throw new SQLException("Update user failed, no rows affected.");
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Customer-Update", e);
    }

    return rowsAffected;
  }


  @Override
  public int deleteCustomer(Customer customer) {
    int rowsAffected = 0;
    String deleteById = "DELETE FROM customers WHERE Customer_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(deleteById)) {
      ps.setInt(1, customer.getId());
      rowsAffected = ps.executeUpdate();
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Customer-Delete", e);
    }

    return rowsAffected;
  }


  /**
   * Helper method to assist with parsing result set data into Customer objects.
   *
   * @param rs Result set from customer DAO query.
   * @return Customer object with parsed information.
   */
  private Customer parseCustomer(ResultSet rs) throws SQLException {
    Customer customer;

    int customerId = rs.getInt("Customer_ID");
    String customerName = rs.getString("Customer_Name");
    String address = rs.getString("Address");
    String postalCode = rs.getString("Postal_Code");
    String phoneNumber = rs.getString("Phone");
    int divisionId = rs.getInt("Division_ID");
    customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId);

    return customer;
  }
}
