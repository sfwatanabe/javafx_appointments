Project: Appointment Scheduler v1.0
Author: Sakae Watanabe
Contact: swatan3@wgu.edu
Date: 2021-05-19

Appointment Scheduler is a Java based application built using Java SE 11 with JavaFX and JDBC
libraries to provide a GUI app for creating, updating, and deleting customers and appointments
for a remote company MySQL database.

Includes the following features:
  * Single factor user authentication.
  * Login attempt logging to log_activity.txt.
  * Alerts for user's appointments within 15 minutes of login.
  * Create/Update/Delete new Customers in database.
  * Create/Update/Delete new appointments in database.
  * Generate basic text reports to application window.

Build SDK & Libraries Used:
  JDK - Java SE 11 - Azul Zulu 11 used - zsrc11.45.27-jdk11.0.10
  JavaFX - Java SE 11 - Azul Zulu 11 used - zsrc11.45.27-jdk11.0.10 (Bundled JavaFX)
  JDBC - MySQL Connector - mysql-connector-java-8.0.23

To Run:
  Download source files to local folder.

  IntelliJ IDEA:
    1. From main menu, select File | Open |.
    2. Select the directory to where you saved the application source and click ok.
    3. Add libraries to project structure -> File | Project Structure | Libraries |.
        a. Add location for JavaFX library.
        b. Add location for MySQL Connector library.
    4. Select Project from left hand menu and set Project SDK to Java SE 11.
    5. Set project language level to 11.
    6. Build project from menu | Build | Build Project |
    7. Run project from menu | Run | Run |

Report Description for Part A3F:
  I Decided upon a report to list appointments by division sorted in a descending order
  by total number of appointments. Management and stakeholders may find it useful to
  track how their division is performing against other regions. Many companies provide
  performance incentives based on similar criteria. In order to pull the report to include
  division name a three table join was required as the transitive relationship flows
  through the customer table.

Discussion of Lambda References:
  Comments and explanations can be found within the javadoc comments for each of
  the following methods:
    * utils.ControlValidation.checkEmptySelections
    * view_controller.AppointmentViewController.initialize
    * view_controller.CustomerViewController.initialize
    * view_controller.MainViewController.initialize
    * view_controller.MainViewController.apptViewRadio
