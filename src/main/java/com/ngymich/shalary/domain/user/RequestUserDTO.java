package com.ngymich.shalary.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestUserDTO {
    private String username;
    private String password;
}
