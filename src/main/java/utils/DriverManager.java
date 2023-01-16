package utils;

import org.openqa.selenium.WebDriver;

import java.util.Objects;
import java.util.function.Supplier;

public class DriverManager implements Supplier<WebDriver> {
    private WebDriver driver;
    private final Supplier<WebDriver> driverSupplier;
    private final DecoratorPipeline<WebDriver> decorators;

    public DriverManager(Supplier<WebDriver> driverSupplier, DecoratorPipeline<WebDriver> decorators) {
        this.driverSupplier = driverSupplier;
        this.decorators = decorators;
    }

    public WebDriver get() {
        if (Objects.isNull(this.driver)) {
            this.driver = driverSupplier.get();
            Runtime current = Runtime.getRuntime();
            current.addShutdownHook(new DriverHook(driver));
            driver = decorators.apply(driver);
        }
        return driver;
    }
}

