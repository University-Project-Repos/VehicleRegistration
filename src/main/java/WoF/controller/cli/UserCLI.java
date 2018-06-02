package WoF.controller.cli;

import WoF.controller.OwnerController;
import java.sql.SQLException;

/**
 * The WoF app CLI user class
 * @author Adam Ross
 */
public class UserCLI extends OwnerController {

    /**
     * Sorts and initiates a user command entered by a user
     * @param command the command string entered by the user
     * @throws SQLException if any error occurred regarding the database
     */
    public UserCLI(String[] command) throws SQLException {
        if (command.length >= 6 && command[1].toLowerCase().equals(CLIEnum.REGISTER.getValue())) {
            if (isValidPassword(command[3]) && isValidEmail(command[2]) && isValidName(command[4]) &&
                    isValidName(command[5])) {
                if (userRegister(command[4], command[5], command[2], command[3])) {
                    System.out.println(wof.getUser().getEmail() + CLIEnum.REGISTERED.getValue() + CLIEnum.WOF.getValue());
                } else {
                    wof.setUser(null);
                    System.out.println(command[2] + CLIEnum.INVALID_REGISTRATION.getValue());
                }
            }
        } else if (command[1].toLowerCase().equals(CLIEnum.LOGIN.getValue()) && command.length >= 4) {
            if (isValidPassword(command[3]) && isValidEmail(command[2])) {
                userLogin(command[2], command[3]);
            }
        } else if (wof.getUser() != null && command[1].toLowerCase().equals(CLIEnum.UPDATE.getValue()) &&
                command.length >= 4) {
            userUpdate(command[2], command[3]);
        } else if (wof.getUser() != null && command[1].toLowerCase().equals(CLIEnum.LIST.getValue())) {
            userList();
        } else if (wof.getUser() != null && command[1].toLowerCase().equals(CLIEnum.LOGOUT.getValue())) {
            wof.logOut();
        } else if (wof.getUser() != null && command[1].toLowerCase().equals(CLIEnum.REMOVE.getValue())) {
            wof.ownerDelete();
        } else {
            WofCLI.invalidCommand();
        }
    }

    /**
     * Displays a list of user credentials for a logged-in user
     */
    private void userList() {
        System.out.println(CLIEnum.USER_DETAILS.getValue() + wof.getUser().getEmail());
        System.out.println(CLIEnum.FORENAME_DETAILS.getValue() + wof.getUser().getForename());
        System.out.println(CLIEnum.SURNAME_DETAILS.getValue() + wof.getUser().getSurname());
        System.out.println(CLIEnum.ADDRESS_ONE_DETAILS.getValue() + wof.getUser().getAddressLineOne());
        System.out.println(CLIEnum.ADDRESS_TWO_DETAILS.getValue() + wof.getUser().getAddressLineTwo());
        System.out.println(CLIEnum.PHONE_DETAILS.getValue() + wof.getUser().getPhone());
    }

    /**
     * Sets a field edit to the History instance and stores the edit to the database
     * @param field  the user field being updated
     * @param update the update for the user field being updated
     * @throws SQLException if any error occurred regarding the database
     */
    private void userUpdate(String field, String update) throws SQLException {
        if (wof.getUser() != null) {
            switch (field.toLowerCase()) {
                case "forename":
                    if (!updateForename(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "surname":
                    if (!updateSurname(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "address-one":
                    if (!updateAddressOne(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "address-two":
                    if (!updateAddressTwo(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "phone":
                    if (!updatePhone(update)) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "password":
                    if (!updatePassword(update)) {
                        WofCLI.invalidCommand();
                    }
                    break;
                default:
                    WofCLI.invalidCommand();
                    break;
            }
        }
    }
}