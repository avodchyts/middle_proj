package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

import static java.lang.String.format;

public class DriverHook extends Thread {
    private final static Logger LOGGER = Logger.getLogger(DriverHook.class);

    public WebDriver driver;

    public DriverHook(WebDriver driver) {
        this.driver = driver;
    }

    public void driverClose() {
        if (Objects.nonNull(driver)) {
            try {
                driver.quit();
            } catch (Exception e) {
                LOGGER.info(format("Problem with closing driver: %s", e.getMessage()));
            }
        }
    }
}
