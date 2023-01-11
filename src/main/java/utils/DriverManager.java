package utils;

import org.openqa.selenium.WebDriver;

import java.util.Objects;
import java.util.function.Supplier;

public class DriverManager implements Supplier<WebDriver> {
    private WebDriver driver;
    private final Supplier<WebDriver> driverSupplier;

    public DriverManager(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }
    public WebDriver get() {
        if (Objects.isNull(this.driver)) {
            this.driver = driverSupplier.get();
            this.driver.manage().window().maximize();
            Runtime current = Runtime.getRuntime();
            current.addShutdownHook(new DriverHook(this.driver));
        }
        return driver;
    }
}

