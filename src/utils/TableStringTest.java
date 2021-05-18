package utils;

import dao.ReportDAO;
import dao.impl.reportDAOImpl.DivisionBookingsDAOImpl;
import dao.impl.reportDAOImpl.MonthlyReportDAOImpl;

/**
 * @author Sakae Watanabe
 */
public class TableStringTest {

  public static void main(String[] args) {
    ReportDAO report = new MonthlyReportDAOImpl();

    report.constructReport();
    String output = report.getReport();
    System.out.println(output);

    report = new DivisionBookingsDAOImpl();
    report.constructReport();
    output = report.getReport();
    System.out.println(output);

  }
}
