package fixtures;

public enum GraphQlEndpoint {
    READ_ENDPOINT("/public/v2/users/780"),
    CREATE_ENDPOINT("/public/v2/users"),
    UPDATE_ENDPOINT("/public/v2/users/780"),
    DELETE_ENDPOINT("/public/v2/users/780");

    public static final String BASE_URI = "https://gorest.co.in";
    public final String endPoint;

    GraphQlEndpoint(String endPoint) {
        this.endPoint = endPoint;
    }
}

