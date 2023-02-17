package fixtures;

public enum GoRestEndpoint {
    READ_ENDPOINT("/public/v2/users/{id}"),
    CREATE_ENDPOINT("/public/v2/users"),
    UPDATE_ENDPOINT("/public/v2/users/{id}"),
    DELETE_ENDPOINT("/public/v2/users/{id}");

    public static final String BASE_URI = "https://gorest.co.in";
    public final String endPoint;

    GoRestEndpoint(String endPoint) {
        this.endPoint = endPoint;
    }
}

