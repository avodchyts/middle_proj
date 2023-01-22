package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NavigationUtilityHeader extends BasePage {
    @FindBy(className = "global-header__utility")
    private WebElement utilityHeader;

    private HasLinks utilityHeaderLinks = () -> utilityHeader;
    public NavigationUtilityHeader(WebDriver driver) {
        super(driver);
    }

    public List<String> getUtilityHeaderLinks() {
        return utilityHeaderLinks.getLinkUrls();
    }
}
