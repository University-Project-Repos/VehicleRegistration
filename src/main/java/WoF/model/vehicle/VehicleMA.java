package WoF.model.vehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * MA vehicle type class - subclass of AbstractVehicle
 * @author Adam Ross
 */
public class VehicleMA extends AbstractVehicle implements Vehicle {

    /**
     * MA vehicle type constructor
     * @param parameters the MA vehicle type constructor parameters
     * @throws SQLException if any error occurred regarding the database
     */
    public VehicleMA(LinkedList<Object> parameters) throws SQLException {
        super(parameters.get(0).toString(), parameters.get(1).toString(), parameters.get(2).toString(),
                (Timestamp) parameters.get(3), parameters.get(4).toString(), parameters.get(5).toString(),
                VehicleEnum.MA, FuelEnum.valueOf(parameters.get(7).toString()));
    }
}
