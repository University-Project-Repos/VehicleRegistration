package WoF.service;

import WoF.model.History;
import WoF.model.HistoryEnum;
import WoF.model.Session;
import WoF.model.vehicle.Vehicle;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.*;


/**
 * Class for communicating with the WoF application SQLite database
 * @author Adam Ross
 */
public class Database {

    private final Session wof = Session.getWoF();
    private final Connection conn;
    private PreparedStatement statement;
    private ResultSet set;

    /**
     * Connects to the WoF application SQLite database
     * @throws SQLException if any error occurred regarding the database
     */
    public Database() throws SQLException {
        this.conn = DriverManager.getConnection(DatabaseEnum.DB_URL.getValue());
    }

    /**
     * Resets and re-samples the database
     * @throws FileNotFoundException if any error occurred regarding file reading
     * @throws SQLException if any error occurred regarding the database
     */
    public void databaseSetup() throws FileNotFoundException, SQLException {
        databaseQueryFile(DatabaseEnum.DB_SCHEMA.getValue());
        databaseQueryFile(DatabaseEnum.DB_SAMPLE.getValue());
    }

    /**
     * Checks the current connection status with the database - for testing
     * @return true if the database is not disconnected, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    public Boolean isConnected() throws SQLException {
        return this.conn != null && !this.conn.isClosed();
    }

    /**
     * Prepares an SQL statement and sets parameters for execution
     * @param sqlStatement the SQL statement string
     * @param parameters   the parameters being set to the SQL statement
     * @throws SQLException if any error occurred regarding the database
     */
    private PreparedStatement prepareStatement(String sqlStatement, HashMap<String, Object> parameters)
            throws SQLException {
        assert null != this.conn && sqlStatement != null;
        this.statement = this.conn.prepareStatement(sqlStatement);

        if (parameters != null) {
            for (int i = 1; i <= parameters.size(); i++) {
                try {
                    this.statement.setString(i, parameters.get(Integer.toString(i)).toString());
                } catch (Exception e) {
                    this.statement.setTimestamp(i, (Timestamp) parameters.get(Integer.toString(i)));
                }
            }
        }
        return this.statement;
    }

    /**
     * Queries the database with statements from file
     * @throws FileNotFoundException if any error occurred regarding file reading
     * @throws SQLException if any error occurred regarding the database
     */
    private void databaseQueryFile(String input) throws FileNotFoundException, SQLException {
        Scanner scan = new Scanner(new FileReader(input)).useDelimiter(";");

        while (scan.hasNext()) {
            prepareStatement(scan.next() + ";", null).executeUpdate();
        }
    }

    /**
     * Checks if an email is registered to an owner account in the database
     * @param email the owner's email
     * @return true if the email exists in the database
     * @throws SQLException if any error occurred regarding the database
     */
    Boolean ownerEmailRegistered(String email) throws SQLException {
        return prepareStatement("SELECT * FROM owner WHERE email = ?", new HashMap<String, Object>(){{
            put("1", email);}}).executeQuery().isBeforeFirst();
    }

    /**
     * Select an existing vehicle owner matching given email and token from owner table in database
     * @param email the email to insert for the vehicle owner search
     * @return the result set (sql cursor) with owner details as in the database, or null otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    public ResultSet getOwner(String email) throws SQLException {
        this.set = prepareStatement("SELECT * FROM owner WHERE email = ? AND token = ?",
                new HashMap<String, Object>(2){{put("1", email); put("2", wof.getToken());}}).executeQuery();

        if (this.set.isBeforeFirst()) {
            return this.set;
        }
        return null;
    }

    /**
     * Log in an existing owner matching given email and password in the owner table in the database
     * @param email    the email to insert for the vehicle owner login
     * @param password the password to insert for the vehicle owner login
     * @return a list of owner field strings such as forename, surname, address and phone
     * @throws SQLException if any error occurred regarding the database
     */
    public String[] ownerLogIn(String email, String password) throws SQLException {
        if (ownerEmailRegistered(email)) {
            if (prepareStatement("UPDATE owner SET token = ?, last_login = CURRENT_TIMESTAMP WHERE " +
                    "email = ? AND password = ?", new HashMap<String, Object>(3) {{
                put("1", wof.getToken());
                put("2", email);
                put("3", password);
            }}).executeUpdate() == 1) {
                this.set = getOwner(email);

                if (this.set != null) {
                    return new String[]{this.set.getString(DatabaseEnum.FORENAME.getValue()),
                            this.set.getString(DatabaseEnum.SURNAME.getValue()),
                            this.set.getString(DatabaseEnum.ADDRESS_LINE_ONE.getValue()),
                            this.set.getString(DatabaseEnum.ADDRESS_LINE_TWO.getValue()),
                            this.set.getString(DatabaseEnum.PHONE.getValue())};
                }
            }
        }
        return null;
    }

    /**
     * Inserts a new row in the owner table in the database if email is unique and all fields are valid
     * @param forename the forename to insert for the vehicle owner registration
     * @param surname  the surname to insert for the vehicle owner registration
     * @param email    the email to insert for the vehicle owner registration
     * @param password the password to insert for the vehicle owner registration
     * @return true if the email does not already exist and the owner is inserted successfully, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    public Boolean insertOwner(String forename, String surname, String email, String password) throws SQLException {
        if (!ownerEmailRegistered(email)) {
            return (prepareStatement("INSERT INTO owner(forename, surname, email, password, token) VALUES " +
                    "(?,?,?,?,?)", new HashMap<String, Object>(5) {{
                put("1", forename);
                put("2", surname);
                put("3", email);
                put("4", password);
                put("5", wof.getToken());
            }}).executeUpdate() == 1);
        }
        return false;
    }

    /**
     * Select an existing vehicle matching given plate from vehicle table in database
     * @param plate the plate to insert for the vehicle search
     * @return the result set (sql cursor) with vehicle details as in the database, or null otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    public ResultSet getVehicleInfo(String plate) throws SQLException {
        this.set = null;
        this.set = prepareStatement("SELECT * FROM vehicle WHERE plate = ?",
                new HashMap<String, Object>(1) {{ put("1", plate); }}).executeQuery();

        if (this.set.isBeforeFirst()) {
            return this.set;
        }
        return null;
    }

    /**
     * Gets all the vehicles registered to a logged in vehicle owner
     * @throws SQLException if any error occurred regarding the database
     */
    public void getVehicles() throws SQLException {
        this.set = getOwnersVehicles();
        HashSet<LinkedList<Object>> vehicles = new HashSet<>();

        if (this.set != null) {
            while (true) {
                assert this.set != null;
                if (!this.set.next()) break;
                if (this.set != null && this.set.getMetaData().getColumnName(1).equals(DatabaseEnum.PLATE.getValue())) {
                    vehicles.add(new LinkedList<>(Arrays.asList(this.set.getString(DatabaseEnum.PLATE.getValue()),
                            this.set.getString(DatabaseEnum.MAKE.getValue()),
                            this.set.getString(DatabaseEnum.MODEL.getValue()),
                            this.set.getTimestamp(DatabaseEnum.MANUFACTURE_DATE.getValue()),
                            this.set.getString(DatabaseEnum.ADDRESS_LINE_ONE.getValue()),
                            this.set.getString(DatabaseEnum.ADDRESS_LINE_TWO.getValue()),
                            this.set.getString(DatabaseEnum.VEHICLE_TYPE.getValue()),
                            this.set.getString(DatabaseEnum.FUEL_TYPE.getValue()))));
                }
            }
        }
        wof.addVehicle(vehicles);
    }

    /**
     * Retrieves the vehicle_id row or rows for a given owner email
     * @return the result set of vehicle data
     * @throws SQLException if any error occurred regarding the database
     */
    private ResultSet getOwnersVehicles() throws SQLException {
        return prepareStatement("SELECT * FROM vehicle INNER JOIN vehicles_owners ON vehicle.plate = " +
                "vehicles_owners.vehicle_id WHERE owner_id = ?", new HashMap<String, Object>(1) {{
            put("1", wof.getUser().getEmail()); }}).executeQuery();
    }

    /**
     * Inserts a new row to the vehicle table in the database
     * @param plate           the plate to insert for the vehicle registration
     * @param make            the make to insert for the vehicle registration
     * @param model           the WoF.model to insert for the vehicle registration
     * @param manufactureDate the manufacture date to insert for the vehicle registration
     * @param addressOne      the address line one to insert for the vehicle registration
     * @param addressTwo      the address line two to insert for the vehicle registration
     * @param vehicleType     the vehicle type to insert for the vehicle registration
     * @param fuelType        the fuel type to insert for the vehicle registration
     * @throws SQLException if any error occurred regarding the database
     */
    public void insertVehicle(String plate, String make, String model, Timestamp manufactureDate, String addressOne,
                              String addressTwo, String vehicleType, String fuelType) throws SQLException {
        if (getVehicleInfo(plate) == null) {
            prepareStatement("INSERT INTO vehicle(plate, make, model, manufacture_date, " +
                    "address_one, address_two, vehicle_type, fuel_type) VALUES (?,?,?,?,?,?,?,?)",
                    new HashMap<String, Object>(8) {{ put("1", plate); put("2", make); put("3", model);
                    put("4", manufactureDate); put("5", addressOne); put("6", addressTwo); put("7", vehicleType);
                    put("8", fuelType); }}).executeUpdate();
            insertVehiclesOwners(plate);
        }
    }

    /**
     * Creates a new vehicles_owners table registry for a newly registered vehicle
     * @param plate the plate to insert for the new vehicle and owner
     * @throws SQLException if any error occurred regarding the database
     */
    private void insertVehiclesOwners(String plate) throws SQLException {
        prepareStatement("INSERT INTO vehicles_owners(owner_id, vehicle_id) VALUES(?,?)",
                new HashMap<String, Object>(2){{put("1", wof.getUser().getEmail());
                put("2", plate);}}).executeUpdate();
    }

    /**
     * Updates a vehicles_owners table to include the VIN of the registered vehicle
     * @param vin the VIN for updating the vehicle registry
     * @throws SQLException if any error occurred regarding the database
     */
    private void insertVehiclesHistory(String vin) throws SQLException {
        prepareStatement("UPDATE vehicles_owners SET vehicle_vin = ? WHERE vehicle_id = ?",
                new HashMap<String, Object>(2){{put("1", vin); put("2", wof.getVehicle().getPlate());}}).
                executeUpdate();
    }

    /**
     * Deletes a row from the vehicles_owners table for specified vehicle plate for a user
     * @throws SQLException if any error occurred regarding the database
     */
    private void deleteOwnersVehicle(String plate) throws SQLException {
        prepareStatement("DELETE FROM vehicles_owners WHERE vehicle_id = ? AND owner_id = ?",
                new HashMap<String, Object>(2){{put("1", plate); put("2", wof.getUser().getEmail());}}).
                executeUpdate();
    }

    /**
     * Delete an existing row matching given email from owner table in database
     * ALso deletes all vehicles registered to the owner being deleted
     * @throws SQLException if any error occurred regarding the database
     */
    public void deleteOwner() throws SQLException {
        for (Vehicle vehicle : wof.getUser().getVehicles()) {
            deleteVehicle(vehicle);
        }
        prepareStatement("DELETE FROM owner WHERE email = ? AND password = ? AND token = ?",
                new HashMap<String, Object>(3) {{ put("1", wof.getUser().getEmail());put("2", wof.getUser().
                        getPassword()); put("3", wof.getToken());}}).executeUpdate();
    }

    /**
     * Delete an existing row matching given plate from the vehicle table in the database,
     * or from the vehicles_owners table if the vehicles has more than one owner
     * @param vehicle the vehicle being deleted
     * @throws SQLException if any error occurred regarding the database
     */
    public void deleteVehicle(Vehicle vehicle) throws SQLException {
        deleteOwnersVehicle(vehicle.getPlate());

        if (vehicle.getHistory() != null) {
            deleteHistory(vehicle.getHistory().getVin());
        }
        prepareStatement("DELETE FROM vehicle WHERE plate = ?",
                new HashMap<String, Object>(1) {{ put("1", vehicle.getPlate()); }}).executeUpdate();
    }

    /**
     * Deletes a vehicle history for a specified vehicle plate
     * @param vin the vin of the vehicle the history is being deleted
     * @throws SQLException if any error occurred regarding the databases
     */
    private void deleteHistory(String vin) throws SQLException {
        prepareStatement("DELETE FROM history WHERE vin = ?",
                new HashMap<String, Object>(1) {{ put("1", vin); }}).executeUpdate();
    }

    /**
     * Select an existing vehicle history matching given plate from history table in database
     * @return the vehicle history matching the vehicle plate, or null otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    public History getVehicleHistory() throws SQLException {
        this.set = prepareStatement("SELECT * FROM history INNER JOIN vehicles_owners ON history.vin = " +
                "vehicles_owners.vehicle_vin WHERE vehicle_id = ?",
                new HashMap<String, Object>(1) {{ put("1", wof.getVehicle().getPlate());}}).executeQuery();

        if (this.set.isBeforeFirst()) {
            return new History(this.set.getString(DatabaseEnum.VIN.getValue()),
                    this.set.getString(DatabaseEnum.ODOMETER_READING.getValue()),
                    this.set.getTimestamp(DatabaseEnum.NZ_REGISTRATION_DATE.getValue()),
                    this.set.getTimestamp(DatabaseEnum.WOF_EXPIRY.getValue()),
                    HistoryEnum.valueOf(set.getString(DatabaseEnum.WOF_STATUS.getValue())));
        }
        return null;
    }

    /**
     * Check if a vehicle VIN is already registered to a vehicles history
     * @param vin the vehicle VIN
     * @return true if the vehicle VIN is already registered to a vehicles history
     * @throws SQLException if any error occurred regarding the database
     */
    public Boolean vinInHistory(String vin) throws SQLException {
        return prepareStatement("SELECT * FROM history WHERE vin = ?",
                new HashMap<String, Object>(1){{put("1", vin);}}).executeQuery().isBeforeFirst();
    }

    /**
     * Log out an existing owner matching given email in owner table in database
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerLogOut() throws SQLException {
        prepareStatement("UPDATE owner SET token = ? WHERE email = ?", new HashMap<String, Object>
                (2){{put("1", null); put("2", wof.getUser().getEmail());}}).executeUpdate();
    }

    /**
     * Register the vehicle history for a specified vehicle
     * @param vin     the VIN to insert for the new vehicle history
     * @param odo     the odometer reading to insert for the new vehicle history
     * @param reg     the NZ first registration date to insert for the new vehicle history
     * @param wofExp  the WoF expiry date to insert for the new vehicle history
     * @param wofStat the WoF status to insert for the new vehicle history
     * @throws SQLException if any error occurred regarding the database
     */
    public void insertHistory(String vin, String odo, Timestamp reg, Timestamp wofExp, String wofStat) throws SQLException {
        this.set = getOwnersVehicles();

        if (this.set != null) {
            while (true) {
                assert this.set != null;
                if (!this.set.next()) break;
                if (this.set != null && this.set.getString(DatabaseEnum.PLATE.getValue()).equals(wof.getVehicle().
                        getPlate()) && this.set.getString(DatabaseEnum.VEHICLE_VIN.getValue()) == null) {
                    prepareStatement("INSERT INTO history(vin, odometer_reading, nz_registration_date," +
                            " wof_expiry, wof_status) VALUES (?,?,?,?,?)", new HashMap<String, Object>(5){{
                        put("1", vin); put("2", odo); put("3", reg); put("4", wofExp); put("5", wofStat);}}).
                            executeUpdate();
                    insertVehiclesHistory(vin);
                }
            }
        }
    }

    /**
     * Updates a specified vehicle history parameter with a given value for a specified vehicle
     * @param param the parameter that is being updated in the vehicle history update
     * @param value the update value to be inserted for the vehicle history update
     * @throws SQLException if any error occurred regarding the database
     */
    public void updateHistory(String param, Object value) throws SQLException {
        if (wof.getVehicle().getHistory().getVin() != null) {
            prepareStatement(String.format("UPDATE history SET %s = ? WHERE vin = ?", param.toLowerCase()),
                    new HashMap<String, Object>(2) {{ put("1", value); put("2", wof.getVehicle().
                            getHistory().getVin());}}).executeUpdate();
        }
    }

    /**
     * Update an existing row matching given plate in the vehicle table in the database
     * @param param the parameter that is being updated in the vehicle update
     * @param value the update value to be inserted for the vehicle update
     * @throws SQLException if any error occurred regarding the database
     */
    public void updateVehicle(String param, Object value) throws SQLException {
        if (getVehicleInfo(wof.getVehicle().getPlate()) != null && !param.toLowerCase().
                equals(DatabaseEnum.PLATE.getValue())) {
            prepareStatement(String.format("UPDATE vehicle SET %s = ? WHERE plate = ?", param),
                    new HashMap<String, Object>(2) {{ put("1", value);put("2", wof.getVehicle().
                            getPlate());}}).executeUpdate();
        }
    }

    /**
     * Update an existing row matching given email in the owner table in the database
     * @param param the parameter that is being updated in the owner update
     * @param value the update value to be inserted for the vehicle update
     * @throws SQLException if any error occurred regarding the database
     */
    public void updateOwner(String param, String value) throws SQLException {
        if (getOwner(wof.getUser().getEmail()) != null && !param.toLowerCase().equals(DatabaseEnum.EMAIL.getValue())) {
            prepareStatement(String.format("UPDATE owner SET %s = ? WHERE email = ?", param.toLowerCase()),
                    new HashMap<String, Object>(2) {{ put("1", value);put("2", wof.getUser().
                            getEmail());}}).executeUpdate();
        }
    }

    /**
     * Closes result set, statement and connection in that order
     * @throws SQLException if any error occurred regarding the database
     */
    public void closeConnections() throws SQLException {
        if (this.set != null) {
            this.set.close();
        }

        if (this.statement != null) {
            this.statement.close();
        }

        if (this.conn != null) {
            this.conn.close();
        }
    }
}
