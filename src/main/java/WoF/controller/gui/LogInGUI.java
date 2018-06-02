package WoF.controller.gui;

import WoF.controller.OwnerController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Controller class for logging in and/or registering a vehicle owner
 * @author Adam Ross
 */
public class LogInGUI extends OwnerController {

    @FXML
    private TextField forename;

    @FXML
    private TextField surname;

    @FXML
    private TextField regEmail;

    @FXML
    private PasswordField regPassword;

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label loginLabel;

    @FXML
    private Label registerLabel;

    /**
     * Registers and logs in a new vehicle owner
     */
    @FXML
    private void register() throws SQLException {
        boolean registrationValid = true;

        if (!isValidName(forename.getText())) {
            WofGUI.setFieldInvalid(forename);
            registrationValid = false;
        }

        if (!isValidName(surname.getText())) {
            WofGUI.setFieldInvalid(surname);
            registrationValid = false;
        }

        if (!isValidEmail(regEmail.getText())) {
            WofGUI.setFieldInvalid(regEmail);
            registrationValid = false;
        }

        if (!isValidPassword(regPassword.getText())) {
            WofGUI.setFieldInvalid(regPassword);
            registrationValid = false;
        }

        if (registrationValid) {
            registerLabel.setVisible(false);

            if (userRegister(forename.getText(), surname.getText(), regEmail.getText(),
                    regPassword.getText())) {
                try {
                    WofGUI.goToOwnerPane();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                WofGUI.setFieldInvalid(regEmail);
                WofGUI.confirmationPopUp(new Alert(Alert.AlertType.WARNING,
                        regEmail.getText() + GUIEnum.INVALID_REGISTRATION.getValue(), ButtonType.OK));
            }
        }
        resetFields(new HashSet<>(Arrays.asList(loginEmail, loginPassword)));
        registerLabel.setVisible(true);
    }

    /**
     * Logs in an existing registered vehicle owner
     * @throws SQLException if any error occurred regarding the database
     */
    @FXML
    private void logIn() throws SQLException {
        boolean loginValid = true;

        if (!isValidEmail(loginEmail.getText())) {
            WofGUI.setFieldInvalid(loginEmail);
            loginValid = false;
        }

        if (!isValidPassword(loginPassword.getText())) {
            WofGUI.setFieldInvalid(loginPassword);
            loginValid = false;
        }

        if (loginValid) {
            loginLabel.setVisible(false);

            if (userLogin(loginEmail.getText(), loginPassword.getText())) {
                try {
                    WofGUI.goToOwnerPane();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        resetFields(new HashSet<>(Arrays.asList(forename, surname, regEmail, regPassword)));
        loginLabel.setVisible(true);
    }

    /**
     * Resets fields not being edited for in the case they are highlighted as invalid
     * @param fields the TextFields not involved in either registration or logging in
     */
    private void resetFields(HashSet<TextField> fields) {
        WofGUI.setFieldsValid(fields);

        for (TextField field : fields) {
            field.setText("");
        }

        if (fields.size() == 4) {
            registerLabel.setVisible(false);
        } else {
            loginLabel.setVisible(false);
        }
    }
}
