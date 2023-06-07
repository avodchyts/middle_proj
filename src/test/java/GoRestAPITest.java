import api.service.UserService;
import config.TestConfig;
import models.UserInfo;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.Assertions;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GoRestAPITest {

    private static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);
    private static final String token = String.format("Bearer %s", PROD_DATA.authorizationToken());
    private final ThreadLocal<UserInfo> createUserTemplate = new ThreadLocal<>();
    private final ThreadLocal<UserInfo> testUser = new ThreadLocal<>();

    @BeforeMethod
    public void preCondition() {
        UUID uniqueId = UUID.randomUUID();
        createUserTemplate.set(
                UserInfo.builder()
                        .name("TestUser")
                        .email(String.format("foo_bar_%s@email.test", uniqueId))
                        .gender("male")
                        .status("active")
                        .build());
        testUser.set(new UserService(token).createUser(createUserTemplate.get()));
    }

    @Test
    public void testReadUsersList() {
        List<UserInfo> userList = Arrays.asList(new UserService(token).getUsersInfo());
        Assertions.assertThat(userList).isNotEmpty();
        Assertions
                .assertThat(userList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(createUserTemplate.get());
    }

    @Test
    public void testCreateUser() {
        Assertions
                .assertThat(testUser.get())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createUserTemplate.get());
    }

    @Test
    public void testReadUser() {
        String userId = testUser.get().getId();
        UserInfo readUser = new UserService(token).getUserInfo(userId);
        Assertions
                .assertThat(readUser.getId())
                .isEqualTo(userId);
        Assertions
                .assertThat(readUser)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createUserTemplate.get());
        testUser.remove();
    }

    @Test
    public void testUpdateUser() {
        UUID uniqueId = UUID.randomUUID();
        String userId = testUser.get().getId();
        UserInfo updateUserData = UserInfo.builder()
                .name("TestUser_updated")
                .email(String.format("foo_bar_%s@email.test", uniqueId))
                .gender("female")
                .status("active")
                .build();
        UserInfo updatedUser = new UserService(token).updateUser(userId, updateUserData);
        Assertions
                .assertThat(updatedUser.getId())
                .isEqualTo(userId);
        Assertions
                .assertThat(updatedUser)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(updateUserData);
        testUser.set(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        new UserService(token).deleteUser(testUser.get().getId());
        List<UserInfo> userList = Arrays.asList(new UserService(token).getUsersInfo());
        Assertions.assertThat(userList).isNotEmpty();
        Assertions
                .assertThat(userList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(testUser.get());
        testUser.remove();
    }

    @AfterMethod
    public void postCondition() {
        if (Objects.nonNull(testUser.get())) {
            new UserService(token).deleteUser(testUser.get().getId());
            testUser.remove();
        }
    }
}
