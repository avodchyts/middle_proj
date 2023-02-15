package support;

import config.TestConfig;
import io.qameta.allure.Attachment;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ui.support.SingletonWebDriver;

import static java.lang.String.format;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = Logger.getLogger(TestListener.class);
    public static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info(format("%s was started", result.getName()));
        LOGGER.info(PROD_DATA.environmentName());
        LOGGER.info(PROD_DATA.browserName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info(format("Test was finished %S", context.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info(format("%S was passed", result.getName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info(format("%S was failed", result.getName()));
        saveFailureScreenShot(SingletonWebDriver.INSTANCE);

    }

    @Attachment
    public byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
