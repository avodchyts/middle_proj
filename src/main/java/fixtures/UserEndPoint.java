package fixtures;

public enum UserEndPoint {
    READ_ENDPOINT("/bin/get/userinfo.json"),
    CREATE_ENDPOINT(""),
    UPDATE_ENDPOINT(""),
    DELETE_ENDPOINT("");

    public static final String BASE_URI = "https://restful-booker.herokuapp.com";
    public final String endPoint;
    UserEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
