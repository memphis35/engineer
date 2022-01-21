package com.example.engineer.repository;

import com.example.engineer.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationCrud extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByEmailAndUuidAndExpiredAtAfter(String email, String uuid, LocalDateTime now);
}
