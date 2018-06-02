package WoF.controller.cli;

/**
 * Constants for CLI
 * @author Adam Ross
 */
enum CLIEnum {

    EXIT("exit"),
    LOGIN("login"),
    LOGOUT("logout"),
    REGISTER("register"),
    UPDATE("update"),
    REMOVE("remove"),
    LIST("list"),
    VEHICLE("vehicle"),
    HISTORY("history"),
    USER("user"),
    USER_DETAILS("User details listed for "),
    FORENAME_DETAILS("Forename:    "),
    SURNAME_DETAILS("Surname:     "),
    ADDRESS_ONE_DETAILS("Address-one: "),
    ADDRESS_TWO_DETAILS("Address-two: "),
    PHONE_DETAILS("Phone:       "),
    VEHICLE_DETAILS("Vehicle details listed for "),
    MAKE_DETAILS("Make:        "),
    MODEL_DETAILS("Model:       "),
    MANUFACTURE_DETAILS("Manufactured:"),
    TYPE_DETAILS("Type:        "),
    FUEL_DETAILS("Fuel:        "),
    HISTORY_DETAILS ("Vehicle history details listed for "),
    INVALID_REGISTRATION(" is already registered to an account"),
    REGISTERED(" successfully registered to "),
    WOF("the WoF app"),
    HISTORY_EXISTS("History is already registered to "),
    HISTORY_REGISTRATION(" history successfully registered for "),
    VIN_DETAILS ("vin:        "),
    ODOMETER_DETAILS ("odometer:   "),
    REGISTERED_DETAILS ("registered: "),
    WOF_EXPIRY_DETAILS ("wof-expiry: "),
    WOF_STATUS_DETAILS ("wof-status: ");

    private final String cliEnum;

    /**
     * Converts a string to a command line constant enum
     * @param cliEnum the string for converting to a command line constant enum
     */
    CLIEnum(final String cliEnum) {
        this.cliEnum = cliEnum;
    }

    /**
     * Converts a command line constant enum to a string
     * @return a command line constant enum converted to a string
     */
    String getValue() {
        return cliEnum;
    }
}