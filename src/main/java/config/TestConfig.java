package config;

import org.aeonbits.owner.Config;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:env.prod.properties",
        "classpath:chrome.browser.properties",
        "classpath:data.properties"
})
public interface TestConfig extends Config {
    @Key("base.url")
    String baseUrl();
    @Key("browser.name")
    String browserName();
    @Key("browser.driver.key")
    String browserSystKey();
    @Key("browser.driver.pathValue")
    String driverPath();
    @Key("env.name")
    String environmentName();
    @Key("mobileDevice")
    String deviceName();
}
