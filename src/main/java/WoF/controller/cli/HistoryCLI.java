package WoF.controller.cli;

import WoF.controller.HistoryController;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The WoF app CLI history class
 * @author Adam Ross
 */
class HistoryCLI extends HistoryController {

    /**
     * Sorts and initiates a history command entered by a user
     * @param command the command string entered by the user
     * @throws SQLException if any error occurred regarding the database
     */
    HistoryCLI(String[] command) throws SQLException {
        wof.setVehicle(null);

        if (wof.getUser() != null && findVehicle(command[2])) {
            if (command.length >= 7 && command[1].toLowerCase().equals(CLIEnum.REGISTER.getValue())) {
                if (hasHistory()) {
                    System.out.println(CLIEnum.HISTORY_EXISTS.getValue() + command[2]);
                } else if (wof.getDatabase().vinInHistory(command[3].toUpperCase())) {
                    System.out.println(command[3] + CLIEnum.INVALID_REGISTRATION.getValue());
                } else if (isValidVin(command[3]) && isOdometerReadingValid(command[4]) && isValidDateString(command[5])
                        && isValidDateString(command[6]) && isWofStatusValid(command[7]) &&
                        isWofDateValid(LocalDate.parse(command[6]), LocalDate.parse(command[5]))) {
                    historyRegister(command[3], command[4], LocalDate.parse(command[5]), LocalDate.parse(command[6]),
                            command[7]);
                    System.out.println(command[2] + CLIEnum.HISTORY_REGISTRATION.getValue() + wof.getUser().getEmail());
                }
            } else if (command.length >= 5 && command[1].toLowerCase().equals(CLIEnum.UPDATE.getValue())) {
                historyUpdate(command[2], command[3], command[4]);
            } else if (command[1].toLowerCase().equals(CLIEnum.LIST.getValue())) {
                historyList(command[2]);
            } else {
                WofCLI.invalidCommand();
            }
        }
    }

    /**
     * Displays a list of vehicle history credentials for a logged-in user's vehicle
     * @param plate the plate of the vehicle having credentials listed
     * @throws SQLException if any error occurred regarding the database
     */
    private void historyList(String plate) throws SQLException {
        if (findVehicle(plate)) {
            setVehicleHistory();
            System.out.println(CLIEnum.HISTORY_DETAILS.getValue() + wof.getVehicle().getPlate());
            if (hasHistory()) {
                System.out.println(CLIEnum.VIN_DETAILS.getValue() + wof.getVehicle().getHistory().getVin());
                System.out.println(CLIEnum.ODOMETER_DETAILS.getValue() + wof.getVehicle().getHistory().getOdometerReading());
                System.out.println(CLIEnum.REGISTERED_DETAILS.getValue() + wof.getVehicle().getHistory().getRegistrationDateNZ());
                System.out.println(CLIEnum.WOF_EXPIRY_DETAILS.getValue() + wof.getVehicle().getHistory().getWofExpiry());
                System.out.println(CLIEnum.WOF_STATUS_DETAILS.getValue() + wof.getVehicle().getHistory().getWofStatus());
            }
        }
    }

    /**
     * Sets a field edit to the History instance and stores the edit to the database
     * @param plate  the plate of the vehicle history being updated
     * @param field  the vehicle history field being updated
     * @param update the update for the vehicle history field being updated
     * @throws SQLException if any error occurred regarding the database
     */
    private void historyUpdate(String plate, String field, String update) throws SQLException {
        if (findVehicle(plate)) {
            switch (field.toLowerCase()) {
                case "odometer-reading":
                    if (!updateOdometerReading(update)) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "registration-date":
                    if (!isValidDateString(update) || !updateRegistrationDate(LocalDate.parse(update))) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "wof-expiry":
                    if (!isValidDateString(update) || !updateWofExpiry(LocalDate.parse(update), null)) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "wof-status":
                    if (!updateWofStatus(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                default:
                    WofCLI.invalidCommand();
            }
        }
    }
}