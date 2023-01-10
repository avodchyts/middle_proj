package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverService {
    private static final Logger LOGGER = Logger.getLogger(DriverService.class);
    public static WebDriver driver;

    private DriverService(){}

    public static WebDriver getEnumDriver() {
        DriverValues browserName = DriverValues.valueOf((LocalFileReader.getLocalFileReaderInstance().getAppPropertiesValue("browser.name")));
        switch (browserName) {
            case CHROME: {
                driver = DriverValues.CHROME.getDriver();
                break;
            }
            case EDGE: {
                driver = DriverValues.EDGE.getDriver();
                break;
            }
            default: {
                LOGGER.error("Browser name is not correct");
                LOGGER.info("Default driver is Chrome Driver");
                driver = DriverValues.CHROME.getDriver();
                break;
            }
        }
        return driver;
    }
}
