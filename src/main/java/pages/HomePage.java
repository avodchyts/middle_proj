package pages;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage implements HasLinks {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public SearchContext get() {
        return searchContext;
    }
}
