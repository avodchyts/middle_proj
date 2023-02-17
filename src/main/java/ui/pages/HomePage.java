package ui.pages;

import org.openqa.selenium.WebDriver;
import ui.support.HasLinks;

public class HomePage extends BasePage implements HasLinks<WebDriver> {
    public HomePage(WebDriver driver) {
        super(driver);
    }
}
