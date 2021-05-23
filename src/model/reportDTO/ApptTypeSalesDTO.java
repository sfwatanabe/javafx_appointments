package model.reportDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for appointment sales by type reporting access holds the
 * name of appointment type and summary of bookings for each month.This class
 * only has getter methods implemented to avoid inadvertent corruption of the
 * data by user.
 *
 * @author Sakae Watanabe
 */
public class ApptTypeSalesDTO {

  /**
   * Appointment type.
   */
  private final String type;

  /**
   * Sales for the given type of appointment over each month.
   */
  private final List<Integer> monthTotal;

  public ApptTypeSalesDTO(String type, ArrayList<Integer> monthTotal) {
    this.type = type;
    this.monthTotal = monthTotal;
  }

  /**
   * @return String value representing the type of appointment.
   */
  public String getType() {
    return type;
  }

  /**
   * @return Number of sales for January.
   */
  public int getJan() {
    return monthTotal.get(0);
  }

  /**
   * @return Number of sales for February.
   */
  public int getFeb() {
    return monthTotal.get(1);
  }

  /**
   * @return Number of sales for March.
   */
  public int getMar() {
    return monthTotal.get(2);
  }

  /**
   * @return Number of sales for April.
   */
  public int getApr() {
    return monthTotal.get(3);
  }

  /**
   * @return Number of sales for May.
   */
  public int getMay() {
    return monthTotal.get(4);
  }

  /**
   * @return Number of sales for June.
   */
  public int getJun() {
    return monthTotal.get(5);
  }

  /**
   * @return Number of sales for July.
   */
  public int getJul() {
    return monthTotal.get(6);
  }

  /**
   * @return Number of sales for August.
   */
  public int getAug() {
    return monthTotal.get(7);
  }

  /**
   * @return Number of sales for September.
   */
  public int getSep() {
    return monthTotal.get(8);
  }

  /**
   * @return Number of sales for October.
   */
  public int getOct() {
    return monthTotal.get(9);
  }

  /**
   * @return Number of sales for November.
   */
  public int getNov() {
    return monthTotal.get(10);
  }

  /**
   * @return Number of sales for December.
   */
  public int getDec() {
    return monthTotal.get(11);
  }

}
