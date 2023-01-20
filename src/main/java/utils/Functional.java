package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public interface Functional {
    public WebElement waitForVisibilityOfElement(WebDriver driver, Duration timeOutSeconds, WebElement webElement);

    public List<String> getSortByHrefAndNullCheckList(List<WebElement> webElements);

    public List<String> getSortByHrefList(List<WebElement> webElements);
}
