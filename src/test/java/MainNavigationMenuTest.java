import data.LanguageUrlDataProvider;
import data.LanguageUrlDto;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import pages.MainNavigationHeader;
import utils.LangValue;
import utils.UrlTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class MainNavigationMenuTest extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(MainNavigationMenuTest.class);
    private static final Pattern contentPattern = Pattern.compile("/content/nutanix/");
    static String patternVariable;
    static String patternValue =String.format(".com/%s",patternVariable);
    private static final Pattern shortDescriptionPattern = Pattern.compile(patternValue);

    @Test(dataProvider = "languageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testMainNavigationMenuLinks(LanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());

        SoftAssertions.assertSoftly(softAssertions -> {
             softAssertions.assertThat(mainNavigationHeader.getNavigationTabNames().size()).isEqualTo(6);
            softAssertions.assertThat(mainNavigationHeader.getNavigationTabLinks()).isNotEmpty();
            for (String name : mainNavigationHeader.getNavigationSubmenuNames()) {
                softAssertions.assertThat(mainNavigationHeader.getNavigationTabSubLinks(name)).isNotEmpty();
            }
        });
    }

    @Test(dataProvider = "languageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testApiContentNavigationTabsMenuLinks(LanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link :
                    mainNavigationHeader.getNavigationTabLinks()) {
                linkAPIChecks(softAssertions, link);
                linkContentChecks(softAssertions, link);
                if (languageUrlDto.name().matches("United States (English)|India|中国 (简体中文)"))
                    continue;
                patternVariable =".com";
                linkShortDescriptionChecks(softAssertions, link);
            }
        });
    }

    @Test(dataProvider = "languageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testApiContentNavigationSubTabsMenuLinks(LanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String name : mainNavigationHeader.getNavigationSubmenuNames()) {
                for (String link : mainNavigationHeader.getNavigationTabSubLinks(name)) {
                    linkAPIChecks(softAssertions, link);
                    linkContentChecks(softAssertions, link);
                    if (languageUrlDto.name().matches("United States (English)|India|中国 (简体中文)"))
                        continue;
                    patternVariable = LangValue.valueOf(languageUrlDto.name()).code;
                    linkShortDescriptionChecks(softAssertions, link);
                    patternVariable = null;
                }
            }
        });
    }

    @Test()
    public void testEnglishApiContentNavigationTabsMenuLinks() {
        getDriver().get(UrlTemplate.getUrl("us"));
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link :
                    mainNavigationHeader.getNavigationTabLinks()) {
                linkAPIChecks(softAssertions, link);
                linkContentChecks(softAssertions, link);
                linkShortDescriptionChecks(softAssertions, link);
                patternValue = ".com";
            }
        });
    }

    @Test()
    public void testEnglishApiContentNavigationSubTabsMenuLinks() {
        getDriver().get(UrlTemplate.getUrl("us"));
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String name : mainNavigationHeader.getNavigationSubmenuNames()) {
                for (String link : mainNavigationHeader.getNavigationTabSubLinks(name)) {
                    linkAPIChecks(softAssertions, link);
                    linkContentChecks(softAssertions, link);
                    patternValue = ".com";
                    linkShortDescriptionChecks(softAssertions, link);
                }
            }
        });
    }

    @Test()
    public void testChinesApiContentNavigationTabsMenuLinks() {
        getDriver().get(UrlTemplate.getUrl("cn"));
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link :
                    mainNavigationHeader.getNavigationTabLinks()) {
                linkAPIChecks(softAssertions, link);
                linkContentChecks(softAssertions, link);
                patternValue = ".cn";
                linkShortDescriptionChecks(softAssertions, link);
            }
        });
    }

    @Test()
    public void testChinesApiContentNavigationSubTabsMenuLinks() {
        getDriver().get(UrlTemplate.getUrl("cn"));
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String name : mainNavigationHeader.getNavigationSubmenuNames()) {
                for (String link : mainNavigationHeader.getNavigationTabSubLinks(name)) {
                    linkAPIChecks(softAssertions, link);
                    linkContentChecks(softAssertions, link);
                    patternValue = ".cn";
                    linkShortDescriptionChecks(softAssertions, link);
                }
            }
        });
    }

    private void linkAPIChecks(SoftAssertions softAssert, String link) {
        LOGGER.info(String.format("API checking link: %s", link));
        Pattern patternApi = Pattern.compile("4\\d{2}");
        ValidatableResponse validatableResponse = given().contentType("application/json").when().get(link).then();
        Matcher matcherApi = patternApi.matcher(validatableResponse.extract().statusLine());
        softAssert.assertThat(matcherApi.find()).isFalse();
    }
    private void linkContentChecks(SoftAssertions softAssert, String link) {
        LOGGER.info(String.format("Content checking link: %s", link));
        Matcher contextMatcher = contentPattern.matcher(link);
        softAssert.assertThat(contextMatcher.find()).isFalse();
    }
    private void linkShortDescriptionChecks(SoftAssertions softAssert, String link) {
        LOGGER.info(String.format("Content checking link: %s", link));
        Matcher contextMatcher = shortDescriptionPattern.matcher(link);
        softAssert.assertThat(contextMatcher.find()).isTrue();
    }
}
