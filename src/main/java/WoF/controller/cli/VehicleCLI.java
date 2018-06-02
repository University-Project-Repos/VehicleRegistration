package WoF.controller.cli;

import WoF.controller.RegexValidationEnum;
import WoF.controller.VehicleController;
import WoF.model.vehicle.VehicleEnum;
import WoF.model.vehicle.FuelEnum;
import WoF.model.vehicle.Vehicle;
import org.apache.commons.lang3.EnumUtils;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The WoF app CLI vehicle class
 * @author Adam Ross
 */
public class VehicleCLI extends VehicleController {
    /**
     * Sorts and initiates a vehicle command entered by a user
     * @param command the command string entered by the user
     * @throws SQLException if any error occurred regarding the database
     */
    public VehicleCLI(String[] command) throws SQLException {
        wof.setVehicle(null);

        if (wof.getUser() != null) {
            if (command.length >= 10 && command[1].toLowerCase().equals(CLIEnum.REGISTER.getValue())) {
                if (wof.getDatabase().getVehicleInfo(command[2].toUpperCase()) == null) {
                    if (isPlateValid(command[2]) && isValidName(command[3]) && isValidName(command[4]) &&
                            isValidDateString(command[5]) && isValidAddress(command[6]) && isValidAddress(command[7])
                            && EnumUtils.isValidEnum(VehicleEnum.class, command[8].toUpperCase()) && EnumUtils.
                            isValidEnum(FuelEnum.class, command[9].toUpperCase()) && vehicleRegister(command[2], command[3],
                            command[4], LocalDate.parse(command[5]), command[6], command[7], command[8], command[9])) {
                            System.out.println(command[2] + CLIEnum.REGISTERED.getValue() + wof.getUser().getEmail());
                    }
                } else {
                    System.out.println(command[2] + CLIEnum.INVALID_REGISTRATION.getValue());
                }
            } else if (command[1].toLowerCase().equals(CLIEnum.UPDATE.getValue()) && command.length >= 5) {
                vehicleUpdate(command[2], command[3], command[4]);
            } else if (command[1].toLowerCase().equals(CLIEnum.REMOVE.getValue()) && command.length >= 3) {
                if (findVehicle(command[2])) {
                    vehicleRemove();
                }
            } else if (command[1].toLowerCase().equals(CLIEnum.LIST.getValue())) {
                vehicleList(command);
            } else {
                WofCLI.invalidCommand();
            }
        }
    }

    /**
     * Finds a vehicle or iterates over each vehicle registered to a user for listing vehicle credentials
     * @param command the vehicle list command entered to extract the vehicle plate details
     */
    private void vehicleList(String[] command) {
        if (command.length > 2 && isPlateValid(command[2].toUpperCase()) && findVehicle(command[2])) {
            vehicleDisplay(wof.getVehicle());
        } else {
            for (Vehicle vehicle : wof.getUser().getVehicles()) {
                vehicleDisplay(vehicle);
            }
        }
    }

    /**
     * Displays a list of vehicle credentials for a logged-in user's vehicle
     * @param vehicle the plate of the vehicle having credentials listed
     */
    private void vehicleDisplay(Vehicle vehicle) {
        System.out.println(CLIEnum.VEHICLE_DETAILS.getValue() + vehicle.getPlate());
        System.out.println(CLIEnum.MAKE_DETAILS.getValue() + vehicle.getMake());
        System.out.println(CLIEnum.MODEL_DETAILS.getValue() + vehicle.getModel());
        System.out.println(CLIEnum.MANUFACTURE_DETAILS.getValue() + vehicle.getManufactureDate().toString().
                split(RegexValidationEnum.SPACE.getValue())[0]);
        System.out.println(CLIEnum.ADDRESS_ONE_DETAILS.getValue() + vehicle.getAddressLineOne());
        System.out.println(CLIEnum.ADDRESS_TWO_DETAILS.getValue() + vehicle.getAddressLineTwo());
        System.out.println(CLIEnum.TYPE_DETAILS.getValue() + vehicle.getVehicleType());
        System.out.println(CLIEnum.FUEL_DETAILS.getValue() + vehicle.getFuelType());
    }

    /**
     * Sets a field edit to the Vehicle instance and stores the edit to the database
     * @param plate  the plate of the vehicle being updated
     * @param field  the vehicle field being updated
     * @param update the update for the vehicle field being updated
     * @throws SQLException if any error occurred regarding the database
     */
    private void vehicleUpdate(String plate, String field, String update) throws SQLException {
        if (findVehicle(plate)) {
            switch (field.toLowerCase()) {
                case "make":
                    if (!updateMake(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "model":
                    if (!updateModel(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "manufacture-date":
                    if (!isValidDateString(update) || !updateManufactureDate(LocalDate.parse(update))) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "address-one":
                    if (!updateVehicleAddressOne(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "address-two":
                    if (!updateVehicleAddressTwo(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "type":
                    if (!updateType(update.toUpperCase())) {
                        WofCLI.invalidCommand();
                    }
                    break;
                case "fuel":
                    if (!updateFuel(update.toUpperCase())) {
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