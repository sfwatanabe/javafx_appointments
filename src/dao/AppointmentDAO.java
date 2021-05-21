package dao;

import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;

/**
 * AppointmentDAO interface specifies methods to be implemented for appointment
 * data retrieval.
 *
 * @author Sakae Watanabe
 */
public interface AppointmentDAO {

  /**
   * getAppointment queries appointment data for matching appointment id.
   *
   * @param appointmentId Appointment ID number to use in query.
   * @return Appointment object representing matching record.
   */
  public Appointment getById(int appointmentId);

  /**
   * @return ObservableList containing all appointment records.
   */
  public ObservableList<Appointment> getAll();

  /**
   * getBefore queries appointment data for all appointments starting between
   * the start and end time not matching ignore. Method used to check for list
   * of conflicting appointments when adding or updating new entries while not
   * checking for the ignored id.
   *
   * @param starts LocalDateTime to use as upper bound for search.
   * @param ends LocalDateTime to use as upper bound for search.
   * @param ignore Id of appointment to be ignored from the search.
   * @return ObservableList where appointments between start and end time.
   */
  public ObservableList<Appointment> getBetween(LocalDateTime starts, LocalDateTime ends, int ignore);

  /**
   * getByContact looks up appointment records matching supplied contact id.
   *
   * @param contactID Integer representing the contact id number.
   * @return ObservableList of appointments matching supplied contact id.
   */
  public ObservableList<Appointment> getByContactID(int contactID);


  /**
   * getByCustomerId looks up appointment records matching supplied contact name.
   *
   * @param customerId Integer representing customer id to match.
   * @return ObservableList of appointments matching supplied customer id.
   */
  public ObservableList<Appointment> getByCustomerId(int customerId);

  /**
   * addAppointment will insert new appointment records into database.
   *
   * @param appointment Appointment object to be added to database.
   * @param user name submitting the appointment to be added.
   * @return Integer value representing primary key of added appointment.
   */
  public int addAppointment(Appointment appointment, User user);

  /**
   * deleteAppointment will be used to delete matching appointment from database.
   *
   * @param appointment Appointment to be deleted from database.
   * @return Integer value representing number of affected rows.
   */
  public int deleteAppointment(Appointment appointment);

  /**
   * deleteAppointment will be used to delete all appointments from database
   * matching customer id of supplied Customer.
   *
   * @param customer Customer for which appointments will be deleted.
   * @return Integer value representing number of affected rows.
   */
  public int deleteAppointmentByCustomer(Customer customer);

  /**
   * updateAppointment will be used to update appointment record in database
   * matching appointment id of supplied object.
   *
   * @param appointment Appointment object to be updated in database.
   * @param user User submitting the appointment to be updated.
   * @return Integer value representing number of affected rows.
   */
  public int updateAppointment(Appointment appointment, User user);

}
