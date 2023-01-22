package pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Map;

import static java.text.MessageFormat.format;

public class LanguageChooser extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(LanguageChooser.class);

    @FindBy(id ="toggle-language")
    private WebElement languageButton;

    @FindBy(id ="language")
    private WebElement languagesDropdown;

    private final HasLinks languagesList = () -> languagesDropdown;

    public LanguageChooser(SearchContext searchContext) {
        super(searchContext);
    }

    public void open() {
        languageButton.click();
        new FluentWait<>(languagesDropdown).withTimeout(Duration.ofMillis(3000)).until(WebElement::isDisplayed);
    }

    public String getSelectedLanguageText() {
        new FluentWait<>(languageButton).withTimeout(Duration.ofMillis(3000)).until(WebElement::isDisplayed);
        return languageButton.getText();
    }

    public void selectLanguage(String languageName) {
        if (StringUtils.equals(getSelectedLanguageText(), languageName)) {
            LOGGER.info(format("%s is already selected", languageName));
            return;
        }
        open();
        languagesList.getLinkElement(languageName).click();
    }

    public Map<String, String> getLanguagesMap() {
        return languagesList.getLinksMap();
    }
}
