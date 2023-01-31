package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface HasLinks<T extends SearchContext> extends Supplier<T> {

    default List<WebElement> getLinkElements() {
        return get().findElements(By.tagName("a"));
    }

    default WebElement getLinkElement(String text) {
        String linkXpathExpression = String.format("//a[contains(text(),'%s')]", text);

        return get().findElement(By.xpath(linkXpathExpression));
    }

    default Map<String, String> getLinksMap() {
        return getLinkElements()
                .stream()
                .collect(Collectors.toMap(WebElement::getText, element -> element.getAttribute("href")));
    }

    default List<String> getLinkNames() {
        return getLinkElements()
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    default List<String> getLinkUrls() {
        return getLinkElements()
                .stream()
                .map(element -> element.getAttribute("href"))
                .toList();
    }
}
