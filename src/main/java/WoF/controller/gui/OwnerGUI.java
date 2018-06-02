package WoF.controller.gui;

import WoF.controller.OwnerController;
import WoF.controller.RegexValidationEnum;
import WoF.model.vehicle.Vehicle;
import WoF.service.DatabaseEnum;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Controller class for the Owner/home pane
 * @author Adam Ross
 */
public class OwnerGUI extends OwnerController {

    @FXML
    private ListView<String> vehicleList;

    @FXML
    private Button editVehicleButton;

    @FXML
    private Button editForenameButton;

    @FXML
    private Button saveForenameButton;

    @FXML
    private Button cancelForenameButton;

    @FXML
    private Label forenameLabel;

    @FXML
    private TextField forenameTextField;

    @FXML
    private Button editSurnameButton;

    @FXML
    private Button saveSurnameButton;

    @FXML
    private Button cancelSurnameButton;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField surnameTextField;

    @FXML
    private Button editAddressLineOneButton;

    @FXML
    private Button saveAddressLineOneButton;

    @FXML
    private Button cancelAddressLineOneButton;

    @FXML
    private Label addressLabelOne;

    @FXML
    private TextField addressTextFieldOne;

    @FXML
    private Button editAddressLineTwoButton;

    @FXML
    private Button saveAddressLineTwoButton;

    @FXML
    private Button cancelAddressLineTwoButton;

    @FXML
    private Label addressLabelTwo;

    @FXML
    private TextField addressTextFieldTwo;

    @FXML
    private Button editPhoneButton;

    @FXML
    private Button savePhoneButton;

    @FXML
    private Button cancelPhoneButton;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneTextField;

    /**
     * Initializes the owner/home pane
     */
    @FXML
    public void initialize() {
        wof.setVehicle(null);
        buttonDisable(editVehicleButton);
        disableEditing(editForenameButton, saveForenameButton, cancelForenameButton, forenameTextField, forenameLabel,
                wof.getUser().getForename());
        disableEditing(editSurnameButton, saveSurnameButton, cancelSurnameButton, surnameTextField, surnameLabel,
                wof.getUser().getSurname());
        disableEditing(editAddressLineOneButton, saveAddressLineOneButton, cancelAddressLineOneButton,
                addressTextFieldOne, addressLabelOne, wof.getUser().getAddressLineOne());
        disableEditing(editAddressLineTwoButton, saveAddressLineTwoButton, cancelAddressLineTwoButton,
                addressTextFieldTwo, addressLabelTwo, wof.getUser().getAddressLineTwo());
        disableEditing(editPhoneButton, savePhoneButton, cancelPhoneButton, phoneTextField, phoneLabel,
                wof.getUser().getPhone());
        displayVehicles();
    }

    /**
     * Displays each vehicle registered to the logged in owner in a ListView
     */
    private void displayVehicles() {
        vehicleList.setOnMouseClicked(event -> displayEditVehicleButton(editVehicleButton));
        vehicleList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        HashSet<String> vehicles = new HashSet<>();

        for (Vehicle vehicle : wof.getUser().getVehicles()) {
            vehicles.add(vehicle.getPlate() + "  " + vehicle.getMake() + "  " + vehicle.getModel());
        }

        if (!vehicles.isEmpty()) {
            vehicleList.itemsProperty().bind(new SimpleListProperty<>(FXCollections.observableArrayList(vehicles)));
        }
    }

    /**
     * Enables and displays a button for editing a vehicle when a vehicle is selected in the vehicles ListView
     * @param editVehicle the edit vehicle button being enabled and displayed
     */
    private void displayEditVehicleButton(Button editVehicle) {
        if (vehicleList.getSelectionModel().getSelectedItem() != null) {
            WofGUI.enableEditingButtons(new HashSet<>(Collections.singletonList(editVehicle)));
        }
    }

    /**
     * Enables editing of Owner fields; converts from Label to editable TextField,
     * with save and cancel buttons replacing the edit button
     * @param edit   the edit button
     * @param save   the save button
     * @param cancel the cancel button
     * @param field  the TextField for editing
     * @param label  the Label displaying the edit
     */
    private void enableEditing(Button edit, Button save, Button cancel, TextField field, Label label) {
        buttonDisable(edit);
        WofGUI.enableEditingButtons(new HashSet<>(Arrays.asList(save, cancel)));
        field.setVisible(true);
        label.setVisible(false);
    }

    /**
     * Disables editing of Owner fields; converts from TextField to Label,
     * with the edit button replacing the save and cancel buttons
     * @param edit   the edit button
     * @param save   the save button
     * @param cancel the cancel button
     * @param field  the TextField for editing
     * @param label  the Label displaying the edit
     * @param text   the text being displayed in the Label
     */
    private void disableEditing(Button edit, Button save, Button cancel, TextField field, Label label, String text) {
        WofGUI.enableEditingButtons(new HashSet<>(Collections.singletonList(edit)));
        buttonDisable(save);
        buttonDisable(cancel);
        field.setVisible(false);
        label.setVisible(true);
        field.setText("");
        field.setStyle(null);
        label.setText(text != null && !text.equals("0") ? text : "");
    }

    /**
     * Disables and sets invisible a button
     * @param button the button being disabled
     */
    private void buttonDisable(Button button) {
        button.setDisable(true);
        button.setVisible(false);
    }

    /**
     * Sets the forename field for editing
     */
    @FXML
    public void editForename() {
        enableEditing(editForenameButton, saveForenameButton, cancelForenameButton, forenameTextField, forenameLabel);
        cancelOtherEdits(DatabaseEnum.FORENAME);
    }

    /**
     * Sets a forename edit to the Owner instance and stores the edit to the database
     *
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void saveForenameEdit() throws SQLException {
        if (updateForename(forenameTextField.getText())) {
            disableEditing(editForenameButton, saveForenameButton, cancelForenameButton, forenameTextField,
                    forenameLabel, wof.getUser().getForename());
        } else {
            WofGUI.setFieldInvalid(forenameTextField);
        }
    }

    /**
     * Cancels a forename edit, converts back from TextField to Label
     */
    @FXML
    public void cancelForenameEdit() {
        disableEditing(editForenameButton, saveForenameButton, cancelForenameButton, forenameTextField, forenameLabel,
                wof.getUser().getForename());
    }

    /**
     * Sets the surname field for editing
     */
    @FXML
    public void editSurname() {
        enableEditing(editSurnameButton, saveSurnameButton, cancelSurnameButton, surnameTextField, surnameLabel);
        cancelOtherEdits(DatabaseEnum.SURNAME);
    }

    /**
     * Sets a surname edit to the Owner instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void saveSurnameEdit() throws SQLException {
        if (updateSurname(surnameTextField.getText())) {
            disableEditing(editSurnameButton, saveSurnameButton, cancelSurnameButton, surnameTextField, surnameLabel,
                    wof.getUser().getSurname());
        } else {
            WofGUI.setFieldInvalid(surnameTextField);
        }
    }

    /**
     * Cancels a surname edit, converts back from TextField to Label
     */
    @FXML
    public void cancelSurnameEdit() {
        disableEditing(editSurnameButton, saveSurnameButton, cancelSurnameButton, surnameTextField, surnameLabel,
                wof.getUser().getSurname());
    }

    /**
     * Sets the address line one field for editing
     */
    @FXML
    public void editAddressLineOne() {
        enableEditing(editAddressLineOneButton, saveAddressLineOneButton, cancelAddressLineOneButton,
                addressTextFieldOne, addressLabelOne);
        cancelOtherEdits(DatabaseEnum.ADDRESS_LINE_ONE);
    }

    /**
     * Sets an address line one edit to the Owner instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void saveAddressLineOneEdit() throws SQLException {
        if (updateAddressOne(addressTextFieldOne.getText())) {
            disableEditing(editAddressLineOneButton, saveAddressLineOneButton, cancelAddressLineOneButton,
                    addressTextFieldOne, addressLabelOne, wof.getUser().getAddressLineOne());
        } else {
            WofGUI.setFieldInvalid(addressTextFieldOne);
        }
    }

    /**
     * Cancels an address line one edit, converts back from TextField to Label
     */
    @FXML
    public void cancelAddressLineOneEdit() {
        disableEditing(editAddressLineOneButton, saveAddressLineOneButton, cancelAddressLineOneButton,
                addressTextFieldOne, addressLabelOne, wof.getUser().getAddressLineOne());
    }

    /**
     * Sets the address line two field for editing
     */
    @FXML
    public void editAddressLineTwo() {
        enableEditing(editAddressLineTwoButton, saveAddressLineTwoButton, cancelAddressLineTwoButton,
                addressTextFieldTwo, addressLabelTwo);
        cancelOtherEdits(DatabaseEnum.ADDRESS_LINE_TWO);
    }

    /**
     * Sets an address line two edit to the Owner instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void saveAddressLineTwoEdit() throws SQLException {
        if (updateAddressTwo(addressTextFieldTwo.getText())) {
            disableEditing(editAddressLineTwoButton, saveAddressLineTwoButton, cancelAddressLineTwoButton,
                    addressTextFieldTwo, addressLabelTwo, wof.getUser().getAddressLineTwo());
        } else {
            WofGUI.setFieldInvalid(addressTextFieldTwo);
        }
    }

    /**
     * Cancels an address line two edit, converts back from TextField to Label
     */
    @FXML
    public void cancelAddressLineTwoEdit() {
        disableEditing(editAddressLineTwoButton, saveAddressLineTwoButton, cancelAddressLineTwoButton,
                addressTextFieldTwo, addressLabelTwo, wof.getUser().getAddressLineTwo());
    }

    /**
     * Sets the phone field for editing
     */
    @FXML
    public void editPhone() {
        enableEditing(editPhoneButton, savePhoneButton, cancelPhoneButton, phoneTextField, phoneLabel);
        cancelOtherEdits(DatabaseEnum.PHONE);
    }

    /**
     * Sets a phone edit to the Owner instance and stores the edit to the database
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void savePhoneEdit() throws SQLException {
        if (updatePhone(phoneTextField.getText())) {
            disableEditing(editPhoneButton, savePhoneButton, cancelPhoneButton, phoneTextField, phoneLabel,
                    wof.getUser().getPhone());
        } else {
            WofGUI.setFieldInvalid(phoneTextField);
        }
    }

    /**
     * Cancels a phone edit, converts back from TextField to Label
     */
    @FXML
    public void cancelPhoneEdit() {
        disableEditing(editPhoneButton, savePhoneButton, cancelPhoneButton, phoneTextField, phoneLabel,
                wof.getUser().getPhone());
    }

    /**
     * Cancels any other potential open field edits when a new field edit is started
     * @param field the new field being edited
     */
    private void cancelOtherEdits(DatabaseEnum field) {
        if (!field.equals(DatabaseEnum.FORENAME)) {
            cancelForenameEdit();
        }

        if (!field.equals(DatabaseEnum.SURNAME)) {
            cancelSurnameEdit();
        }

        if (!field.equals(DatabaseEnum.ADDRESS_LINE_ONE)) {
            cancelAddressLineOneEdit();
        }

        if (!field.equals(DatabaseEnum.ADDRESS_LINE_TWO)) {
            cancelAddressLineTwoEdit();
        }

        if (!field.equals(DatabaseEnum.PHONE)) {
            cancelPhoneEdit();
        }
    }

    /**
     * Popup dialog for changing a user's password - passwords are visible
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    public void updatePassword() throws SQLException {
        TextInputDialog dialog = new TextInputDialog(wof.getUser().getPassword());
        dialog.setContentText("Enter new password:");
        dialog.showAndWait();
        updatePassword(dialog.getResult());
    }

    /**
     * Enables the editing of a selected vehicle, changes scene to the Vehicle pane
     * @throws IOException exception for error reading the fxml file
     */
    @FXML
    public void editVehicle() throws IOException {
        findVehicle(vehicleList.getSelectionModel().getSelectedItem().split(RegexValidationEnum.SPACE.getValue())[0]);
        WofGUI.goToVehiclePane();
    }

    /**
     * Removes an Owner profile from the database and sets the scene to the login pane
     * @throws SQLException if any error occurred regarding the database
     * @throws IOException  exception for error reading the fxml file
     */
    @FXML
    public void removeProfile() throws SQLException, IOException {
        if (WofGUI.confirmationPopUp(new Alert(AlertType.CONFIRMATION,
                GUIEnum.REMOVE_PROFILE_MSG.getValue(), ButtonType.OK, ButtonType.CANCEL))) {
            WofGUI.goToLogInPane();
            wof.ownerDelete();
        }
    }

    /**
     * Sets the scene to the vehicle pane for registering a new vehicle
     * @throws IOException exception for error reading the fxml file
     */
    @FXML
    public void registerVehicle() throws IOException {
        WofGUI.goToVehiclePane();
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
}
