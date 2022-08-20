package com.owoonan.owoonan.domain.user.util;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.domain.vo.ProviderType;
import com.owoonan.owoonan.domain.user.domain.vo.RoleType;

public class GivenUser {
    public static final String userId = "testuserId";
    public static final String email = "test@test.com";
    public static final String username = "testUserName";
    public static final RoleType roleTyle = RoleType.USER;
    public static final ProviderType provider = ProviderType.LOCAL;

    public static User toEntity() {
        return User.builder()
                .userId(userId)
                .email(email)
                .username(username)
                .emailVerifiedYn("0")
                .roleType(roleTyle)
                .providerType(provider)
                .build();

    }
}
