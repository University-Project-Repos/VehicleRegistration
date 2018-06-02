Feature: Vehicle Registration Management

  Scenario: Successfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is not in the database
    When I register a vehicle with plate "NEW123", make "Toyota", model "Corolla", manufacture date "2010-01-01", address one "A", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW123" is now in the database
    And a message "NEW123 successfully registered to one@test.com" is displayed on the screen

  Scenario: Unsuccessfully register an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "four@test.com" and password "four"
    And the vehicle with plate "NEW123" is in the database
    When I register a vehicle with plate "NEW123", make "Mitsubishi", model "Gallant", manufacture date "1998-01-01", address one "Another", address two "Home", type "MA", and fuel type "diesel"
    And a message "NEW123 is already registered to an account" is displayed on the screen

  Scenario: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    When I register a vehicle with plate "", make "Toyota", model "Corolla", manufacture date "2010-01-01", address one "A", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "" is now not in the database

  Scenario: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    When I register a vehicle with plate "NEW456", make "", model "Corolla", manufacture date "2010-01-01", address one "A", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW456" is now not in the database

  Scenario: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    When I register a vehicle with plate "NEW456", make "Toyota", model "", manufacture date "2010-01-01", address one "A", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW456" is now not in the database

  Scenario: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    When I register a vehicle with plate "NEW456", make "Toyota", model "Corolla", manufacture date "", address one "A", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW456" is now not in the database

  Scenario: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    When I register a vehicle with plate "NEW456", make "Toyota", model "Corolla", manufacture date "2010-01-01", address one "", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW456" is now not in the database

  Scenario: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    When I register a vehicle with plate "NEW456", make "Toyota", model "Corolla", manufacture date "2010-01-01", address one "A", address two "", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW456" is now not in the database

  Scenario: Successfully list the information for an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    When I list the information for vehicle "NEW123"
    Then a message containing "NEW123", "TOYOTA", "COROLLA", "2010-01-01", "A", "HOME", "MA", "PETROL" is displayed on the screen

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "make" has existing value "TOYOTA" in the database
    When I update/edit vehicle "NEW123" field "make" to the new value "Honda"
    Then the vehicle "NEW123" field "make" will have value "HONDA" in the database

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "model" has existing value "COROLLA" in the database
    When I update/edit vehicle "NEW123" field "model" to the new value "Civic"
    Then the vehicle "NEW123" field "model" will have value "CIVIC" in the database

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "manufacture_date" has existing value "2010-01-01" in the database
    When I update/edit vehicle "NEW123" field "manufacture-date" to the new value "2000-12-12"
    Then the vehicle "NEW123" field "manufacture_date" will have value "2000-12-12" in the database

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "address_one" has existing value "A" in the database
    When I update/edit vehicle "NEW123" field "address-one" to the new value "Different"
    Then the vehicle "NEW123" field "address_one" will have value "DIFFERENT" in the database

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "address_two" has existing value "HOME" in the database
    When I update/edit vehicle "NEW123" field "address-two" to the new value "Place"
    Then the vehicle "NEW123" field "address_two" will have value "PLACE" in the database

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "vehicle_type" has existing value "MA" in the database
    When I update/edit vehicle "NEW123" field "type" to the new value "O"
    Then the vehicle "NEW123" field "vehicle_type" will have value "O" in the database

  Scenario: Successfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "fuel_type" has existing value "PETROL" in the database
    When I update/edit vehicle "NEW123" field "fuel" to the new value "other"
    Then the vehicle "NEW123" field "fuel_type" will have value "OTHER" in the database

  Scenario: Unsuccessfully edit/update an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    And the vehicle "NEW123" field "plate" has existing value "NEW123" in the database
    When I update/edit vehicle "NEW123" field "plate" to the new value "NEW789"
    Then the vehicle "NEW123" field "plate" will have value "NEW123" in the database

  Scenario: Successfully remove an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "one@test.com" and password "two"
    And the vehicle with plate "NEW123" is in the database
    When I remove the vehicle "NEW123"
    Then the vehicle with plate "NEW123" is now not in the database

  Scenario: Successfully register a vehicle to an owner's WoF app account wth a plate from a removed vehicle
    Given I am connected to the WoF app database
    And I am logged in to my account with email "four@test.com" and password "four"
    And the vehicle with plate "NEW123" is not in the database
    When I register a vehicle with plate "NEW123", make "Toyota", model "Corolla", manufacture date "2010-01-01", address one "A", address two "Home", type "MA", and fuel type "petrol"
    Then the vehicle with plate "NEW123" is now in the database
    And a message "NEW123 successfully registered to four@test.com" is displayed on the screen

  Scenario: Successfully remove existing vehicle registration(s) when the owner's WoF app account is removed
    Given I am connected to the WoF app database
    And I am logged in to my account with email "four@test.com" and password "four"
    And the vehicle with plate "NEW123" is in the database
    When I remove the registered vehicle owner account
    Then the owner with email "four@test.com" must not be in the database
    And the vehicle with plate "NEW123" is now not in the database