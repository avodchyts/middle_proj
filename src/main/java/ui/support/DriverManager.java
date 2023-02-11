package ui.support;

import org.openqa.selenium.WebDriver;
import support.DecoratorPipeline;

import java.util.function.Supplier;

public class DriverManager implements Supplier<WebDriver> {
    private final WebDriver driver;
    private final DecoratorPipeline<WebDriver> decorators;

    public DriverManager(WebDriver driver, DecoratorPipeline<WebDriver> decorators) {
        this.driver = driver;
        this.decorators = decorators;
    }

    public WebDriver get() {
        return decorators.decorate(driver);
    }
}