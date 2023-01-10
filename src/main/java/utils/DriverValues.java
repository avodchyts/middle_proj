package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public enum DriverValues {
    CHROME(new ChromeDriver()),
    EDGE(new EdgeDriver());

    public WebDriver driver;

    private DriverValues(WebDriver driver){
        this.driver = driver;
    }

    public WebDriver getDriver(){
        return driver;
    }
}

