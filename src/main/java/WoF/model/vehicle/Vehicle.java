package WoF.model.vehicle;

import WoF.model.History;
import java.sql.Timestamp;

/**
 * Vehicle interface
 * @author Adam Ross
 */
public interface Vehicle {

    /**
     * Gets a vehicles plate
     * @return the vehicles plate
     */
    String getPlate();

    /**
     * Gets a vehicles make
     * @return the vehicles make
     */
    String getMake();

    /**
     * Sets a vehicles make
     * @param make a vehicles make
     */
    void setMake(String make);

    /**
     * Gets a vehicles WoF.model
     * @return the vehicles WoF.model
     */
    String getModel();

    /**
     * Sets a vehicles WoF.model
     * @param model a vehicles WoF.model
     */
    void setModel(String model);

    /**
     * Gets a vehicles manufacture date
     * @return the vehicles manufacture date
     */
    Timestamp getManufactureDate();

    /**
     * Sets a vehicles manufacture date
     * @param manufactureDate a vehicles manufacture date
     */
    void setManufactureDate(Timestamp manufactureDate);

    /**
     * Gets a vehicles address line one
     * @return the vehicles address line one
     */
    String getAddressLineOne();

    /**
     * Sets a vehicles address line one
     * @param address a vehicles address line one
     */
    void setAddressLineOne(String address);

    /**
     * Gets a vehicles address line two
     * @return the vehicles address line two
     */
    String getAddressLineTwo();

    /**
     * Sets a vehicles address line two
     * @param address a vehicles address line two
     */
    void setAddressLineTwo(String address);

    /**
     * Gets a vehicles type
     * @return the vehicles type
     */
    VehicleEnum getVehicleType();

    /**
     * Sets a vehicles type
     * @param vehicleType a vehicles type
     */
    void setVehicleType(VehicleEnum vehicleType);

    /**
     * Gets a vehicles fuel type
     * @return the vehicles fuel type
     */
    FuelEnum getFuelType();

    /**
     * Sets a vehicles fuel type
     * @param fuelType a vehicles fuel type
     */
    void setFuelType(FuelEnum fuelType);

    /**
     * Sets a vehicles history
     * @param history a vehicles history
     */
    void setHistory (History history);

    /**
     * Gets a vehicles history
     * @return the vehicles history
     */
    History getHistory();
}

