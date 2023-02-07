package support;

import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

import java.util.function.Function;

import static io.restassured.RestAssured.given;

public enum ApiClient implements Function<RequestDto, ResponseDto> {

    DUMMY_OK(
            request -> new RestAssuredResponseImpl(),
            response -> ResponseDto.builder().statusCode(String.valueOf(HttpStatus.SC_OK))
                    .statusMessage("OK")
                    .build()
    ),
    DUMMY_ERROR(
            request -> new RestAssuredResponseImpl(),
            response -> ResponseDto.builder().statusCode(String.valueOf(HttpStatus.SC_NOT_FOUND))
                    .statusMessage("Not found")
                    .build());


        /* here you need to implement real requester and response processor for GET requests */
        static {
            given().contentType("application/json").when().get(RequestDto.resourceLink).then();
        }

    private final Function<RequestDto, Response> requester;
    private final Function<Response, ResponseDto> responseProcessor;
    ApiClient(Function<RequestDto, Response> requester, Function<Response, ResponseDto> responseProcessor) {
        this.requester = requester;
        this.responseProcessor = responseProcessor;
    }
    @Override
    public ResponseDto apply(RequestDto request) {
        Response response = requester.apply(request);
        return responseProcessor.apply(response);
    }
}