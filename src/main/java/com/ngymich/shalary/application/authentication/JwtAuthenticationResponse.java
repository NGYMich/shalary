package com.ngymich.shalary.application.authentication;
import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private UserInfo user;
}
