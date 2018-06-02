package WoF.controller.gui;

/**
 * Constants for GUI
 * @author Adam Ross
 */
enum GUIEnum {

    GUI_TITLE("WoF Registration App"),
    HISTORY_PANE("/view/history.fxml"),
    VEHICLE_PANE("/view/vehicle.fxml"),
    LOGIN_PANE("/view/login.fxml"),
    HOME_PANE("/view/owner.fxml"),
    INVALID_COLOR("invalid-border"),
    INVALID_REGISTRATION(" is already registered to an account"),
    HISTORY_REGISTERED("History is already registered for "),
    REMOVE_PROFILE_MSG("Confirm Profile Removal"),
    REMOVE_VEHICLE_MSG("Confirm Vehicle Removal");

    private final String guiEnum;

    /**
     * Converts a string to a GUI constant enum
     * @param guiEnum the string for converting to a GUI constant enum
     */
    GUIEnum(final String guiEnum) {
        this.guiEnum = guiEnum;
    }

    /**
     * Converts a GUI constant enum to a string
     * @return a GUI constant enum converted to a string
     */
    String getValue() {
        return guiEnum;
    }
}
