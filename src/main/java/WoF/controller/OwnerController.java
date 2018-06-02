package WoF.controller;

import WoF.model.Owner;
import WoF.service.DatabaseEnum;
import org.apache.commons.validator.routines.EmailValidator;
import java.sql.SQLException;

/**
 * OwnerController class - subclass of Controller
 */
public class OwnerController extends Controller {

    /**
     * Logs in an existing registered vehicle owner
     * @param email    the existing user's email
     * @param password the existing user's password
     * @return true if the user is logged in, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected boolean userLogin(String email, String password) throws SQLException {
        wof.setToken();
        String[] owner = wof.getDatabase().ownerLogIn(email, password);

        if (owner != null) {
            wof.setUser(new Owner(owner[0], owner[1], email, password));
            if (owner[2] != null) {
                wof.getUser().setAddressLineOne(owner[2]);
            }
            if (owner[3] != null) {
                wof.getUser().setAddressLineTwo(owner[3]);
            }
            if (owner[4] != null) {
                wof.getUser().setPhone(owner[4]);
            }
            wof.getDatabase().getVehicles();
            return true;
        }
        return false;
    }

    /**
     * Registers and logs in a new vehicle owner
     * @param forename the user's forename
     * @param surname  the user's surname
     * @param email    the user's email
     * @param password the user's password
     * @return true if the user is registered, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean userRegister(String forename, String surname, String email, String password)
            throws SQLException {
        wof.setToken();
        wof.setUser(new Owner(forename.toUpperCase(), surname.toUpperCase(), email, password));
        return wof.ownerInsert();
    }

    /**
     * Sets a forename edit to the Owner instance and stores the edit to the database
     * @param update the update for the forename
     * @return true if the forename is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateForename(String update) throws SQLException {
        if (isValidName(update)) {
            wof.getUser().setForename(update);
            wof.ownerUpdate(DatabaseEnum.FORENAME.getValue(), update);
            return true;
        }
        return false;
    }

    /**
     * Sets a surname edit to the Owner instance and stores the edit to the database
     * @param update the update for the surname
     * @return true if the surname is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updateSurname(String update) throws SQLException {
        if (isValidName(update)) {
            wof.getUser().setSurname(update);
            wof.ownerUpdate(DatabaseEnum.SURNAME.getValue(), update);
            return true;
        }
        return false;
    }

    /**
     * Sets a phone edit to the Owner instance and stores the edit to the database
     * @param update the update for the phone
     * @return true if the phone is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updatePhone(String update) throws SQLException {
        if (isValidNumber(update)) {
            wof.getUser().setPhone(update);
            wof.ownerUpdate(DatabaseEnum.PHONE.getValue(), update);
            return true;
        }
        return false;
    }

    /**
     * Sets a password edit to the Owner instance and stores the edit to the database
     * @param update the update for the password
     * @return true if the password is updated, false otherwise
     * @throws SQLException if any error occurred regarding the database
     */
    protected Boolean updatePassword(String update) throws SQLException {
        if (isValidPassword(update)) {
            wof.getUser().setPassword(update);
            wof.ownerUpdate(DatabaseEnum.PASSWORD.getValue(), update);
            return true;
        }
        return false;
    }

    /**
     * Checks if a password entry is valid (contains alphanumeric, special characters, and no spaces)
     * @param pw the password being validated
     * @return true if the password is valid, false otherwise
     */
    protected Boolean isValidPassword(String pw) {
        return pw != null && pw.length() > 0 && pw.matches(RegexValidationEnum.VALID_PASSWORD.getValue());
    }

    /**
     * Checks if an email entry is a valid email format
     * @param email the email being validated
     * @return true if the email is valid, false otherwise
     */
    protected Boolean isValidEmail(String email) {
        return EmailValidator.getInstance(true).isValid(email);
    }

}
