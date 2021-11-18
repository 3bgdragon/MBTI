package com.example.security.domain.user;

import com.example.security.core.BaseJpaQueryDSLRepository;

import java.util.Optional;

public interface UserRepository  extends BaseJpaQueryDSLRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
    /*Optional<UserInfo> findByEmail(String email);*/
}
