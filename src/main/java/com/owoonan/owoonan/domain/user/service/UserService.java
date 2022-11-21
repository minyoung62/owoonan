package com.owoonan.owoonan.domain.user.service;

import com.owoonan.owoonan.domain.user.domain.User;
import com.owoonan.owoonan.domain.user.domain.vo.RoleType;
import com.owoonan.owoonan.domain.user.dto.UserResponseDto;
import com.owoonan.owoonan.domain.user.error.UserAlreadyRegisterException;
import com.owoonan.owoonan.domain.user.error.UserNotFoundException;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findUser(final String userId) {
        User user = getUser(userId);
        return UserResponseDto.of(user);
    }

    public void deleteUser(final String userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }

    public void registerUser(final String userId) {
        User user = getUser(userId);
        if(user.getRoleType() != RoleType.GUEST) throw new UserAlreadyRegisterException(ErrorCode.USER_ALREADY_REGISTER);
        user.setRoleType(RoleType.USER);
        userRepository.save(user);
    }
    private User getUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        return user;
    }
}
