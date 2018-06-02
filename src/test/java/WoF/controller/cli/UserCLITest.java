package WoF.controller.cli;

import WoF.model.Owner;
import WoF.model.Session;
import WoF.service.DatabaseEnum;
import WoF.service.DatabaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.Assert.assertNotEquals;

/**
 * Test the UserCLI class
 * @author Adam Ross
 */
public class UserCLITest {

    private final Session wof = Session.getWoF();
    private final DatabaseTest db = new DatabaseTest();
    private ByteArrayOutputStream printStatements;

    /**
     * Collects System.out.println statements for testing
     */
    private void collectPrintStatements() {
        printStatements = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printStatements));
    }

    /**
     * Resets the database and inserts test dummy data
     * @throws SQLException if any error occurred regarding the database
     * @throws FileNotFoundException if any error occurred regarding file reading
     */
    @Before
    public void setUp() throws SQLException, FileNotFoundException {
        wof.startSession();
        wof.resetDatabase();
    }

    /**
     * Closes the database connection
     * @throws SQLException if any error occurred regarding the database
     */
    @After
    public void tearDown() throws SQLException {
        wof.closeSession();
    }

    /**
     * Registers a vehicle owner for testing
     * @param forename the owner's forename
     * @param surname the owner's surname
     * @param email the owner's email
     * @param password the owner's password
     * @throws SQLException if any error occurred regarding the database
     */
    public void registerUser(String forename, String surname, String email, String password) throws SQLException {
        collectPrintStatements();
        new UserCLI(new String[]{"user", "register", email, password, forename, surname});
    }

    /**
     * Prints a list of a vehicle owner's information
     * @throws SQLException if any error occurred regarding the database
     */
    public void userList() throws SQLException {
        collectPrintStatements();
        new UserCLI(new String[]{"user", "list"});
    }

    /**
     * Test if a message displayed to the screen is the expected message
     * @param message the expected message
     */
    public void testMessage(String message) {
        Assert.assertEquals(message, printStatements.toString().trim().replace("\r",""));
    }

    /**
     * Logs in an existing vehicle owner registration to the WoF app
     * @param email the owner's email
     * @param password the owner's password
     * @throws SQLException if any error occurred regarding the database
     */
    public void loginUser(String email, String password) throws SQLException {
        new UserCLI(new String[]{"user", "login", email, password});
    }


    /**
     * Test that an owner's field has teh correct value
     * @param field the database table field
     * @param value the value that should be set to the database table field
     * @throws SQLException if any error occurred regarding the database
     */
    public void checkOwnerField(String field, String value) throws SQLException {
        if (value != null && value.equals("empty/null")) {
            value = null;
        }
        Assert.assertEquals(value, wof.getDatabase().getOwner(wof.getUser().getEmail()).getString(field));
    }

    /**
     * Updates a vehicle owner's field with a new value
     * @param field the database table field
     * @param value the new value being set to that field
     * @throws SQLException if any error occurred regarding the database
     */
    public void userUpdate(String field, String value) throws SQLException {
        new UserCLI(new String[]{"user", "update", field, value});
    }

    /**
     * Removes a vehicle owner's registration to the WoF app
     * @throws SQLException if any error occurred regarding the database
     */
    public void removeUser() throws SQLException {
        db.ownerIsLoggedIn();
        new UserCLI(new String[]{"user", "remove"});
    }

    /**
     * Test if a user is not registered to the WoF app
     * @param email the email being checked if it is in the database
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerNotRegistered(String email) throws SQLException {
        wof.setUser(new Owner("test", "test", email, "test"));
        db.emailNotExistsInDatabase();
    }

    /**
     * Tests four consecutive login's to three different accounts
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void successfullyLoginUserTest() throws SQLException {
        loginUser("one@test.com", "one");
        db.emailExistsInDatabase();
        db.ownerIsInDatabase();
        loginUser("two@test.com", "two");
        db.emailExistsInDatabase();
        db.ownerIsInDatabase();
        loginUser("four@test.com", "four");
        db.emailExistsInDatabase();
        db.ownerIsInDatabase();
        loginUser("one@test.com", "one");
        db.emailExistsInDatabase();
        db.ownerIsInDatabase();
    }

    /**
     * Test unsuccessful login with unregistered email and registered email with incorrect password
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void unsuccessfullyLoginUserTest() throws SQLException {
        loginUser("zero@test.com", "zero");
        wof.setUser(new Owner("WRONG", "USER", "zero@test.com", "zero"));
        db.emailNotExistsInDatabase();
        loginUser("one@test.com", "two");
        wof.setUser(new Owner("WRONG", "USER", "one@test.com", "two"));
        db.emailExistsInDatabase();
        loginUser("one@test.com", "one"); // login with correct password to get user info from database
        assertNotEquals("two", wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.PASSWORD.getValue()));
    }

    /**
     * Tests a successful vehicle owner registration to the WoF app
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void successfullyRegisterUserTest() throws SQLException {
        registerUser("test", "test", "zero@test.com", "zero");
        testMessage(wof.getUser().getEmail() + CLIEnum.REGISTERED.getValue() + CLIEnum.WOF.getValue());
    }

    /**
     * Tests an unsuccessful vehicle owner registration to the WoF app
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void unsuccessfullyRegisterExistingUserTest() throws SQLException {
        registerUser("test", "test", "one@test.com", "pointless");
        testMessage("one@test.com" + CLIEnum.INVALID_REGISTRATION.getValue());
    }

    /**
     * Tests a printed list of owner information is the correct information
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void userListTest() throws SQLException {
        loginUser("one@test.com", "one");
        userList();
        testMessage("User details listed for one@test.com\nForename:    " + wof.getUser().getForename() +
                "\nSurname:     " + wof.getUser().getSurname() + "\nAddress-one: " + wof.getUser().getAddressLineTwo() +
                "\nAddress-two: " + wof.getUser().getAddressLineTwo() + "\nPhone:       " + wof.getUser().getPhone());
    }

    /**
     * Tests owner field update is successful
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void userFieldUpdateTest() throws SQLException {
        loginUser("one@test.com", "one");
        checkOwnerField(DatabaseEnum.FORENAME.getValue(), wof.getUser().getForename());
        userUpdate(DatabaseEnum.FORENAME.getValue(), "NEW_FORENAME");
        checkOwnerField(DatabaseEnum.FORENAME.getValue(), "NEW_FORENAME");
        checkOwnerField(DatabaseEnum.SURNAME.getValue(), wof.getUser().getSurname());
        userUpdate(DatabaseEnum.SURNAME.getValue(), "NEW_SURNAME");
        checkOwnerField(DatabaseEnum.SURNAME.getValue(), "NEW_SURNAME");
        checkOwnerField(DatabaseEnum.ADDRESS_LINE_ONE.getValue(), wof.getUser().getAddressLineOne());
        userUpdate("address-one", "NEW_ADDRESS_ONE");
        checkOwnerField(DatabaseEnum.ADDRESS_LINE_ONE.getValue(), "NEW_ADDRESS_ONE");
        checkOwnerField(DatabaseEnum.ADDRESS_LINE_TWO.getValue(), wof.getUser().getAddressLineTwo());
        userUpdate("address-two", "NEW_ADDRESS_TWO");
        checkOwnerField(DatabaseEnum.ADDRESS_LINE_TWO.getValue(), "NEW_ADDRESS_TWO");
        checkOwnerField(DatabaseEnum.PHONE.getValue(), wof.getUser().getPhone());
        userUpdate(DatabaseEnum.PHONE.getValue(), "0123");
        checkOwnerField(DatabaseEnum.PHONE.getValue(), "0123");
        checkOwnerField(DatabaseEnum.PASSWORD.getValue(), wof.getUser().getPassword());
        userUpdate(DatabaseEnum.PASSWORD.getValue(), "new_password");
        checkOwnerField(DatabaseEnum.PASSWORD.getValue(), "new_password");
    }

    /**
     * Tests the removal of an owner registration removes all information from database
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void userRemoveTest() throws SQLException {
        loginUser("two@test.com", "two");
        removeUser();
        ownerNotRegistered("two@test.com");
    }

}