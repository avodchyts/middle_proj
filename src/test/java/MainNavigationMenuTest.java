import data.LanguageUrlDataProvider;
import data.LanguageUrlDto;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import pages.MainNavigationHeader;

public class MainNavigationMenuTest extends BaseTest {

    @Test(dataProvider = "languageUrl", dataProviderClass = LanguageUrlDataProvider.class)
    public void testMainNavigationMenuLinks(LanguageUrlDto languageUrlDto) {
        getDriver().get(languageUrlDto.url());
        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(mainNavigationHeader.getNavigationTabNames()).isNotEmpty();
            softAssertions.assertThat(mainNavigationHeader.getNavigationTabLinks()).isNotEmpty();

            for (String name : mainNavigationHeader.getNavigationSubmenuNames()) {
                softAssertions.assertThat(mainNavigationHeader.getNavigationTabSubLinks(name)).isNotEmpty();
            }
        });
    }
}
