package models.service;

import api.RestAssuredApiClient;
import api.models.RequestDto;
import api.models.ResponseDto;
import config.TestConfig;
import fixtures.GoRestEndpoint;
import fixtures.UserEndPoint;
import models.UserInfo;
import org.aeonbits.owner.ConfigFactory;

import java.util.Collections;

public class UserService {
    public static final TestConfig PROD_DATA = ConfigFactory.create(TestConfig.class);

    public UserInfo getUserInfo() {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(UserEndPoint.BASE_URI.concat(GoRestEndpoint.READ_ENDPOINT.endPoint))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }
    public UserInfo getUserGoRestAPI(String userId) {
        RequestDto requestDto = RequestDto
                .builder()
                .queryParams(Collections.singletonMap("id", userId))
                .resourceLink(GoRestEndpoint.BASE_URI.concat(GoRestEndpoint.READ_ENDPOINT.endPoint))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo[] getUsersInfo() {
        RequestDto requestDto = RequestDto
                .builder()
                .resourceLink(UserEndPoint.BASE_URI.concat(GoRestEndpoint.READ_ENDPOINT.endPoint))
                .build();
        ResponseDto responseDto = RestAssuredApiClient.GET
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo[].class);
    }

    public UserInfo createUser(UserInfo userInfo) {
        RequestDto requestDto = RequestDto
                .builder()
                .header(Collections.singletonMap("Authorization", "Bearer " + PROD_DATA.authorizationToken()))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.CREATE_ENDPOINT.endPoint)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.POST
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public UserInfo updateUser(String userId, UserInfo userInfo) {
        RequestDto requestDto = RequestDto
                .builder()
                .header(Collections.singletonMap("Authorization", "Bearer " + PROD_DATA.authorizationToken()))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.UPDATE_ENDPOINT.endPoint)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.POST
                .apply(requestDto);
        return responseDto
                .getBody()
                .as(UserInfo.class);
    }

    public ResponseDto deleteUser(UserInfo userInfo) {
        RequestDto requestDto = RequestDto
                .builder()
                .header(Collections.singletonMap("Authorization", "Bearer " + PROD_DATA.authorizationToken()))
                .body(userInfo)
                .resourceLink(GoRestEndpoint.DELETE_ENDPOINT.endPoint)
                .build();
        ResponseDto responseDto = RestAssuredApiClient.POST
                .apply(requestDto);
        return responseDto;
    }
}
