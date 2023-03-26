package com.ngymich.shalary.application.authentication;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import com.ngymich.shalary.application.util.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Data
@PasswordMatches
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private Long userID;

    private String providerUserId;

    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    private SocialProvider socialProvider;

    @Size(min = 4, message = "{Size.userDto.password}")
    private String password;

    @NotEmpty
    private String matchingPassword;

}
