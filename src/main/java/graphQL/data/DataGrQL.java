package graphQL.data;

import graphQL.entity.User;

import java.util.Collections;
import java.util.List;

public class DataGrQL {
    private List<User> allUsers;

    public DataGrQL() {

    }

    public DataGrQL(List<User> allBooks) {
        this.allUsers = allBooks;
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(allUsers);
    }
}
