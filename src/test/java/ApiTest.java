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
        String link = "https://my.nutanix.com/api/v1/appconfigs";
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(link)
                .queryParams(Collections.singletonMap("id", "notAllowedspList"))
                .build();

        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);

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
        String link = "https://my.nutanix.com/api/v1/appconfigs";
        AppConfigsResponse[] responseBody = new AppConfigsService(link).getAppConfigs("notAllowedspList");
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
    public void testApiFromMainPage() {
        String link = "https://www.nutanix.com/libs/granite/csrf/token.json";
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(link)
                .build();

        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);

        Assertions
                .assertThat(responseDto.getStatusCode())
                .isEqualTo(200);

        Assertions
                .assertThat(responseDto.getStatusMessage())
                .containsIgnoringCase("OK");
    }

    @Test
    public void testApiUserInfo() {
        String link = "https://www.nutanix.com/bin/get/userinfo.json";
        UserInfo expectedUser = UserInfo.builder()
                .userId("anonymous")
                .build();

        UserInfo userInfo = new UserService(link)
                .getUserInfo();
        Assertions
                .assertThat(userInfo).isEqualTo(expectedUser);
    }
}
