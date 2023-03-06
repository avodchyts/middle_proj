package api;

import api.models.RequestDto;
import api.models.ResponseDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.function.Function;

public enum RestAssuredApiClient implements Function<RequestDto, ResponseDto> {
    GET(
            requestDto -> {
                RequestSpecification requestSpecification = getRequestSpecification(requestDto);

                return requestSpecification.get(requestDto.getResourceLink());
            },
            response -> ResponseClient.getResponseDTO(getErrorMessage(response))),
    POST(
            requestDto -> {
                RequestSpecification requestSpecification = getRequestSpecification(requestDto);

                return requestSpecification.post(requestDto.getResourceLink());
            },
            response -> ResponseClient.getResponseDTO(getErrorMessage(response))),
    PUT(
            requestDto -> {
                RequestSpecification requestSpecification = getRequestSpecification(requestDto);

                return requestSpecification.put(requestDto.getResourceLink());
            },
            response -> ResponseClient.getResponseDTO(getErrorMessage(response))),
    DELETE(
            requestDto -> {
                RequestSpecification requestSpecification = getRequestSpecification(requestDto);

                return requestSpecification.delete(requestDto.getResourceLink());
            },
            response -> ResponseClient.getResponseDTO(getErrorMessage(response))),
    ;

    private final Function<RequestDto, Response> requester;
    private final Function<Response, ResponseDto> responseProcessor;

    RestAssuredApiClient(Function<RequestDto, Response> requester, Function<Response, ResponseDto> responseProcessor) {
        this.requester = requester;
        this.responseProcessor = responseProcessor;
    }

    private static final Logger LOGGER = Logger.getLogger(RestAssuredApiClient.class);

    @Override
    public ResponseDto apply(RequestDto request) throws RestAssuredApiClientError {
        try {
            Response response = requester.apply(request);
            return responseProcessor.apply(response);
        } catch (RestAssuredApiClientError restAssuredApiClientError) {
            LOGGER.error(restAssuredApiClientError.getMessage());
            throw restAssuredApiClientError;
        }
    }

    private static String getErrorMessage(Response response) {
        if (ContentType.HTML.matches(response.contentType())) {
            return response.htmlPath().get("html.body");
        }

        if (ContentType.JSON.matches(response.contentType())) {
            return response.body().asPrettyString();
        }

        if (ContentType.TEXT.matches(response.contentType())) {
            return response.body().asString();
        }

        return "Error has occurred";
    }

    private static RequestSpecification getRequestSpecification(RequestDto requestDto) {
        RequestSpecification requestSpecification = RestAssured.with();

        if (Objects.nonNull(requestDto.getHeader())) {
            requestSpecification.headers(requestDto.getHeader());
        }

        if (Objects.nonNull(requestDto.getContentType())) {
            requestSpecification.contentType(requestDto.getContentType());
        }

        if (Objects.nonNull(requestDto.getQueryParams())) {
            requestSpecification.queryParams(requestDto.getQueryParams());
        }

        if (Objects.nonNull(requestDto.getPathParams())) {
            requestSpecification.pathParams(requestDto.getPathParams());
        }

        if (Objects.nonNull(requestDto.getBody())) {
            requestSpecification.body(requestDto.getBody());
        }

        return requestSpecification;
    }
}