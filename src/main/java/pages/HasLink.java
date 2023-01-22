package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public interface HasLink extends Supplier<SearchContext> {
    default WebElement getLinkElement() {
        return get().findElement(By.xpath("//a/@href"));
    }

    default String getLinkName() {
        return getLinkElement().getText();
    }

    default String getLinkUrl() {
        return getLinkElement().getAttribute("href");
    }
}
