package api;

import api.models.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.function.Function;

public enum ApiClient implements Function<RequestDto, IResponseDto> {
    GET(
            requestDto -> RestAssured.with().get(requestDto.getResourceLink()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return ModelFactory.selectResponseDtoType(response, "Response_Dto").get();

            }),
    GET_USER_INFO(
            requestDto -> RestAssured.with().get(requestDto.getResourceLink()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return ModelFactory.selectResponseDtoType(response, "User_Dto").get();
            }),
    GET_PARAM(
            requestDto -> RestAssured.with().queryParam("id", requestDto.getId()).get(requestDto.getResourceLink()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return ModelFactory.selectResponseDtoType(response, "User_Dto").get();
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