package com.ngymich.shalary.application.authentication;
import com.ngymich.shalary.application.user.UserDTO;
import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private UserDTO user;
}
