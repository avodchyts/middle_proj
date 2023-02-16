package fixtures;

public enum UserEndPoints {
    GET_ENDPOINT("https://www.nutanix.com/bin/get/userinfo.json"),
    POST_ENDPOINT(""),
    PUT_ENDPOINT(""),
    DELETE_ENDPOINT("");

    public final String endPoint;
    UserEndPoints(String endPoint) {
        this.endPoint = endPoint;
    }
}
