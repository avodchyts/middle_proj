package pages;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

public class BasePage implements Supplier<SearchContext> {
    protected final SearchContext searchContext;
    protected BasePage(SearchContext searchContext) {
        this.searchContext = searchContext;
        PageFactory.initElements(this.searchContext, this);
    }

    @Override
    public SearchContext get() {
        return searchContext;
    }
}
