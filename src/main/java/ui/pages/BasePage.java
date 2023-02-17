package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.function.Supplier;

public class BasePage implements Supplier<WebDriver> {
    protected final WebDriver webDriver;
    protected BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
    }

    @Override
    public WebDriver get() {
        return webDriver;
    }
}
