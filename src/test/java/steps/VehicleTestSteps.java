package steps;

import WoF.controller.cli.VehicleCLITest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Acceptance testing for WoF app vehicle registration
 * @author Adam Ross
 */
public class VehicleTestSteps {

    private final VehicleCLITest vehicleTest = new VehicleCLITest();

    @Before
    public void setUp() throws SQLException, FileNotFoundException {
        vehicleTest.setUp();
    }

    @After
    public void tearDown() throws SQLException {
        vehicleTest.tearDown();
    }

    /**
     * Checks that a message is displayed on the screen
     * @param message the message being checked
     */
    @Then("^a message \"([^\"]*)\" is displayed on the screen$")
    public void aMessageIsDisplayedOnTheScreen(String message) {
        vehicleTest.testMessage(message);
    }

    /**
     * Tests if a message displayed to screen matches the expected message containing vehicle information
     * @param plate the vehicle plate
     * @param make the vehicle plate
     * @param model the vehicle model
     * @param manufactureDate the vehicle manufacture date
     * @param addressOne the vehicle address line one
     * @param addressTwo the vehicle address line two
     * @param vehicleType the vehicle type
     * @param fuelType the vehicle fuel type
     */
    @Then("^a message containing \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" is displayed on the screen$")
    public void aMessageContainingIsDisplayedOnTheScreen(String plate, String make, String model,
                                                         String manufactureDate, String addressOne, String addressTwo,
                                                         String vehicleType, String fuelType) {
        String message = String.format("Vehicle details listed for %s\nMake:        %s\nModel:       %s\nManufactured" +
                ":%s\nAddress-one: %s\nAddress-two: %s\nType:        %s\nFuel:        %s", plate, make, model,
                manufactureDate, addressOne, addressTwo, vehicleType, fuelType);
        vehicleTest.testMessage(message);
    }

    /**
     * Tests that a vehicle plate is not in the database
     * @param plate the vehicle plate
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^the vehicle with plate \"([^\"]*)\" is not in the database$")
    public void theVehicleWithPlateIsNotInTheDatabase(String plate) throws Throwable {
        vehicleTest.vehicleNotRegistered(plate);
    }

    /**
     * Registers a vehicle to the WoF app for testing
     * @param plate the vehicle plate
     * @param make the vehicle plate
     * @param model the vehicle model
     * @param manufactureDate the vehicle manufacture date
     * @param addressOne the vehicle address line one
     * @param addressTwo the vehicle address line two
     * @param vehicleType the vehicle type
     * @param fuelType the vehicle fuel type
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I register a vehicle with plate \"([^\"]*)\", make \"([^\"]*)\", model \"([^\"]*)\", manufacture date \"([^\"]*)\", address one \"([^\"]*)\", address two \"([^\"]*)\", type \"([^\"]*)\", and fuel type \"([^\"]*)\"$")
    public void iRegisterAVehicleWithPlateMakeModelManufactureDateAddressOneAddressTwoTypeAndFuelType(String plate,
                                                                                                      String make,
                                                                                                      String model,
                                                                                                      String manufactureDate,
                                                                                                      String addressOne,
                                                                                                      String addressTwo,
                                                                                                      String vehicleType,
                                                                                                      String fuelType) throws Throwable {
        vehicleTest.registerVehicle(plate, make, model, manufactureDate, addressOne, addressTwo, vehicleType, fuelType);
    }

    /**
     * Tests that a vehicle is in the database before a test action
     * @param plate the vehicle plate
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the vehicle with plate \"([^\"]*)\" is now in the database$")
    public void theVehicleWithPlateIsNowInTheDatabase(String plate) throws Throwable {
        vehicleTest.vehicleRegistered(plate);
    }

    /**
     * Tests that a vehicle is in the database
     * @param plate the vehicle plate
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^the vehicle with plate \"([^\"]*)\" is in the database$")
    public void theVehicleWithPlateIsInTheDatabase(String plate) throws Throwable {
        vehicleTest.vehicleRegistered(plate);
    }

    /**
     * Tests that a vehicle is not in the database after a test action
     * @param plate the vehicle plate
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the vehicle with plate \"([^\"]*)\" is now not in the database$")
    public void theVehicleWithPlateIsNowNotInTheDatabase(String plate) throws Throwable {
        vehicleTest.vehicleNotRegistered(plate);
    }

    /**
     * Information for a vehicle is displayed as a list
     * @param plate the vehicle plate
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I list the information for vehicle \"([^\"]*)\"$")
    public void iListTheInformationForVehicle(String plate) throws Throwable {
        vehicleTest.vehicleList(plate);
    }

    /**
     * Tests that a vehicle field matches a given value before updating
     * @param plate the vehicle plate
     * @param field the vehicle field
     * @param val the given value
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Given("^the vehicle \"([^\"]*)\" field \"([^\"]*)\" has existing value \"([^\"]*)\" in the database$")
    public void theVehicleFieldHasExistingValueInTheDatabase(String plate, String field, String val) throws Throwable {
        vehicleTest.checkVehicleField(plate, field, val);
    }

    /**
     * Updates a vehicles field with a given value for testing
     * @param plate the vehicle plate
     * @param field the vehicle field
     * @param val the given value
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I update/edit vehicle \"([^\"]*)\" field \"([^\"]*)\" to the new value \"([^\"]*)\"$")
    public void iUpdateEditVehicleFieldToTheNewValue(String plate, String field, String val) throws Throwable {
        vehicleTest.vehicleUpdate(plate, field, val);
    }

    /**
     * Tests that a vehicle field matches a given value after updating
     * @param plate the vehicle plate
     * @param field the vehicle field
     * @param val the given value
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @Then("^the vehicle \"([^\"]*)\" field \"([^\"]*)\" will have value \"([^\"]*)\" in the database$")
    public void theVehicleFieldWillHaveValueInTheDatabase(String plate, String field, String val) throws Throwable {
        vehicleTest.checkVehicleField(plate, field, val);
    }

    /**
     * Removes a vehicle from the database for testing
     * @param plate the vehicle plate
     * @throws Throwable if any error occurred regarding the acceptance testing
     */
    @When("^I remove the vehicle \"([^\"]*)\"$")
    public void iRemoveTheVehicle(String plate) throws Throwable {
        vehicleTest.removeVehicle(plate);
    }
}
