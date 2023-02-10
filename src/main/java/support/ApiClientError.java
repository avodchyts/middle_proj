package support;

import io.restassured.response.Response;

public class ApiClientError extends Error {
    public ApiClientError(Response response) {
        super(response.asString());
    }
}
