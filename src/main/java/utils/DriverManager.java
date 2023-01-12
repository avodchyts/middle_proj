package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static DriverManager driverManager;
    private static WebDriver driver;

    private DriverManager() {
        driver = DriverFactory.selectDriver();
    }

    public static DriverManager getInstance() {
        driverManager = new DriverManager();
        return driverManager;
    }

    public WebDriver getDriver() {
        return driver;
    }
    public void closeDriver() {
        driver.quit();
    }
}
