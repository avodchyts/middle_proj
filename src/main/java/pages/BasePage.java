package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Functional;
import utils.MainFunctional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BasePage {
    protected final WebDriver driver;
    protected Functional mainFunctional;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        mainFunctional = new MainFunctional();
    }
}
