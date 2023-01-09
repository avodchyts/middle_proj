package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NavigationHeaderUtilityPage extends BasePage {

    private static final Logger LOGGER = Logger.getLogger(NavigationHeaderUtilityPage.class);
    @FindBy(xpath = "//nav[contains(@class, 'global-header')]/ul/li/a['href']")
    private List<WebElement> utilityLinks;
    @FindBy(xpath = "//ul[@id='language']//a['href']")
    private List<WebElement> languagesList;
    @FindBy(xpath = "//span[contains(@class,'login-link-ref')]/a['href']")
    private WebElement loginLink;

    public NavigationHeaderUtilityPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getUtilityLinks() {
        return utilityLinks.stream().map(o -> o.getAttribute("href")).collect(Collectors.toList());
    }
    public String getLoginLink() {
        return loginLink.getAttribute("href");
    }

    public List<String> getLanguagesLinks() {
        return languagesList.stream().map(o -> o.getAttribute("href")).collect(Collectors.toList());
    }
    public Map<String, String> getLanguagesMap() {
        return languagesList.stream().collect(Collectors.toMap(o -> o.getAttribute("text"), b -> b.getAttribute("href")));
    }
}
