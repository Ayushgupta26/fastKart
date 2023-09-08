package com.fastkart.auth.repository;

import com.fastkart.auth.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByUserName(String username);
}
