package com.owoonan.owoonan.domain.user.dto;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.domain.vo.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserResponseDto {

    private String Email;
    private String username;
    private RoleType roleType;
    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .Email(user.getEmail())
                .username(user.getUsername())
                .roleType(user.getRoleType())
                .build();
    }
}
