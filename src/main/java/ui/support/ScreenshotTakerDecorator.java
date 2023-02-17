package ui.support;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.decorators.Decorated;
import org.openqa.selenium.support.decorators.WebDriverDecorator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.time.LocalDateTime;

import static java.lang.String.format;

public class ScreenshotTakerDecorator extends WebDriverDecorator<WebDriver> {

    @Override
    public Object onError(
            Decorated<?> target,
            Method method,
            Object[] args,
            InvocationTargetException e) throws Throwable {
        var original = target.getOriginal();
        if (original instanceof TakesScreenshot) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) original;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(format("target/screenshot_%s_%s.png", method.getName(), LocalDateTime.now())));
        }
        throw e.getTargetException();
    }
}
