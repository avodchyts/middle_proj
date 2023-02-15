package api;

import api.models.IResponseDto;
import api.models.ModelFactory;
import api.models.RequestDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;

public enum HttpApiClient implements IApiClient<RequestDto, IResponseDto>{

    GET(
            requestDto -> HttpClient.newHttpClient().send((HttpRequest.newBuilder().uri(new URI(requestDto.getResourceLink())).GET()), HttpResponse.BodyHandlers.ofString()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return ModelFactory.selectResponseDtoType(response, "Response_Dto").get();

            }),
    GET_USER_INFO(
            requestDto -> HttpClient.newHttpClient().send((HttpRequest.newBuilder().uri(new URI(requestDto.getResourceLink())).GET()), HttpResponse.BodyHandlers.ofString()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return response.as(ModelFactory.selectResponseDtoType(response, "User_Dto").get().getClass());
            }),
    GET_PARAM(
            requestDto -> HttpClient.newHttpClient().send((HttpRequest.newBuilder().uri(new URI(requestDto.getResourceLink())).header("id", requestDto.getId()).GET()), HttpResponse.BodyHandlers.ofString()),
            response -> {
                if (response.statusCode() / 100 == 4) {
                    throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
                }
                return ModelFactory.selectResponseDtoType(response, "Response_Dto").get();

            }
    );

//    GET_USER_INFO(
//            requestDto -> HttpClient.newHttpClient().send((HttpRequest.newBuilder().uri(new URI(requestDto.getResourceLink())), HttpResponse.BodyHandlers.ofString()).GET()),
//    response -> {
//        if (response.statusCode() / 100 == 4) {
//            throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
//        }
//        return response.as(ModelFactory.selectResponseDtoType(response, "Response_Dto").get().getClass());
//    }),
//
//    GET_PARAM(
//            requestDto ->HttpClient.newHttpClient().send((HttpRequest.newBuilder().uri(new URI(requestDto.getResourceLink())).header("id",requestDto.getId())).GET()),
//    response ->
//
//    {
//        if (response.statusCode() / 100 == 4) {
//            throw new IllegalArgumentException((String) response.htmlPath().get("html.body"));
//        }
//        return response.as(ModelFactory.selectResponseDtoType(response, "User_Dto").get().getClass());
//    });
    private final Function<RequestDto, Response> requester;
    private final Function<Response, IResponseDto> responseProcessor;

    HttpApiClient(Function<RequestDto, Response> requester, Function<Response, IResponseDto> responseProcessor) {
        this.requester = requester;
        this.responseProcessor = responseProcessor;
    }

    private static final Logger LOGGER = Logger.getLogger(RestAssureApiClient.class);

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
