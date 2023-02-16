package models.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import models.UserInfo;

public class UserService {
    public String link;

    public UserService(String link) {
        this.link = link;
    }

    public UserInfo getUserInfo() {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(link)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }
}
