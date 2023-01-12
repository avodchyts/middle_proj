
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverManager;
import utils.DriverHook;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public WebDriver setDriver() {
        driver = DriverManager.getInstance().getDriver();
        driver.manage().window().maximize();
        return driver;

    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        DriverManager.getInstance().closeDriver();
        Runtime current = Runtime.getRuntime();
        current.addShutdownHook(new DriverHook());
    }
}
