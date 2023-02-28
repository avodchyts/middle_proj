import api.models.ResponseDto;
import models.UserInfo;
import models.service.UserService;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GoRestApiTestSecond {

    @Test
    public void createUsersTest() {
        UserInfo user1 = UserInfo.builder()
                .id("1")
                .name("firstTestUser")
                .email("firstTestUser@wehner-krajcik.info")
                .gender("male")
                .status("active")
                .build();

        UserInfo user2 = UserInfo.builder()
                .id("2")
                .name("secondTestUser")
                .email("secondTestUser@wehner-krajcik.info")
                .gender("female")
                .status("active")
                .build();

        List<UserInfo> listUser = Arrays.asList(user1, user2);
        for (UserInfo user : listUser) {
            UserInfo expectedUser = new UserService().createUser(user);
            Assertions
                    .assertThat(expectedUser.equals(user)).isTrue();
        }
    }

    @Test
    public void testUsersList() {
        List<UserInfo> userList = Arrays.asList(new UserService().getUsersInfo());

        Assertions
                .assertThat(userList.size() > 0).isTrue();
    }

    @Test
    public void testUser() {
        String userId = "2";
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
        UserInfo expectedUser = new UserService().updateUser("1", user);
        Assertions
                .assertThat(expectedUser.equals(user)).isFalse();
    }

    @Test
    public void testDeleteUser() {
        UserInfo user = UserInfo.builder()
                .id("2")
                .build();
        ResponseDto expectedResponse = new UserService().deleteUser(user);
        Assertions
                .assertThat(expectedResponse.getStatusCode() == 200);
        Assertions.assertThat(expectedResponse.getStatusMessage().contains("success")).isTrue();
    }
}
