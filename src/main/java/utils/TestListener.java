package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pages.BasePage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static java.lang.String.format;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = Logger.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info(format("%s was started", result.getName()));
        LOGGER.info(LocalFileReader.INSTANCE.getAppPropertiesValue("environment.name"));
        LOGGER.info(LocalFileReader.INSTANCE.getAppPropertiesValue("browser.name"));
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
        takeScreenshot();
    }

    private void takeScreenshot() {
        File screenshot = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(format(".//target/screenshots/%s.png", LocalDate.now())));
        } catch (IOException e) {
            LOGGER.error("Failed to save screenshots");
            throw new ListenerException("Failed to save screenshots", e);
        }
    }
}
