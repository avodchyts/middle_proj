import config.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import support.DecoratorPipeline;
import support.DeviceFactory;
import ui.support.*;
import support.Decorator;

import java.util.function.Supplier;

import static java.util.Objects.isNull;

public abstract class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private Supplier<WebDriver> driverSupplier;
    private static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);
    protected static final String URL = PROD_DATA.baseUrl();
    protected WebDriver driver;

    static {
        System.setProperty(PROD_DATA.browserSystKey(), PROD_DATA.driverPath());
    }
    public final WebDriver getDriver() {
        if (isNull(driverSupplier)) {
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
        DecoratorPipeline<WebDriver> decorators = new DecoratorPipeline<>(windowMaximizer)
                .addDecorator(new EventFiringDecorator<>(new WebDriverLogger())::decorate)
                .addDecorator(new ScreenshotTakerDecorator()::decorate)
                .addDecorator(new DeviceEmulationDecorator(DeviceFactory.selectDeviceByName(PROD_DATA.deviceName())));
        WebDriver driverFactory = SingletonWebDriver.INSTANCE;
        driverSupplier = new DriverManager(driverFactory, decorators);
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        getDriver().quit();
    }
}
