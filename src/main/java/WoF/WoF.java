package WoF;

import WoF.controller.gui.WofGUI;
import WoF.controller.cli.WofCLI;
import WoF.model.Session;
import javafx.application.Application;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Starts the WoF app - all design is improvised during refactoring to include a GUI, which is for GUI practice
 * @author Adam Ross
 */
public class WoF {

    private final Session wof = Session.getWoF(); // instantiates the WoF app session

    /**
     * Constructor for the WoF app - starts database, and sets the database for testing
     * @throws FileNotFoundException if any error occurred regarding file reading
     * @throws SQLException if any error occurred regarding the database
     */
    public WoF() throws FileNotFoundException, SQLException {
        wof.startSession(); // connects to the WoF SQLite DB
        wof.resetDatabase(); // resets the DB and inserts test data
    }

    /**
     * Main method - starts the WoF app with either CLI or GUI
     * @param args command input string
     * @throws FileNotFoundException if any error occurred regarding file reading
     * @throws SQLException if any error occurred regarding the database
     */
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        final String arg = "gui"; // constant for the arg that initiates the GUI
        WoF app = new WoF();

        try {
            if (Arrays.asList(args).contains(arg)) {
                Application.launch(WofGUI.class); // launches the basic WoF app GUI
            } else {
                new WofCLI(); // launches the basic WoF app CLI
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        app.closeSession(); // closes the WoF app session
    }

    /**
     * Closes the WoF app session
     * @throws SQLException if any error occurred regarding the database
     */
    private void closeSession() throws SQLException {
        wof.closeSession(); // closes all connections
    }
}