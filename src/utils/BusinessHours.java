package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Utility class for providing the business hours to the application in local time format matching
 * the system default zone id. To change the business hours for the application we can adjust the
 * time zone, open, or closing hours.
 *
 * @author Sakae Watanabe
 */
public class BusinessHours {

  //===========================================================================
  // DATA MEMBERS
  //===========================================================================

  /**
   * Time zone for the business HQ location.
   */
  private static final ZoneId businessZone = ZoneId.of("America/New_York");

  /**
   * Total hours of operation for the business.
   */
  private static final int SHIFT_LENGTH = 14;

  /**
   * Opening business local time based on HQ location.
   */
  private static final LocalTime openHours = LocalTime.of(8, 00);

  /**
   * Closing business local time based on HQ location.
   */
  private static final LocalTime closeHours = openHours.plusHours(SHIFT_LENGTH);

  /**
   * ZonedDateTime object for the opening business hours based on HQ.
   */
  private static final ZonedDateTime openZDT = ZonedDateTime
      .of(LocalDate.now(), openHours, businessZone);
  /**
   * ZonedDateTime object for the opening business hours based on HQ.
   */
  private static final ZonedDateTime closeZDT = ZonedDateTime
      .of(LocalDate.now(), closeHours, businessZone);

  /**
   * Opening business hours converted to local system default zone id.
   */
  private static final LocalTime localOpen = makeLocalTime(openZDT);

  /**
   * Closing business hours converted to local system default zone id.
   */
  private static final LocalTime localClose = makeLocalTime(closeZDT);

  /**
   * Time slot interval at which time blocks will be spaced.
   */
  private static final int SPACING = 10;


  /**
   * Provides ObservableList of local times that are spaced according to provided business rules
   * before closing time.
   *
   * @return ObservableList of local time slots before the closing time.
   */
  public static ObservableList<LocalTime> getStartTimes() {
    return getTimes(localOpen, localClose.minusMinutes(SPACING));
  }

  /**
   * Provides ObservableList of local times that are spaced according to provided business rules
   * before closing time.
   *
   * @return ObservableList of local time slots before the closing time.
   */
  public static ObservableList<LocalTime> getEndTimes() {
    return getTimes(localOpen.plusMinutes(SPACING), localClose);
  }

  /**
   * Creates an ObservableList of local times starting at start time until the end of SHIFT_LENGTH.
   * List will be spaced out using the minutes value of SPACING .
   *
   * @param start Starting time for the local time list.
   * @param end   Upper bound for the local time list.
   * @return ObservableList of local time objects in the range [start, end]
   */
  private static ObservableList<LocalTime> getTimes(LocalTime start, LocalTime end) {
    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    int hours = start.getHour();
    int minutes = start.getMinute();
    var currentTime = start;
    var addMinutes = 0;
    var shiftMinutes = (SHIFT_LENGTH * 60) - SPACING;

//    while (currentTime.isBefore(end) || currentTime.equals(end)) {
    while (addMinutes <= shiftMinutes) {
//      times.add(LocalTime.of(hours, minutes));
      times.add(currentTime.plusMinutes(addMinutes));
//      minutes += BusinessHours.SPACING;
//      hours += minutes / 60;
//      minutes %= 60;
      addMinutes += SPACING;
//      currentTime = LocalTime.of(hours, minutes);
    }
    return times;
  }


  /**
   * Helper method to convert the given ZonedDateTime to a local time object.
   *
   * @param zdt ZonedDateTime object to extract LocalTime from.
   * @return LocalTime object with the system default zone id.
   */
  private static LocalTime makeLocalTime(ZonedDateTime zdt) {
    Instant instant = zdt.toInstant();
    ZonedDateTime locZDT = instant.atZone(ZoneId.systemDefault());
    return locZDT.toLocalTime();
  }

  // TODO Remove this straggler - > or rewrite as a unit test.
  public static void main(String[] args) {
    System.out.println("Start: " + localOpen);
    System.out.println("End: " + localClose);
    System.out.println(getStartTimes());
    System.out.println(getEndTimes());
  }
}
