package WoF.model.vehicle;

/**
 * Constants for vehicle classes
 * @author Adam Ross
 */
public enum VehicleEnum {

    MA ( "MA" ),
    MB ( "MB" ),
    MC ( "MC" ),
    T ( "T" ),
    O ( "O" );

    private final String vehicleEnum;

    /**
     * Converts a string to a vehicle constant enum
     * @param vehicleEnum the string being converted to a vehicle constant enum
     */
    VehicleEnum ( final String vehicleEnum ) {
        this.vehicleEnum = vehicleEnum.toUpperCase ();
    }

    /**
     * Converts a vehicle constant enum to a string
     * @return a vehicle constant enum as a string
     */
    public String getValue () {
        return vehicleEnum;
    }
}
