import data.ExtendLanguageUrlDto;
import data.LanguageUrlDataProvider;
import data.LanguageUrlDto;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import ui.pages.NavigationUtilityHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class NavigationUtilityHeaderTest extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger(NavigationUtilityHeaderTest.class);
    private static final Pattern contentPattern = Pattern.compile("/content/nutanix/");
    static String patternVariable;
    static String patternValue = String.format(".com/%s", patternVariable);
    private static final Pattern shortDescriptionPattern = Pattern.compile(patternValue);

    @Test(dataProvider = "filteredLanguageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testApiNavigationUtilityHeaderLinks(ExtendLanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        NavigationUtilityHeader navigationUtilityHeader = new NavigationUtilityHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link : navigationUtilityHeader.getUtilityHeaderLinks()) {
                linkAPIChecks(softAssertions, link);
            }
        });
    }

    @Test(dataProvider = "filteredLanguageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testContentNavigationUtilityHeaderLinks(ExtendLanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        NavigationUtilityHeader navigationUtilityHeader = new NavigationUtilityHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link : navigationUtilityHeader.getUtilityHeaderLinks()) {
                linkContentChecks(softAssertions, link);
                patternVariable = languageUrlDto.shortDescription();
                linkShortDescriptionChecks(softAssertions, link);
                patternVariable = null;
            }
        });
    }
    @Test(dataProvider = "engChinesLanguageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testEngChinesApiNavigationUtilityHeaderLinks(LanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        NavigationUtilityHeader navigationUtilityHeader = new NavigationUtilityHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link : navigationUtilityHeader.getUtilityHeaderLinks()) {
                linkAPIChecks(softAssertions, link);
            }
        });
    }

    @Test(dataProvider = "engChinesLanguageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testEngChinesContentNavigationUtilityHeaderLinks(ExtendLanguageUrlDto extendLanguageUrlDto) {
        getDriver().get(extendLanguageUrlDto.url());
        NavigationUtilityHeader navigationUtilityHeader = new NavigationUtilityHeader(getDriver());
        SoftAssertions.assertSoftly(softAssertions -> {
            for (String link : navigationUtilityHeader.getUtilityHeaderLinks()) {
                linkContentChecks(softAssertions, link);
                patternValue = String.format(".%s", extendLanguageUrlDto.shortDescription());
                linkShortDescriptionChecks(softAssertions, link);
                patternValue = null;
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
