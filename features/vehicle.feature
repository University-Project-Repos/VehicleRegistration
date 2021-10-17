Feature: Vehicle Registration Management

  Scenario Outline: Successfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is not in the database
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now in the database
    And a message "<message>" is displayed on the screen
    Examples:
      | email        | password | plate  | make   | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType | message                                        |
      | one@test.com | one      | NEW123 | Toyota | Corolla | 2010-01-01      | A          | Home       | MA          | petrol   | NEW123 successfully registered to one@test.com |

  Scenario Outline: Unsuccessfully register an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    And a message "<message>" is displayed on the screen
    Examples:
      | email        | password | plate  | make | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType | message                                    |
      | one@test.com | one      | ABC123 | FORD | MODEL T | 1970-01-01      | MICHIGAN   | USA        | MA          | OTHER    | ABC123 is already registered to an account |

  Scenario Outline: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate | make   | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      |       | Toyota | Corolla | 2010-01-01      | A          | Home       | MA          | petrol   |

  Scenario Outline: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  | make | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      | NEW456 |      | Corolla | 2010-01-01      | A          | Home       | MA          | petrol   |

  Scenario Outline: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  | make   | model | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      | NEW456 | Toyota |       | 2010-01-01      | A          | Home       | MA          | petrol   |

  Scenario Outline: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  | make   | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      | NEW456 | Toyota | Corolla |                 | A          | Home       | MA          | petrol   |

  Scenario Outline: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  | make   | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      | NEW456 | Toyota | Corolla | 2010-01-01      |            | Home       | MA          | petrol   |

  Scenario Outline: Unsuccessfully register a new vehicle to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  | make   | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      | NEW456 | Toyota | Corolla | 2010-01-01      | A          |            | MA          | petrol   |

  Scenario Outline: Successfully list the information for an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    When I list the information for vehicle "<plate>"
    Then a message containing "<plate>", "<make>", "<model>", "<manufactureDate>", "<addressOne>", "<addressTwo>", "<vehicleType>", "<fuelType>" is displayed on the screen
    Examples:
      | email        | password | plate  | make | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType |
      | one@test.com | one      | ABC123 | FORD | MODEL T | 1970-01-01      | MICHIGAN   | USA        | MA          | OTHER    |

  Scenario Outline: Successfully edit/update an existing vehicle registration make field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val2>" in the database
    Examples:
      | email        | password | plate  | field | val  | val1  | val2  |
      | one@test.com | one      | ABC123 | make  | FORD | Honda | HONDA |

  Scenario Outline: Successfully edit/update an existing vehicle registration model field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val2>" in the database
    Examples:
      | email        | password | plate  | field | val     | val1  | val2  |
      | one@test.com | one      | ABC123 | model | MODEL T | Civic | CIVIC |

  Scenario Outline: Successfully edit/update an existing vehicle registration manufacture date field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field1>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val1>" in the database
    Examples:
      | email        | password | plate  | field            | val | field1           | val1       |
      | one@test.com | one      | ABC123 | manufacture_date | 0   | manufacture-date | 2000-12-12 |

  Scenario Outline: Successfully edit/update an existing vehicle registration address one field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field1>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val2>" in the database
    Examples:
      | email        | password | plate  | field       | val      | field1      | val1      | val2      |
      | one@test.com | one      | ABC123 | address_one | MICHIGAN | address-one | Different | DIFFERENT |

  Scenario Outline: Successfully edit/update an existing vehicle registration address two field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field1>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val2>" in the database
    Examples:
      | email        | password | plate  | field       | val | field1      | val1  | val2  |
      | one@test.com | one      | ABC123 | address_two | USA | address-two | Place | PLACE |

  Scenario Outline: Successfully edit/update an existing vehicle registration vehicle type field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field1>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val1>" in the database
    Examples:
      | email        | password | plate  | field        | val | field1 | val1 |
      | one@test.com | one      | ABC123 | vehicle_type | MA  | type   | O    |

  Scenario Outline: Successfully edit/update an existing vehicle registration fuel type field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field1>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val2>" in the database
    Examples:
      | email        | password | plate  | field     | val   | field1 | val1   | val2   |
      | one@test.com | one      | ABC123 | fuel_type | OTHER | fuel   | petrol | PETROL |

  Scenario Outline: Unsuccessfully edit/update an existing vehicle registration plate field to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    And the vehicle "<plate>" field "<field>" has existing value "<val>" in the database
    When I update/edit vehicle "<plate>" field "<field>" to the new value "<val1>"
    Then the vehicle "<plate>" field "<field>" will have value "<val>" in the database
    Examples:
      | email        | password | plate  | field | val    | val1   |
      | one@test.com | one      | ABC123 | plate | ABC123 | NEW789 |

  Scenario Outline: Successfully remove an existing vehicle registration to an owner's WoF app account
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    When I remove the vehicle "<plate>"
    Then the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  |
      | one@test.com | one      | ABC123 |

  Scenario Outline: Successfully register a vehicle to an owner's WoF app account wth a plate from a removed vehicle
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is not in the database
    When I register a vehicle with plate "<plate>", make "<make>", model "<model>", manufacture date "<manufactureDate>", address one "<addressOne>", address two "<addressTwo>", type "<vehicleType>", and fuel type "<fuelType>"
    Then the vehicle with plate "<plate>" is now in the database
    And a message "<message>" is displayed on the screen
    Examples:
      | email         | password | plate  | make   | model   | manufactureDate | addressOne | addressTwo | vehicleType | fuelType | message                                         |
      | four@test.com | four     | NEW123 | Toyota | Corolla | 2010-01-01      | A          | Home       | MA          | petrol   | NEW123 successfully registered to four@test.com |

  Scenario Outline: Successfully remove existing vehicle registration(s) when the owner's WoF app account is removed
    Given I am connected to the WoF app database
    And I am logged in to my account with email "<email>" and password "<password>"
    And the vehicle with plate "<plate>" is in the database
    When I remove the registered vehicle owner account
    Then the owner with email "<email>" must not be in the database
    And the vehicle with plate "<plate>" is now not in the database
    Examples:
      | email        | password | plate  |
      | one@test.com | one      | ABC123 |