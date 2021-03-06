USE techmarket;

INSERT INTO DISCOUNT_CODE (DISCOUNT_CODE, RATE) VALUES ('N', 0.00);
INSERT INTO DISCOUNT_CODE (DISCOUNT_CODE, RATE) VALUES ('H', 5.00);
INSERT INTO DISCOUNT_CODE (DISCOUNT_CODE, RATE) VALUES ('M', 11.00);

INSERT INTO PRODUCT_CODE (PROD_CODE, DISCOUNT_CODE, DESCRIPTION) VALUES ('SW', 'M', 'Software');
INSERT INTO PRODUCT_CODE (PROD_CODE, DISCOUNT_CODE, DESCRIPTION) VALUES ('HW', 'H', 'Hardware');

INSERT INTO MANUFACTURER (MANUFACTURER_ID, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, ZIP, PHONE, FAX, EMAIL, REP) VALUES (19985678, 'HTECHMARKETy End Searching', '5 81st Street', 'Suite 100', 'Mountain View', 'CA', '94043', '650-555-0102', '408-555-0103', 'hTECHMARKETysearching@example.com', 'John Snow');
INSERT INTO MANUFACTURER (MANUFACTURER_ID, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, ZIP, PHONE, FAX, EMAIL, REP) VALUES (19986982, 'Smith Bird Watching', '4000 Finch Circle', 'Building 14', 'Santa Clara', 'CA', '95051', '650-555-0111', '408-555-0112', 'www.sbw@example.com', 'Brian Washington');

INSERT INTO PRODUCT (PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION, IMAGE) VALUES (980001, 19985678, 'SW', 1095.00, 800000, 8.25, TRUE, 'Identity Server', 'default-software.png');
INSERT INTO PRODUCT (PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION, IMAGE) VALUES (980005, 19986982, 'SW', 11500.99, 500, 55.25, TRUE, 'Accounting TECHMARKETlication', 'default-software.png');
INSERT INTO PRODUCT (PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION, IMAGE) VALUES (958889, 19986982, 'HW', 595.95, 0, 1.25, FALSE, '686 7Ghz Computer', 'computer.png');

INSERT INTO MICRO_MARKET (ZIP_CODE, RADIUS, AREA_LENGTH, AREA_WIDTH) VALUES ('95117', 755.778, 547.967, 468.858);

INSERT INTO USER (EMAIL, PASSWORD, IS_ADMIN) VALUES ('admin@techmarket.com', 'admin', TRUE);
INSERT INTO USER (EMAIL, PASSWORD) VALUES ('jumboeagle@example.com', 'user1');
INSERT INTO USER (EMAIL, PASSWORD) VALUES ('www.new.example.com', 'user2');

INSERT INTO CUSTOMER (CUSTOMER_ID, DISCOUNT_CODE, ZIP, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, PHONE, FAX, CREDIT_LIMIT, USER_EMAIL) VALUES (1, 'N', '95117', 'Jumbo Eagle Corp', '111 E. Las Olivas Blvd', 'Suite 51', 'Fort Lauderdale', 'FL', '305-555-0188', '305-555-0189', 100000, 'jumboeagle@example.com');
INSERT INTO CUSTOMER (CUSTOMER_ID, DISCOUNT_CODE, ZIP, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, PHONE, FAX, CREDIT_LIMIT, USER_EMAIL) VALUES (2, 'M', '95117', 'New Enterprises', '9754 Main Street', 'P.O. Box 567', 'Miami', 'FL', '305-555-0148', '305-555-0149', 50000, 'www.new.example.com');

INSERT INTO FREIGHT_COMPANY (COMPANY_ID, NAME) VALUES (1, "Poney Express");
INSERT INTO FREIGHT_COMPANY (COMPANY_ID, NAME) VALUES (2, "USPS Supply Chain Solutions");
INSERT INTO FREIGHT_COMPANY (COMPANY_ID, NAME) VALUES (3, "XPO Logistics");
INSERT INTO FREIGHT_COMPANY (COMPANY_ID, NAME) VALUES (4, "Penske Logistics");
INSERT INTO FREIGHT_COMPANY (COMPANY_ID, NAME) VALUES (5, "J.B. Hunt Transport Services");

INSERT INTO ORDER_STATUS (STATUS_CODE, DESCRIPTION) VALUES (1, "Purchased");
INSERT INTO ORDER_STATUS (STATUS_CODE, DESCRIPTION) VALUES (2, "Prepared");
INSERT INTO ORDER_STATUS (STATUS_CODE, DESCRIPTION) VALUES (3, "Sent");
INSERT INTO ORDER_STATUS (STATUS_CODE, DESCRIPTION) VALUES (4, "Received");
INSERT INTO ORDER_STATUS (STATUS_CODE, DESCRIPTION) VALUES (5, "Cancelled");

INSERT INTO PURCHASE_ORDER (ORDER_NUM, CUSTOMER_ID, SHIPPING_COST, SALES_DATE, SHIPPING_DATE, FREIGHT_COMPANY, STATUS) VALUES (10398001, 1, 449.00, '2011-05-24', '2011-05-24', 1, 4);

INSERT INTO ORDER_PRODUCT (ORDER_NUM, PRODUCT_ID, QUANTITY) VALUES (10398001, 980001, 10);
INSERT INTO ORDER_PRODUCT (ORDER_NUM, PRODUCT_ID, QUANTITY) VALUES (10398001, 958889, 6);

INSERT INTO PRODUCT (IMAGE, MANUFACTURER_ID, PRODUCT_CODE, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE)
VALUES ('inteli5.png', '19985678', 'HW', 'CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SEXTA GEN', 238.0, 100, 8.7, 1),
('msiz17a.png', '19985678', 'HW', 'PLACA MSI Z17A GAMING PRO DD4', 230.0, 100, 8.7, 1),
('8gbdd4.png', '19986982', 'HW', 'MEMORIA 8GB DD4 BUS 2133', 60.0, 100, 8.7, 1),
('seagate1tb.png', '19985678', 'HW', 'DISCO D 1TB SATA III SEAGATE 64 MB 72RPM', 52.0, 100, 8.7, 1),
('dvdasus.png', '19985678', 'HW', 'GRABADOR DVD 24X ASUS NEGRO', 17.0, 100, 8.7, 1),
('gtx9704gb.png', '19985678', 'HW', 'VIDEO MSI NVIDIA GTX970 4GB GDDR5 256BITS', 430.0, 100, 8.7, 1),
('gtx9804gb.png', '19986982', 'HW', 'VIDEO MSI NVIDIA GTX980 4GB GDDR5 256BITS', 504.0, 100, 8.7, 1),
('corsair500r.png', '19985678', 'HW', 'CASE CORSAIR 500R MT BLACK', 125.0, 100, 8.7, 1),
('corsaircx750w.png', '19985678', 'HW', 'FUENTE 750W CORSAIR CX750W 80+BRONZ', 99.0, 100, 8.7, 1),
('logitechmk120.png', '19985678', 'HW', 'TECLADO Y MOUSE LOGITECH MK120 USB SP', 10.0, 100, 8.7, 1),
('forza8avr.png', '19985678', 'HW', 'ESTABILIZADOR FORZA 8 AVR 220V FVR-1202', 13.0, 100, 8.7, 1),
('asusipsmx270h.png', '19986982', 'HW', 'MONITOR 27 LED ASUS IPS MX279H FH 5M FULL HD', 320.0, 100, 8.7, 1),
('adatasp550.png', '19985678', 'HW', 'DISCO SOLIDO SSD 240GB A-DATA SP550 2.5 SATA 6GB', 96.0, 100, 8.7, 1);