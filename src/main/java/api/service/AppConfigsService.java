package api.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import fixtures.AppconfigEndPoint;
import models.AppConfigsResponse;

import java.util.Collections;

public class AppConfigsService {


    public ResponseDto getResponseAppConfigs(String paramValue) {
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(AppconfigEndPoint.BASE_URL.concat(AppconfigEndPoint.READ.endPoint))
                .queryParams(Collections.singletonMap("id", paramValue))
                .build();
        return RestAssuredApiClient.GET.
                apply(requestDto);
    }

    public AppConfigsResponse[] getAppConfigs(String paramValue) {
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(AppconfigEndPoint.BASE_URL.concat(AppconfigEndPoint.READ.endPoint))
                .queryParams(Collections.singletonMap("id", paramValue))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET.
                apply(requestDto);
        return responseDto
                .getBody()
                .as(AppConfigsResponse[].class);
    }

    public AppConfigsResponse[] postAppConfigs(String paramValue) {
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(AppconfigEndPoint.BASE_URL.concat(AppconfigEndPoint.CREATE.endPoint))
                .queryParams(Collections.singletonMap("id", paramValue))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET.
                apply(requestDto);
        return responseDto
                .getBody()
                .as(AppConfigsResponse[].class);
    }

    public AppConfigsResponse[] putAppConfigs(String paramValue) {
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(AppconfigEndPoint.BASE_URL.concat(AppconfigEndPoint.UPDATE.endPoint))
                .queryParams(Collections.singletonMap("id", paramValue))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET.
                apply(requestDto);
        return responseDto
                .getBody()
                .as(AppConfigsResponse[].class);
    }
}
