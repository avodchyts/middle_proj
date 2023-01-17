
import config.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.*;

import java.util.Objects;
import java.util.function.Supplier;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private Supplier<WebDriver> driverSupplier;
    public static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);
    protected static final String URL = PROD_DATA.baseUrl();

    static {
        System.setProperty(PROD_DATA.browserSystKey(), PROD_DATA.driverPath());
    }

    protected final WebDriver getDriver() {
        if (Objects.isNull(driverSupplier)) {
            throw new IllegalStateException("Driver source is not set!");
        }
        return driverSupplier.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        Decorator<WebDriver> windowMaximizer = driver -> {
            driver.manage().window().maximize();
            return driver;
        };
        DecoratorPipeline<WebDriver> decorators = new DecoratorPipeline<>(windowMaximizer);
        decorators.addDecorator((Decorator<WebDriver>) new EventFiringDecorator(new WebDriverLogger()));
        Supplier<WebDriver> driverFactory = DriverFactory.selectDriverSupplier(PROD_DATA.browserName());
        driverSupplier = new DriverManager(driverFactory, decorators);
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        getDriver().quit();
    }
}
