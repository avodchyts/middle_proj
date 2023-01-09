package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.EnvironmentsValues;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class HomePage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(HomePage.class);
    private final static String PAGE_URL = EnvironmentsValues.getUrlValue();
    private final static String LANG_OPTION = "//li/a[text()='%s']";
    @FindBy(xpath = "//button[@id='toggle-language']")
    private WebElement languageButton;

    @FindBy(xpath = "//a['href']")
    private List<WebElement> linksList;

    @FindBy(xpath = "//button[text()='Accept All Cookies']")
    private WebElement acceptCookiesButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }
    public HomePage openHomePage() {
        driver.navigate().to(PAGE_URL);
        return this;
    }

    public List<String> getLinks() {
        return getSortByHrefAndNullCheckList(linksList);
    }
    public String getLanguageText() {
        return waitForVisibilityOfElement(driver, Duration.ofSeconds(3), languageButton).getText();
    }
    public void setLanguage(String languageValue) {
        if (isLanguageSelected(languageValue)) {
            LOGGER.info(format("%s is already selected", languageValue));
            return;
        }
        waitForVisibilityOfElement(driver, Duration.ofSeconds(3), languageButton).click();
        WebElement languageOption = driver.findElement(By.xpath(format(LANG_OPTION, languageValue)));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        languageOption.click();
    }

    public boolean isLanguageSelected(String languageValue) {
        return getLanguageText().equals(languageValue);
    }
}
