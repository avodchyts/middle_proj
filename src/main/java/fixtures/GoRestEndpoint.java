package fixtures;

public enum GoRestEndpoint {
    READ_USERS("/public/v2/users"),
    READ_USER("/public/v2/users/{id}"),
    CREATE_USERS("/public/v2/users"),
    UPDATE_USERS("/public/v2/users/{id}"),
    DELETE_USERS("/public/v2/users/{id}");

    private static final String BASE_URI = "https://gorest.co.in";
    private final String endPoint;

    GoRestEndpoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPoint() {
        return String.format("%s%s", BASE_URI, endPoint);
    }
}

