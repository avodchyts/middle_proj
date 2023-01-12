package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.time.LocalDate;

public class ListenerTest implements ITestListener {
private static final Logger LOGGER = Logger.getLogger(ListenerTest.class);

    @Override
    public void onTestStart(ITestResult result) {

        LOGGER.info(String.format("%s was started", result.getName()));
        LOGGER.info(LocalFileReader.INSTANCE.getAppPropertiesValue("environment.name"));
        LOGGER.info(LocalFileReader.INSTANCE.getAppPropertiesValue("browser.name"));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info(String.format("%S was passed", result.getName()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info(String.format("%S was failed", result.getName()));
    }

    private void takeScreenshot()  {
        File screenshot = ((TakesScreenshot) DriverManager.getDrivers()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(String.format(".//target/screenshots/%s.png", LocalDate.now())));
        } catch (IOException e){
            LOGGER.error("Failed to save screenshots");
            e.printStackTrace();
        }
    }
}
