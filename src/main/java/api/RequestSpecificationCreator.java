package api;

import api.models.RequestDto;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Objects;
import java.util.function.Function;

public class RequestSpecificationCreator implements Function<RequestDto<?>, RequestSpecification> {

    @Override
    public RequestSpecification apply(RequestDto<?> requestDto) {
        RequestSpecification requestSpecification = RestAssured.with()
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        if (Objects.nonNull(requestDto.getHeaders())) {
            requestSpecification.headers(requestDto.getHeaders());
        }

        if (Objects.nonNull(requestDto.getContentType())) {
            requestSpecification.contentType(requestDto.getContentType());
        } else {
            requestSpecification.contentType(ContentType.JSON);
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
