CREATE TABLE `SME_USER_INFO` (
  `USER_ID` int(11) NOT NULL,
  `BANK_ACCOUNT_NO` int(15) NOT NULL,
  `BANK_IFSC_CODE` varchar(15) NOT NULL,
  `BANK_NAME` varchar(45) NOT NULL,
  `BRANCH_NAME` varchar(45) DEFAULT NULL,
  `DIRECTOR_FNAME` varchar(45) NOT NULL,
  `DIRECTOR_LNAME` varchar(45) NOT NULL,
  `PAN_CARD_NO` varchar(45) NOT NULL,
  `AADHAR_CARD_ID` varchar(45) NOT NULL,
  KEY `FK_UID_idx` (`USER_ID`),
  CONSTRAINT `FK_UID` FOREIGN KEY (`USER_ID`) REFERENCES `CLIENT_LOGIN_INFO` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
)