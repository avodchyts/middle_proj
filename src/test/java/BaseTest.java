
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;
import utils.DriverManager;
import utils.DriverHook;
import utils.LocalFileReader;

import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.lang.System.setProperty;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private Supplier<WebDriver> driverSupplier;

    protected final WebDriver getDriver() {
        if (Objects.isNull(driverSupplier)) {
            throw new IllegalStateException("Driver source is not set!");
        }
        return this.driverSupplier.get();
    }

    private void setSystemDriver(String browserName) {
        switch (browserName) {
            case "CHROME": {
                System.setProperty("webdriver.chrome.driver", LocalFileReader.INSTANCE.getAppPropertiesValue("chromeDriver.path"));
                break;
            }
            case "EDGE": {
                System.setProperty("webdriver.chrome.driver", LocalFileReader.INSTANCE.getAppPropertiesValue("edgeDriver.path"));
                break;
            }
            default: {
                LOGGER.error(format("Unknown browser name: %s", browserName));
                LOGGER.info(format("default browser is Chrome"));
                System.setProperty("webdriver.chrome.driver", LocalFileReader.INSTANCE.getAppPropertiesValue("chromeDriver.path"));
                break;
            }
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        String browserName = LocalFileReader.INSTANCE.getAppPropertiesValue("browser.name");
        setSystemDriver(browserName);
        Supplier<WebDriver> driverFactory = DriverFactory.selectDriverSupplier(browserName);
        this.driverSupplier = new DriverManager(driverFactory);
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        getDriver().quit();
    }
}
