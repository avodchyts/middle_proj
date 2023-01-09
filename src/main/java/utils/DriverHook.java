package utils;

import org.openqa.selenium.WebDriver;

public class DriverHook extends Thread {
    WebDriver driver;
    public  void driverClose() {
        if (!driver.toString().equals(null)) {
            driver.quit();
            driver = null;
        }
    }
}
