package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NavigationMainPage extends BasePage{
    private final String tabSubLinkLocator = "//a['href' and contains(text(),'%s')]/following-sibling::ul/li/a['href']";
    @FindBy(xpath = "//nav[@class='main-navigation']/ul/li/a['href']")
    private List<WebElement> mainNavigationLinks;
    public NavigationMainPage(WebDriver driver){
        super(driver);
    }
    public List<String> getMainNavigationLinks() {
        return mainFunctional.getSortByHrefList(mainNavigationLinks);
    }
    public List<String> getMainNavigationTabNames() {
        return mainNavigationLinks.stream().map(o -> o.getText()).collect(Collectors.toList());
    }

    public List<String> getMainNavigationSubLinks() {
        List<String> subLinks = new ArrayList<String>();
        List<String> tabNames = getMainNavigationTabNames().stream().filter(x -> !x.contains("Test Drive")).collect(Collectors.toList());
        for (String tabName : tabNames) {
            List<WebElement> webSubLinks = driver.findElements(By.xpath(String.format(tabSubLinkLocator, tabName)));
            subLinks.addAll(mainFunctional.getSortByHrefAndNullCheckList(webSubLinks));
        }
        return subLinks;
    }
}
