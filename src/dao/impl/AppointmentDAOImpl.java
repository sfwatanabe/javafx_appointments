package dao.impl;

import dao.AppointmentDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

/**
 * @author Sakae Watanabe
 */
public class AppointmentDAOImpl implements AppointmentDAO {

  @Override
  public Appointment getById(int appointmentId) {
    return null;
  }

  @Override
  public ObservableList<Appointment> getAll() {
    return null;
  }

  @Override
  public ObservableList<Appointment> getBefore(LocalDateTime startLimit) {
    return null;
  }

  @Override
  public ObservableList<Appointment> getBetween(LocalDateTime starts, LocalDateTime ends) {
    return null;
  }

  @Override
  public ObservableList<Appointment> getByContact(String contactName) {
    return null;
  }

  @Override
  public int addAppointment(Appointment appointment, User user) {
    return 0;
  }

  @Override
  public int deleteAppointment(Appointment appointment, User user) {
    return 0;
  }

  @Override
  public int updateAppointment(Appointment appointment, User user) {
    return 0;
  }

  private Appointment parseAppointment(ResultSet rs) throws SQLException {
    Appointment appointment = null;

    int id = rs.getInt("Appointment_ID");
    int customerId = rs.getInt("Customer_ID");
    int contactId = rs.getInt("Contact_ID");
    int userId = rs.getInt("User_ID");
    String title = rs.getString("Title");
    String description = rs.getString("Description");
    String type = rs.getString("Type");
    String location = rs.getString("Location");
    LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
    LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();

    return appointment;
  }

}
