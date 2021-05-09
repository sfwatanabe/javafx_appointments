package dao;

import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

/**
 * AppointmentDAO interface specifies methods to be implemented for apointment
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
   * getBefore queries appointment data for all appointments starting at or
   * before the given start time.
   *
   * @param startLimit LocalDateTime to use as upper bound for search.
   * @return ObservableList where appointments start on or before given time.
   */
  public ObservableList<Appointment> getBefore(LocalDateTime startLimit);

  /**
   * getBefore queries appointment data for all appointments starting between
   * the start and end time. Method used to check for list of conflicting
   * appointments when adding or updating new entries.
   *
   * @param starts LocalDateTime to use as upper bound for search.
   * @param ends LocalDateTime to use as upper bound for search.
   * @return ObservableList where appointments between start and end time.
   */
  public ObservableList<Appointment> getBetween(LocalDateTime starts, LocalDateTime ends);

  /**
   * getByContact looks up appointment records matching supplied contact name.
   *
   * @param contactName String representing contact name to match.
   * @return ObservableList of appointments matching supplied contact name.
   */
  public ObservableList<Appointment> getByContact(String contactName);

  /**
   * addAppointment will insert new appointment records into database.
   *
   * @param appointment Appointment object to be added to database.
   * @param user name submitting the appointment to be added.
   * @return Integer value representing primary key of added appointment.
   */
  public int addAppointment(Appointment appointment, User user);

  /**
   * deleteAppointment will be used to delete appointment record from database that
   * matches appointment id of supplied object.
   *
   * @param appointment Appointment object to be removed from database.
   * @param user User submitting the appointment to be deleted.
   * @return Integer value representing number of affected rows.
   */
  public int deleteAppointment(Appointment appointment, User user);

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
