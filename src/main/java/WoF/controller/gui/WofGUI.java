package WoF.controller.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

/**
 * Main controller class
 * @author Adam Ross
 */
public class WofGUI extends Application {
    private static Stage stage;
    private static final int reducedWidth = 183;

    /**
     * Starts the GUI by opening the login pane
     * @param primaryStage Primary stage
     * @throws IOException exception for error reading the fxml file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setTitle(GUIEnum.GUI_TITLE.getValue());
        stage.setResizable(true);
        newScene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(GUIEnum.LOGIN_PANE.getValue()))));
    }

    /**
     * Sets a new scene
     * @param scene the new scene being set
     */
    private static void newScene(Parent scene) {
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets the scene to the login pane
     * @throws IOException exception for error reading the fxml file
     */
    static void goToLogInPane() throws IOException {
        newScene(FXMLLoader.load(Objects.requireNonNull(WofGUI.class.getResource(GUIEnum.LOGIN_PANE.getValue()))));
    }

    /**
     * Sets the scene to the owner pane
     * @throws IOException exception for error reading the fxml file
     */
    static void goToOwnerPane() throws IOException {
        newScene(FXMLLoader.load(Objects.requireNonNull(WofGUI.class.getResource(GUIEnum.HOME_PANE.getValue()))));
    }

    /**
     * Sets the scene to the vehicle pane
     * @throws IOException exception for error reading the fxml file
     */
    static void goToVehiclePane() throws IOException {
        newScene(FXMLLoader.load(Objects.requireNonNull(WofGUI.class.getResource(GUIEnum.VEHICLE_PANE.getValue()))));
    }

    /**
     * Sets the scene to the history pane
     * @throws IOException exception for error reading the fxml file
     */
    static void goToHistoryPane() throws IOException {
        newScene(FXMLLoader.load(Objects.requireNonNull(WofGUI.class.getResource(GUIEnum.HISTORY_PANE.getValue()))));
    }

    /**
     * Sets a TextField border to red if it contains an invalid entry
     * @param field teh TextField being set as invalid
     */
    static void setFieldInvalid(TextField field) {
        field.getStyleClass().add(GUIEnum.INVALID_COLOR.getValue());
    }

    /**
     * Sets TextFields to not have any coloured border; to not be invalid
     * @param fields the TextFields being set as valid
     */
    static void setFieldsValid(HashSet<TextField> fields) {
        for (TextField field : fields) {
            field.getStyleClass().remove(GUIEnum.INVALID_COLOR.getValue());
        }
    }

    /**
     * Sets DatePickers to not have any coloured border; to not be invalid
     * @param pickers the DatePickers being set as valid
     */
    static void setPickersValid(HashSet<DatePicker> pickers) {
        for (DatePicker picker : pickers) {
            picker.getStyleClass().remove(GUIEnum.INVALID_COLOR.getValue());
        }
    }

    /**
     * Reduces the width of TextFields to a set constant integer value 'reducedWidth'
     * @param fields the TextFields being reduced in width
     */
    static void reduceFieldWidths(HashSet<TextField> fields) {
        for (TextField field : fields) {
            field.setMaxWidth(reducedWidth);
        }
    }

    /**
     * Reduces the width of DatePickers to a set constant integer value 'reducedWidth'
     * @param dates the DatePickers being reduced in width
     */
    static void reduceDateWidths(HashSet<DatePicker> dates) {
        for (DatePicker date : dates) {
            date.setMaxWidth(reducedWidth);
        }
    }

    /**
     * Reduces the width of ChoiceBoxes to a set constant integer value 'reducedWidth'
     * @param boxes the ChoiceBoxes being reduced in width
     */
    static void reduceChoiceBoxWidths(HashSet<ChoiceBox<String>> boxes) {
        for (ChoiceBox box : boxes) {
            box.setMaxWidth(reducedWidth);
        }
    }

    /**
     * Enables and sets to visible editing buttons
     * @param buttons the buttons being enabled and set to visible for editing
     */
    static void enableEditingButtons(HashSet<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
        setButtonsVisible(buttons);
    }

    /**
     * Sets a DatePicker as invalid by colouring the border red
     * @param datePicker the DatePicker being set to invalid
     */
    static void setDatePickerInvalid(DatePicker datePicker) {
        datePicker.getStyleClass().add(GUIEnum.INVALID_COLOR.getValue());
    }

    /**
     * Alerts a logged in owner with a popup requesting confirmation to execute a command
     * @param alert the confirmation alert being displayed to the logged in owner
     * @return true if alert is confirmed with a yes, false otherwise
     */
    static Boolean confirmationPopUp(Alert alert) {
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    /**
     * Disables a TextField from being editable
     * @param field the TextField being disabled
     */
    static void disableTextField(TextField field) {
        field.setDisable(true);
        field.setEditable(false);
    }

    /**
     * Sets buttons to be visible
     * @param buttons the buttons being set to visible
     */
    static void setButtonsVisible(HashSet<Button> buttons) {
        for (Button button : buttons) {
            button.setVisible(true);
        }
    }
}

