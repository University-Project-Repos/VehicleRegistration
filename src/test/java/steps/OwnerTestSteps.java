package steps;

import WoF.controller.cli.UserCLITest;
import WoF.model.Owner;
import WoF.model.Session;
import WoF.service.DatabaseTest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Acceptance testing for WoF app owner registration
 * @author Adam Ross
 */
public class OwnerTestSteps {

    private final Session wof = Session.getWoF();
    private final DatabaseTest db = new DatabaseTest();
    private final UserCLITest userTest = new UserCLITest();

    /**
     * Check if there is a current database connection
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^I am connected to the WoF app database$")
    public void iAmConnectedToTheWoFAppDatabase() throws Throwable {
        db.testDatabaseConnection();
    }

    /**
     * Checks if an owner email is not registered to the WoF app database
     * @param email thr owner email
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^I am not yet registered to the WoF app - the email \"([^\"]*)\" is not registered to any existing account$")
    public void iAmNotYetRegisteredToTheWoFAppTheEmailIsNotRegisteredToAnyExistingAccount(String email) throws Throwable {
        wof.setUser(new Owner("temp", "temp", email, "temp"));
        db.emailNotExistsInDatabase();
    }

    /**
     * Registers a new owner to the WoF app and database
     * @param forename owner forename
     * @param surname owner surname
     * @param email owner email
     * @param password owner password
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I register a WoF app account given forename \"([^\"]*)\", surname \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iRegisterAWoFAppAccountGivenForenameSurnameEmailAndPassword(String forename, String surname,
                                                                            String email, String password) throws Throwable {
        userTest.registerUser(forename, surname, email, password);
    }

    /**
     * Checks that the newly registered owner is in the database
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the owner must be in the database$")
    public void theOwnerMustBeInTheDatabase() throws Throwable {
        db.ownerIsInDatabase();
    }

    @Given("^I am logged in to my account with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iAmLoggedInToMyAccountWithEmailAndPassword(String email, String password) throws Throwable {
        userTest.loginUser(email, password);
        db.ownerIsLoggedIn();
    }

    /**
     * Checks that a message is displayed on the screen
     * @param message the message being checked
     */
    @Then("^a message \"([^\"]*)\" must be displayed on the screen$")
    public void aMessageMustBeDisplayedOnTheScreen(String message) {
        userTest.testMessage(message);
    }

    /**
     * Checks if an owner email is already registered to the WoF app database
     * @param email thr owner email
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^the email \"([^\"]*)\" is registered to an existing account$")
    public void theEmailIsRegisteredToAnExistingAccount(String email) throws Throwable {
        wof.setUser(new Owner("temp", "temp", email, "temp"));
        db.emailExistsInDatabase();
    }

    /**
     * Tests if an owner credentials don't match that of an already existing registered account
     * @param forename the forename being tested
     * @param surname the surname being tested
     * @param password the password being tested
     * @param email the email being tested
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the owner with forename \"([^\"]*)\", surname \"([^\"]*)\" and password \"([^\"]*)\" must not be in the database with email \"([^\"]*)\"$")
    public void theOwnerWithForenameSurnameAndPasswordMustNotBeInTheDatabaseWithEmail(String forename, String surname,
                                                                                      String password, String email) throws Throwable {
        userTest.loginUser(email, "one");
        wof.setUser(new Owner(forename, surname, email, password));
        db.emailExistsInDatabase();
        db.ownerIsNotInDatabase();
    }

    /**
     * Tests if an owner credentials match that of an already existing registered account
     * @param forename the forename being tested
     * @param surname the surname being tested
     * @param password the password being tested
     * @param email the email being tested
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the owner with forename \"([^\"]*)\", surname \"([^\"]*)\", and password \"([^\"]*)\" must be in the database with email \"([^\"]*)\"$")
    public void theOwnerWithForenameSurnameAndPasswordMustBeInTheDatabaseWithEmail(String forename, String surname,
                                                                                   String password, String email) throws Throwable {
        wof.setUser(new Owner(forename, surname, email, password));
        db.emailExistsInDatabase();
        db.ownerIsInDatabase();
    }

    /**
     * Tests if an email is not entered to the database
     * @param email the email being tested
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the owner with email \"([^\"]*)\" must not be in the database$")
    public void theOwnerWithEmailMustNotBeInTheDatabase(String email) throws Throwable {
        userTest.ownerNotRegistered(email);
    }

    /**
     * Checks if a given database field in the owner table has a given value
     * @param field the database table field name
     * @param val the value for the field
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^the vehicle owner account \"([^\"]*)\" field is currently \"([^\"]*)\"$")
    public void theVehicleOwnerAccountFieldIsCurrently(String field, String val) throws Throwable {
        userTest.checkOwnerField(field, val);
    }

    /**
     * Updates a database field in the owner table with a new value for testing
     * @param field the database table field name
     * @param val the value for the field
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I update the vehicle owner account \"([^\"]*)\" field to \"([^\"]*)\"$")
    public void iUpdateTheVehicleOwnerAccountFieldTo(String field, String val) throws Throwable {
        userTest.userUpdate(field, val);
    }

    /**
     * Logs in a registered vehicle owner to the WoF app for testing
     * @param email the owner's email
     * @param password the owner's password
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I login with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iLoginWithEmailAndPassword(String email, String password) throws Throwable {
        userTest.loginUser(email, password);
    }

    /**
     * Tests if a vehicle owner is currently logged in to an account
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^I must be logged in to my account$")
    public void iMustBeLoggedInToMyAccount() throws Throwable {
        db.ownerIsLoggedIn();
    }

    /**
     * Gets a list of owner table credentials for testing
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I view my account information$")
    public void iViewMyAccountInformation() throws Throwable {
        userTest.userList();
    }

    /**
     * Removes a registered vehicle owner account for testing
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I remove the registered vehicle owner account$")
    public void iRemoveTheRegisteredVehicleOwnerAccount() throws Throwable {
        userTest.removeUser();
    }

    /**
     * Tests that a vehicle owner is not logged in to any account
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^I must not be logged in to my account$")
    public void iMustNotBeLoggedInToMyAccount() throws Throwable {
        db.ownerIsNotLoggedIn();
    }

    /**
     * Tests the printed list of owner credentials
     * @param forename the owner's forename
     * @param surname the owner's surname
     */
    @Then("^a message containing \"([^\"]*)\", \"([^\"]*)\" and null for all other fields must be displayed on the screen$")
    public void aMessageContainingAndNullForAllOtherFieldsIsDisplayedOnTheScreen(String forename, String surname) {
        String message = "User details listed for one@test.com\nForename:    " + forename + "\nSurname:     " +
                surname + "\nAddress-one: " + null + "\nAddress-two: " + null + "\nPhone:       " + null;
        userTest.testMessage(message);
    }
}

