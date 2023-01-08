package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BasePage {

    public final Logger LOGGER = Logger.getLogger(BasePage.class);
    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

}
