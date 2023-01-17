package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import pages.BasePage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;

public class WebDriverLogger implements WebDriverListener {
    private static final Logger LOGGER = Logger.getLogger(WebDriverLogger.class);

    @Override
    public void beforeGet(WebDriver driver, String url) {
        LOGGER.info(format("Going to open url: %s", url));
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        LOGGER.info(format("Looking for element: %s ", locator.toString()));
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        LOGGER.info(format("Element %s was found by locator: %s", result.toString(), locator.toString()));
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        LOGGER.info(format("Element %s has text [%s]", element, result));
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        LOGGER.info(format("Method %S was failed", method.getName()));
        LOGGER.info(format("Exception: %s", e.getMessage()));
        LOGGER.error(e);
        takesScreenshot();
    }

    @Override
    public void afterQuit(WebDriver driver) {
        LOGGER.info(format("Driver [%s] was quited", driver.getTitle()));
    }

    private void takesScreenshot() {
        File screnshot = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.FILE);
        File screnshotFolder = new File(System.getProperty("user.dir"), "screenshots");
        screnshotFolder.mkdir();
        try {
            FileUtils.copyFile(screnshot, new File(screnshotFolder, System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ListenerException("Failed to save screenshots", e);
        }
    }
}
