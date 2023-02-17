package ui.support;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

import java.util.Objects;
import java.util.function.Supplier;

import static support.TestListener.PROD_DATA;

public enum SingletonWebDriver implements WebDriver {
    INSTANCE;
    private WebDriver webDriver;
    private final Supplier<WebDriver> webDriverSupplier;

    private interface Teardown {
        void quit();

        void close();
    }

    SingletonWebDriver() {
        webDriverSupplier = DriverFactory.selectDriverSupplier(PROD_DATA.browserName());
    }

    @Delegate(excludes = Teardown.class)
    private WebDriver getWebDriver() {
        if (Objects.isNull(webDriver)) {
            webDriver = webDriverSupplier.get();
            Runtime current = Runtime.getRuntime();
            current.addShutdownHook(new DriverHook(webDriver));
        }
        return webDriver;
    }

    @Override
    public void close() {
        if (Objects.nonNull(webDriver)) {
            webDriver.close();
        }
    }

    @Override
    public void quit() {
        if (Objects.nonNull(webDriver)) {
            webDriver.quit();
        }
        webDriver = null;
    }
}
