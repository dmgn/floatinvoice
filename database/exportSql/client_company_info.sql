CREATE TABLE `CLIENT_COMPANY_INFO` (
  `COMPANY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `BANK_ACCOUNT_NO` int(15) NOT NULL,
  `BANK_IFSC_CODE` int(10) NOT NULL,
  `BANK_NAME` varchar(45) NOT NULL,
  `BRANCH_NAME` varchar(45) NOT NULL,
  `DIRECTOR_FNAME` varchar(45) NOT NULL,
  `DIRECTOR_LNAME` varchar(45) NOT NULL,
  `PAN_CARD_NO` varchar(45) NOT NULL,
  `AADHAR_CARD_ID` varchar(45) NOT NULL,
  `COMPANY_NAME` varchar(100) NOT NULL,
  `STREET` varchar(45) NOT NULL,
  `CITY` varchar(45) NOT NULL,
  `STATE` varchar(45) NOT NULL,
  `ZIP_CODE` varchar(45) NOT NULL,
  `COUNTRY` varchar(45) NOT NULL,
  `PHONE_NO` int(11) NOT NULL,
  `INSERT_DT` datetime NOT NULL,
  `ACRONYM` varchar(10) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`),
  KEY `USER_ID_FK` (`USER_ID`),
  CONSTRAINT `USER_ID_FK` FOREIGN KEY (`USER_ID`) REFERENCES `CLIENT_LOGIN_INFO` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) 