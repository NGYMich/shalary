package com.ngymich.shalary.application.authentication;

import java.util.List;

import lombok.Value;

@Value
public class UserInfo {
    private String id, displayName, email;
}
