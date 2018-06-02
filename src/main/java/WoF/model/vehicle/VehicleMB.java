package WoF.model.vehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * MB vehicle type class - subclass of AbstractVehicle
 * @author Adam Ross
 */
public class VehicleMB extends AbstractVehicle implements Vehicle {

    /**
     * MB vehicle type constructor
     * @param parameters the MB vehicle type constructor parameters
     * @throws SQLException if any error occurred regarding the database
     */
    public VehicleMB(LinkedList<Object> parameters) throws SQLException {
        super(parameters.get(0).toString(), parameters.get(1).toString(), parameters.get(2).toString(),
                (Timestamp) parameters.get(3), parameters.get(4).toString(), parameters.get(5).toString(),
                VehicleEnum.MB, FuelEnum.valueOf(parameters.get(7).toString()));
    }
}
