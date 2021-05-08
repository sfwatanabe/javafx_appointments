package dao;

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
   * getCustomer queries customer data matching customer id number.
   *
   * @param id Customer ID number to use in query.
   * @return Customer object representing matching record.
   */
  public Customer getById(int id);

  /**
   * @return ObservableList of Customer objects.
   */
  public ObservableList<Customer> getAll();

  /**
   * addCustomer will be insert new customer records into database.
   *
   * @param customer Customer object to be added to database.
   * @param user User name submitting the customer to be added.
   * @return Integer value representing primary key of added customer.
   */
  public int addCustomer(Customer customer, String user);

  /**
   * deleteCustomer will be used to delete customer record from database that
   * matches customer id of supplied object.
   *
   * @param customer Customer object to be removed from database.
   * @param user User name submitting the customer to be deleted.
   * @return Integer value representing number of affected rows.
   */
  public int deleteCustomer(Customer customer, String user);

  /**
   * updateCustomer will be used to update customer record in database matching
   * customer id of supplied object.
   *
   * @param customer Customer object to be updated in database.
   * @param user User name submitting the customer to be updated.
   * @return Integer value representing number of affected rows.
   */
  public int updateCustomer(Customer customer, String user);
}
