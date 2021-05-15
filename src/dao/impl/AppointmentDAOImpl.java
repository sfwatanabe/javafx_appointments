package dao.impl;

import dao.AppointmentDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;
import utils.DBConnector;
import utils.NotificationHandler;

/**
 * Implementations for the AppointmentDAO interface.
 *
 * @author Sakae Watanabe
 */
public class AppointmentDAOImpl implements AppointmentDAO {

  //===========================================================================
  // Data Members
  //===========================================================================

  /**
   * Connection instance for accessing application database.
   */
  private Connection conn;

  /**
   * Constructor for AppointmentDAOImpl obtains connection reference from the DBConnector class.
   */
  public AppointmentDAOImpl() {
    this.conn = DBConnector.getConnection();
  }

  //===========================================================================
  // Methods
  //===========================================================================

  @Override
  public Appointment getById(int appointmentId) {
    Appointment appointment = null;
    String queryById = "SELECT a.Appointment_ID, a.Customer_ID, a.Contact_ID, c.Contact_Name,"
        + " a.User_ID, a.Title, a.Description, a.Type, a.Location, a.Start, a.End"
        + " FROM appointments AS a"
        + " INNER JOIN contacts AS c"
        + " WHERE a.Contact_ID = c.Contact_ID AND a.Appointment_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(queryById)) {
      ps.setInt(1, appointmentId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          appointment = parseAppointment(rs);
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Appointment", e);
    }

    return appointment;
  }

  @Override
  public ObservableList<Appointment> getAll() {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    String queryAll = "SELECT a.Appointment_ID, a.Customer_ID, a.Contact_ID, c.Contact_Name,"
        + " a.User_ID, a.Title, a.Description, a.Type, a.Location, a.Start, a.End "
        + " FROM appointments AS a \n"
        + " INNER JOIN contacts AS c"
        + " WHERE a.Contact_ID = c.Contact_ID ";

    try (PreparedStatement ps = conn.prepareStatement(queryAll);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        appointments.add(parseAppointment(rs));
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Appointment", e);
    }

    return appointments;
  }

  @Override
  public ObservableList<Appointment> getBefore(LocalDateTime startLimit) {
    return null;
  }

  @Override
  public ObservableList<Appointment> getBetween(LocalDateTime starts, LocalDateTime ends) {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    String queryBetween = "";

    return null;
  }

  @Override
  public ObservableList<Appointment> getByCustomerId(int customerId) {
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    String queryByCustomerId =
        "SELECT a.Appointment_ID, a.Customer_ID, a.Contact_ID, c.Contact_Name,"
            + " a.User_ID, a.Title, a.Description, a.Type, a.Location, a.Start, a.End"
            + " FROM appointments AS a"
            + " INNER JOIN contacts AS c"
            + " WHERE a.Contact_ID = c.Contact_ID AND a.Customer_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(queryByCustomerId)) {
      ps.setInt(1, customerId);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          appointments.add(parseAppointment(rs));
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Appointment", e);
    }

    return appointments;
  }

  @Override
  public ObservableList<Appointment> getByContact(String contactName) {
    return null;
  }

  @Override
  public int addAppointment(Appointment appointment, User user) {
    int newAppointmentID = 0;
    int rowsAffected = 0;

    String newAppointment = "INSERT INTO appointments (Title, Description, Location, Type,\n"
        + "Start, End, Create_Date, Created_By, Last_Update,\n"
        + "Last_Updated_By, Customer_ID, User_ID, Contact_ID)\n"
        + "VALUES (?,?,?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";

    try (PreparedStatement ps = conn
        .prepareStatement(newAppointment, PreparedStatement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, appointment.getTitle());
      ps.setString(2, appointment.getDescription());
      ps.setString(3, appointment.getLocation());
      ps.setString(4, appointment.getType());
      ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
      ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
      ps.setString(7, user.getName());
      ps.setString(8, user.getName());
      ps.setInt(9, appointment.getCustomerId());
      ps.setInt(10, user.getId());
      ps.setInt(11, appointment.getContactId());

      rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Create appointment failed, no rows affected.");
      }
      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          newAppointmentID = keys.getInt(1);
        } else {
          throw new SQLException("Create appointment failed, no rows affected.");
        }
      }
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Appointment-Add", e);
    }

    return newAppointmentID;
  }

  @Override
  public int deleteAppointment(Appointment appointment) {
    int rowsAffected = 0;
    String deleteById = "DELETE FROM appointments WHERE Appointment_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(deleteById)) {
      ps.setInt(1, appointment.getId());
      rowsAffected = ps.executeUpdate();
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Appointment", e);
    }

    return rowsAffected;
  }

  @Override
  public int deleteAppointmentByCustomer(Customer customer) {
    int rowsAffected = 0;
    String deleteByCustomer = "DELETE FROM appointments WHERE Customer_ID = ?";

    try (PreparedStatement ps = conn.prepareStatement(deleteByCustomer)) {
      ps.setInt(1, customer.getId());
      rowsAffected = ps.executeUpdate();
    } catch (SQLException e) {
      NotificationHandler.sqlPopup("Appointment", e);
    }

    return rowsAffected;
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

    int id = rs.getInt("a.Appointment_ID");
    int customerId = rs.getInt("a.Customer_ID");
    int contactId = rs.getInt("a.Contact_ID");
    String contactName = rs.getString("c.Contact_Name");
    int userId = rs.getInt("a.User_ID");
    String title = rs.getString("a.Title");
    String description = rs.getString("a.Description");
    String type = rs.getString("a.Type");
    String location = rs.getString("a.Location");
    LocalDateTime startTime = rs.getTimestamp("a.Start").toLocalDateTime();
    LocalDateTime endTime = rs.getTimestamp("a.End").toLocalDateTime();

    appointment = new Appointment(id, customerId, contactId, contactName, userId, title,
        description, type, location, startTime, endTime);

    return appointment;
  }

}
