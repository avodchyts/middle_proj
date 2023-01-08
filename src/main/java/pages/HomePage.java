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

public class HomePage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(HomePage.class);
    final static String PAGE_URL = EnvironmentsValues.getUrlValue();
    final static String langOption = "//li/a[text()='%s']";

    @FindBy(xpath = "//a['href']")
    private WebElement firstLink;

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
        List<String> links = new ArrayList<>();
        links = linksList.stream().filter(x->x.getAttribute("href")!=null).map(o -> o.getAttribute("href")).collect(Collectors.toList());
        return links;
    }

    public String getLanguageText() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        webDriverWait.until(ExpectedConditions.visibilityOf(languageButton));
        String languageText = languageButton.getText();

        return languageText;
    }

    public void setLanguage(String languageValue) {
        if (isLanguageSelected(languageValue)) {
            LOGGER.info(String.format("%s is already selected", languageValue));
            return;
        }
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        webDriverWait.until(ExpectedConditions.visibilityOf(languageButton));
        languageButton.click();
        WebElement languageOption = driver.findElement(By.xpath(String.format(langOption, languageValue)));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        languageOption.click();
    }

    public boolean isLanguageSelected(String languageValue) {
        return getLanguageText().equals(languageValue);
    }

}
