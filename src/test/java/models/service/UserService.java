package models.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import fixtures.UserEndPoints;
import models.UserInfo;

public class UserService {
    public UserService() {
    }

    public UserInfo getUserInfo() {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(UserEndPoints.GET_ENDPOINT.endPoint)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }


}
