import api.models.ResponseDto;
import models.UserInfo;
import models.service.AppConfigsService;
import models.service.UserService;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GoRestAPITest {

    @Test
    public void testUsersList() {
        List<UserInfo> userList = Arrays.asList(new UserService().getUsersInfo());

        Assertions
                .assertThat(userList.size() > 0).isTrue();
    }
    @Test
    public void testCreateUser() {
        UserInfo user = UserInfo.builder()
                .id("4")
                .name("TestUser")
                .email("kaniyar_swami_ret@wehner-krajcik.info")
                .gender("male")
                .status("active")
                .build();
        UserInfo expectedUser = new UserService().createUser(user);
        Assertions
                .assertThat(expectedUser.equals(user)).isTrue();
    }

    @Test
    public void testUser() {
        String userId = "4";
        UserInfo user = new UserService().getUserGoRestAPI(userId);
        Assertions
                .assertThat(user.getId().equals(userId)).isTrue();
        Assertions.assertThat(Objects.nonNull(user)).isTrue();
    }

    @Test
    public void testUpdateUser() {
        UserInfo user = UserInfo.builder()
                .name("TestUser_updated")
                .email("kaniyar_swami_ret@wehner-krajcik.update")
                .gender("female")
                .status("active")
                .build();
        UserInfo expectedUser = new UserService().updateUser("4", user);
        Assertions
                .assertThat(expectedUser.equals(user)).isFalse();
    }

    @Test
    public void testDeleteUser() {
        UserInfo user = UserInfo.builder()
                .id("4")
                .build();
        ResponseDto expectedResponse = new UserService().deleteUser(user);
        Assertions
                .assertThat(expectedResponse.getStatusCode() == 200);
        Assertions.assertThat(expectedResponse.getStatusMessage().contains("success")).isTrue();
    }

}
