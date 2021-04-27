use `techmarket`;

DROP TABLE IF EXISTS `DISCOUNT_CODE`;
CREATE TABLE `DISCOUNT_CODE` (
  `DISCOUNT_CODE` CHAR(1) NOT NULL,
  `RATE` DECIMAL(4, 2)
,
  PRIMARY KEY (`DISCOUNT_CODE`)
);

DROP TABLE IF EXISTS `MANUFACTURER`;
CREATE TABLE `MANUFACTURER` (
  `MANUFACTURER_ID` INTEGER NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(30),
  `ADDRESSLINE1` VARCHAR(30),
  `ADDRESSLINE2` VARCHAR(30),
  `CITY` VARCHAR(25),
  `STATE` CHAR(2),
  `ZIP` CHAR(10),
  `PHONE` VARCHAR(12),
  `FAX` VARCHAR(12),
  `EMAIL` VARCHAR(40),
  `REP` VARCHAR(30)
,
  PRIMARY KEY (`MANUFACTURER_ID`)
);

DROP TABLE IF EXISTS `MICRO_MARKET`;
CREATE TABLE `MICRO_MARKET` (
  `ZIP_CODE` VARCHAR(10) NOT NULL,
  `RADIUS` DOUBLE,
  `AREA_LENGTH` DOUBLE,
  `AREA_WIDTH` DOUBLE
,
  PRIMARY KEY (`ZIP_CODE`)
);

DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `EMAIL` VARCHAR(40) NOT NULL,
  `PASSWORD` VARCHAR(240) NOT NULL,
  PRIMARY KEY (`EMAIL`),
  `IS_ADMIN` BOOLEAN NOT NULL DEFAULT '0'
);
DROP TABLE IF EXISTS `CUSTOMER`;
CREATE TABLE `CUSTOMER` (
  `CUSTOMER_ID` INTEGER NOT NULL AUTO_INCREMENT,
  `DISCOUNT_CODE` CHAR(1) NOT NULL,
  `ZIP` VARCHAR(10) NOT NULL,
  `NAME` VARCHAR(30),
  `ADDRESSLINE1` VARCHAR(30),
  `ADDRESSLINE2` VARCHAR(30),
  `CITY` VARCHAR(25),
  `STATE` CHAR(2),
  `PHONE` CHAR(12),
  `FAX` CHAR(12),
  `CREDIT_LIMIT` INTEGER,
  FOREIGN KEY `fk_cu_dc` (`DISCOUNT_CODE`) REFERENCES `DISCOUNT_CODE` (`DISCOUNT_CODE`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY `fk_cu_mm` (`ZIP`) REFERENCES `MICRO_MARKET` (`ZIP_CODE`) ON DELETE CASCADE ON UPDATE CASCADE
,
  FOREIGN KEY `fk_cu_u` (`USER_EMAIL`) REFERENCES `USER` (`EMAIL`) ON DELETE CASCADE ON UPDATE CASCADE,
  `USER_EMAIL` VARCHAR(40),
  PRIMARY KEY (`CUSTOMER_ID`)
);

DROP TABLE IF EXISTS `PRODUCT_CODE`;
CREATE TABLE `PRODUCT_CODE` (
  `PROD_CODE` CHAR(2) NOT NULL,
  `DISCOUNT_CODE` CHAR(1) NOT NULL,
  `DESCRIPTION` VARCHAR(10),
  FOREIGN KEY `fk_pc_dc` (`DISCOUNT_CODE`) REFERENCES `DISCOUNT_CODE` (`DISCOUNT_CODE`) ON DELETE CASCADE ON UPDATE CASCADE
,
  PRIMARY KEY (`PROD_CODE`)
);

DROP TABLE IF EXISTS `PRODUCT`;
CREATE TABLE `PRODUCT` (
  `PRODUCT_ID` INTEGER NOT NULL AUTO_INCREMENT,
  `MANUFACTURER_ID` INTEGER NOT NULL,
  `PRODUCT_CODE` CHAR(2) NOT NULL,
  `PURCHASE_COST` DECIMAL(12, 2),
  `QUANTITY_ON_HAND` INTEGER,
  `MARKUP` DECIMAL(4, 2),
  `AVAILABLE` BOOLEAN       ,
  `DESCRIPTION` VARCHAR(50),
  FOREIGN KEY `fk_p_pc` (`PRODUCT_CODE`) REFERENCES `PRODUCT_CODE` (`PROD_CODE`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY `fk_p_m` (`MANUFACTURER_ID`) REFERENCES `MANUFACTURER` (`MANUFACTURER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
,
  PRIMARY KEY (`PRODUCT_ID`),
  `IMAGE` VARCHAR(100)
);

DROP TABLE IF EXISTS `FREIGHT_COMPANY`;
CREATE TABLE `FREIGHT_COMPANY` (
  `COMPANY_ID` INTEGER NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(100),
  PRIMARY KEY (`COMPANY_ID`)
);
DROP TABLE IF EXISTS `ORDER_STATUS`;
CREATE TABLE `ORDER_STATUS` (
  `STATUS_CODE` INTEGER NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`STATUS_CODE`)
);
DROP TABLE IF EXISTS `PURCHASE_ORDER`;
CREATE TABLE `PURCHASE_ORDER` (
  `ORDER_NUM` INTEGER NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` INTEGER NOT NULL,
  `SHIPPING_COST` DECIMAL(12, 2),
  `SALES_DATE` DATE,
  `SHIPPING_DATE` DATE,
  `FREIGHT_COMPANY` INTEGER,
  FOREIGN KEY `fk_po_cu` (`CUSTOMER_ID`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  `STATUS` INTEGER NOT NULL,
  FOREIGN KEY `fk_po_os` (`STATUS`) REFERENCES `ORDER_STATUS` (`STATUS_CODE`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY `fk_po_fc` (`FREIGHT_COMPANY`) REFERENCES `FREIGHT_COMPANY` (`COMPANY_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (`ORDER_NUM`)
);

DROP TABLE IF EXISTS `ORDER_PRODUCT`;
CREATE TABLE `ORDER_PRODUCT` (
  `ORDER_NUM` INTEGER,
  `PRODUCT_ID` INTEGER,
  `QUANTITY` SMALLINT NOT NULL,
  PRIMARY KEY (`ORDER_NUM`,`PRODUCT_ID`),
  FOREIGN KEY `fk_op_p` (`PRODUCT_ID`) REFERENCES `PRODUCT` (`PRODUCT_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY `fk_op_po` (`ORDER_NUM`) REFERENCES `PURCHASE_ORDER` (`ORDER_NUM`) ON DELETE CASCADE ON UPDATE CASCADE
);
