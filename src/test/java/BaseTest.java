
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

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private Supplier<WebDriver> driverSupplier;

    protected final WebDriver getDriver() {
        if (Objects.isNull(driverSupplier)) {
            throw new IllegalStateException("Driver source is not set!");
        }
        return this.driverSupplier.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        String browserName = LocalFileReader.INSTANCE.getAppPropertiesValue("browser.name");
        Supplier<WebDriver> driverFactory = DriverFactory.selectDriverSupplier(browserName);
        this.driverSupplier = new DriverManager(driverFactory);
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        Runtime current = Runtime.getRuntime();
        current.addShutdownHook(new DriverHook(getDriver()));

    }
}
