package utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;

import java.util.Objects;

import static java.lang.String.format;

public enum EnvironmentsValue {
    PROD("https://www.nutanix.com/"),
    TEST_ENV("https://aemstage.nutanix.com/");
    private static final Logger LOGGER = Logger.getLogger(EnvironmentsValue.class);
    public String urlValue;

    private EnvironmentsValue(String urlValue) {
        this.urlValue = urlValue;
    }
    public static String getUrlValue() {
            EnvironmentsValue environmentsValue = EnvironmentsValue.valueOf(LocalFileReader.INSTANCE.getAppPropertiesValue("environment.name").toUpperCase());
            return environmentsValue.urlValue;
    }
}
