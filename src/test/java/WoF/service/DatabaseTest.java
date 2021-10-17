package WoF.service;

import WoF.model.Owner;
import WoF.model.Session;
import WoF.model.vehicle.Vehicle;
import WoF.model.vehicle.VehicleMA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Test for the database class
 * @author Adam Ross
 */
public class DatabaseTest {

    private final Session wof = Session.getWoF();
    private final String plate = "ABC123";

    public DatabaseTest() {}

    /**
     * Set up prior to testing
     * @throws SQLException if any error occurred regarding the database
     * @throws FileNotFoundException if any error occurred regarding file reading
     */
    @Before
    public void setUp() throws SQLException, FileNotFoundException {
        wof.startSession();
        wof.resetDatabase();
        wof.setToken();
        wof.setUser(new Owner("TEST", "DUMMY", "one@test.com", "one"));
    }

    /**
     * Tear down following testing
     * @throws SQLException if any error occurred regarding the database
     */
    @After
    public void tearDown() throws SQLException {
        wof.closeSession();
    }

    /**
     * Tests that there is a current database connection
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void testDatabaseConnection() throws SQLException {
        assertTrue(wof.getDatabase().isConnected());
    }

    /**
     * Checks if an owner email is registered in the database
     * @throws SQLException if any error occurred regarding the database
     */
    public void emailExistsInDatabase() throws SQLException {
        assertTrue(wof.getDatabase().ownerEmailRegistered(wof.getUser().getEmail()));
    }

    /**
     * Checks if an owner email is now registered in the database
     * @throws SQLException if any error occurred regarding the database
     */
    public void emailNotExistsInDatabase() throws SQLException {
        assertFalse(wof.getDatabase().ownerEmailRegistered(wof.getUser().getEmail()));
    }

    /**
     * Checks if an owner is successfully inserted to the database
     * @throws SQLException if any error occurred regarding the database
     */
    private void ownerInsertsInDatabase() throws SQLException {
        assertTrue(wof.ownerInsert());
    }

    /**
     * Checks if an owner is in the database
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerIsInDatabase() throws SQLException {
        assertEquals(wof.getUser().getForename(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.FORENAME.getValue()));
        assertEquals(wof.getUser().getSurname(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.SURNAME.getValue()));
        assertEquals(wof.getUser().getAddressLineOne(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.ADDRESS_LINE_ONE.getValue()));
        assertEquals(wof.getUser().getAddressLineTwo(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.ADDRESS_LINE_TWO.getValue()));
        assertEquals(wof.getUser().getPhone(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.PHONE.getValue()));
        assertEquals(wof.getUser().getPassword(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.PASSWORD.getValue()));
    }

    /**
     * Checks if owner is not registered, but email is - only works if all forename, surname and password are different
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerIsNotInDatabase() throws SQLException {
        assertEquals("WRONG", wof.getUser().getForename().toUpperCase());
        assertNotEquals(wof.getUser().getForename(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.FORENAME.getValue()));
        assertNotEquals(wof.getUser().getSurname(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.SURNAME.getValue()));
        assertNotEquals(wof.getUser().getPassword(), wof.getDatabase().getOwner(wof.getUser().getEmail()).
                getString(DatabaseEnum.PASSWORD.getValue()));
    }

    /**
     * Test a vehicle owner is successfully logged in to the WoF app
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerIsLoggedIn() throws SQLException {
        assertNotNull(wof.getDatabase().ownerLogIn(wof.getUser().getEmail(), wof.getUser().getPassword()));
    }

    /**
     * Test a vehicle owner is successfully logged in to the WoF app
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerIsNotLoggedIn() throws SQLException {
        assertNull(wof.getDatabase().ownerLogIn(wof.getUser().getEmail(), wof.getUser().getPassword()));
    }


    /**
     * Tests the ownerLogIn() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void ownerLogIn() throws SQLException {
        emailExistsInDatabase();
        ownerIsLoggedIn();
        ownerIsInDatabase();
    }

    /**
     * Tests the insertOwner() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void insertOwner() throws SQLException {
        wof.setUser(new Owner("TEST", "DUMMY", "three@test.com", "three"));
        emailNotExistsInDatabase();
        ownerInsertsInDatabase();
        ownerIsInDatabase();
    }

    /**
     * Tests the ownerLogOut() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void ownerLogOut() throws SQLException {
        ownerIsLoggedIn();
        ownerIsInDatabase();
        wof.getDatabase().ownerLogOut();
        assertNull(wof.getDatabase().getOwner(wof.getUser().getEmail()));
    }

    /**
     * Tests the deleteOwner() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void deleteOwner() throws SQLException {
        ownerIsLoggedIn();
        ownerIsInDatabase();
        wof.getDatabase().deleteOwner();
        emailNotExistsInDatabase();
        ownerIsNotLoggedIn();
    }

    /**
     * Checks each field in a database vehicle entry is equal to the tested credentials
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleIsInDatabase() throws SQLException {
        assertEquals(wof.getVehicle().getPlate(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.PLATE.getValue()));
        assertEquals(wof.getVehicle().getMake(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.MAKE.getValue()));
        assertEquals(wof.getVehicle().getModel(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.MODEL.getValue()));
        assertEquals(wof.getVehicle().getManufactureDate(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getTimestamp(DatabaseEnum.MANUFACTURE_DATE.getValue()));
        assertEquals(wof.getVehicle().getAddressLineOne(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.ADDRESS_LINE_ONE.getValue()));
        assertEquals(wof.getVehicle().getAddressLineTwo(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.ADDRESS_LINE_TWO.getValue()));
        assertEquals(wof.getVehicle().getVehicleType().getValue(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.VEHICLE_TYPE.getValue()));
        assertEquals(wof.getVehicle().getFuelType().getValue(), wof.getDatabase().getVehicleInfo(wof.getVehicle().getPlate()).getString(DatabaseEnum.FUEL_TYPE.getValue()));
    }

    /**
     * Tests if a vehicle is not registered in the database
     * @param plate the vehicle plate
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleIsNotInDatabase(String plate) throws SQLException {
        assertNull(wof.getDatabase().getVehicleInfo(plate.toUpperCase()));
    }

    /**
     * Tests the getVehicleInfo() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void getVehicleInfo() throws SQLException {
        assertNull(wof.getDatabase().getVehicleInfo("XYZ890"));
        wof.setVehicle(new VehicleMA(new LinkedList<>(Arrays.asList("ABC123", "FORD", "MODEL T",
                Timestamp.valueOf(LocalDate.parse("1970-01-01").atTime(1, 0)),
                "MICHIGAN", "USA", "MA", "OTHER"))));
        vehicleIsInDatabase();
    }

    /**
     * Sets a vehicle to the session singleton class
     * @param plate the vehicle plate
     * @throws SQLException if any error occurred regarding the database
     */
     public void setVehicle(String plate) throws SQLException {
         wof.getUser().getVehicles().clear();
         wof.getDatabase().getVehicles();
         System.out.println();
         for (Vehicle v : wof.getUser().getVehicles()) {
             if (v.getPlate().equals(plate.toUpperCase())) {
                 wof.setVehicle(v);
             }
         }
     }

    /**
     * Tests the getVehicles() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void getVehicles() throws SQLException {
        assertEquals(0, wof.getUser().getVehicles().size());
        wof.getDatabase().getVehicles();
        assertEquals(3, wof.getUser().getVehicles().size());
        ArrayList<String> plates = new ArrayList<>(wof.getUser().getVehicles().size());

        for (Vehicle v : wof.getUser().getVehicles()) {
            plates.add(v.getPlate());
        }
        assertTrue(plates.contains(plate));
    }

    /**
     * Tests the insertVehicle() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void insertVehicle() throws SQLException {
        assertNull(wof.getDatabase().getVehicleInfo("XYZ890"));
        wof.setVehicle(new VehicleMA(new LinkedList<>(Arrays.asList("XYZ890", "TOYOTA", "COROLLA",
                Timestamp.valueOf(LocalDate.parse("2000-01-01").atStartOfDay()), "TOYOTA", "JAPAN", "MA", "PETROL"))));
        wof.getDatabase().insertVehicle(wof.getVehicle().getPlate(), wof.getVehicle().getMake(), wof.getVehicle().getModel(),
                wof.getVehicle().getManufactureDate(), wof.getVehicle().getAddressLineOne(),
                wof.getVehicle().getAddressLineTwo(), wof.getVehicle().getVehicleType().getValue(),
                wof.getVehicle().getFuelType().getValue());
        vehicleIsInDatabase();
    }

    /**
     * Tests the deleteVehicle() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void deleteVehicle() throws SQLException {
        setVehicle(plate);
        assertNotNull(wof.getVehicle());
        vehicleIsInDatabase();
        wof.getDatabase().deleteVehicle(new VehicleMA(new LinkedList<>(Arrays.asList(plate, wof.getDatabase().
                        getVehicleInfo(plate).getString(DatabaseEnum.MAKE.getValue()), wof.getDatabase().
                        getVehicleInfo(plate).getString(DatabaseEnum.MODEL.getValue()), wof.getDatabase().
                        getVehicleInfo(plate).getTimestamp(DatabaseEnum.MANUFACTURE_DATE.getValue()), wof.getDatabase().
                        getVehicleInfo(plate).getString(DatabaseEnum.ADDRESS_LINE_ONE.getValue()), wof.getDatabase().
                        getVehicleInfo(plate).getString(DatabaseEnum.ADDRESS_LINE_TWO.getValue()), wof.getDatabase().
                        getVehicleInfo(plate).getString(DatabaseEnum.VEHICLE_TYPE.getValue()), wof.getDatabase().
                getVehicleInfo(plate).getString(DatabaseEnum.FUEL_TYPE.getValue())))));
        assertNull(wof.getDatabase().getVehicleInfo(plate));
        vehicleIsNotInDatabase(plate);
    }

    /**
     * Checks if a vehicle has any history
     * @throws SQLException if any error occurred regarding the database
     */
    public void vehicleHasHistory() throws SQLException {
        wof.getVehicle().setHistory(wof.getDatabase().getVehicleHistory());
        assertNotNull(wof.getVehicle().getHistory());
        assertEquals(wof.getVehicle().getHistory().getVin(), wof.getVehicle().getHistory().getVin());
        assertEquals(wof.getVehicle().getHistory().getOdometerReading(),
                wof.getVehicle().getHistory().getOdometerReading());
        assertEquals(wof.getVehicle().getHistory().getRegistrationDateNZ(),
                wof.getVehicle().getHistory().getRegistrationDateNZ());
        assertEquals(wof.getVehicle().getHistory().getWofExpiry(), wof.getVehicle().getHistory().getWofExpiry());
        assertEquals(wof.getVehicle().getHistory().getWofStatus().getValue(),
                wof.getVehicle().getHistory().getWofStatus().getValue());
    }

    /**
     * Tests the getVehicleHistory() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void getVehicleHistory() throws SQLException {
        setVehicle(plate);
        vehicleHasHistory();
    }

    /**
     * Tests the insertHistory() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void insertHistory() throws SQLException {
        setVehicle("HIJ345");
        assertNull(wof.getVehicle().getHistory());
        wof.getDatabase().insertHistory("09876509876512345", "100",
                Timestamp.valueOf(LocalDate.parse("2010-01-01").atStartOfDay()),
                Timestamp.valueOf(LocalDate.parse("2019-01-01").atStartOfDay()), "PASSED");
        vehicleHasHistory();
    }

    /**
     * Tests the updateHistory() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void updateHistory() throws SQLException {
        setVehicle(plate);
        vehicleHasHistory();
        wof.getDatabase().updateHistory(DatabaseEnum.ODOMETER_READING.getValue(), "111111");
        vehicleHasHistory();
        wof.getDatabase().updateHistory(DatabaseEnum.WOF_STATUS.getValue(), "PASSED");
        vehicleHasHistory();
    }

    /**
     * Tests the updateVehicle() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void updateVehicle() throws SQLException {
        setVehicle(plate);
        vehicleIsInDatabase();
        wof.getDatabase().updateVehicle(DatabaseEnum.MAKE.getValue(), "HOLDEN");
        setVehicle(plate);
        vehicleIsInDatabase();
        wof.getDatabase().updateVehicle(DatabaseEnum.MODEL.getValue(), "COMMODORE");
        setVehicle(plate);
        vehicleIsInDatabase();
        wof.getDatabase().updateVehicle(DatabaseEnum.ADDRESS_LINE_ONE.getValue(), "SOUTH");
        setVehicle(plate);
        vehicleIsInDatabase();
        wof.getDatabase().updateVehicle(DatabaseEnum.ADDRESS_LINE_TWO.getValue(), "AUSSIE");
        setVehicle(plate);
        vehicleIsInDatabase();
        wof.getDatabase().updateVehicle(DatabaseEnum.VEHICLE_TYPE.getValue(), "O");
        setVehicle(plate);
        vehicleIsInDatabase();
        wof.getDatabase().updateVehicle(DatabaseEnum.FUEL_TYPE.getValue(), "DIESEL");
        setVehicle(plate);
        vehicleIsInDatabase();
    }

    /**
     * Tests the updateOwner() method
     * @throws SQLException if any error occurred regarding the database
     */
    @Test
    public void updateOwner() throws SQLException {
        ownerIsLoggedIn();
        ownerIsInDatabase();
        wof.getDatabase().updateOwner(DatabaseEnum.FORENAME.getValue(), "NEW_NAME");
        ownerIsLoggedIn();
        wof.getUser().setForename("NEW_NAME");
        ownerIsInDatabase();
        wof.getDatabase().updateOwner(DatabaseEnum.SURNAME.getValue(), "NEW_SURNAME");
        ownerIsLoggedIn();
        wof.getUser().setSurname("NEW_SURNAME");
        ownerIsInDatabase();
        wof.getDatabase().updateOwner(DatabaseEnum.ADDRESS_LINE_ONE.getValue(), "NEW");
        ownerIsLoggedIn();
        wof.getUser().setAddressLineOne("NEW");
        ownerIsInDatabase();
        wof.getDatabase().updateOwner(DatabaseEnum.ADDRESS_LINE_TWO.getValue(), "HOME");
        ownerIsLoggedIn();
        wof.getUser().setAddressLineTwo("HOME");
        ownerIsInDatabase();
        wof.getDatabase().updateOwner(DatabaseEnum.PHONE.getValue(), "1234567");
        ownerIsLoggedIn();
        wof.getUser().setPhone("1234567");
        ownerIsInDatabase();
        wof.getDatabase().updateOwner(DatabaseEnum.PASSWORD.getValue(), "two");
        wof.getUser().setPassword("two");
        ownerIsLoggedIn();
        ownerIsInDatabase();
    }
}