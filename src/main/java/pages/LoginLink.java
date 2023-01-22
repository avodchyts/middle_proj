package pages;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginLink extends BasePage {
    @FindBy(css = "span.login-link-ref")
    private WebElement loginButton;

    private final HasLink loginLink = () -> loginButton;

    public LoginLink(SearchContext searchContext) {
        super(searchContext);
    }

    public String getLoginUrl() {
        return loginLink.getLinkUrl();
    }
}
