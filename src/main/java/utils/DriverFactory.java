package utils;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private static WebDriver driver;
    public static WebDriver getDriver() {
        driver = DriverAdjust.selectDriver();
    return driver;
    }
    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}
