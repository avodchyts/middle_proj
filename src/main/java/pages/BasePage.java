package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BasePage {
    protected final WebDriver driver;
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    protected static WebElement waitForVisibilityOfElement(WebDriver driver, Duration timeOutSeconds, WebElement webElement) {
        return new WebDriverWait(driver, timeOutSeconds).until(ExpectedConditions.visibilityOf(webElement));
    }
    protected static List<String> getSortByHrefAndNullCheckList(List<WebElement> webElements) {
        return webElements.stream()
                .filter(x -> x.getAttribute("href") != null)
                .map(o -> o.getAttribute("href"))
                .collect(Collectors.toList());
    }

    protected static List<String> getSortByHrefList(List<WebElement> webElements) {
        return webElements.stream()
                .map(o -> o.getAttribute("href"))
                .collect(Collectors.toList());
    }
}
