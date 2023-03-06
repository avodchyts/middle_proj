package api;

import api.models.ResponseDto;
import io.restassured.response.Response;

public class ResponseClient {

    private ResponseClient() {
    }

    public static ResponseDto getResponseDTO(Response response, String errorMessage) {
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
