package WoF.model.vehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * vehicle trailer class - subclass of AbstractVehicle
 * @author Adam Ross
 */
public class VehicleT extends AbstractVehicle implements Vehicle {

    /**
     * Vehicle trailer constructor
     * @param parameters the vehicle trailer constructor parameters
     * @throws SQLException if any error occurred regarding the database
     */
    public VehicleT(LinkedList<Object> parameters) throws SQLException {
        super(parameters.get(0).toString(), parameters.get(1).toString(), parameters.get(2).toString(),
                (Timestamp) parameters.get(3), parameters.get(4).toString(), parameters.get(5).toString(),
                VehicleEnum.T, FuelEnum.NA);
    }
}
