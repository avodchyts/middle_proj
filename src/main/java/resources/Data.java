package resources;

import org.aeonbits.owner.Config;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:env.${env.name}.properties", "classpath:${browser.name}.browser.properties"})
public interface Data extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("browser.name")
    String browserName();
    @Key("browser.driver.key")
    @DefaultValue("webdriver.chrome.driver")
    String browserSystKey();
    @Key("browser.driver.pathValue")
    String driverPath();
}
