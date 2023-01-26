package pages;

import com.google.common.cache.RemovalListener;
import groovy.lang.IntRange;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class MainNavigationHeader extends BasePage {
   private final String tabNameText = "//li[a[contains(@data-target,'%s')]]";
    @FindBy(className = "main-navigation")
    private WebElement mainNavigation;

    @FindBy(xpath = "//section[@class='flyout']")
    private WebElement mainSectionMenu;

    public MainNavigationHeader(WebDriver driver) {
        super(driver);
    }

    private final HasLinks mainNavigationTabs = new HasLinks() {
        @Override
        public SearchContext get() {
            return mainNavigation;
        }

        @Override
        public List<WebElement> getLinkElements() {
            return mainNavigation.findElements(By.xpath("//nav[@class='main-navigation']/ul/li/a"));
        }
    };

    private final HasLinks mainNavigationSubMenus = new HasLinks() {
        @Override
        public SearchContext get() {
            return mainNavigation;
        }

        @Override
        public List<WebElement> getLinkElements() {
            return mainNavigation.findElements(By.cssSelector(".global-header__nav__item--has-children > a"));
        }
    };

    private final HasLinks mainNavigationSectionSubMenus = new HasLinks() {
        @Override
        public SearchContext get() {
            return mainSectionMenu;
        }

        @Override
        public WebElement getLinkElement(String name) {
            String locatorText = String.format("//section[contains(@data-flyout,'%s')]", name);
            return mainSectionMenu.findElement(By.xpath(locatorText));
        }
    };

    public List<String> getNavigationTabNames() {
        return mainNavigationTabs.getLinkNames();
    }

    public List<String> getNavigationTabLinks() {
        return mainNavigationTabs.getLinkUrls();
    }

    public List<String> getNavigationSubmenuNames() {
        return mainNavigationSubMenus.getLinkNames();
    }

    public List<String> getNavigationTabSubLinks(String tabName) {
        tabMouseHoverAction(tabName);
        WebElement navigationMenuElement = mainNavigationSectionSubMenus
                .getLinkElement(tabName);
        return ((HasLinks) () -> navigationMenuElement).getLinkUrls();
    }

    private void tabMouseHoverAction(String name) {
        Actions actions = new Actions((WebDriver) searchContext);
        WebElement tabNameButton = searchContext.findElement(By.xpath(String.format(tabNameText, name)));
        IntStream.range(0, 4).forEach(i -> {
                    new FluentWait<>(tabNameButton).withTimeout(Duration.ofMillis(3000)).until(WebElement::isDisplayed);
                    actions.moveToElement(tabNameButton, 10, 10).perform();
                }
        );
    }
}
