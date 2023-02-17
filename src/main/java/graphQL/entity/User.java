package graphQL.entity;

import graphQL.handler.UserHandler;
import graphQL.utils.SchemaUtils;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;

import java.util.List;

@GraphQLName(SchemaUtils.USER)
public class User {

    @GraphQLField
    private Long id;
    @GraphQLField
    private String name;
    @GraphQLField
    private String email;
    @GraphQLField
    private String gender;
    @GraphQLField
    private String status;

    public User(String name, String email, String gender, String status) {
        this.id = genId();
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus(String status) {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Long genId() {
        Long id = 1L;
        try {
            List<User> users = new UserHandler().getUsers();
            for (User user : users)
                id = (user.getId() > id ? user.getId() : id) + 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
