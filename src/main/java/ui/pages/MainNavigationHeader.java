package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import ui.support.HasLinks;

import java.util.List;

public class MainNavigationHeader extends BasePage {
    @FindBy(className = "main-navigation-wrapper")
    private WebElement mainNavigation;

    @FindBy(xpath = "//section[@class='flyout']")
    private WebElement mainSectionMenu;

    public MainNavigationHeader(WebDriver driver) {
        super(driver);
    }

    private final HasLinks<WebElement> mainNavigationTabs = new HasLinks<>() {
        @Override
        public WebElement get() {
            return mainNavigation;
        }

        @Override
        public List<WebElement> getLinkElements() {
            return mainNavigation.findElements(By.xpath(".//nav[@class='main-navigation']/ul/li/a"));
        }
    };

    private final HasLinks<WebElement> mainNavigationSubMenus = new HasLinks<>() {
        @Override
        public WebElement get() {
            return mainNavigation;
        }

        @Override
        public List<WebElement> getLinkElements() {
            return mainNavigation.findElements(By.cssSelector(".global-header__nav__item--has-children > a"));
        }
    };

    private final HasLinks<WebElement> mainNavigationSectionSubMenus = new HasLinks<>() {
        @Override
        public WebElement get() {
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
        WebElement navigationMenuElement = mainNavigationSectionSubMenus.getLinkElement(tabName);

        return ((HasLinks<WebElement>) () -> navigationMenuElement).getLinkUrls();
    }

    private void tabMouseHoverAction(String name) {
        Actions actions = new Actions(webDriver);
        // private final String tabNameText = "//li[a[contains(@data-target,'%s')]]";
        String tabNameText = "//a[contains(text(),'%s')]";
        WebElement tabNameButton = webDriver.findElement(By.xpath(String.format(tabNameText, name)));
        actions.moveToElement(mainNavigation).perform();
        actions.moveToElement(tabNameButton).perform();
    }
}
