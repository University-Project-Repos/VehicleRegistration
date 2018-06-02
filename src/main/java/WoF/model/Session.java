package WoF.model;

import WoF.model.vehicle.Vehicle;
import WoF.model.vehicle.VehicleEnum;
import WoF.model.vehicle.VehicleMA;
import WoF.model.vehicle.VehicleMB;
import WoF.model.vehicle.VehicleMC;
import WoF.model.vehicle.VehicleO;
import WoF.model.vehicle.VehicleT;
import WoF.service.Database;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Lazy initialization singleton for the WoF app session
 * @author Adam Ross
 */
public class Session {

    private static Session instance;
    private Owner user;
    private Database database;
    private Vehicle selectedVehicle;
    private String token;

    /**
     * Constructs for the WoF app session
     */
    private Session() { }

    /**
     * Gets the WoF app session instance; instantiates the instance if the instance is null
     * @return the WoF app session instance
     */
    public static Session getWoF() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Connects to, resets and re-samples the WoF SQLite database
     * @throws SQLException if any error occurred regarding the database
     */
    public void startSession() throws SQLException {
        this.database = new Database(); // instantiates the WoF database instance
    }

    /**
     * Resets and database and inserts test dummy data
     * @throws FileNotFoundException if any error occurred regarding file reading
     * @throws SQLException if any error occurred regarding the database
     */
    public void resetDatabase() throws FileNotFoundException, SQLException {
        this.database.databaseSetup();
    }

    /**
     * Closes the WoF app
     * @throws SQLException if any error occurred regarding the database
     */
    public void closeSession() throws SQLException {
        if (this.user != null) {
            this.logOut();
        }
        this.database.closeConnections(); // closes all database connections
    }

    /**
     * Creates a random token string for a logged in owner
     * @return new token string
     */
    private String createToken() {
        return Long.toString(Math.abs(new SecureRandom().nextLong()), 16);
    }

    /**
     * Gets the token
     * @return the token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Sets the token
     */
    public void setToken() {
        this.token = createToken();
    }

    /**
     * Gets the user
     * @return the user
     */
    public Owner getUser() {
        return this.user;
    }

    /**
     * Sets a new user
     * @param newUser the new user as an Owner
     */
    public void setUser(Owner newUser) {
        this.user = newUser;
    }

    /**
     * Gets the database
     * @return the database
     */
    public Database getDatabase() {
        return this.database;
    }

    /**
     * Sets a new selected vehicle by a vehicle owner for editing
     * @param newVehicle the selected vehicle
     */
    public void setVehicle(Vehicle newVehicle) {
        this.selectedVehicle = newVehicle;
    }

    /**
     * Gets a vehicle selected by an owner
     * @return the vehicle data
     */
    public Vehicle getVehicle() {
        return this.selectedVehicle;
    }

    /**
     * Inserts an owner to the owner table in the database, and logs the owner in
     * @return true if the vehicle owner is inserted to the database, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    public Boolean ownerInsert() throws SQLException {
        return this.database.insertOwner(this.user.getForename(), this.user.getSurname(), this.user.getEmail(),
                this.user.getPassword());
    }

    /**
     * Updates a field in the owner table of the database
     * @param param the field being updated
     * @param value the update data
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerUpdate(String param, String value) throws SQLException {
        this.database.updateOwner(param, value);
    }

    /**
     * Removes an owner from the owner table in the database
     * Also removes vehicle(s) registered to that owner if
     * the vehicle is not registered to any other owner(s)
     * @throws SQLException if any error occurred regarding the database
     */
    public void ownerDelete() throws SQLException {
        this.database.deleteOwner();
        this.setUser(null);
    }

    /**
     * Logs out an already logged in owner
     * @throws SQLException if any error occurred regarding the database
     */
    public void logOut() throws SQLException {
        this.database.ownerLogOut();
        this.setUser(null);
    }

    /**
     * Creates vehicle class instances and adds these to a HashSet in an Owner class instance
     * @param vehicles a HashSet of Vehicle instances
     * @throws SQLException if any error occurred regarding the database
     */
    public void addVehicle(HashSet<LinkedList<Object>> vehicles) throws SQLException {
        for (LinkedList<Object> vehicle : vehicles) {
            switch (VehicleEnum.valueOf(vehicle.get(6).toString())) {
                case MA:
                    this.user.getVehicles().add(new VehicleMA(vehicle));
                    break;
                case MB:
                    this.user.getVehicles().add(new VehicleMB(vehicle));
                    break;
                case MC:
                    this.user.getVehicles().add(new VehicleMC(vehicle));
                    break;
                case O:
                    this.user.getVehicles().add(new VehicleO(vehicle));
                    break;
                case T:
                    this.user.getVehicles().add(new VehicleT(vehicle));
            }
        }
    }
}
