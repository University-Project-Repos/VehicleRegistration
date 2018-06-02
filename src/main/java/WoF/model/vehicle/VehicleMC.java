package WoF.model.vehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * MC vehicle type class - sub class of AbstractVehicle
 * @author Adam Ross
 */
public class VehicleMC extends AbstractVehicle implements Vehicle {

    /**
     * MC vehicle type constructor
     * @param parameters the MC vehicle type constructor parameters
     * @throws SQLException if any error occurred regarding the database
     */
    public VehicleMC(LinkedList<Object> parameters) throws SQLException {
        super(parameters.get(0).toString(), parameters.get(1).toString(), parameters.get(2).toString(),
                (Timestamp) parameters.get(3), parameters.get(4).toString(), parameters.get(5).toString(),
                VehicleEnum.MC, FuelEnum.valueOf(parameters.get(7).toString()));
    }
}
