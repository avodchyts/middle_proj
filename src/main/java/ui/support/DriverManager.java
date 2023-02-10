package ui.support;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

import java.util.Objects;
import java.util.function.Supplier;

import static support.TestListener.PROD_DATA;

public enum DriverManager implements WebDriver {
    INSTANCE;
    private WebDriver driver;
    private final Supplier<WebDriver> webDriverSupplier;
    private interface Teardown {
        void quit();
        void close();
    }
    DriverManager() {
        webDriverSupplier = DriverFactory.selectDriverSupplier(PROD_DATA.browserName());
    }
    @Delegate(excludes = Teardown.class)
    private WebDriver getWebDriver() {
        if (Objects.isNull(driver)) {
            driver = webDriverSupplier.get();
            Runtime current = Runtime.getRuntime();
            current.addShutdownHook(new DriverHook(driver));
        }
        return driver;
    }
    @Override
    public void close() {
        if (Objects.nonNull(driver)) {
            driver.close();
        }
    }
    @Override
    public void quit() {
        if (Objects.nonNull(driver)) {
            driver.quit();
        }
        driver = null;
    }
}