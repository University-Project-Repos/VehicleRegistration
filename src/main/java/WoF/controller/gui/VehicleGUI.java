package WoF.controller.gui;

import WoF.controller.VehicleController;
import WoF.model.vehicle.VehicleEnum;
import WoF.model.vehicle.FuelEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Controller class for the Vehicle pane
 * @author Adam Ross
 */
public class VehicleGUI extends VehicleController {

    @FXML
    private TextField plateTextField;

    @FXML
    private Button savePlateButton;

    @FXML
    private TextField makeTextField;

    @FXML
    private Button saveMakeButton;

    @FXML
    private TextField modelTextField;

    @FXML
    private Button saveModelButton;

    @FXML
    private DatePicker manufactureDatePicker;

    @FXML
    private Button saveManufactureDateButton;

    @FXML
    private TextField addressLineOneField;

    @FXML
    private Button saveAddressLineOneButton;

    @FXML
    private TextField addressLineTwoField;

    @FXML
    private Button saveAddressLineTwoButton;

    @FXML
    private ChoiceBox<String> vehicleTypeChoiceBox;

    @FXML
    private Button saveVehicleTypeButton;

    @FXML
    private ChoiceBox<String> fuelTypeChoiceBox;

    @FXML
    private Button saveFuelTypeButton;

    @FXML
    private Button vehicleRegisterButton;

    @FXML
    private Button historyRegisterButton;

    @FXML
    private Button historyEditButton;

    @FXML
    private Button removeVehicleButton;

    /**
     * Initializes the Vehicle pane to either first registering or editing an existing vehicle registration
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void initialize() throws SQLException {
        initializeChoiceBoxes();

        if (wof.getVehicle() == null) {
            manufactureDatePicker.setValue(LocalDate.now());
            setVehicleType(VehicleEnum.MA);
            setFuelType(FuelEnum.PETROL);
            addressLineOneField.setText(wof.getUser().getAddressLineOne());
            addressLineTwoField.setText(wof.getUser().getAddressLineTwo());
            WofGUI.enableEditingButtons(new HashSet<>(Collections.singletonList(vehicleRegisterButton)));
        } else {
            if (wof.getVehicle().getHistory() == null) {
                setVehicleHistory();
            }
            setUpEditing();
        }
    }

    /**
     * Sets up the vehicle pane for editing an already existing vehicle registration
     */
    private void setUpEditing() {
        if (wof.getVehicle().getHistory() != null) {
            WofGUI.enableEditingButtons(new HashSet<>(Collections.singletonList(historyEditButton)));
        } else {
            WofGUI.enableEditingButtons(new HashSet<>(Collections.singletonList(historyRegisterButton)));
        }
        WofGUI.reduceFieldWidths(new HashSet<>(Arrays.asList(plateTextField, makeTextField, modelTextField,
                addressLineOneField, addressLineTwoField)));
        WofGUI.reduceDateWidths(new HashSet<>(Collections.singletonList(manufactureDatePicker)));
        WofGUI.reduceChoiceBoxWidths(new HashSet<>(Arrays.asList(vehicleTypeChoiceBox, fuelTypeChoiceBox)));
        plateTextField.setText(wof.getVehicle().getPlate());
        makeTextField.setText(wof.getVehicle().getMake());
        modelTextField.setText(wof.getVehicle().getModel());
        manufactureDatePicker.setValue(wof.getVehicle().getManufactureDate().toLocalDateTime().
                toLocalDate());
        addressLineOneField.setText(wof.getVehicle().getAddressLineOne());
        addressLineTwoField.setText(wof.getVehicle().getAddressLineTwo());
        setVehicleType(wof.getVehicle().getVehicleType());
        setFuelType(wof.getVehicle().getFuelType());
        WofGUI.disableTextField(plateTextField);
        WofGUI.setButtonsVisible(new HashSet<>(Collections.singletonList(savePlateButton)));
        WofGUI.enableEditingButtons(new HashSet<>(Arrays.asList(saveMakeButton, saveModelButton,
                saveVehicleTypeButton, saveManufactureDateButton, saveAddressLineOneButton, saveAddressLineTwoButton,
                removeVehicleButton)));
    }

    /**
     * Populates the vehicle and fuel type ChoiceBoxes with vehicle and fuel types respectively
     */
    private void initializeChoiceBoxes() {
        if (wof.getVehicle() != null) {
            saveFuelTypeButton.setVisible(true);
        }
        vehicleTypeChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(
                VehicleEnum.MA.getValue(),
                VehicleEnum.MB.getValue(),
                VehicleEnum.MC.getValue(),
                VehicleEnum.O.getValue(),
                VehicleEnum.T.getValue())));
        fuelTypeChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(
                FuelEnum.PETROL.getValue(),
                FuelEnum.DIESEL.getValue(),
                FuelEnum.ELECTRIC.getValue(),
                FuelEnum.GAS.getValue(),
                FuelEnum.OTHER.getValue())));
        vehicleTypeChoiceBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.equals(VehicleEnum.T.getValue())) {
                setFuelType(FuelEnum.NA);
            } else {
                if (wof.getVehicle() == null) {
                    setFuelType(FuelEnum.PETROL);
                } else {
                    setFuelType(wof.getVehicle().getFuelType());
                }
            }
        });
    }

    /**
     * Sets a vehicle type for displaying on the vehicle type ChoiceBox
     * @param vehicleType the vehicle type being displayed on the ChoiceBox
     */
    private void setVehicleType(VehicleEnum vehicleType) {
        vehicleTypeChoiceBox.setValue(vehicleType.getValue());
    }

    /**
     * Sets a fuel type for displaying on the fuel type ChoiceBox
     * @param fuelType the fuel type being displayed on the ChoiceBox
     */
    private void setFuelType(FuelEnum fuelType) {
        fuelTypeChoiceBox.setValue(fuelType.getValue());

        if (vehicleTypeChoiceBox.getValue().equals(VehicleEnum.T.getValue())) {
            fuelTypeChoiceBox.setDisable(true);
            saveFuelTypeButton.setDisable(true);
        } else {
            fuelTypeChoiceBox.setDisable(false);
            saveFuelTypeButton.setDisable(false);
        }
    }

    /**
     * Sets a make edit to the Vehicle instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void makeEdit() throws SQLException {
        if (updateMake(makeTextField.getText())) {
            WofGUI.setFieldsValid(new HashSet<>(Collections.singletonList(makeTextField)));
        } else {
            WofGUI.setFieldInvalid(makeTextField);
        }
    }

    /**
     * Sets a model edit to the Vehicle instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void modelEdit() throws SQLException {
        if (updateModel(modelTextField.getText())) {
            WofGUI.setFieldsValid(new HashSet<>(Collections.singletonList(modelTextField)));
        } else {
            WofGUI.setFieldInvalid(modelTextField);
        }
    }

    /**
     * Sets a manufacture date edit to the Vehicle instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void manufactureDateEdit() throws SQLException {
        if (updateManufactureDate(manufactureDatePicker.getValue())) {
            WofGUI.setPickersValid(new HashSet<>(Collections.singletonList(manufactureDatePicker)));
        } else {
            WofGUI.setDatePickerInvalid(manufactureDatePicker);
        }
    }

    /**
     * Sets an address line one edit to the Vehicle instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void addressLineOneFieldEdit() throws SQLException {
        if (updateVehicleAddressOne(addressLineOneField.getText())) {
            WofGUI.setFieldsValid(new HashSet<>(Collections.singletonList(addressLineOneField)));
        } else {
            WofGUI.setFieldInvalid(addressLineOneField);
        }
    }

    /**
     * Sets an address line two edit to the Vehicle instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void addressLineTwoFieldEdit() throws SQLException {
        if (updateVehicleAddressTwo(addressLineTwoField.getText())) {
            WofGUI.setFieldsValid(new HashSet<>(Collections.singletonList(addressLineTwoField)));
        } else {
            WofGUI.setFieldInvalid(addressLineTwoField);
        }
    }

    /**
     * Sets a vehicle type edit to the Vehicle instance and stores the edit to the database
     */
    @FXML
    public void vehicleTypeEdit() {
        updateType(vehicleTypeChoiceBox.getValue());
    }

    /**
     * Sets a fuel type edit to the Vehicle instance and stores the edit to the database
     */
    @FXML
    public void fuelTypeEdit() {
        if (fuelTypeChoiceBox.getValue().equals(FuelEnum.NA.getValue()) &&
                !vehicleTypeChoiceBox.getValue().equals(VehicleEnum.T.getValue())) {
            fuelTypeChoiceBox.getStyleClass().add(GUIEnum.INVALID_COLOR.getValue());
        } else {
            updateFuel(fuelTypeChoiceBox.getValue());
            fuelTypeChoiceBox.getStyleClass().remove(GUIEnum.INVALID_COLOR.getValue());
        }
    }

    /**
     * Registers a new vehicle to the vehicle owner, creates a vehicle instance, inserts vehicle data to database
     * @throws IOException  exception for error reading the fxml file
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void registerVehicle() throws IOException, SQLException {
        WofGUI.setFieldsValid(new HashSet<>(Arrays.asList(plateTextField, makeTextField, modelTextField,
                addressLineOneField, addressLineTwoField)));
        fuelTypeChoiceBox.getStyleClass().remove(GUIEnum.INVALID_COLOR.getValue());
        boolean registrationValid = true;

        if (!isPlateValid(plateTextField.getText())) {
            WofGUI.setFieldInvalid(plateTextField);
            registrationValid = false;
        }

        if (!isValidName(makeTextField.getText())) {
            WofGUI.setFieldInvalid(makeTextField);
            registrationValid = false;
        }

        if (!isValidName(modelTextField.getText())) {
            WofGUI.setFieldInvalid(modelTextField);
            registrationValid = false;
        }

        if (!isValidDate(manufactureDatePicker.getValue())) {
            WofGUI.setDatePickerInvalid(manufactureDatePicker);
            registrationValid = false;
        }

        if (!isValidAddress(addressLineOneField.getText())) {
            WofGUI.setFieldInvalid(addressLineOneField);
            registrationValid = false;
        }

        if (!isValidAddress(addressLineTwoField.getText())) {
            WofGUI.setFieldInvalid(addressLineTwoField);
            registrationValid = false;
        }

        if (fuelTypeChoiceBox.getValue().equals(FuelEnum.NA.getValue()) &&
                !vehicleTypeChoiceBox.getValue().equals(VehicleEnum.T.getValue())) {
            fuelTypeChoiceBox.getStyleClass().add(GUIEnum.INVALID_COLOR.getValue());
            registrationValid = false;
        }

        if (wof.getDatabase().getVehicleInfo(plateTextField.getText().toUpperCase()) != null) {
            WofGUI.setFieldInvalid(plateTextField);
            registrationValid = false;
            WofGUI.confirmationPopUp(new Alert(Alert.AlertType.WARNING, plateTextField.getText()
                    + GUIEnum.INVALID_REGISTRATION.getValue(), ButtonType.OK));
        }

        if (registrationValid) {
            vehicleRegister(plateTextField.getText(), makeTextField.getText(),
                modelTextField.getText(), manufactureDatePicker.getValue(), addressLineOneField.getText(),
                addressLineTwoField.getText(), vehicleTypeChoiceBox.getValue(), fuelTypeChoiceBox.getValue());
            goHome();
        }
    }

    /**
     * Sets the scene to the history pane
     * @throws IOException exception for error reading the fxml file
     */
    @FXML
    public void registerHistory() throws IOException {
        WofGUI.goToHistoryPane();
    }

    /**
     * Removes a vehicle from the owners vehicles list, and removes vehicle data from the database
     * @throws SQLException if any error occurred regarding the database
     * @throws IOException  exception for error reading the fxml file
     */
    @FXML
    public void removeVehicle() throws SQLException, IOException {
        if (WofGUI.confirmationPopUp(new Alert(AlertType.CONFIRMATION,
                GUIEnum.REMOVE_VEHICLE_MSG.getValue(), ButtonType.OK, ButtonType.CANCEL))) {
            vehicleRemove();
            WofGUI.goToOwnerPane();
        }
    }

    /**
     * Logs an owner out and sets the scene to the login pane
     * @throws IOException  exception for error reading the fxml file
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void logoutOwner() throws IOException, SQLException {
        WofGUI.goToLogInPane();
        wof.logOut();
    }

    /**
     * Sets the scene to the owner/home pane
     * @throws IOException exception for error reading the fxml file
     */
    @FXML
    public void goHome() throws IOException {
        WofGUI.goToOwnerPane();
    }
}