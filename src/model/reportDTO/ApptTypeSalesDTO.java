package model.reportDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for appointment sales by type reporting access holds the name of appointment
 * type and summary of bookings for each month.This class only has getter methods implemented to avoid inadvertent
 * corruption of the data by user.
 *
 * @author Sakae Watanabe
 */
public class ApptTypeSalesDTO {

  /**
   * Appointment type.
   */
  private String type;

  /**
   * Sales for the given type of appointment over each month.
   */
  private List<Integer> monthTotal;

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
   * Return number of sales for January.
   */
  public int getJan() {
    return monthTotal.get(0);
  }

  /**
   * Return number of sales for February.
   */
  public int getFeb() {
    return monthTotal.get(1);
  }

  /**
   * Return number of sales for March.
   */
  public int getMar() {
    return monthTotal.get(2);
  }

  /**
   * Return number of sales for April.
   */
  public int getApr() {
    return monthTotal.get(3);
  }

  /**
   * Return number of sales for May.
   */
  public int getMay() {
    return monthTotal.get(4);
  }

  /**
   * Return number of sales for June.
   */
  public int getJun() {
    return monthTotal.get(5);
  }

  /**
   * Return number of sales for July.
   */
  public int getJul() {
    return monthTotal.get(6);
  }

  /**
   * Return number of sales for August.
   */
  public int getAug() {
    return monthTotal.get(7);
  }

  /**
   * Return number of sales for September.
   */
  public int getSep() {
    return monthTotal.get(8);
  }

  /**
   * Return number of sales for October.
   */
  public int getOct() {
    return monthTotal.get(9);
  }

  /**
   * Return number of sales for November.
   */
  public int getNov() {
    return monthTotal.get(10);
  }

  /**
   * Return number of sales for December.
   */
  public int getDec() {
    return monthTotal.get(11);
  }

}
