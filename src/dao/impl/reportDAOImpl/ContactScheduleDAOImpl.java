package dao.impl.reportDAOImpl;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.ReportDAO;
import dao.impl.AppointmentDAOImpl;
import dao.impl.ContactDAOImpl;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.Contact;
import utils.TextTableBuilder;

/**
 * Report dao for listing schedule for each contact in the organization with
 * appointments sorted by start date.
 *
 * @author Sakae Watanabe
 */
public class ContactScheduleDAOImpl extends ReportDAO {

  //===========================================================================
  // Data Members & Constructor
  //===========================================================================

  /**
   * DAO for accessing appointment data.
   */
  private AppointmentDAO appointmentDAO;

  /**
   * DAO for accessing contact list data.
   */
  private ContactDAO contactDAO;


  /**
   * Complete list of contacts currently in the company databse.
   */
  private List<Contact> contacts;

  /**
   * List to hold appointment data for table creation.
   */
  private List<Appointment> appointments;

  /**
   * Builds report table data for appointments retrieved from database.
   */
  private TextTableBuilder<Appointment> tableBuilder;

  /**
   * StringBuilder that will hold different parts of the report before final assembly.
   */
  private StringBuilder reportString;

  /**
   * Initializes ArrayLists for both contacts and appointments as well as StringBuilder
   * for reportString.
   */
  public ContactScheduleDAOImpl() {
    this.contactDAO = new ContactDAOImpl();
    this.appointmentDAO = new AppointmentDAOImpl();
    this.contacts = new ArrayList<>();
    this.appointments = new ArrayList<>();
    this.reportString = new StringBuilder();
  }

  //===========================================================================
  // Report Construction Area
  //===========================================================================

  @Override
  public void constructReport() {
    contacts = contactDAO.getAll();

    for (Contact c : contacts) {
      String sectionHeading = "Appointments for " + c.getName() + "\n" + "------------------------\n";

      appointments = appointmentDAO.getByContactID(c.getId());

      if (!appointments.isEmpty()){
        tableBuilder = new TextTableBuilder<>();
        tableBuilder.addColumn("Appt. ID", Appointment::getId);
        tableBuilder.addColumn("Title", Appointment::getTitle);
        tableBuilder.addColumn("Type", Appointment::getType);
        tableBuilder.addColumn("Description", Appointment::getDescription);
        tableBuilder.addColumn("Start", Appointment::getStartLocalString);
        tableBuilder.addColumn("End", Appointment::getEndLocalString);
        tableBuilder.addColumn("Customer ID", Appointment::getCustomerId);

        reportString.append(sectionHeading).append(tableBuilder.createString(appointments)).append("\n\n");
      } else {
          reportString.append(sectionHeading).append("No Appointments Scheduled\n\n");
      }
    }

    setReport(reportString.toString());
  }

}
