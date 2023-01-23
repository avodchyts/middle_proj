package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MainFunctional implements Functional {
    @Override
    public WebElement waitForVisibilityOfElement(WebDriver driver, Duration timeOutSeconds, WebElement webElement) {
        return new WebDriverWait(driver, timeOutSeconds).until(ExpectedConditions.visibilityOf(webElement));
    }

    @Override
    public List<String> getSortByHrefAndNullCheckList(List<WebElement> webElements) {
        return webElements.stream()
                .filter(x -> x.getAttribute("href") != null)
                .map(o -> o.getAttribute("href"))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSortByHrefList(List <WebElement> webElements) {
        return webElements.stream()
                .map(o -> o.getAttribute("href"))
                .collect(Collectors.toList());
    }
}
