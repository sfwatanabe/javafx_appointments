package utils;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.ReportDAO;
import dao.impl.AppointmentDAOImpl;
import dao.impl.ContactDAOImpl;
import dao.impl.reportDAOImpl.ContactScheduleDAOImpl;
import dao.impl.reportDAOImpl.DivisionBookingsDAOImpl;
import dao.impl.reportDAOImpl.MonthlyReportDAOImpl;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.Contact;

/**
 * @author Sakae Watanabe
 */
public class TableStringTest {

  public static void main(String[] args) {
    //region Report output test
    ReportDAO report = new MonthlyReportDAOImpl();
//
//    report.constructReport();
//    String output = report.getReport();
//    System.out.println(output);
//
//    report = new DivisionBookingsDAOImpl();
//    report.constructReport();
//    output = report.getReport();
//    System.out.println(output);

    report = new ContactScheduleDAOImpl();
    report.constructReport();
    String output = report.getReport();
    System.out.println(output);

    //endregion

    //region Contact Schedule Test
//    ContactDAO contactDAO = new ContactDAOImpl();
//    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
//    List<Appointment> appointments = new ArrayList<>();
//
//    List<Contact> contacts = contactDAO.getAll();
//    for (Contact c : contacts) {
//      System.out.println("Schedule for " + c.getName() + "\n\n");
//      appointments = appointmentDAO.getByContactID(c.getId());
//      if(appointments.isEmpty()) {
//        System.out.println("----No Appointments----");
//      } else {
//        System.out.println(appointments);
//      }
//    }
    //endregion

  }
}
