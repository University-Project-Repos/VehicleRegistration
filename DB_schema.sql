/*
 * Schema for WoF app database
 * Design has emphasis on user, vehicle and history tables based on acceptance criteria
 * Other tables are not implemented and follow the initial UML conceptual class diagram
 * Author: Adam Ross
 */
DROP TABLE IF EXISTS inspection_vehicle;
DROP TABLE IF EXISTS inspection;
DROP TABLE IF EXISTS inspector;
DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS vehicles_owners;
DROP TABLE IF EXISTS owner;
DROP TABLE IF EXISTS vehicle;

CREATE TABLE IF NOT EXISTS owner (
  forename text NOT NULL,
  surname text NOT NULL,
  email text NOT NULL PRIMARY KEY UNIQUE,
  password text NOT NULL,
  address_one text DEFAULT NULL,
  address_two text DEFAULT NULL,
  phone text DEFAULT NULL,
  creation_date TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
  last_login TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
  token text DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS vehicle (
  plate text NOT NULL PRIMARY KEY UNIQUE,
  make text NOT NULL,
  model text NOT NULL,
  manufacture_date TIMESTAMP NOT NULL,
  address_one text NOT NULL,
  address_two text NOT NULL,
  vehicle_type text NOT NULL,
  fuel_type text DEFAULT NULL,
  creation_date TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
  CHECK(length(plate) <= 6), /* plates can be no longer than 6 chars */
  CHECK(vehicle_type IN ("MA", "MB", "MC", "T", "O")),
  CHECK(fuel_type IN ("DIESEL", "PETROL", "ELECTRIC", "GAS", "OTHER", "NA"))
);

CREATE TABLE IF NOT EXISTS history (
  vin text(17) NOT NULL PRIMARY KEY UNIQUE,
  odometer_reading text DEFAULT NULL,
  nz_registration_date TIMESTAMP NOT NULL,
  wof_expiry TIMESTAMP NOT NULL,
  wof_status text NOT NULL,
  CHECK(length(vin) == 17), /* VIN is required to be 17 chars long */
  CHECK(wof_status IN ("PASSED", "FAILED", "EXPIRED"))
);

CREATE TABLE IF NOT EXISTS vehicles_owners (
  owner_id text NOT NULL,
  vehicle_id text NOT NULL,
  vehicle_vin text(17) DEFAULT NULL,
  PRIMARY KEY(owner_id, vehicle_id),
  FOREIGN KEY(owner_id) REFERENCES owner(email),
  FOREIGN KEY(vehicle_id) REFERENCES vehicle(plate),
  FOREIGN KEY(vehicle_vin) REFERENCES history(vin)
  CHECK(length(vehicle_id) <= 6),
  CHECK(length(vehicle_vin) == 17)
);

CREATE TABLE IF NOT EXISTS inspector (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  forename text NOT NULL,
  surname text NOT NULL
);

CREATE TABLE IF NOT EXISTS inspection (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  score text NOT NULL,
  inspection_date TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
  CHECK(length(score) <= 3) /* inspection score is a percentage, 0-100 */
);

CREATE TABLE IF NOT EXISTS inspection_vehicle (
  inspection_id INTEGER PRIMARY KEY,
  vehicle_id text NOT NULL,
  FOREIGN KEY(inspection_id) REFERENCES inspection(id),
  FOREIGN KEY(vehicle_id) REFERENCES vehicle(plate),
  CHECK(length(vehicle_id) <= 6)
);