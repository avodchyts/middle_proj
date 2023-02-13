import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import support.*;

public class ApiTest extends BaseTest {
    @Test
    public void testLoginAPI() {
        getDriver().get(URL);
        String link = "https://my.nutanix.com/api/v1/appconfigs";
        RequestDto requestDto = new RequestDto();
        requestDto.setId("notAllowedspList");
        requestDto.setResourceLink(link);
        ParamResponseDto paramResponseDto = (ParamResponseDto) ApiClient.GET_PARAM.apply(requestDto);
        Assertions.assertThat(paramResponseDto.getData().contains("sizer-prod")).isTrue();
    }

    @Test
    public void testApiFromMainPage() {
        getDriver().get(URL);
        String link = "https://www.nutanix.com/libs/granite/csrf/token.json";
        RequestDto requestDto = new RequestDto();
        requestDto.setResourceLink(link);
        ResponseDto responseDto = (ResponseDto) ApiClient.GET.apply(requestDto);
        Assertions.assertThat(responseDto.getStatusCode() == 200).isTrue();
        Assertions.assertThat(responseDto.getStatusMessage().toLowerCase().contains("success")).isTrue();
    }

    @Test
    public void testApiUserInfo() {
        getDriver().get(URL);
        String link = "https://www.nutanix.com/bin/get/userinfo.json";
        RequestDto requestDto = new RequestDto();
        requestDto.setResourceLink(link);
        UserDTO responseDto = (UserDTO) ApiClient.GET.apply(requestDto);
        Assertions.assertThat(responseDto.getUserId().toLowerCase().equals("anonymous")).isTrue();
    }
}
