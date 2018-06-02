package WoF.model.vehicle;

import WoF.model.Session;
import WoF.model.History;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Vehicle abstract class
 * @author Adam Ross
 */
public abstract class AbstractVehicle implements Vehicle {

    private final Session wof = Session.getWoF();
    private final String plate;
    private String make;
    private String model;
    private Timestamp manufactureDate;
    private String addressLineOne;
    private String addressLineTwo;
    private VehicleEnum vehicleType;
    private FuelEnum fuelType;
    private History history;

    /**
     * Vehicle constructor
     * @param plate           the vehicles plate
     * @param make            the vehicle make
     * @param model           the vehicle WoF.model
     * @param manufactureDate the vehicle manufacture date
     * @param addressLineOne  the vehicles address line one
     * @param addressLineTwo  the vehicles address line two
     * @param vehicleType     the vehicle type
     * @param fuelType        the vehicle fuel type
     * @throws SQLException if any error occurred regarding the database
     */
    AbstractVehicle(String plate, String make, String model, Timestamp manufactureDate, String addressLineOne,
                    String addressLineTwo, VehicleEnum vehicleType, FuelEnum fuelType) throws SQLException{
        this.plate = plate.toUpperCase();
        this.make = make.toUpperCase();
        this.model = model.toUpperCase();
        this.manufactureDate = manufactureDate;
        this.addressLineOne = addressLineOne.toUpperCase();
        this.addressLineTwo = addressLineTwo.toUpperCase();
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.vehicleInsert();
    }

    /**
     * Gets a vehicles plate
     * @return the vehicles plate
     */
    public String getPlate() {
        return this.plate;
    }

    /**
     * Gets a vehicles make
     * @return the vehicles make
     */
    public String getMake() {
        return this.make;
    }

    /**
     * Sets a vehicles make
     * @param make a vehicles make
     */
    public void setMake(String make) {
        this.make = make.toUpperCase();
    }

    /**
     * Gets a vehicles WoF.model
     * @return the vehicles WoF.model
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Sets a vehicles WoF.model
     * @param model a vehicles WoF.model
     */
    public void setModel(String model) {
        this.model = model.toUpperCase();
    }

    /**
     * Gets a vehicles manufacture date
     * @return the vehicles manufacture date
     */
    public Timestamp getManufactureDate() {
        return this.manufactureDate;
    }

    /**
     * Sets a vehicles manufacture date
     * @param manufactureDate a vehicles manufacture date
     */
    public void setManufactureDate(Timestamp manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     * Gets a vehicles address line one
     * @return the vehicles address line one
     */
    public String getAddressLineOne() {
        return this.addressLineOne;
    }

    /**
     * Sets a vehicles address line one
     * @param address a vehicles address line one
     */
    public void setAddressLineOne(String address) {
        this.addressLineOne = address.toUpperCase();
    }

    /**
     * Gets a vehicles address line two
     * @return the vehicles address line two
     */
    public String getAddressLineTwo() {
        return this.addressLineTwo;
    }

    /**
     * Sets a vehicles address line two
     * @param address a vehicles address line two
     */
    public void setAddressLineTwo(String address) {
        this.addressLineTwo = address.toUpperCase();
    }

    /**
     * Gets a vehicles type
     * @return the vehicles type
     */
    public VehicleEnum getVehicleType() {
        return this.vehicleType;
    }

    /**
     * Sets a vehicles type
     * @param vehicleType a vehicles type
     */
    public void setVehicleType(VehicleEnum vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * Gets a vehicles fuel type
     * @return the vehicles fuel type
     */
    public FuelEnum getFuelType() {
        return this.fuelType;
    }

    /**
     * Sets a vehicles fuel type
     * @param fuelType a vehicles fuel type
     */
    public void setFuelType(FuelEnum fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Sets a vehicles history
     * @param history a vehicles history
     */
    public void setHistory(History history) {
        this.history = history;
    }

    /**
     * Gets a vehicles history
     * @return the vehicles history
     */
    public History getHistory() {
        return this.history;
    }

    /**
     * Insert a new vehicle to the vehicle table in the database for a logged in owner
     * @throws SQLException if any error occurred regarding the database
     */
    private void vehicleInsert() throws SQLException {
        wof.getDatabase().insertVehicle(this.plate, this.make, this.model, this.manufactureDate, this.addressLineOne,
                this.addressLineTwo, this.vehicleType.getValue(), this.fuelType.getValue());
    }
}
