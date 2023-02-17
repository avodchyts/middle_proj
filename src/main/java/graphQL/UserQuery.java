package graphQL;

import graphQL.entity.User;
import graphQL.utils.SchemaUtils;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static graphQL.handler.UserHandler.getUsers;

@GraphQLName(SchemaUtils.QUERY)
public class UserQuery {

    @GraphQLField
    public static User retrieveUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.ID) final String id) {
        final Optional<User> any = getUsers(env).stream()
                .filter(c -> c.getId() == Long.parseLong(id))
                .findFirst();
        return any.orElse(null);
    }

    @GraphQLField
    public static List<User> searchName(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.NAME) final String name) {
        return getUsers(env).stream()
                .filter(c -> c.getName()
                        .contains(name))
                .collect(Collectors.toList());
    }

    @GraphQLField
    public static List<User> listUsers(final DataFetchingEnvironment env) {
        return getUsers(env);
    }

}
