CREATE TABLE customers (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(100) NOT NULL,
  name varchar(100) NOT NULL,
  age tinyint DEFAULT(18),
  PRIMARY KEY (id),
  UNIQUE (email)
);

CREATE TABLE accounts (
  id bigint NOT NULL AUTO_INCREMENT,
  balance double DEFAULT '0',
  currency enum('CHF','EUR','GBP','UAH','USD') NOT NULL,
  number varchar(45) NOT NULL,
  customer_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE TABLE employers (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  address varchar(300) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE customer_employer (
  customer_id bigint DEFAULT NULL,
  employer_id bigint DEFAULT NULL
);

INSERT INTO customers (email, name, age)
VALUES
  ('john.doe@example.com', 'John Doe', 32),
  ('ronaldo@example.com', 'Cristiano Ronaldo', 40),
  ('messi@example.com', 'Leo Messi', 37);

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
