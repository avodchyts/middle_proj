package api;

import api.models.ResponseDto;
import io.restassured.response.Response;

public class ResponseClient {
    public static Response response;

    private ResponseClient(Response response) {
        this.response = response;
    }

    public static ResponseDto getResponseDTO(String errorMessage) {
        if (response.statusCode() / 100 != 2) {
            throw new RestAssuredApiClientError(errorMessage, response);
        }
        return ResponseDto.builder()
                .statusMessage(response.getStatusLine())
                .statusCode(response.statusCode())
                .contentType(response.contentType())
                .body(response.body())
                .build();
    }
}
