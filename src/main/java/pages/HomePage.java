package pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage implements HasLinks<WebDriver> {
    public HomePage(WebDriver driver) {
        super(driver);
    }
}
