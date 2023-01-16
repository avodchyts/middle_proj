
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
import java.util.function.UnaryOperator;

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
        if (Objects.isNull(driverSupplier)) {
            throw new IllegalStateException("Driver source is not set!");
        }
       return driver;
     }

    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        UnaryOperator<WebDriver> windowMaximizer = driver -> {
            driver.manage().window().maximize();
            return driver;
        };
        DecoratorPipeline<WebDriver> decorators = new DecoratorPipeline<>(windowMaximizer);
        Supplier<WebDriver> driverFactory = DriverFactory.selectDriverSupplier(PROD_DATA.browserName());
        driverSupplier = new DriverManager(driverFactory, decorators);
        driver = new EventFiringDecorator(new WebDriverLogger()).decorate(driverSupplier.get());


        }
        @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        getDriver().quit();
    }
}
