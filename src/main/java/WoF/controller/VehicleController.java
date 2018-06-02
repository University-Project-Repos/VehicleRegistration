package WoF.controller;

import WoF.model.vehicle.VehicleEnum;
import WoF.model.vehicle.FuelEnum;
import WoF.service.DatabaseEnum;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * VehicleController class - subclass of Controller
 */
public class VehicleController extends Controller {

    /**
     * Registers a new vehicle to the vehicle owner, creates a vehicle instance, inserts vehicle data to database
     * @param plate      the vehicles plate
     * @param make       the vehicles make
     * @param model      the vehicles model
     * @param date       the vehicles manufacture date
     * @param addressOne the vehicles address line one
     * @param addressTwo the vehicles address line two
     * @param type       the vehicles type
     * @param fuel       the vehicles fuel type
     * @return true if the vehicle is successfully registered, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean vehicleRegister(String plate, String make, String model, LocalDate date, String addressOne,
                                      String addressTwo, String type, String fuel) throws SQLException {
        if (wof.getDatabase().getVehicleInfo(plate) == null) {
            updateAddressFields(addressOne, addressTwo);
            wof.addVehicle(new HashSet<>(Collections.singletonList(new LinkedList<>(Arrays.asList(plate.toUpperCase(),
                    make.toUpperCase(), model.toUpperCase(), Timestamp.valueOf(date.atStartOfDay()),
                    addressOne.toUpperCase(), addressTwo.toUpperCase(), type.toUpperCase(), fuel.toUpperCase())))));
            return true;
        }
        return false;
    }

    /**
     * Removes a vehicle from being registered to a user and from the database
     * @throws SQLException if any error occurred regarding the database
     */
    protected void vehicleRemove() throws SQLException {
        wof.getUser().getVehicles().remove(wof.getVehicle());
        wof.getDatabase().deleteVehicle(wof.getVehicle());
    }

    /**
     * Sets a make edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle make
     * @return true if the make is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateMake(String update) throws SQLException {
        if (isValidName(update)) {
            wof.getVehicle().setMake(update);
            wof.getDatabase().updateVehicle(DatabaseEnum.MAKE.getValue(), wof.getVehicle().getMake());
            return true;
        }
        return false;
    }

    /**
     * Sets a model edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle model
     * @return true if the model is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateModel(String update) throws SQLException {
        if (isValidName(update)) {
            wof.getVehicle().setModel(update);
            wof.getDatabase().updateVehicle(DatabaseEnum.MODEL.getValue(), wof.getVehicle().getModel());
            return true;
        }
        return false;
    }

    /**
     * Sets a manufacture date edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle manufacture date
     * @return true if the manufacture date is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateManufactureDate(LocalDate update) throws SQLException {
        if (isValidDate(update)) {
            wof.getVehicle().setManufactureDate(Timestamp.valueOf(update.atStartOfDay()));
            wof.getDatabase().updateVehicle(DatabaseEnum.MANUFACTURE_DATE.getValue(), wof.getVehicle().
                    getManufactureDate());
            return true;
        }
        return false;
    }

    /**
     * Sets an address line one edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle address line one
     * @return true if the address line one is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateVehicleAddressOne(String update) throws SQLException {
        if (isValidAddress(update)) {
            updateAddressFields(update, null);
            wof.getVehicle().setAddressLineOne(update);
            wof.getDatabase().updateVehicle(DatabaseEnum.ADDRESS_LINE_ONE.getValue(), wof.getVehicle().
                    getAddressLineOne());
            return true;
        }
        return false;
    }

    /**
     * Sets an address line two edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle address line two
     * @return true if the address line two is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateVehicleAddressTwo(String update) throws SQLException {
        if (isValidAddress(update)) {
            updateAddressFields(null, update);
            wof.getVehicle().setAddressLineTwo(update);
            wof.getDatabase().updateVehicle(DatabaseEnum.ADDRESS_LINE_TWO.getValue(), wof.getVehicle().
                    getAddressLineTwo());
            return true;
        }
        return false;
    }

    /**
     * Sets a vehicle type edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle type
     * @return true if the type is updated, false otherwise
     */
    protected Boolean updateType(String update) {
        try {
            wof.getVehicle().setVehicleType(VehicleEnum.valueOf(update));
            wof.getDatabase().updateVehicle(DatabaseEnum.VEHICLE_TYPE.getValue(), wof.getVehicle().getVehicleType().
                    getValue());

            if (wof.getVehicle().getVehicleType().getValue().equals(VehicleEnum.T.getValue())) {
                updateFuel(FuelEnum.NA.getValue());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets a fuel type edit to the Vehicle instance and stores the edit to the database
     * @param update the update for the vehicle fuel type
     * @return true if the fuel is updated, false otherwise
     */
    protected Boolean updateFuel(String update) {
        try {
            wof.getVehicle().setFuelType(FuelEnum.valueOf(update));
            wof.getDatabase().updateVehicle(DatabaseEnum.FUEL_TYPE.getValue(), wof.getVehicle().getFuelType());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a plate entry is a valid plate format
     * @param plate the plate entry
     * @return true if the string is valid, false otherwise
     */
    protected Boolean isPlateValid(String plate) {
        final int maxPlateLength = 6;
        return isValidId(plate) && plate.length() > 0 && plate.length() <= maxPlateLength;
    }

    /**
     * Updates vehicle owner address fields if they are null
     * @param one address line one
     * @param two address line two
     * @throws SQLException if any error occurred regarding the database
     */
    private void updateAddressFields(String one, String two) throws SQLException {
        if (one != null && wof.getUser().getAddressLineOne() == null) {
            updateAddressOne(one);
        }

        if (two != null && wof.getUser().getAddressLineTwo() == null) {
            updateAddressTwo(two);
        }
    }
}
