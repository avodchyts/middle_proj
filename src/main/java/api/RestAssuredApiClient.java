package api;

import api.models.RequestDto;
import api.models.ResponseDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum RestAssuredApiClient implements Function<RequestDto, ResponseDto> {
    GET(RequestSenderOptions::get),
    POST(RequestSenderOptions::post),
    PUT(RequestSenderOptions::put),
    DELETE(RequestSenderOptions::delete),
    ;

    private final BiFunction<RequestSpecification, String, Response> requester;

    RestAssuredApiClient(BiFunction<RequestSpecification, String, Response> requester) {
        this.requester = requester;
    }

    private static final Logger LOGGER = Logger.getLogger(RestAssuredApiClient.class);

    @Override
    public ResponseDto apply(RequestDto request) throws RestAssuredApiClientError {
        try {
            RequestSpecification requestSpecification = new RequestSpecificationCreator().apply(request);
            Response response = requester.apply(requestSpecification, request.getResourceLink());
            return new ResponseDtoExtractor().apply(response);
        } catch (RestAssuredApiClientError restAssuredApiClientError) {
            LOGGER.error(restAssuredApiClientError.getMessage());
            throw restAssuredApiClientError;
        }
    }
}