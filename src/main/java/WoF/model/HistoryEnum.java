package WoF.model;

/**
 * Constants for the most recent WoF test status results
 * @author Adam Ross
 */
public enum HistoryEnum {

    PASSED("PASSED"),
    FAILED("FAILED"),
    EXPIRED("EXPIRED");

    private final String historyEnum;

    /**
     * Converts a string to a most recent WoF test status constant enum
     * @param historyEnum the string being converted to a WoF test status constant enum
     */
    HistoryEnum(final String historyEnum) {
        this.historyEnum = historyEnum.toUpperCase();
    }

    /**
     * Converts a most recent WoF test status constant enum to a string
     * @return a most recent WoF test status constant enum as a string
     */
    public String getValue() {
        return historyEnum;
    }

}
