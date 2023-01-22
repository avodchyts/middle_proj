package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainNavigationHeader extends BasePage {
    @FindBy(className = "main-navigation")
    private WebElement mainNavigation;

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
            return mainNavigation.findElements(By.cssSelector(".global-header__nav__item > a"));
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
        WebElement navigationMenuElement = mainNavigationSubMenus
                .getLinkElement(tabName)
                .findElement(By.xpath("//following-sibling::ul"));
        return ((HasLinks) () -> navigationMenuElement).getLinkUrls();
    }
}
