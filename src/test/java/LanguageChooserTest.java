import data.LanguageUrlDataProvider;
import data.LanguageUrlDto;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.LanguageChooser;

import java.util.List;

public class LanguageChooserTest extends BaseTest {

    @Test(dataProvider = "languageUrls", dataProviderClass = LanguageUrlDataProvider.class)
    public void testLanguagesList(List<LanguageUrlDto> languageLinks) {
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String selectedLanguage = languageChooser.getSelectedLanguageText();
        List<LanguageUrlDto> expectedLanguages = languageLinks
                .stream()
                .filter(language -> ! language.name().equals(selectedLanguage))
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
}
