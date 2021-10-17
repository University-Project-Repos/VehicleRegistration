Feature: Vehicle Owner Account Management

  Scenario Outline: Successfully register a new vehicle owner (email) to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "<email>" is not registered to any existing account
    When I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner must be in the database
    And a message "<message>" must be displayed on the screen
    Examples:
      | email          | forename | surname | password | message                                               |
      | joe@bloggs.com | Joe      | Bloggs  | pw       | joe@bloggs.com successfully registered to the WoF app |

  Scenario Outline: Unsuccessfully register an existing vehicle owner (email) to a WoF app account
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner with forename "<forename1>", surname "<surname1>" and password "<password>" must not be in the database with email "<email>"
    And the owner with forename "<forename2>", surname "<surname2>", and password "<password1>" must be in the database with email "<email>"
    And a message "<message>" must be displayed on the screen
    Examples:
      | email        | forename | surname | password | forename1 | surname1 | forename2 | surname2 | password1 | message                                          |
      | one@test.com | Wrong    | Owner   | two      | WRONG     | OWNER    | TEST      | DUMMY    | one       | one@test.com is already registered to an account |

  Scenario Outline: Unsuccessfully register a new vehicle owner with an empty forename field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "<email>" is not registered to any existing account
    When I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner with email "<email>" must not be in the database
    Examples:
      | email         | forename | surname | password |
      | zero@test.com |          | Dummy   | zero     |

  Scenario Outline: Unsuccessfully register a new vehicle owner with an empty surname field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "<email>" is not registered to any existing account
    When I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner with email "<email>" must not be in the database
    Examples:
      | email         | forename | surname | password |
      | zero@test.com | Test     |         | zero     |

  Scenario Outline: Unsuccessfully register a new vehicle owner with an empty email field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "<email>" is not registered to any existing account
    When I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner with email "<email1>" must not be in the database
    Examples:
      | email | forename | surname | password | email1        |
      |       | Test     | Dummy   | zero     | zero@test.com |

  Scenario Outline: Unsuccessfully register a new vehicle owner with an empty password field to a WoF app account
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "<email>" is not registered to any existing account
    When I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner with email "<email>" must not be in the database
    Examples:
      | email         | forename | surname | password |
      | zero@test.com | Test     | Dummy   |          |

  Scenario Outline: A registered vehicle owner can login to their account
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    Then I must be logged in to my account
    Examples:
      | email        | password |
      | one@test.com | one      |

  Scenario Outline: A vehicle owner who is not registered cannot login
    Given I am connected to the WoF app database
    And I am not yet registered to the WoF app - the email "<email>" is not registered to any existing account
    When I login with email "<email>" and password "<password>"
    Then I must not be logged in to my account
    Examples:
      | email          | password |
      | three@test.com | three    |

  Scenario Outline: A registered vehicle owner can view their account information
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    And I view my account information
    Then a message containing "<forename>", "<surname>" and null for all other fields must be displayed on the screen
    Examples:
      | email        | password | forename | surname |
      | one@test.com | one      | TEST     | DUMMY   |

  Scenario Outline: A registered vehicle owner can not update/edit their email information
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    And the vehicle owner account "<field>" field is currently "<val>"
    And I update the vehicle owner account "<field>" field to "<val1>"
    Then the vehicle owner account "<field>" field is currently "<val>"
    Examples:
      | email        | password | field | val          | val1               |
      | one@test.com | one      | email | one@test.com | different@test.com |

  Scenario Outline: A registered vehicle owner can update/edit their address information
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    And the vehicle owner account "<field>" field is currently "<val>"
    And the vehicle owner account "<field1>" field is currently "<val>"
    And I update the vehicle owner account "<field2>" field to "<val1>"
    And I update the vehicle owner account "<field3>" field to "<val2>"
    Then the vehicle owner account "<field>" field is currently "<val1>"
    And the vehicle owner account "<field1>" field is currently "<val2>"
    Examples:
      | email        | password | field       | val        | field1      | field2      | val1 | field3      | val2 |
      | one@test.com | one      | address_one | empty/null | address_two | address-one | NEW  | address-two | HOME |

  Scenario Outline: A registered vehicle owner can update/edit their phone information
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    And the vehicle owner account "<field>" field is currently "<val>"
    And I update the vehicle owner account "<field>" field to "<val1>"
    Then the vehicle owner account "<field>" field is currently "<val1>"
    Examples:
      | email        | password | field | val        | val1      |
      | one@test.com | one      | phone | empty/null | 012345678 |

  Scenario Outline: A registered vehicle owner can update/edit their password information
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    And the vehicle owner account "<field>" field is currently "<val>"
    And I update the vehicle owner account "<field>" field to "<val1>"
    Then the vehicle owner account "<field>" field is currently "<val1>"
    Examples:
      | email        | password | field    | val | val1 |
      | one@test.com | one      | password | one | two  |

  Scenario Outline: A registered vehicle owner can remove their account
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    When I login with email "<email>" and password "<password>"
    And I remove the registered vehicle owner account
    Then the owner with email "<email>" must not be in the database
    Examples:
      | email        | password |
      | two@test.com | two      |

  Scenario Outline: A vehicle owner can register a new account with an email from a removed account
    Given I am connected to the WoF app database
    And the email "<email>" is registered to an existing account
    And I am logged in to my account with email "<email>" and password "<password>"
    When I remove the registered vehicle owner account
    And the owner with email "<email>" must not be in the database
    And I register a WoF app account given forename "<forename>", surname "<surname>", email "<email>" and password "<password>"
    Then the owner must be in the database
    And a message "<message>" must be displayed on the screen
    Examples:
      | email         | password | forename | surname | message                                              |
      | four@test.com | four     | New      | Dummy   | four@test.com successfully registered to the WoF app |