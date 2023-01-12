package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    protected static WebElement waitForVisibilityOfElement(WebDriver driver, Duration timeOutSeconds, WebElement webElement) {
        return new WebDriverWait(driver, timeOutSeconds).until(ExpectedConditions.visibilityOf(webElement));
    }
}
