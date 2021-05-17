package utils;

import dao.impl.AppointmentDAOImpl;
import java.util.List;
import model.Appointment;

/**
 * @author Sakae Watanabe
 */
public class TableStringTest {

  public static void main(String[] args) {
    AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
    List<Appointment> appointments = appointmentDAO.getAll();

    TextTableBuilder<Appointment> reportTest = new TextTableBuilder<>();
    reportTest.addColumn("ID", Appointment::getId);
    reportTest.addColumn("Contact Name", Appointment::getContactName);
    reportTest.addColumn("Customer ID", Appointment::getCustomerId);
    reportTest.addColumn("Type", Appointment::getType);

    String output = reportTest.createString(appointments);

    System.out.println(output);
  }
}
