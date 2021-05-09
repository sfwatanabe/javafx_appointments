package dao.impl;

import dao.AppointmentDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import utils.DBConnector;
import utils.ErrorHandler;

/**
 * Implementations for the AppointmentDAO interface.
 *
 * @author Sakae Watanabe
 */
public class AppointmentDAOImpl implements AppointmentDAO {

  //===========================================================================
  // Data Members
  //===========================================================================

  /** Connection instance for accessing application database. */
  private Connection conn;

  /**
   * Constructor for AppointmentDAOImpl obtains connection reference from the
   * DBConnector class.
   */
  public AppointmentDAOImpl() { this.conn = DBConnector.getConnection(); }
  
  //===========================================================================
  // Methods
  //===========================================================================

  @Override
  public Appointment getById(int appointmentId) {
    Appointment appointment = null;
    String queryById = "SELECT * FROM appointments WHERE Appointment_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(queryById)) {
      ps.setInt(1, appointmentId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          appointment = parseAppointment(rs);
        }
      }
    } catch (SQLException e) {
      ErrorHandler.sqlPopup("Appointment", e);
    }

    return appointment;
  }

  @Override
  public ObservableList<Appointment> getAll() {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    String queryAll = "SELECT * FROM appointments";

    try (PreparedStatement ps = conn.prepareStatement(queryAll);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        appointments.add(parseAppointment(rs));
      }
    } catch (SQLException e) {
      ErrorHandler.sqlPopup("Appointment", e);
    }

    return appointments;
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


  /**
   * Helper method for parsing result set data into Appointment objects.
   *
   * @param rs Result set form appointment DAO query.
   * @return Appointment object with parsed information.
   */
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

    appointment = new Appointment(id, customerId, contactId, userId, title, description, type,
                                  location, startTime, endTime);

    return appointment;
  }

}
