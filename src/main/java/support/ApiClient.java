package support;

import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.util.function.Function;

public enum ApiClient implements Function<RequestDto, IResponseDto> {
    DUMMY_OK(
            request -> new RestAssuredResponseImpl(),
            response -> ResponseDto.builder()
                    .statusCode(HttpStatus.SC_OK)
                    .statusMessage("HTTP/1.1 200 OK")
                    .build()
    ),
    DUMMY_ERROR(
            request -> new RestAssuredResponseImpl(),
            response -> {
                throw new ApiClientError(response);
            }
    ),
    GET(
            requestDto -> RestAssured.with().get(requestDto.getResourceLink()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return ResponseDto.builder().statusCode(response.statusCode()).statusMessage(response.getStatusLine()).build();
            }),
    GET_USER_INFO(
            requestDto -> RestAssured.with().get(requestDto.getResourceLink()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return UserDTO.builder().userId(response.jsonPath().getString("userId")).build();
            }),
    GET_PARAM(
            requestDto -> RestAssured.with().queryParam("id", requestDto.getId()).get(requestDto.getResourceLink()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return response.as(ParamResponseDto.class);
            });
    private final Function<RequestDto, Response> requester;
    private final Function<Response, IResponseDto> responseProcessor;
    ApiClient(Function<RequestDto, Response> requester, Function<Response, IResponseDto> responseProcessor) {
        this.requester = requester;
        this.responseProcessor = responseProcessor;
    }
    private static final Logger LOGGER = Logger.getLogger(ApiClient.class);

    @Override
    public IResponseDto apply(RequestDto request) throws ApiClientError {
        try {
            Response response = requester.apply(request);
            return responseProcessor.apply(response);
        } catch (ApiClientError mess) {
            LOGGER.info(mess.getMessage());
            throw new RuntimeException(mess.getMessage());
        }
    }
}