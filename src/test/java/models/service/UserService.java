package models.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import fixtures.UserEndPoint;
import models.UserInfo;

public class UserService {

    public UserInfo getUserInfo() {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(UserEndPoint.BASE_URI.concat(UserEndPoint.READ_ENDPOINT.endPoint))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo createUser(UserInfo userInfo) {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(UserEndPoint.CREATE_ENDPOINT.endPoint)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo updateUser(String userId, UserInfo user) {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(UserEndPoint.UPDATE_ENDPOINT.endPoint)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }
}
