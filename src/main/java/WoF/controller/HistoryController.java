package WoF.controller;

import WoF.model.History;
import WoF.model.HistoryEnum;
import WoF.model.vehicle.VehicleEnum;
import WoF.service.DatabaseEnum;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * HistoryController class - subclass of Controller
 */
public class HistoryController extends Controller {

    /**
     * Registers a vehicle's history, creates a History instance and inserts the history to the database
     * @param vin              the vehicles vin
     * @param odometerReading  the vehicles current odometer reading
     * @param registrationDate the vehicles NZ registration date
     * @param wofExpiry        the vehicles WoF expiry date
     * @param wofStatus        the vehicles current WoF status
     * @throws SQLException if any error occurred regarding the database
     */
    protected void historyRegister(String vin, String odometerReading, LocalDate registrationDate,
                                   LocalDate wofExpiry, String wofStatus) throws SQLException {
        if (!wof.getVehicle().getVehicleType().equals(VehicleEnum.T)) {
            wof.getVehicle().setHistory(new History(vin.toUpperCase(), odometerReading,
                    Timestamp.valueOf(registrationDate.atStartOfDay()),
                    Timestamp.valueOf(wofExpiry.atStartOfDay()), HistoryEnum.valueOf(wofStatus.toUpperCase())));
        } else {
            wof.getVehicle().setHistory(new History(vin.toUpperCase(), null,
                    Timestamp.valueOf(registrationDate.atStartOfDay()),
                    Timestamp.valueOf(wofExpiry.atStartOfDay()), HistoryEnum.valueOf(wofStatus.toUpperCase())));
        }
    }

    /**
     * Sets an odometer reading edit to the History instance and stores the edit to the database
     * @param update the update for the odometer reading
     * @return true if the odometer reading is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateOdometerReading(String update) throws SQLException {
        if (isValidNumber(update)) {
            wof.getVehicle().getHistory().setOdometerReading(update);
            wof.getDatabase().updateHistory(DatabaseEnum.ODOMETER_READING.getValue(), wof.getVehicle().
                    getHistory().getOdometerReading());
            return true;
        }
        return false;
    }

    /**
     * Sets a first registered in NZ date edit to the History instance and stores the edit to the database
     * @param update the update for the NZ registration date
     * @return true if the registration date is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateRegistrationDate(LocalDate update) throws SQLException {
        if (isRegistrationDateValid(update)) {
            wof.getVehicle().getHistory().setRegistrationDateNZ(Timestamp.valueOf(update.atStartOfDay()));
            wof.getDatabase().updateHistory(DatabaseEnum.NZ_REGISTRATION_DATE.getValue(), wof.getVehicle().
                    getHistory().getRegistrationDateNZ());
            return true;
        }
        return false;
    }

    /**
     * Sets a WoF expiry date edit to the History instance and stores the edit to the database
     * @param update the update for the WoF expiry date
     * @return true if the wof expiry is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateWofExpiry(LocalDate update, LocalDate registration) throws SQLException {
        if (registration == null) {
            registration = wof.getVehicle().getHistory().getRegistrationDateNZ().toLocalDateTime().toLocalDate();
        }
        if (isWofDateValid(update, registration)) {
            wof.getVehicle().getHistory().setWofExpiry(Timestamp.valueOf(update.atStartOfDay()));
            wof.getDatabase().updateHistory(DatabaseEnum.WOF_EXPIRY.getValue(), wof.getVehicle().getHistory().
                    getWofExpiry());
            return true;
        }
        return false;
    }

    /**
     * Sets a WoF status edit to the History instance and stores the edit to the database
     * @param update the update for the WoF status
     * @return true if the status is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateWofStatus(String update) throws SQLException {
        if (isWofStatusValid(update)) {
            wof.getVehicle().getHistory().setWofStatus(HistoryEnum.valueOf(update));
            wof.getDatabase().updateHistory(DatabaseEnum.WOF_STATUS.getValue(), wof.getVehicle().
                    getHistory().getWofStatus().getValue());
            return true;
        }
        return false;
    }

    /**
     * Checks if a vehicle has history registered to it
     * @return true if vehicle has history registered, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean hasHistory() throws SQLException {
        return wof.getDatabase().getVehicleHistory() != null;
    }

    /**
     * Checks if a vin entry is a valid vin format
     * @param vin the vin entry
     * @return true if the string is valid, false otherwise
     */
    protected Boolean isValidVin(String vin) {
        final int vinLength = 17;
        return isValidId(vin) && vin.length() == vinLength;
    }

    /**
     * Checks if a WoF date entry is a valid WoF date format - after manufacture and NZ registration date
     * @param date         the WoF date entry
     * @param registration the NZ registration date
     * @return true if the date is valid, false otherwise
     */
    protected Boolean isWofDateValid(LocalDate date, LocalDate registration) {
        return date.isAfter(wof.getVehicle().getManufactureDate().toLocalDateTime().toLocalDate()) &&
                date.isAfter(registration);
    }

    /**
     * Checks if a WoF status entry is a valid WoF status format
     * @param wofStatus the WoF status entry
     * @return true if the status is valid, false otherwise
     */
    protected Boolean isWofStatusValid(String wofStatus) {
        return Arrays.stream(HistoryEnum.values()).anyMatch((t) -> t.name().equals(wofStatus.toUpperCase()));
    }

    /**
     * Checks if a NZ registration date entry is a valid NZ registration date format - not before manufacture date
     * @param date the NZ registration date entry
     * @return true if the date is valid, false otherwise
     */
    protected Boolean isRegistrationDateValid(LocalDate date) {
        return isValidDate(date) && !date.isBefore(wof.getVehicle().getManufactureDate().toLocalDateTime().toLocalDate());
    }

    /**
     * Checks if an odometer reading entry is a valid odometer reading format
     * @param odometerReading the odometer reading entry
     * @return true if the string is valid, false otherwise
     */
    protected Boolean isOdometerReadingValid(String odometerReading) {
        return wof.getVehicle().getVehicleType().equals(VehicleEnum.T) || isValidNumber(odometerReading);
    }
}
