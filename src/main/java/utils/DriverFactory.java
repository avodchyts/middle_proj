package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.function.Supplier;

import static java.lang.String.format;

public enum DriverFactory implements Supplier<WebDriver> {
    CHROME(ChromeDriver::new),
    EDGE(EdgeDriver::new);

    private DriverFactory(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
    private final Supplier<WebDriver> driverSupplier;

    public WebDriver get() {
        return this.driverSupplier.get();
    }

    public static Supplier<WebDriver> selectDriverSupplier(String driverName) {
        DriverFactory dS = DriverFactory.valueOf(driverName.toUpperCase());
        switch (dS) {
            case CHROME: {
                System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
                return DriverFactory.CHROME;
            }
            case EDGE: {
                return DriverFactory.EDGE;
            }
            default: {
                LOGGER.error(format("Could not find driver supplier for {}", driverName));
                LOGGER.info(format("Could not find driver supplier for {}, getting Chrome", driverName));
                return DriverFactory.CHROME;
            }
        }
    }
}


