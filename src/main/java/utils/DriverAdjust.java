package utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Map;

public class DriverAdjust {
    private static final Logger LOGGER = Logger.getLogger(DriverAdjust.class);
    private static WebDriver driver;

    private DriverAdjust() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (LocalFileReader.getAppPropertiesValue("browser.name")) {
                case "Chrome": {
                    System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
                    driver = new ChromeDriver();
                    LOGGER.info("Chrome driver is started");
                    break;
                }
                case "Edge": {
                    driver = new EdgeDriver();
                    break;
                }
                default: {
                    LOGGER.error("Browser name is not correct");
                    break;
                }
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}
