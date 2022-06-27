package com.owoonan.owoonan.global.jwt.repository;

import com.owoonan.owoonan.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
