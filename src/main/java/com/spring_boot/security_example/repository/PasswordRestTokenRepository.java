package com.spring_boot.security_example.repository;

import com.spring_boot.security_example.entity.PasswordRestToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRestTokenRepository extends JpaRepository<PasswordRestToken, Long> {
    PasswordRestToken findByToken(String token);
}
