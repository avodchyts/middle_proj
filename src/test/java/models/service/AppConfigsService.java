package models.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import models.AppConfigsResponse;

import java.util.Collections;

public class AppConfigsService {
    private String link;

    public AppConfigsService(String link) {
        this.link = link;
    }

    public AppConfigsResponse[] getAppConfigs(String paramValue) {
        RequestDto requestDto = RequestDto.builder()
                .resourceLink(link)
                .queryParams(Collections.singletonMap("id", paramValue))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET.
                apply(requestDto);
        return responseDto
                .getBody()
                .as(AppConfigsResponse[].class);
    }
}
