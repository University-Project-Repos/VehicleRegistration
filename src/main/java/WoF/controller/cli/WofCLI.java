package WoF.controller.cli;

import java.sql.SQLException;
import java.util.Scanner;
import WoF.controller.Controller;
import WoF.controller.RegexValidationEnum;

/**
 * The WoF app CLI class
 * @author Adam Ross
 */
public class WofCLI extends Controller {

    private String[] command;

    /**
     * Receives, sorts and initiates commands entered by a user
     * @throws SQLException if any error occurred regarding the database
     */
    public WofCLI() throws SQLException {
        introduction();

        while (!readInput()[0].toLowerCase().equals(CLIEnum.EXIT.getValue())) {
            switch (command[0].toLowerCase()) {
                case "user":
                    new UserCLI(command);
                    break;
                case "vehicle":
                    new VehicleCLI(command);
                    break;
                case "history":
                    new HistoryCLI(command);
                    break;
                case "help":
                    help(command);
                    break;
                default:
                    invalidCommand();
                    break;
            }
        }
    }

    /**
     * Scans and reads user input
     * @return the user input as a string[]
     */
    private String[] readInput() {
        if (wof.getUser() != null) {
            System.out.print(wof.getUser().getEmail() + RegexValidationEnum.SPACE.getValue());
        }
        System.out.print(">> ");
        command = new Scanner(System.in).nextLine().split(RegexValidationEnum.SPACE.getValue());
        return command;
    }

    /**
     * Prints to terminal an introduction to the WoF app
     */
    private void introduction() {
        System.out.println("**********************************");
        System.out.println("** WoF Vehicle Registration CLI **");
        System.out.println("**********************************");
        System.out.println("Enter 'help' for command options");
        System.out.println("To terminate the CLI enter 'exit'");
        System.out.println("**********************************");
    }

    /**
     * Prints to terminal helpful instructions for using the app - not the best :)
     */
    private void help(String[] command) {
        System.out.println("Commands for using the WoF Vehicle Registration CLI:");

        if (command.length == 1 || command.length > 1 && command[1].toLowerCase().equals(CLIEnum.USER.getValue())) {
            System.out.println("USER commands:");
            System.out.println(" - user login [email] [password]");
            System.out.println(" - user register [email] [password] [forename] [surname]");
            System.out.println(" - user update [field*] [update]");
            System.out.println(" - user list");
            System.out.println(" - user logout");
            System.out.println(" - user remove");
            System.out.println(" - *Options: - field: - forename");
            System.out.println("                      - surname");
            System.out.println("                      - address-one");
            System.out.println("                      - address-two");
            System.out.println("                      - phone");
            System.out.println("                      - password");
        }

        if (command.length == 1 || command.length > 1 && command[1].toLowerCase().equals(CLIEnum.VEHICLE.getValue())) {
            System.out.println("VEHICLE commands:");
            System.out.println(" - vehicle register [plate**] [make] [model] [manufacture-date**] [address-one] [address-two] [type**] [fuel**]");
            System.out.println(" - vehicle update [plate] [field*] [update**]");
            System.out.println(" - vehicle list");
            System.out.println(" - vehicle list [plate]");
            System.out.println(" - vehicle remove [plate]");
            System.out.println(" - * Options: - field: - make");
            System.out.println("                       - model");
            System.out.println("                       - manufacture-date**");
            System.out.println("                       - address-one");
            System.out.println("                       - address-two");
            System.out.println("                       - type**");
            System.out.println("                       - fuel**");
            System.out.println(" - ** Formatting: - plate: unique combination of 1 - 6 alphanumeric characters");
            System.out.println("                  - manufacture-date: YYYY-MM-DD");
            System.out.println("                  - type: any one of 'ma', 'mb', 'mc', 'o', 't'");
            System.out.println("                  - fuel: any one of 'petrol', 'diesel', 'electric', 'gas', 'other', 'na'");
        }

        if (command.length == 1 || command.length > 1 && command[1].toLowerCase().equals(CLIEnum.HISTORY.getValue())) {
            System.out.println("HISTORY commands:");
            System.out.println(" - history register [plate**] [vin**] [odometer-reading] [registration-date**] [wof-expiry**] [wof-status**]");
            System.out.println(" - history update [field*] [update**]");
            System.out.println(" - history list [plate]");
            System.out.println(" - * Options: - field: - odometer-reading");
            System.out.println("                       - registration-date**");
            System.out.println("                       - wof-expiry**");
            System.out.println("                       - wof-status**");
            System.out.println(" - ** Formatting: - plate: unique combination of 1 - 6 alphanumeric characters");
            System.out.println("                  - vin: unique combination of 17 alphanumerical characters");
            System.out.println("                  - registration-date: YYYY-MM-DD");
            System.out.println("                  - wof-expiry: YYYY-MM-DD");
            System.out.println("                  - wof-status: any one of 'passed' 'failed', 'expired'");
        }
        System.out.println("To terminate the CLI enter 'exit'");
    }

    /**
     * Prints a message to terminal to let user understand an invalid command was entered
     */
    static void invalidCommand() {
        System.out.println("Invalid command. Enter 'help' for a list of valid commands");
    }
}