package api;

import api.models.ResponseDto;
import io.restassured.response.Response;
import lombok.Getter;

public class RestAssuredApiClientError extends Error {
    @Getter
    private final ResponseDto response;

    public RestAssuredApiClientError(String message, Response response) {
        super(message);
        this.response = ResponseDto.builder()
                .statusCode(response.getStatusCode())
                .statusMessage(response.getStatusLine())
                .contentType(response.contentType())
                .body(response.body())
                .build();
    }

}
