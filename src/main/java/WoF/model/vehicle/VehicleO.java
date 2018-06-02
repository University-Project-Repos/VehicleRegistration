package WoF.model.vehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * Other vehicle type class - subclass of AbstractVehicle
 * @author Adam Ross
 */
public class VehicleO extends AbstractVehicle implements Vehicle {

    /**
     * Other vehicle type constructor
     * @param parameters the other vehicle type constructor parameters
     * @throws SQLException if any error occurred regarding the database
     */
    public VehicleO(LinkedList<Object> parameters) throws SQLException {
        super(parameters.get(0).toString(), parameters.get(1).toString(), parameters.get(2).toString(),
                (Timestamp) parameters.get(3), parameters.get(4).toString(), parameters.get(5).toString(),
                VehicleEnum.O, FuelEnum.valueOf(parameters.get(7).toString()));
    }
}
