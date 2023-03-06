import api.models.ResponseDto;
import config.TestConfig;
import models.AppConfigsResponse;
import models.UserInfo;
import api.service.AppConfigsService;
import api.service.UserService;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ApiTest {

    private static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);
    private static final String token = String.format("Bearer %s", PROD_DATA.authorizationToken());

    @Test
    public void testLoginAPI() {

        ResponseDto responseDto = new AppConfigsService()
                .getResponseAppConfigs("notAllowedspList");

        Assertions
                .assertThat(responseDto.getStatusCode())
                .isEqualTo(200);
        Assertions.assertThat(responseDto.getStatusMessage()
                        .toLowerCase()
                        .contains("success"))
                .isTrue();
    }

    @Test
    public void testBodyLoginAPI() {
        AppConfigsResponse[] responseBody = new AppConfigsService()
                .getAppConfigs("notAllowedspList");
        Assertions
                .assertThat(responseBody)
                .hasSize(1);

        Assertions
                .assertThat(responseBody[0].getId())
                .isEqualTo("notAllowedspList");

        Assertions
                .assertThat(responseBody[0].getData())
                .contains("sizer-prod");
    }
}
