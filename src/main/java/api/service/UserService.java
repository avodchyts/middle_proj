package api.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import fixtures.GoRestEndpoint;
import models.UserInfo;

import java.util.Collections;

public class UserService {
    private final String token;

    public UserService(String token) {
        this.token = token;
    }
    public UserInfo getUserInfo(String id) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .resourceLink(GoRestEndpoint.READ_USER.getEndPoint())
                .pathParams(Collections.singletonMap("id", id))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo[] getUsersInfo() {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .resourceLink(GoRestEndpoint.READ_USERS.getEndPoint())
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo[].class);
    }

    public UserInfo createUser(UserInfo userInfo) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .headers(Collections.singletonMap("Authorization", token))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.CREATE_USERS.getEndPoint())
                .build();
        ResponseDto responseDto = RestAssuredApiClient.POST
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo updateUser(String userId, UserInfo userInfo) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .headers(Collections.singletonMap("Authorization", token))
                .pathParams(Collections.singletonMap("id", userId))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.UPDATE_USERS.getEndPoint())
                .build();
        ResponseDto responseDto = RestAssuredApiClient.PUT
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public void deleteUser(String userId) {
        RequestDto<UserInfo> requestDto = RequestDto
                .<UserInfo>builder()
                .headers(Collections.singletonMap("Authorization", token))
                .pathParams(Collections.singletonMap("id", userId))
                .resourceLink(GoRestEndpoint.DELETE_USERS.getEndPoint())
                .build();
        RestAssuredApiClient.DELETE.apply(requestDto);
    }
}
