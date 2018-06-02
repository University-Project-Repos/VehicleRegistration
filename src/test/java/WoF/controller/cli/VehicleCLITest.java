package WoF.controller.cli;

import WoF.controller.RegexValidationEnum;
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


/**
 * Test the VehicleCLI class
 * @author Adam Ross
 */
public class VehicleCLITest {

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
        new UserCLITest().loginUser("one@test.com", "one");
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
     * Registers a vehicle to the database for testing
     * @param plate the vehicle plate
     * @param make the vehicle plate
     * @param model the vehicle model
     * @param manufactureDate the vehicle manufacture date
     * @param addressOne the vehicle address line one
     * @param addressTwo the vehicle address line two
     * @param type the vehicle type
     * @param fuel the vehicle fuel type
     * @throws SQLException if any error occurred regarding the database
     */
    public void registerVehicle(String plate, String make, String model, String manufactureDate, String addressOne,
                                String addressTwo, String type, String fuel) throws SQLException {
        collectPrintStatements();
        new VehicleCLI(new String[]{"vehicle", "register", plate, make, model, manufactureDate, addressOne, addressTwo,
                type, fuel});
    }

    /**
     * Test if a message displayed to the screen is the expected message
     * @param message the expected message
     */
    public void testMessage(String message) {
        Assert.assertEquals(message, printStatements.toString().trim().replace("\r",""));
    }

    /**
     * Prints a list of a vehicle owner's information
     * @param plate the vehicle plate
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleList(String plate) throws SQLException {
        collectPrintStatements();
        new VehicleCLI(new String[]{"vehicle", "list", plate});
    }

    /**
     * Tests if a vehicle registration is successful
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void testSuccessfulVehicleRegistration() throws SQLException {
        db.vehicleIsNotInDatabase("XYZ123");
        registerVehicle("XYZ123", "Porsche", "911", "2016-01-01", "some",
                "place", "MA", "petrol");
        db.setVehicle("XYZ123");
        db.vehicleIsInDatabase();
        testMessage("XYZ123" + CLIEnum.REGISTERED.getValue() + "one@test.com");
    }

    /**
     * Tests if a vehicle registration is unsuccessful
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void testUnsuccessfulVehicleRegistration() throws SQLException {
        registerVehicle("ABC123", "Porsche", "911", "2016-01-01", "some",
                "place", "MA", "petrol");
        testMessage("ABC123" + CLIEnum.INVALID_REGISTRATION.getValue());
    }

    /**
     * Tests if a vehicle list matches the vehicle information
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void testVehicleList() throws SQLException {
        vehicleList("abc123");
        testMessage("Vehicle details listed for ABC123\nMake:        FORD\nModel:       MODEL T\n" +
                "Manufactured:1970-01-01\nAddress-one: MICHIGAN\nAddress-two: USA\nType:        MA\nFuel:        OTHER");
    }

    /**
     * Test that a vehicle's field has the correct value
     * @param plate the vehicle plate
     * @param field the database table field
     * @param value the value that should be set to the database table field
     * @throws SQLException if any error occurred regarding the database
     */
    public void checkVehicleField(String plate, String field, String value) throws SQLException {
        db.setVehicle(plate);
        if (field.equals("manufacture_date")) {
            Assert.assertEquals(value, wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(field)
                    .split(RegexValidationEnum.SPACE.getValue())[0]);
        } else {
            Assert.assertEquals(value, wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(field));
        }
    }

    /**
     * Updates a vehicle's field with a new value
     * @param plate the plate of the vehicle being updated
     * @param field the database table field
     * @param value the new value being set to that field
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleUpdate(String plate, String field, String value) throws SQLException {
        new VehicleCLI(new String[]{"vehicle", "update", plate, field, value});
    }

    /**
     * Removes a vehicle owner's registration to the WoF app
     * @param plate the vehicle plate being removed
     * @throws SQLException if any error occurred regarding the database
     */
    public void removeVehicle(String plate) throws SQLException {
        db.ownerIsLoggedIn();
        db.vehicleIsInDatabase();
        new VehicleCLI(new String[]{"vehicle", "remove", plate});
    }

    /**
     * Test if a vehicle is not registered to the WoF app
     * @param plate the plate being checked if it is in the database
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleNotRegistered(String plate) throws SQLException {
        db.vehicleIsNotInDatabase(plate);
    }

    /**
     * Test if a vehicle is registered to the WoF app
     * @param plate the plate being checked if it is in the database
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleRegistered(String plate) throws SQLException {
        db.setVehicle(plate);
        db.vehicleIsInDatabase();
    }

    /**
     * Tests vehicle field update is successful
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void userFieldUpdateTest() throws SQLException {
        db.setVehicle("abc123");
        checkVehicleField("abc123", DatabaseEnum.MAKE.getValue(), wof.getVehicle().getMake());
        vehicleUpdate("abc123", DatabaseEnum.MAKE.getValue(), "NEW_MAKE");
        checkVehicleField("abc123", DatabaseEnum.MAKE.getValue(), "NEW_MAKE");
        checkVehicleField("abc123", DatabaseEnum.MODEL.getValue(), wof.getVehicle().getModel());
        vehicleUpdate("abc123", DatabaseEnum.MODEL.getValue(), "NEW_MODEL");
        checkVehicleField("abc123", DatabaseEnum.MODEL.getValue(), "NEW_MODEL");
        vehicleUpdate("abc123", "manufacture-date", "2018-10-10");
        checkVehicleField("abc123", DatabaseEnum.MANUFACTURE_DATE.getValue(), "2018-10-10");
        checkVehicleField("abc123", DatabaseEnum.ADDRESS_LINE_ONE.getValue(), wof.getVehicle().
                getAddressLineOne());
        vehicleUpdate("abc123", "address-one", "NEW_ADDRESS_ONE");
        checkVehicleField("abc123", DatabaseEnum.ADDRESS_LINE_ONE.getValue(), "NEW_ADDRESS_ONE");
        checkVehicleField("abc123", DatabaseEnum.ADDRESS_LINE_TWO.getValue(), wof.getVehicle().
                getAddressLineTwo());
        vehicleUpdate("abc123", "address-two", "NEW_ADDRESS_TWO");
        checkVehicleField("abc123", DatabaseEnum.ADDRESS_LINE_TWO.getValue(), "NEW_ADDRESS_TWO");
        checkVehicleField("abc123", DatabaseEnum.VEHICLE_TYPE.getValue(), wof.getVehicle().
                getVehicleType().getValue());
        vehicleUpdate("abc123", "type", "MC");
        checkVehicleField("abc123", DatabaseEnum.VEHICLE_TYPE.getValue(), "MC");
        checkVehicleField("abc123", DatabaseEnum.FUEL_TYPE.getValue(), wof.getVehicle().
                getFuelType().getValue());
        vehicleUpdate("abc123", "fuel", "other");
        checkVehicleField("abc123", DatabaseEnum.FUEL_TYPE.getValue(), "OTHER");
    }

    /**
     * Tests the removal of an owner registration removes all information from database
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void userRemoveTest() throws SQLException {
        db.setVehicle("abc123");
        removeVehicle("abc123");
        vehicleNotRegistered("abc123");
    }

}