package WoF.model;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Vehicle history class
 * @author Adam Ross
 */
public class History {

    private final Session wof = Session.getWoF();
    private final String vin;
    private String odometerReading;
    private Timestamp registrationDateNZ;
    private Timestamp wofExpiry;
    private HistoryEnum wofStatus;

    /**
     * Vehicle history constructor
     * @param vin                the vehicles VIN (17 chars)
     * @param odometerReading    the vehicle's odometer reading (null for trailers)
     * @param registrationDateNZ the date the vehicles is first registered in NZ
     * @param wofExpiry          the expiry date of the vehicles most recent WoF
     * @param wofStatus          the status of the vehicles most recent WoF test
     * @throws SQLException if any error occurred regarding the database
     */
    public History(String vin, String odometerReading, Timestamp registrationDateNZ, Timestamp wofExpiry,
                   HistoryEnum wofStatus) throws SQLException {
        this.vin = vin.toUpperCase();
        this.odometerReading = odometerReading;
        this.registrationDateNZ = registrationDateNZ;
        this.wofExpiry = wofExpiry;
        this.wofStatus = wofStatus;
        this.historyInsert();
    }

    /**
     * Gets a vehicles VIN
     * @return a vehicles VIN
     */
    public String getVin() {
        return this.vin;
    }

    /**
     * Gets a vehicle's odometer reading
     * @return a vehicle's odometer reading
     */
    public String getOdometerReading() {
        return this.odometerReading;
    }

    /**
     * Sets a vehicle's odometer reading
     * @param odometerReading a vehicle's odometer reading
     */
    public void setOdometerReading(String odometerReading) {
        this.odometerReading = odometerReading;
    }

    /**
     * Gets a vehicles date of first registration in NZ
     * @return a vehicles date of first registration in NZ
     */
    public Timestamp getRegistrationDateNZ() {
        return this.registrationDateNZ;
    }

    /**
     * Sets a vehicles date of first registration in NZ
     * @param registrationDateNZ a vehicles date of first registration in NZ
     */
    public void setRegistrationDateNZ(Timestamp registrationDateNZ) {
        this.registrationDateNZ = registrationDateNZ;
    }

    /**
     * Gets an expiration date for a vehicles most recent WoF
     * @return an expiration date for a vehicles most recent WoF
     */
    public Timestamp getWofExpiry() {
        return this.wofExpiry;
    }

    /**
     * Sets the expiration date for a vehicles most recent WoF
     * @param wofExpiry the WoF expiry date
     */
    public void setWofExpiry(Timestamp wofExpiry) {
        this.wofExpiry = wofExpiry;
    }

    /**
     * Gets the status of a vehicles most recent WoF test
     * @return the status of a vehicles most recent WoF test
     */
    public HistoryEnum getWofStatus() {
        return this.wofStatus;
    }

    /**
     * Sets the status of a vehicles most recent WoF test
     * @param wofStatus the WoF status
     */
    public void setWofStatus(HistoryEnum wofStatus) {
        this.wofStatus = wofStatus;
    }

    /**
     * Insert a new vehicle to the vehicle table in the database for a logged in owner
     * @throws SQLException if any error occurred regarding the database
     */
    private void historyInsert() throws SQLException {
        wof.getDatabase().insertHistory(this.vin, this.odometerReading, this.registrationDateNZ, this.wofExpiry,
                this.wofStatus.getValue());
    }
}
