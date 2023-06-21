package com.farmAttic.client;

import com.farmAttic.Dtos.UserInfoDto;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Client("https://dev-72143172.okta.com/oauth2/default")
public interface UserInfoClient {

    @Post(value = "https://dev-72143172.okta.com/oauth2/default/v1/userinfo")
    UserInfoDto getUserInfo(@Header(AUTHORIZATION) String authorization);


}
