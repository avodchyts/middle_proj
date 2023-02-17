package graphQL;

import graphQL.entity.User;
import graphQL.utils.SchemaUtils;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Optional;

import static graphQL.handler.UserHandler.getUsers;

@GraphQLName(SchemaUtils.MUTATION)
public class UserMutation {
    @GraphQLField
    public static User createUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.NAME) final String name, @NotNull @GraphQLName(SchemaUtils.EMAIL) final String email, @NotNull @GraphQLName(SchemaUtils.GENDER) final String gender, @NotNull @GraphQLName(SchemaUtils.STATUS) final String status) {
        List<User> users = getUsers(env);
        final User user = new User(name, email, gender, status);
        users.add(user);
        return user;
    }

    @GraphQLField
    public static User updateUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.ID) final String id, @NotNull @GraphQLName(SchemaUtils.NAME) final String name, @NotNull @GraphQLName(SchemaUtils.EMAIL) final String email,
                                  @NotNull @GraphQLName(SchemaUtils.GENDER) final String gender, @NotNull @GraphQLName(SchemaUtils.STATUS) final String status) {
        final Optional<User> user = getUsers(env).stream()
                .filter(c -> c.getId() == Long.parseLong(id))
                .findFirst();
        if (!user.isPresent()) {
            return null;
        }
        user.get()
                .setName(name);
        user.get()
                .setEmail(email);
        user.get()
                .setGender(gender);
        user.get()
                .getStatus(status);
        return user.get();
    }

    @GraphQLField
    public static User deleteUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.ID) final String id) {
        final List<User> users = getUsers(env);
        final Optional<User> user = users.stream()
                .filter(c -> c.getId() == Long.parseLong(id))
                .findFirst();
        if (!user.isPresent()) {
            return null;
        }
        users.removeIf(c -> c.getId() == Long.parseLong(id));
        return user.get();
    }
}
