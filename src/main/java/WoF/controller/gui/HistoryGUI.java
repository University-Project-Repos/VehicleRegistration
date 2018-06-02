package WoF.controller.gui;

import WoF.controller.HistoryController;
import WoF.model.HistoryEnum;
import WoF.model.vehicle.VehicleEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Controller class for the History pane
 * @author Adam Ross
 */
public class HistoryGUI extends HistoryController {

    @FXML
    private TextField vinTextField;

    @FXML
    private Button saveVinButton;

    @FXML
    private TextField plateTextField;

    @FXML
    private Button savePlateButton;

    @FXML
    private TextField odometerReadingTextField;

    @FXML
    private Button saveOdometerReadingButton;

    @FXML
    private DatePicker registrationDatePicker;

    @FXML
    private Button saveRegistrationDateButton;

    @FXML
    private DatePicker wofExpiryDatePicker;

    @FXML
    private Button saveWofExpiryDateButton;

    @FXML
    private ChoiceBox<String> wofStatusChoiceBox;

    @FXML
    private Button saveWofStatusButton;

    @FXML
    private Button historyRegisterButton;

    /**
     * Initializes the History pane to either first registering or editing an existing vehicles history
     */
    @FXML
    public void initialize() {
        initializeChoiceBoxes();
        plateTextField.setText(wof.getVehicle().getPlate());

        if (wof.getVehicle().getHistory() == null) {
            registrationDatePicker.setValue(LocalDate.now());
            wofExpiryDatePicker.setValue(LocalDate.now());
            setWofStatus(HistoryEnum.FAILED);
            WofGUI.enableEditingButtons(new HashSet<>(Collections.singletonList(historyRegisterButton)));
        } else {
            setUpEditing();
        }

        if (wof.getVehicle().getVehicleType() == VehicleEnum.T) {
            saveOdometerReadingButton.setDisable(true);
            WofGUI.disableTextField(odometerReadingTextField);
        }
        wofExpiryDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue.isAfter(LocalDate.now()) && wofStatusChoiceBox.getValue().
                    equals(HistoryEnum.PASSED.getValue())) {
                wofStatusChoiceBox.setValue(HistoryEnum.EXPIRED.getValue());
            }
        });
    }

    /**
     * Sets up the history pane for editing an already existing vehicle history
     */
    private void setUpEditing() {
        WofGUI.reduceFieldWidths(new HashSet<>(Arrays.asList(vinTextField, plateTextField,
                odometerReadingTextField)));
        WofGUI.reduceDateWidths(new HashSet<>(Arrays.asList(registrationDatePicker, wofExpiryDatePicker)));
        WofGUI.reduceChoiceBoxWidths(new HashSet<>(Collections.singletonList(wofStatusChoiceBox)));
        WofGUI.enableEditingButtons(new HashSet<>(Arrays.asList(saveOdometerReadingButton,
                saveRegistrationDateButton,
                saveWofExpiryDateButton, saveWofStatusButton)));
        vinTextField.setText(wof.getVehicle().getHistory().getVin());
        WofGUI.setButtonsVisible(new HashSet<>(Arrays.asList(saveVinButton, savePlateButton)));
        odometerReadingTextField.setText(wof.getVehicle().getHistory().getOdometerReading());
        registrationDatePicker.setValue(wof.getVehicle().getHistory().getRegistrationDateNZ().
                toLocalDateTime().
                toLocalDate());
        wofExpiryDatePicker.setValue(wof.getVehicle().getHistory().getWofExpiry().
                toLocalDateTime().toLocalDate());
        setWofStatus(wof.getVehicle().getHistory().getWofStatus());
        WofGUI.disableTextField(vinTextField);
    }

    /**
     * Populates the WoF status ChoiceBox with WoF status'
     */
    private void initializeChoiceBoxes() {
        wofStatusChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(
                HistoryEnum.PASSED.getValue(), HistoryEnum.FAILED.getValue(), HistoryEnum.EXPIRED.getValue())));
    }

    /**
     * Sets the WoF ChoiceBox to show a given WoF status
     * @param wofStatus the status selected from displaying on the WoF ChoiceBox
     */
    private void setWofStatus(HistoryEnum wofStatus) {
        wofStatusChoiceBox.setValue(wofStatus.getValue());
    }

    /**
     * Sets an odometer reading edit to the History instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void odometerReadingEdit() throws SQLException {
        if (updateOdometerReading(odometerReadingTextField.getText())) {
            WofGUI.setFieldsValid(new HashSet<>(Collections.singletonList(odometerReadingTextField)));
        } else {
            WofGUI.setFieldInvalid(odometerReadingTextField);
        }
    }

    /**
     * Sets a first registered in NZ date edit to the History instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void registrationDateEdit() throws SQLException {
        if (updateRegistrationDate(registrationDatePicker.getValue())) {
            WofGUI.setPickersValid(new HashSet<>(Collections.singletonList(registrationDatePicker)));
        } else {
            WofGUI.setDatePickerInvalid(registrationDatePicker);
        }
    }

    /**
     * Sets a WoF expiry date edit to the History instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void wofExpiryDateEdit() throws SQLException {
        if (updateWofExpiry(wofExpiryDatePicker.getValue(), registrationDatePicker.getValue())) {
            WofGUI.setPickersValid(new HashSet<>(Collections.singletonList(wofExpiryDatePicker)));
        } else {
            WofGUI.setDatePickerInvalid(registrationDatePicker);
        }
    }

    /**
     * Sets a WoF status edit to the History instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void wofStatusEdit() throws SQLException {
        updateWofStatus(wofStatusChoiceBox.getValue());
    }

    /**
     * Registers a vehicle's history, creates a History instance and inserts the history to the database
     * @throws SQLException if any error occurred regarding the database
     * @throws IOException  exception for error reading the fxml file
     */
    @FXML
    public void registerHistory() throws SQLException, IOException {
        WofGUI.setFieldsValid(new HashSet<>(Arrays.asList(vinTextField, odometerReadingTextField)));
        WofGUI.setPickersValid(new HashSet<>(Arrays.asList(registrationDatePicker, wofExpiryDatePicker)));
        boolean registrationValid = true;

        if (!isValidVin(vinTextField.getText())) {
            WofGUI.setFieldInvalid(vinTextField);
            registrationValid = false;
        }

        if (!isOdometerReadingValid(odometerReadingTextField.getText())) {
            WofGUI.setFieldInvalid(odometerReadingTextField);
            registrationValid = false;
        }

        if (!isRegistrationDateValid(registrationDatePicker.getValue())) {
            WofGUI.setDatePickerInvalid(registrationDatePicker);
            registrationValid = false;
        }

        if (!isWofDateValid(wofExpiryDatePicker.getValue(), registrationDatePicker.getValue())) {
            WofGUI.setDatePickerInvalid(wofExpiryDatePicker);
            registrationValid = false;
        }

        if (wof.getDatabase().vinInHistory(vinTextField.getText().toUpperCase())) {
            WofGUI.setFieldInvalid(vinTextField);
            registrationValid = false;
            WofGUI.confirmationPopUp(new Alert(Alert.AlertType.WARNING, GUIEnum.HISTORY_REGISTERED.
                    getValue() + vinTextField.getText(), ButtonType.OK));
        }

        if (registrationValid) {
            historyRegister(vinTextField.getText(), odometerReadingTextField.getText(),
                    registrationDatePicker.getValue(), wofExpiryDatePicker.getValue(),
                    wofStatusChoiceBox.getValue());
            WofGUI.goToVehiclePane();
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
     * Sets the scene to the vehicle pane
     * @throws IOException exception for error reading the fxml file
     */
    @FXML
    public void goBackToVehicle() throws IOException {
        WofGUI.goToVehiclePane();
    }
}
