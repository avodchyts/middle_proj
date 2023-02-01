package ui.support;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Objects;
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
        try {
            return Objects.requireNonNull(
                    EnumUtils.getEnum(DriverFactory.class, driverName.toUpperCase())
            );
        } catch (NullPointerException e) {
            LOGGER.error(format("Could not find driver supplier for %s", driverName));
            LOGGER.info(format("Could not find driver supplier for %s, getting Chrome", driverName));
            return DriverFactory.CHROME;
        }
    }
}


