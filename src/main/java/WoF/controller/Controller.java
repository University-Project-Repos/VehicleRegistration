package WoF.controller;

import WoF.model.Session;
import WoF.model.vehicle.Vehicle;
import WoF.service.DatabaseEnum;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controller super class - poorly designed; improvised.
 */
public class Controller {

    public final Session wof = Session.getWoF();

    /**
     * Checks if a word which should contain only alphanumeric characters is valid
     * @param name the string being validated
     * @return true if the name is valid, false otherwise
     */
    protected Boolean isValidName(String name) {
        return name.matches(RegexValidationEnum.VALID_NAME.getValue());
    }

    /**
     * Checks if an address is valid
     * @param address the address being validated
     * @return true if the address is valid, false otherwise
     */
    protected Boolean isValidAddress(String address) {
        return address.matches(RegexValidationEnum.VALID_ADDRESS.getValue());
    }

    /**
     * Checks if a date is valid, that is not set to after today's date
     * @param date the date being validated
     * @return true if the date is valid, false otherwise
     */
    protected Boolean isValidDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }

    /**
     * Checks if a string is a valid date
     * @param str the string being validated
     * @return true if the string date is valid, false otherwise
     */
    protected Boolean isValidDateString(String str) {
        try {
            return isValidDate(LocalDate.parse(str));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Finds a user's vehicle from the list of registered vehicles to that user
     * @param plate the vehicles plate
     */
    protected Boolean findVehicle(String plate) {
        for (Vehicle vehicle : wof.getUser().getVehicles()) {
            if (plate.toUpperCase().equals(vehicle.getPlate())) {
                wof.setVehicle(vehicle);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the vehicle history for a selected vehicle for a logged-in user
     * @throws SQLException if any error occurred regarding the database
     */
    protected void setVehicleHistory() throws SQLException {
        wof.getVehicle().setHistory(wof.getDatabase().getVehicleHistory());
    }

    /**
     * Checks if a string is a valid integer
     * @param number the string being validated
     * @return true if the string is valid, false otherwise
     */
    Boolean isValidNumber(String number) {
        return number.matches(RegexValidationEnum.VALID_NUMBER.getValue());
    }

    /**
     * Checks if an ID is valid
     * @param id the string being validated as an ID
     */
    Boolean isValidId(String id) {
        return id.toUpperCase().matches(RegexValidationEnum.VALID_ID.getValue());
    }

    /**
     * Sets an address line one edit to the Owner instance and stores the edit to the database
     * @param update the update for the address line one
     * @return true if the address line one is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateAddressOne(String update) throws SQLException {
        if (isValidAddress(update)) {
            wof.getUser().setAddressLineOne(update);
            wof.ownerUpdate(DatabaseEnum.ADDRESS_LINE_ONE.getValue(), update);
            return true;
        }
        return false;
    }

    /**
     * Sets an address line two edit to the Owner instance and stores the edit to the database
     * @param update the update for the address line two
     * @return true if the address line two is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateAddressTwo(String update) throws SQLException {
        if (isValidAddress(update)) {
            wof.getUser().setAddressLineTwo(update);
            wof.ownerUpdate(DatabaseEnum.ADDRESS_LINE_TWO.getValue(), update);
            return true;
        }
        return false;
    }
}
