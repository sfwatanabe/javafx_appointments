Project: Appointment Scheduler v1.0
Author: Sakae Watanabe
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

Build Dependencies:
  JDK - Java SE 11 - Azul Zulu 11 used - zsrc11.45.27-jdk11.0.10
  JavaFX - Java SE 11 - Azul Zulu 11 used - zsrc11.45.27-jdk11.0.10 (Bundled JavaFX)
  JDBC - MySQL Connector - mysql-connector-java-8.0.23

To Run:
  Download source files to local folder.

  IntelliJ IDEA:
    1. From main menu, select File | Open.
    2. Select the directory to where you saved the application source and click ok.
    3. Select create project from existing sources.
