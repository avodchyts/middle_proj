import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import models.AppConfigsResponse;
import models.UserInfo;
import models.service.AppConfigsService;
import models.service.EntityService;
import models.service.UserService;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Collections;

public class ApiTest {

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

    @Test
    public void testApiUserInfo() {
        UserInfo expectedUser = UserInfo.builder()
                .id("anonymous")
                .build();

        UserInfo userInfo = new UserService()
                .getUserInfo();
        Assertions
                .assertThat(userInfo).isEqualTo(expectedUser);
    }
}
