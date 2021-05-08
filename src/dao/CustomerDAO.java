package dao;

import java.sql.SQLException;
import javafx.collections.ObservableList;
import model.Customer;

/**
 * CustomerDAO interface specifies methods to be implemented for customer data
 * retrieval.
 *
 * @author Sakae Watanabe
 */
public interface CustomerDAO {

  /**
   * getCustomer will be used to get customer data matching customer id number.
   *
   * @param id Customer ID number to use in query.
   * @return Customer object representing matching record.
   */
  public Customer getCustomer(int id) throws SQLException;

  /**
   * @return ObservableList of Customer objects.
   */
  public ObservableList<Customer> getAllCustomers() throws SQLException;

  /**
   * addCustomer will be used to insert new customer records into database.
   *
   * @param customer Customer object to be added to database.
   * @param user User name submitting the customer to be added.
   * @return Int value representing primary key of added customer.
   */
  public int addCustomer(Customer customer, String user) throws SQLException;

  /**
   * deleteCustomer will be used to delete customer record from database that
   * matches customer id of supplied object.
   *
   * @param customer Customer object to be removed from database.
   * @param user User name submitting the customer to be added.
   * @return Int value representing number of affected rows.
   */
  public int deleteCustomer(Customer customer, String user) throws SQLException;

  /**
   * updateCustomer will be used to update customer record in database matching
   * customer id of supplied object.
   *
   * @param customer Customer object to be updated in database.
   * @param user User name submitting the customer to be added.
   * @return Int value representing number of affected rows.
   */
  public int updateCustomer(Customer customer, String user) throws SQLException;
}
