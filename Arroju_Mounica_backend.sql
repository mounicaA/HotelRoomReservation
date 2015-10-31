CREATE DATABASE `arroju_mounica_db`;

CREATE TABLE `bookings` (
  `BookingID` int(11) NOT NULL AUTO_INCREMENT,
  `RoomID` int(11) NOT NULL,
  `PromotionID` int(11) DEFAULT NULL,
  `CheckInDate` datetime DEFAULT NULL,
  `CheckOutDate` datetime DEFAULT NULL,
  `NoOfNights` int(11) DEFAULT NULL,
  `BookingStatus` varchar(45) DEFAULT NULL,
  `NoOfAdults` int(11) DEFAULT NULL,
  `NoOfKids` int(11) DEFAULT NULL,
  `ConfirmationNumber` varchar(45) DEFAULT NULL,
  `FinalBookingPrice` float DEFAULT NULL,
  `TaxAmount` float DEFAULT NULL,
  `PaymentTotal` float DEFAULT NULL,
  PRIMARY KEY (`BookingID`),
  KEY `PromotionID_idx` (`PromotionID`),
  KEY `RoomID_idx` (`RoomID`),
  CONSTRAINT `PromotionID` FOREIGN KEY (`PromotionID`) REFERENCES `promotions` (`PromotionID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `RoomID` FOREIGN KEY (`RoomID`) REFERENCES `room` (`RoomID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `customerdata` (
  `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `EmailID` varchar(100) DEFAULT NULL,
  `Street_Address` varchar(200) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `State` varchar(45) DEFAULT NULL,
  `zip` int(11) DEFAULT NULL,
  `PhoneNumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`)
);

CREATE TABLE `promotions` (
  `PromotionID` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `StartDate` datetime DEFAULT NULL,
  `EndDate` datetime DEFAULT NULL,
  `DiscountPercent` float DEFAULT NULL,
  PRIMARY KEY (`PromotionID`)
);

CREATE TABLE `room` (
  `RoomID` int(11) NOT NULL,
  `RoomNumber` varchar(45) DEFAULT NULL,
  `RoomStatus` varchar(45) DEFAULT NULL,
  `BedSize` varchar(45) DEFAULT NULL,
  `AirConditionFlag` bit(1) DEFAULT NULL,
  `BalconyViewFlag` bit(1) DEFAULT NULL,
  `InternetAvailabilityFlag` bit(1) DEFAULT NULL,
  `RoomExtendedPrice` float DEFAULT NULL,
  `RoomTypeID` int(11) NOT NULL,
  PRIMARY KEY (`RoomID`),
  KEY `RoomTypeID_idx` (`RoomTypeID`),
  CONSTRAINT `RoomTypeID` FOREIGN KEY (`RoomTypeID`) REFERENCES `roomtype` (`RoomTypeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `roomtype` (
  `RoomTypeID` int(11) NOT NULL,
  `RoomType` varchar(45) NOT NULL,
  `BasePrice` float NOT NULL,
  PRIMARY KEY (`RoomTypeID`)
);

CREATE TABLE `totalbill` (
  `BillID` int(11) NOT NULL AUTO_INCREMENT,
  `BillDescription` varchar(45) DEFAULT NULL,
  `BillDate` datetime DEFAULT NULL,
  `PaymentMethod` varchar(45) DEFAULT NULL,
  `BillAmount` float DEFAULT NULL,
  PRIMARY KEY (`BillID`)
);

CREATE TABLE `bill` (
  `BillID` int(11) NOT NULL,
  `BookingID` int(11) NOT NULL,
  PRIMARY KEY (`BillID`,`BookingID`),
  KEY `BookingID_idx` (`BookingID`),
  CONSTRAINT `BillID` FOREIGN KEY (`BillID`) REFERENCES `totalbill` (`BillID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `BookingID` FOREIGN KEY (`BookingID`) REFERENCES `bookings` (`BookingID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `customerbills` (
  `CustomerID` int(11) NOT NULL,
  `BillID` int(11) NOT NULL,
  PRIMARY KEY (`CustomerID`,`BillID`),
  KEY `BillID_idx` (`BillID`),
  CONSTRAINT `idBill` FOREIGN KEY (`BillID`) REFERENCES `totalbill` (`BillID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idCustomer` FOREIGN KEY (`CustomerID`) REFERENCES `customerdata` (`CustomerID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `customerbookings` (
  `CustomerID` int(11) NOT NULL,
  `BookingID` int(11) NOT NULL,
  PRIMARY KEY (`CustomerID`,`BookingID`),
  KEY `BookingID_idx` (`BookingID`),
  CONSTRAINT `idBookingFK` FOREIGN KEY (`BookingID`) REFERENCES `bookings` (`BookingID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idCustomerFK` FOREIGN KEY (`CustomerID`) REFERENCES `customerdata` (`CustomerID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO `arroju_mounica_db`.`roomtype` (`RoomTypeID`, `RoomType`, `BasePrice`) VALUES ('1', 'Single', '99');
INSERT INTO `arroju_mounica_db`.`roomtype` (`RoomTypeID`, `RoomType`, `BasePrice`) VALUES ('2', 'Standard', '155');
INSERT INTO `arroju_mounica_db`.`roomtype` (`RoomTypeID`, `RoomType`, `BasePrice`) VALUES ('3', 'Deluxe', '180');
INSERT INTO `arroju_mounica_db`.`roomtype` (`RoomTypeID`, `RoomType`, `BasePrice`) VALUES ('4', 'Family', '190');
INSERT INTO `arroju_mounica_db`.`roomtype` (`RoomTypeID`, `RoomType`, `BasePrice`) VALUES ('5', 'ApartmentSuit', '195');
INSERT INTO `arroju_mounica_db`.`roomtype` (`RoomTypeID`, `RoomType`, `BasePrice`) VALUES ('6', 'PresidentialSuite', '225');

INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('1', '101', 'Available', 'Queen', 1, 0, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('2', '102', 'Available', 'Queen', 1, 0, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('3', '103', 'Available', 'Queen', 1, 0, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('4', '104', 'Available', 'Queen', 1, 0, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('5', '105', 'Available', 'Queen', 1, 0, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('6', '106', 'Available', 'King', 1, 1, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('7', '107', 'Available', 'King', 1, 1, 1, '180', '3');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('8', '108', 'Available', 'King', 1, 1, 1, '180', '3');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('9', '109', 'Available', 'King', 1, 1, 1, '180', '3');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('10', '110', 'Available', 'King', 1, 1, 1, '190', '4');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('11', '201', 'Available', 'Single', 0, 1, 1, '99', '1');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('12', '202', 'Available', 'Single', 0, 1, 1, '99', '1');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('13', '203', 'Available', 'Single', 0, 1, 1, '99', '1');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('14', '204', 'Available', 'Single', 1, 1, 1, '190', '4');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('15', '205', 'Available', 'Single', 1, 1, 1, '190', '4');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('16', '206', 'Available', 'Single', 1, 1, 1, '195', '5');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('17', '207', 'Available', 'Queen', 1, 0, 1, '195', '5');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('18', '208', 'Available', 'Queen', 1, 0, 1, '190', '4');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('19', '209', 'Available', 'Queen', 1, 0, 1, '190', '4');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('20', '210', 'Available', 'Queen', 1, 0, 1, '190', '4');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('21', '301', 'Available', 'Single', 1, 1, 1, '155', '2');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('22', '302', 'Available', 'Single', 1, 1, 1, '180', '3');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('23', '303', 'Available', 'Single', 1, 1, 1, '180', '3');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('24', '304', 'Available', 'Single', 1, 1, 1, '180', '3');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('25', '305', 'Available', 'Single', 1, 1, 1, '195', '5');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('26', '306', 'Available', 'King', 1, 1, 1, '195', '5');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('27', '307', 'Available', 'King', 1, 1, 1, '225', '6');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('28', '308', 'Available', 'King', 1, 1, 1, '225', '6');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('29', '309', 'Available', 'King', 1, 1, 1, '195', '5');
INSERT INTO `arroju_mounica_db`.`room` (`RoomID`, `RoomNumber`, `RoomStatus`, `BedSize`, `AirConditionFlag`, `BalconyViewFlag`, `InternetAvailabilityFlag`, `RoomExtendedPrice`, `RoomTypeID`) VALUES ('30', '310', 'Available', 'King', 1, 1, 1, '195', '5');

















