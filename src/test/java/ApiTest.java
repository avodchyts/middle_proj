import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class ApiTest extends BaseTest{
    private static final Logger LOGGER = Logger.getLogger(ApiTest.class);

    @Test(description = "API links checking")
    public void allLinksCheckTests() {
        SoftAssert softAssert = new SoftAssert();
        Pattern pattern = Pattern.compile("4\\d{2}");

        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);

        List<String> links = homePage.getLinks();
        for (int i = 0; i < links.size(); i++) {
            String url = links.get(i);
            if (url.contains("www.nutanix.in"))
                continue;
            LOGGER.info(url);
            LOGGER.info(String.format("Index url: %d", i));
            ValidatableResponse validatableResponse = given().contentType("application/json").when().get(url).then();
            String lineValue = validatableResponse.extract().statusLine();
            String codeStatus = String.valueOf(validatableResponse.extract().statusCode());
            validatableResponse.log().status();
            Matcher matcher = pattern.matcher(lineValue);
            softAssert.assertFalse(matcher.find(), String.format("Link is not valid: %s, status code: %s", url, codeStatus));
        }
        softAssert.assertAll();
    }
}
