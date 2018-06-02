package WoF.service;

/**
 * Constants for the database
 * @author Adam Ross
 */
public enum DatabaseEnum {

    DB_URL("jdbc:sqlite:AssignmentOneDB.sqlite"),
    DB_SCHEMA("DB_schema.sql"),
    DB_SAMPLE("DB_sample.sql"),
    VIN("vin"),
    PLATE("plate"),
    MAKE("make"),
    MODEL("model"),
    MANUFACTURE_DATE("manufacture_date"),
    ADDRESS_LINE_ONE("address_one"),
    ADDRESS_LINE_TWO("address_two"),
    VEHICLE_TYPE("vehicle_type"),
    FUEL_TYPE("fuel_type"),
    VEHICLE_VIN("vehicle_vin"),
    ODOMETER_READING("odometer_reading"),
    NZ_REGISTRATION_DATE("nz_registration_date"),
    WOF_EXPIRY("wof_expiry"),
    WOF_STATUS("wof_status"),
    PASSWORD ("password"),
    FORENAME("forename"),
    SURNAME("surname"),
    EMAIL("email"),
    PHONE("phone");

    private final String databaseEnum;

    /**
     * Converts a string to a database constant enum
     * @param databaseEnum the string being converted to a database constant enum
     */
    DatabaseEnum(final String databaseEnum) {
        this.databaseEnum = databaseEnum;
    }

    /**
     * Converts a database constant enum to a string
     * @return a constant database enum converted to a string
     */
    public String getValue() {
        return databaseEnum;
    }
}
