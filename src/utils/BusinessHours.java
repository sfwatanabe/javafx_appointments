package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Utility class for providing the business hours to the application in local
 * time format matching the system default zone id. To change the business hours
 * for the application we can adjust the time zone, open, or closing hours.
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
  private static final LocalTime openHours = LocalTime.of(8, 0);

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
   * Provides ObservableList of local times that are spaced according to provided
   * business rules before closing time.
   *
   * @return ObservableList of local time slots before the closing time.
   */
  public static ObservableList<LocalTime> getStartTimes() {
    return getTimes(localOpen);
  }

  /**
   * Provides ObservableList of local times that are spaced according to provided
   * business rules before closing time.
   *
   * @return ObservableList of local time slots before the closing time.
   */
  public static ObservableList<LocalTime> getEndTimes() {
    return getTimes(localOpen.plusMinutes(SPACING));
  }

  /**
   * Creates an ObservableList of local times starting at start time until the
   * end of SHIFT_LENGTH. List will be spaced out by minutes value SPACING.
   *
   * @param start Starting time for the local time list.
   * @return ObservableList of local time objects in the range [start, end]
   */
  private static ObservableList<LocalTime> getTimes(LocalTime start) {
    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    var addMinutes = 0;
    var shiftMinutes = (SHIFT_LENGTH * 60) - SPACING;

    while (addMinutes <= shiftMinutes) {
      times.add(start.plusMinutes(addMinutes));
      addMinutes += SPACING;
    }
    return times;
  }

  /**
   * Determines if the start and end times fall within business hours.
   *
   * @param start Start time in local date time format.
   * @param end End time in local date time format.
   * @return True if the appointment is within a valid shift, false otherwise.
   */
  public static boolean insideShift(LocalDateTime start, LocalDateTime end) {
    boolean inside = true;

    ZonedDateTime startZDT = toBusinessZDT(start);
    ZonedDateTime endZDT = toBusinessZDT(end);
    ZonedDateTime openingZDT = ZonedDateTime.of(startZDT.toLocalDate(), openHours, businessZone);
    ZonedDateTime closingZDT = openingZDT.plusHours(SHIFT_LENGTH);

    System.out.println(start);
    System.out.println(end);

    System.out.println(startZDT);
    System.out.println(endZDT);

    System.out.println(openingZDT);
    System.out.println(closingZDT);

    if (startZDT.compareTo(endZDT) > 0) {
      inside = false;
    }
    if (startZDT.compareTo(openingZDT) < 0) {
      inside = false;
    }
    if (endZDT.compareTo(closingZDT) > 0) {
      inside = false;
    }

    return inside;
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

  /**
   * Helper method converts a local date time object to a ZonedDateTime at the
   * business zone id.
   *
   * @param ldt LocalDateTime object to be converted to the business ZonedDateTime.
   * @return ZonedDateTime object with same instant at business zone id.
   */
  private static ZonedDateTime toBusinessZDT(LocalDateTime ldt) {
    ZonedDateTime ldtZDT = ldt.atZone(ZoneId.systemDefault());
    return ldtZDT.withZoneSameInstant(businessZone);
  }

  /**
   * @return String representing the open and closing hours range in local time.
   */
  public static String getLocalBusinessHours() {
    return localOpen + " - " + localClose;
  }


  /**
   * Converts the given local date time into business time based on the home
   * zone id of the business application.
   *
   * @param ldt Local date time in system default time zone.
   * @return Formatted date time string for use in log files or console output
   *         in business hours.
   */
  public static String businessNow(LocalDateTime ldt) {
    ZonedDateTime asBusinessZDT = toBusinessZDT(ldt);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm z");
    return asBusinessZDT.format(dtf);
  }

}
