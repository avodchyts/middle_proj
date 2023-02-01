package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.support.HasLink;

public class LoginLink extends BasePage {
    @FindBy(css = "span.login-link-ref")
    private WebElement loginButton;

    private final HasLink<WebElement> loginLink = () -> loginButton;

    public LoginLink(WebDriver webDriver) {
        super(webDriver);
    }

    public String getLoginUrl() {
        return loginLink.getLinkUrl();
    }
}
