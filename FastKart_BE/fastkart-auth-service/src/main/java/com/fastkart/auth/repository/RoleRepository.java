package com.fastkart.auth.repository;


import com.fastkart.auth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
}
