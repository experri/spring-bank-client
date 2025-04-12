CREATE TABLE customers (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  name varchar(100) NOT NULL,
  age tinyint DEFAULT(18),
  phone varchar(20) DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE (email)
);

CREATE TABLE accounts (
  id bigint NOT NULL AUTO_INCREMENT,
  balance double DEFAULT '0',
  currency enum('CHF','EUR','GBP','UAH','USD') NOT NULL,
  number varchar(45) NOT NULL,
  customer_id bigint NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE TABLE employers (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  address varchar(300) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE users (
   id bigint NOT NULL AUTO_INCREMENT,
   email varchar(100) NOT NULL,
   password varchar(100) NOT NULL,
   username varchar(100) NOT NULL,
   created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   UNIQUE (email)
 );

CREATE TABLE customer_employer (
  customer_id bigint DEFAULT NULL,
  employer_id bigint DEFAULT NULL
);

INSERT INTO customers (email, name, age, password)
VALUES
  ('john.doe@example.com', 'John Doe', 32, 'password'),
  ('ronaldo@example.com', 'Cristiano Ronaldo', 40, 'password'),
  ('messi@example.com', 'Leo Messi', 37, 'password');

-- Insert sample accounts
INSERT INTO accounts (balance, currency, number, customer_id)
VALUES
  (12345.23, 'USD', 'ACC12345', 1),
  (40000000.00, 'EUR', 'ACC23456', 2),
  (500.00, 'UAH', 'ACC34567', 3),
  (35000.00, 'GBP', 'ACC45678', 1),
  (200.00, 'CHF', 'ACC56789', 2);

-- Insert sample employers
INSERT INTO employers (name, address)
VALUES
  ('Microsoft Corporation', '1 Microsoft Way, Redmond, WA 98052, US'),
  ('Apple Inc', 'One Apple Park Way; Cupertino, CA 95014, U.S.A.'),
  ('SpaceX', '1 Rocket Rd. Hawthorne, CA 90250. United States');

-- Insert sample customer-employer relationships
INSERT INTO customer_employer (customer_id, employer_id)
VALUES
  (1, 1), -- John Doe works for Tech Corp
  (2, 2), -- Jane Smith works for Finance Inc
  (3, 3); -- Alex Brown works for Health Plus
