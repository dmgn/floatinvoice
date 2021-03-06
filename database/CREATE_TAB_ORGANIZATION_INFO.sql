CREATE TABLE `ORGANIZATION_INFO` (
  `COMPANY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACRONYM` varchar(10) NOT NULL,
  `COMPANY_NAME` varchar(100) NOT NULL,
  `STREET` varchar(45) NOT NULL,
  `CITY` varchar(45) NOT NULL,
  `STATE` varchar(45) NOT NULL,
  `ZIP_CODE` varchar(45) NOT NULL,
  `COUNTRY` varchar(45) NOT NULL,
  `PHONE_NO` int(11) NOT NULL,
  `INSERT_DT` datetime NOT NULL,
  `UPDATE_DT` datetime NOT NULL,
  `UPDATE_BY` varchar(45) NOT NULL,
  `CREATED_BY` varchar(45) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`)
);