
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.TestNG;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import utils.DriverAdjust;
import utils.ListenerTest;

import java.time.Duration;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    protected WebDriver driver;

//    @BeforeTest(alwaysRun = true)
//    public void setup(ITestContext ctx) {
//        TestRunner runner = (TestRunner) ctx;
//        String path="reports";
//        runner.setOutputDirectory(path+"/output-testng.html");
//
//        String nm = runner.getOutputDirectory();
//        LOGGER.info(nm);
//
//    }

    @BeforeMethod(alwaysRun = true)
    public WebDriver setDriver() {
        driver = DriverAdjust.getDriver();
        return driver;

    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        DriverAdjust.closeDriver();
    }
}
