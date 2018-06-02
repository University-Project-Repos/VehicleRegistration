package WoF.controller;

/**
 * Constants for regex validations
 * @author Adam Ross
 */
public enum RegexValidationEnum {

    VALID_NAME ("^[\\w]+[\\w -]*$"),
    VALID_ADDRESS ("^[\\w]+[\\w -,.]*$"),
    VALID_NUMBER ("^[\\d]+$"),
    VALID_ID ("^[\\w]+$"),
    VALID_PASSWORD ("^[a-zA-Z0-9!@#$&()\\\\-`.+,/\\\"]*$"),
    SPACE (" ");

    private final String regexValidationEnum;

    /**
     * Converts a string to a regex validation constant enum
     * @param regexValidationEnum the string for converting to a controller constant enum
     */
    RegexValidationEnum(final String regexValidationEnum) {
        this.regexValidationEnum = regexValidationEnum;
    }

    /**
     * Converts a regex validation constant enum to a string
     * @return a regex validation constant enum converted to a string
     */
    public String getValue() {
        return regexValidationEnum;
    }
}

