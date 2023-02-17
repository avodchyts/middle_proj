package data;

import fixtures.LangValue;
import fixtures.UrlTemplate;
import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.Iterator;

public class LanguageUrlDataProvider {

    @DataProvider
    public static Object[][] languageUrls() {
        return new Object[][]{{
                Arrays.stream(LangValue.values())
                        .map(langValue -> new LanguageUrlDto(langValue.name, UrlTemplate.getUrl(langValue.code)))
                        .toList()
        }};
    }

    @DataProvider
    public static Iterator<Object[]> languageUrl() {
        return Arrays.stream(LangValue.values())
                        .map(langValue -> new LanguageUrlDto(langValue.name, UrlTemplate.getUrl(langValue.code)))
                        .map(o -> new Object[] { o })
                        .iterator();
    }

    @DataProvider
    public static Iterator<Object[]> filteredLanguageUrl() {
        return Arrays.stream(LangValue.values()).filter(n -> !n.equals(LangValue.ENGLISH) || !n.equals(LangValue.CHINESE) || !n.equals(LangValue.INDIAN))
                .map(langValue -> new ExtendLanguageUrlDto(langValue.name, langValue.code, UrlTemplate.getUrl(langValue.code)))
                .map(o -> new Object[]{o})
                .iterator();
    }

    @DataProvider
    public static Iterator<Object[]> engChinesLanguageUrl() {
        return Arrays.stream(LangValue.values()).filter(n -> n.equals(LangValue.ENGLISH) || n.equals(LangValue.CHINESE))
                .map(langValue -> new ExtendLanguageUrlDto(langValue.name, langValue.code, UrlTemplate.getUrl(langValue.code)))
                .map(o -> new Object[]{o})
                .iterator();
    }

}
