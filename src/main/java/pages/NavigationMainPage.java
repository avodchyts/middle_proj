package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NavigationMainPage extends BasePage{

    private final String tabSubLinkLocator = "//a['href' and contains(text(),'%s')]/following-sibling::ul/li/a['href']";
    @FindBy(xpath = "//nav[@class='main-navigation']/ul/li/a['href']")
    private List<WebElement> mainNavigationLinks;

    public NavigationMainPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public List<String> getMainNavigationLinks() {
        List<String> navigationLinks = mainNavigationLinks.stream().map(o -> o.getAttribute("href")).collect(Collectors.toList());
        return navigationLinks;
    }

    public List<String> getMainNavigationTabNames(){
        List<String> tabNames = mainNavigationLinks.stream().map(o ->o.getText()).collect(Collectors.toList());
        return tabNames;
    }

    public List<String> getMainNavigationSubLinks(){
        List<String> subLinks = new ArrayList<String>();
        List<String> tabNames = getMainNavigationTabNames();
        for (String tabName:tabNames) {
            if(tabName.contains("Test Drive"))
                continue;
            List<WebElement> webSubLinks = driver.findElements(By.xpath(String.format(tabSubLinkLocator,tabName)));
            List<String> nmList = webSubLinks.stream().filter(x->x.getAttribute("href")!=null).map(o->o.getAttribute("href")).collect(Collectors.toList());
            subLinks.addAll(nmList);
        }
        return subLinks;
    }
}
