Feature: Vehicle Owner Account Management

  Scenario: Successfully register a new vehicle owner (email) to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "joe@bloggs.com" is not registered to any existing account
    When I register a WoF app account given forename "Joe", surname "Bloggs", email "joe@bloggs.com" and password "pw"
    Then the owner must be in the database
    And a message "joe@bloggs.com successfully registered to the WoF app" must be displayed on the screen

  Scenario: Unsuccessfully register an existing vehicle owner (email) to a WoF app account
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I register a WoF app account given forename "Wrong", surname "Owner", email "one@test.com" and password "two"
    Then the owner with forename "WRONG", surname "OWNER" and password "two" must not be in the database with email "one@test.com"
    And the owner with forename "TEST", surname "DUMMY", and password "one" must be in the database with email "one@test.com"
    And a message "one@test.com is already registered to an account" must be displayed on the screen

  Scenario: Unsuccessfully register a new vehicle owner with an empty forename field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "zero@test.com" is not registered to any existing account
    When I register a WoF app account given forename "", surname "Dummy", email "zero@test.com" and password "zero"
    Then the owner with email "zero@test.com" must not be in the database

  Scenario: Unsuccessfully register a new vehicle owner with an empty surname field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "zero@test.com" is not registered to any existing account
    When I register a WoF app account given forename "Test", surname "", email "zero@test.com" and password "zero"
    Then the owner with email "zero@test.com" must not be in the database

  Scenario: Unsuccessfully register a new vehicle owner with an empty email field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "" is not registered to any existing account
    When I register a WoF app account given forename "Test", surname "Dummy", email "" and password "zero"
    Then the owner with email "zero@test.com" must not be in the database

  Scenario: Unsuccessfully register a new vehicle owner with an empty password field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "zero@test.com" is not registered to any existing account
    When I register a WoF app account given forename "Test", surname "Dummy", email "zero@test.com" and password ""
    Then the owner with email "zero@test.com" must not be in the database

  Scenario: A registered vehicle owner can login to their account
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I login with email "one@test.com" and password "one"
    Then I must be logged in to my account

  Scenario: A vehicle owner who is not registered cannot login
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "three@test.com" is not registered to any existing account
    When I login with email "three@test.com" and password "three"
    Then I must not be logged in to my account

  Scenario: A registered vehicle owner can view their account information
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I login with email "one@test.com" and password "one"
    And I view my account information
    Then a message containing "TEST", "DUMMY" and null for all other fields must be displayed on the screen

  Scenario: A registered vehicle owner can not update/edit their email information
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I login with email "one@test.com" and password "one"
    And the vehicle owner account "email" field is currently "one@test.com"
    And I update the vehicle owner account "email" field to "different@test.com"
    Then the vehicle owner account "email" field is currently "one@test.com"

  Scenario: A registered vehicle owner can update/edit their address information
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I login with email "one@test.com" and password "one"
    And the vehicle owner account "address_one" field is currently "empty/null"
    And the vehicle owner account "address_two" field is currently "empty/null"
    And I update the vehicle owner account "address-one" field to "NEW"
    And I update the vehicle owner account "address-two" field to "HOME"
    Then the vehicle owner account "address_one" field is currently "NEW"
    And the vehicle owner account "address_two" field is currently "HOME"

  Scenario: A registered vehicle owner can update/edit their phone information
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I login with email "one@test.com" and password "one"
    And the vehicle owner account "phone" field is currently "empty/null"
    And I update the vehicle owner account "phone" field to "012345678"
    Then the vehicle owner account "phone" field is currently "012345678"

  Scenario: A registered vehicle owner can update/edit their password information
    Given I am connected to the WoF app database
    And the email "one@test.com" is registered to an existing account
    When I login with email "one@test.com" and password "one"
    And the vehicle owner account "password" field is currently "one"
    And I update the vehicle owner account "password" field to "two"
    Then the vehicle owner account "password" field is currently "two"

  Scenario: A registered vehicle owner can remove their account
    Given I am connected to the WoF app database
    And the email "two@test.com" is registered to an existing account
    When I login with email "two@test.com" and password "two"
    And I remove the registered vehicle owner account
    Then the owner with email "two@test.com" must not be in the database

  Scenario: A vehicle owner can register a new account with an email from a removed account
    Given I am connected to the WoF app database
    And the email "four@test.com" is registered to an existing account
    And I am logged in to my account with email "four@test.com" and password "four"
    When I remove the registered vehicle owner account
    And the owner with email "four@test.com" must not be in the database
    And I register a WoF app account given forename "New", surname "Dummy", email "four@test.com" and password "four"
    Then the owner must be in the database
    And a message "four@test.com successfully registered to the WoF app" must be displayed on the screen