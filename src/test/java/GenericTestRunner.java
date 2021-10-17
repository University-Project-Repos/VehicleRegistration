import WoF.model.Session;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features",
        plugin = { "pretty",
                "html: target/site/cucumber-pretty",
                "json: target/cucumber.json"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = "steps")

/*
 * Cucumber test runner
 */
public class GenericTestRunner {

    /**
     * Resets the database and inserts test dummy data
     * @throws SQLException if any error occurred regarding the database
     * @throws FileNotFoundException if any error occurred regarding file reading
     */
    @BeforeClass
    public static void connectToDatabase() throws SQLException, FileNotFoundException {
        final Session wof = Session.getWoF();
        wof.startSession();
        wof.resetDatabase();
    }

    /**
     * Closes the database connection
     * @throws SQLException if any error occurred regarding the database
     */
    @AfterClass
    public static void disconnectFromDatabase() throws SQLException {
        final Session wof = Session.getWoF();
        wof.closeSession();
    }
}