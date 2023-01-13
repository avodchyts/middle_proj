
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import resources.Data;
import utils.*;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static java.lang.String.format;
import static java.lang.System.setProperty;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private Supplier<WebDriver> driverSupplier;

    private Data propData = ConfigFactory.create(Data.class);
    protected final WebDriver getDriver() {
        if (Objects.isNull(driverSupplier)) {
            throw new IllegalStateException("Driver source is not set!");
        }
        return this.driverSupplier.get();
    }

    protected String getBaseUrl() {
        return propData.baseUrl();
    }

    private void setSystemDriver() {
        System.setProperty(propData.browserSystKey(),propData.driverPath());
    }

    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        setSystemDriver();
        UnaryOperator<WebDriver> windowMaximizer = driver -> {
            driver.manage().window().maximize();
            return driver;
        };
        DecoratorPipeline<WebDriver> decorators = new DecoratorPipeline<>(windowMaximizer);
        Supplier<WebDriver> driverFactory = DriverFactory.selectDriverSupplier(propData.browserName());
        driverSupplier = new DriverManager(driverFactory, decorators);
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        getDriver().quit();
    }
}
