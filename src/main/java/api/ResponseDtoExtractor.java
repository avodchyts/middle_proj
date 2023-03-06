package api;

import api.models.ResponseDto;
import io.restassured.response.Response;

import java.util.function.Function;

public class ResponseDtoExtractor implements Function<Response, ResponseDto> {

    public ResponseDto apply(Response response) {
        if (response.statusCode() / 100 != 2) {
            new ErrorResponseHandler().accept(response);
        }

        return ResponseDto.builder()
                .statusMessage(response.getStatusLine())
                .statusCode(response.statusCode())
                .contentType(response.contentType())
                .body(response.body())
                .build();
    }
}
