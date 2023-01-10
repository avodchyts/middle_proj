package utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
    private static WebDriver driver;
    private DriverFactory() {
    }
    public static WebDriver selectDriver() {
        switch (LocalFileReader.getLocalFileReaderInstance().getAppPropertiesValue("browser.name")) {
            case "Chrome": {
                setUpChromeDriver();
                LOGGER.info("Chrome driver is started");
                break;
            }
            case "Edge": {
                driver = new EdgeDriver();
                break;
            }
            default: {
                LOGGER.error("Browser name is not correct");
                LOGGER.info("Default driver is Chrome Driver");
                setUpChromeDriver();
                break;
            }
        }
        return driver;
    }
    private static WebDriver setUpChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        return driver;
    }
}
