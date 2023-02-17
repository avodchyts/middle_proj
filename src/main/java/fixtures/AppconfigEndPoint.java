package fixtures;

public enum AppconfigEndPoint {
    CREATE(""),
    READ("/api/v1/appconfigs"),
    UPDATE(""),
    DELETE("");

    public final String endPoint;
    public static final String BASE_URL = "https://my.nutanix.com";

    AppconfigEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
