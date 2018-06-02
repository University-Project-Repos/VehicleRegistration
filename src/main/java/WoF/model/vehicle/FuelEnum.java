package WoF.model.vehicle;

/**
 * Constants for fuel type classes
 * @author Adam Ross
 */
public enum FuelEnum {

    PETROL ( "PETROL" ),
    DIESEL ( "DIESEL" ),
    ELECTRIC ( "ELECTRIC" ),
    GAS ( "GAS" ),
    OTHER ( "OTHER" ),
    NA ( "NA" );

    private final String fuelEnum;

    /**
     * Converts a string to a fuel type constant enum
     * @param fuelEnum the string being converted to a fuel type constant enum
     */
    FuelEnum ( final String fuelEnum ) {
        this.fuelEnum = fuelEnum.toUpperCase ();
    }

    /**
     * Converts a fuel type constant enum to a string
     * @return a fuel type constant enum as a string
     */
    public String getValue () {
        return fuelEnum;
    }
}

