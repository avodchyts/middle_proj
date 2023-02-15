import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import data.LanguageUrlDataProvider;
import data.LanguageUrlDto;
import io.qameta.allure.*;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import ui.pages.LanguageChooser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Epic("Testing Allure library")
@Feature("Verify FEATURE Operations Allure library")
public class LanguageChooserTest extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(LanguageChooserTest.class);
    private static final Pattern contentPattern = Pattern.compile("/content/nutanix/");

    @Test(dataProvider = "languageUrls", dataProviderClass = LanguageUrlDataProvider.class, description = "Checking languages list")
    @Story("Language Chooser Tests")
    @Severity(SeverityLevel.NORMAL)
    @Description("Checking languages list")
    public void testLanguagesList(List<LanguageUrlDto> languageLinks) {
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String selectedLanguage = languageChooser.getSelectedLanguageText();
        List<LanguageUrlDto> expectedLanguages = languageLinks
                .stream()
                .filter(language -> !language.name().equals(selectedLanguage))
                .toList();
        languageChooser.open();
        List<LanguageUrlDto> languages = languageChooser
                .getLanguagesMap()
                .entrySet()
                .stream()
                .map(entry -> new LanguageUrlDto(entry.getKey(), entry.getValue()))
                .toList();

        Assertions.assertThat(languages).containsExactlyElementsOf(expectedLanguages);
    }

    @Test(description = "API checking languages list")
    @Story("Language Chooser Tests")
    @Severity(SeverityLevel.NORMAL)
    @Description("API checking languages list")
    public void testApiLanguagesLink() {
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        languageChooser.open();
        List<LanguageUrlDto> languages = languageChooser
                .getLanguagesMap()
                .entrySet()
                .stream().filter(o->!"http://www.nutanix.in/".equals(o.getValue()))
                .map(entry -> new LanguageUrlDto(entry.getKey(), entry.getValue()))
                .toList();
        SoftAssertions.assertSoftly(softAssertions -> {
            for (LanguageUrlDto language : languages) {
                linkAPIChecks(softAssertions, language.url());
            }
        });
    }

    @Test(description = "Content checking languages list")
    @Story("Language Chooser Tests")
    @Severity(SeverityLevel.NORMAL)
    @Description("Content checking languages list")
    public void testContentLanguagesLink() {
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        languageChooser.open();
        List<LanguageUrlDto> languages = languageChooser
                .getLanguagesMap()
                .entrySet()
                .stream()
                .map(entry -> new LanguageUrlDto(entry.getKey(), entry.getValue()))
                .toList();
        SoftAssertions.assertSoftly(softAssertions -> {
            for (LanguageUrlDto language : languages) {
                linkContentChecks(softAssertions, language.url());
            }
        });
    }

    private void linkAPIChecks(SoftAssertions softAssert, String link) {
        LOGGER.info(String.format("API checking link: %s", link));
        Pattern patternApi = Pattern.compile("4\\d{2}");

        RequestDto requestDto = RequestDto.builder()
                .resourceLink(link)
                .build();

        ResponseDto actualResponseDto = RestAssuredApiClient.GET
                .apply(requestDto);

        Matcher matcherApi = patternApi.matcher(String.valueOf(actualResponseDto.getStatusCode()));

        softAssert.assertThat(matcherApi.find()).isFalse();
    }

    private void linkContentChecks(SoftAssertions softAssert, String link) {
        LOGGER.info(String.format("Content checking link: %s", link));
        Matcher contextMatcher = contentPattern.matcher(link);
        softAssert.assertThat(contextMatcher.find()).isFalse();
    }
}
