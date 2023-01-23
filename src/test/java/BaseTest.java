import config.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v104.emulation.Emulation;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.*;

import java.util.Optional;
import java.util.function.Supplier;
import static java.util.Objects.isNull;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private Supplier<WebDriver> driverSupplier;
    public static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);
    protected static final String URL = PROD_DATA.baseUrl();
    protected WebDriver driver;
    static {
        System.setProperty(PROD_DATA.browserSystKey(), PROD_DATA.driverPath());
    }
    protected final WebDriver getDriver() {
        if (isNull(driverSupplier)) {
            throw new IllegalStateException("Driver source is not set!");
        }
       return driver;
     }

    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        Decorator<WebDriver> windowMaximizer = driver -> {
            driver.manage().window().maximize();
            return driver;
        };
        DecoratorPipeline<WebDriver> decorators = new DecoratorPipeline<>(windowMaximizer)
                .addDecorator(new EventFiringDecorator<>(new WebDriverLogger())::decorate)
                .addDecorator(new ScreenshotTakerDecorator()::decorate);
        Supplier<WebDriver> driverFactory = DriverFactory.selectDriverSupplier(PROD_DATA.browserName());
        driverSupplier = new DriverManager(driverFactory, decorators);
    }

    protected void setDeviceModeView() {
        DeviceFactory currentDevice = DeviceFactory.selectDeviceByName(PROD_DATA.deviceName());
        DevTools devTools = ((HasDevTools) getDriver()).getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setDeviceMetricsOverride(currentDevice.deviceWidth,
                currentDevice.deviceHeight,
                currentDevice.deviceScale,
                currentDevice.isMobileDevice,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
    }
        @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        getDriver().quit();
    }
}
