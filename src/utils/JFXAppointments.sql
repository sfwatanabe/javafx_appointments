CREATE DATABASE IF NOT EXISTS `YOUR_DATABASE` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE YOUR_DATABASE;

CREATE TABLE `contacts`
(
    `Contact_ID`   INT         NOT NULL AUTO_INCREMENT,
    `Contact_Name` varchar(50) NOT NULL,
    `Email`        varchar(50) NOT NULL,
    PRIMARY KEY (`Contact_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `countries`
(
    `Country_ID`      INT         NOT NULL AUTO_INCREMENT,
    `Country`         varchar(50) NOT NULL,
    `Create_Date`     datetime             DEFAULT CURRENT_TIMESTAMP,
    `Created_By`      varchar(50)          DEFAULT NULL,
    `Last_Update`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Last_Updated_By` varchar(50)          DEFAULT NULL,
    PRIMARY KEY (`Country_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `users`
(
    `User_ID`         INT       NOT NULL AUTO_INCREMENT,
    `User_Name`       varchar(50)        DEFAULT NULL,
    `Password`        text,
    `Create_Date`     datetime           DEFAULT CURRENT_TIMESTAMP,
    `Created_By`      varchar(50)        DEFAULT NULL,
    `Last_Update`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Last_Updated_By` varchar(50)        DEFAULT NULL,
    PRIMARY KEY (`User_ID`),
    UNIQUE KEY `User_Name` (`User_Name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `first_level_divisions`
(
    `Division_ID`     INT         NOT NULL AUTO_INCREMENT,
    `Division`        varchar(50) NOT NULL,
    `Create_Date`     datetime             DEFAULT CURRENT_TIMESTAMP,
    `Created_By`      varchar(50)          DEFAULT NULL,
    `Last_Update`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Last_Updated_By` varchar(50)          DEFAULT NULL,
    `COUNTRY_ID`      INT         NOT NULL,
    PRIMARY KEY (`Division_ID`),
    KEY `COUNTRY_ID` (`COUNTRY_ID`),
    CONSTRAINT `first_level_divisions_ibfk_1` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `countries` (`Country_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 105
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `customers`
(
    `Customer_ID`     INT          NOT NULL AUTO_INCREMENT,
    `Customer_Name`   varchar(50)  NOT NULL,
    `Address`         varchar(100) NOT NULL,
    `Postal_Code`     varchar(50)  NOT NULL,
    `Phone`           varchar(50)  NOT NULL,
    `Create_Date`     datetime              DEFAULT CURRENT_TIMESTAMP,
    `Created_By`      varchar(50)           DEFAULT NULL,
    `Last_Update`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Last_Updated_By` varchar(50)           DEFAULT NULL,
    `Division_ID`     INT          NOT NULL,
    PRIMARY KEY (`Customer_ID`),
    KEY `Division_ID` (`Division_ID`),
    CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`Division_ID`) REFERENCES `first_level_divisions` (`Division_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `appointments`
(
    `Appointment_ID`  INT         NOT NULL AUTO_INCREMENT,
    `Title`           varchar(50) NOT NULL,
    `Description`     varchar(50)          DEFAULT NULL,
    `Location`        varchar(50) NOT NULL,
    `Type`            varchar(50)          DEFAULT NULL,
    `Start`           datetime             DEFAULT CURRENT_TIMESTAMP,
    `End`             datetime             DEFAULT CURRENT_TIMESTAMP,
    `Create_Date`     datetime             DEFAULT CURRENT_TIMESTAMP,
    `Created_By`      varchar(50)          DEFAULT NULL,
    `Last_Update`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `Last_Updated_By` varchar(50)          DEFAULT NULL,
    `Customer_ID`     INT                  DEFAULT NULL,
    `User_ID`         INT                  DEFAULT NULL,
    `Contact_ID`      INT                  DEFAULT NULL,
    PRIMARY KEY (`Appointment_ID`),
    KEY `Customer_ID` (`Customer_ID`),
    KEY `User_ID` (`User_ID`),
    KEY `Contact_ID` (`Contact_ID`),
    CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`Customer_ID`),
    CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`User_ID`) REFERENCES `users` (`User_ID`),
    CONSTRAINT `appointments_ibfk_3` FOREIGN KEY (`Contact_ID`) REFERENCES `contacts` (`Contact_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = utf8mb4;

-- Test User
INSERT INTO users VALUES(1, 'test', 'test', NOW(), 'script', NOW(), 'script');