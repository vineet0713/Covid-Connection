CREATE TABLE buyers (
  buyer_id int NOT NULL AUTO_INCREMENT,
  buyer_name varchar(20) DEFAULT NULL,
  PRIMARY KEY (buyer_id),
  UNIQUE KEY buyer_name (buyer_name)
);

CREATE TABLE suppliers (
  supplier_id int NOT NULL AUTO_INCREMENT,
  supplier_name varchar(20) DEFAULT NULL,
  PRIMARY KEY (supplier_id),
  UNIQUE KEY supplier_name (supplier_name)
);

CREATE TABLE subscription_category (
  category_id int NOT NULL AUTO_INCREMENT,
  name varchar(20) DEFAULT NULL,
  PRIMARY KEY (category_id),
  UNIQUE KEY name (name)
);


CREATE TABLE buyer_requests (
  request_id int NOT NULL AUTO_INCREMENT,
  request_category varchar(50) DEFAULT NULL,
  quantity int DEFAULT NULL,
  buyer_id varchar(20) DEFAULT NULL,
  location varchar(20) DEFAULT NULL,
  PRIMARY KEY (request_id)
);


CREATE TABLE supplier_responses (
  request_id int DEFAULT NULL,
  supplier_id int DEFAULT NULL,
  supplier_response varchar(10) DEFAULT NULL,
  supplier_quote decimal(7,2) DEFAULT NULL,
  FOREIGN KEY (request_id) REFERENCES buyer_requests (request_id),
   FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);

CREATE TABLE supplier_subcriptions(
  supplier_id int NOT NULL,
  subscription_category varchar(20) NOT NULL,
  PRIMARY KEY (supplier_id,subscription_category),
 FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);



