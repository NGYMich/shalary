package com.ngymich.shalary.application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ngymich.shalary.application.authentication.LocalUser;
import com.ngymich.shalary.application.authentication.SocialProvider;
import com.ngymich.shalary.application.authentication.UserInfo;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;

public class GeneralUtils {

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }


    public static UserInfo buildUserInfo(LocalUser localUser) {
        PersistableUser user = localUser.getUser();

        return UserInfo
                .builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}