/*
 * Sample for WoF app database testing
 * Contains data for the implemented user story testing
 * Author: Adam Ross
 */
INSERT INTO owner (forename, surname, email, password) VALUES
("TEST", "DUMMY", "one@test.com", "one"),
("TEST", "DUMBER", "two@test.com", "two"),
("EXTRA", "TEST", "four@test.com", "four");

INSERT INTO vehicle (plate, make, model, manufacture_date, address_one, address_two, vehicle_type, fuel_type) VALUES
("ABC123", "FORD", "MODEL T", 0, "MICHIGAN", "USA", "MA", "OTHER"),
("XYZ987", "VW", "BEETLE", 0, "WOLFSBURG", "DEUTSCHLAND", "MA", "DIESEL"),
("HIJ345", "BMW", "MINI", 946684800000, "MÜNCHEN", "DEUTSCHLAND", "MA", "PETROL"),
("000000", "RELIANT", "ROBIN", 126144000000, "MERRIE", "ENGLAND", "O", "OTHER" ),
("111111", "LADA", "VAZ-2101", 31449600000, "SOVETSKIY", "SOYUZ", "MA", "PETROL");

INSERT INTO vehicles_owners (owner_id, vehicle_id, vehicle_vin) VALUES
("one@test.com", "ABC123", "12345678901234567"),
("one@test.com", "HIJ345", NULL),
("two@test.com", "XYZ987", "89012345678901234"),
("two@test.com", "000000", "00000000000000000"),
("one@test.com", "111111", "11111111111111111");

INSERT INTO history (vin, odometer_reading, nz_registration_date, wof_expiry, wof_status) VALUES
("12345678901234567", "999999", 0, 946598400, "FAILED"),
("89012345678901234", "999999", 0, 946598400, "EXPIRED"),
("00000000000000000", "000001", 694137600000, 1562457600000, "PASSED"),
("11111111111111111", "111111",  31536000000, 63072000000, "PASSED");