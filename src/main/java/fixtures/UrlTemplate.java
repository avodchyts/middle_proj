package fixtures;

import config.TestConfig;
import org.aeonbits.owner.ConfigFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum UrlTemplate {
    INDIA("in", "http://www.nutanix.in/"),
    CHINA("cn", "https://www.nutanix.cn/"),
    UNITED_STATES("com", "https://www.nutanix.com"),
    ;


    private final String langCode;
    private final String template;

    private static final Map<String, String> TEMPLATE_MAP;
    private static final String BASE_URL  = ConfigFactory.create(TestConfig.class).baseUrl();

    UrlTemplate(String langCode, String template) {
        this.langCode = langCode;
        this.template = template;
    }

    static {
        TEMPLATE_MAP = Arrays.stream(values()).collect(Collectors.toMap(value -> value.langCode, value -> value.template));
    }
    public static String getUrl(String langCode) {
        return TEMPLATE_MAP.getOrDefault(langCode, String.format("%s/%s", BASE_URL, langCode));
    }
}
