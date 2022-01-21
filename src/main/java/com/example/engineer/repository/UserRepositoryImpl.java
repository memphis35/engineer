package com.example.engineer.repository;

import com.example.engineer.domain.EmailVerification;
import com.example.engineer.domain.User;
import com.example.engineer.exceptions.UserNotFoundException;
import com.example.engineer.exceptions.VerifyingLinkExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final EmailVerificationCrud emailVerificationCrud;

    @Override
    public User findUser(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User createUser(User newUser) {
        return userJpaRepository.save(newUser);
    }

    @Override
    public boolean userExists(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userJpaRepository.findUserByEmail(email);
    }

    @Override
    public String createVerifyingLink(EmailVerification verification) {
        emailVerificationCrud.save(verification);
        return String.format("http://localhost:8080/verifyEmail?email=%s&uuid=%s",
                verification.getEmail(), verification.getUuid());
    }

    @Override
    @Transactional
    public void verifyEmail(String email, String uuid) {
        emailVerificationCrud.findByEmailAndUuidAndExpiredAtAfter(email, uuid, LocalDateTime.now())
                .ifPresentOrElse(emailVerification -> {
                    log.debug("E-mail verification UUID has been found");
                    final User user = userJpaRepository.findUserByEmail(emailVerification.getEmail());
                    user.setEnabled(true);
                    log.info("User {} is now enabled and can login into the system", user.getEmail());
                    emailVerificationCrud.delete(emailVerification);
                    log.debug("E-mail verification UUID has been deleted");
                }, () -> {
                    log.warn("Can't find any verification");
                    throw new VerifyingLinkExpiredException("Срок действия регистрационной ссылки истек");
                });
    }

    @Override
    public User findUserWithTasks(String email) {
        return userJpaRepository.findUserWithTasks(email);
    }

    public static void main(String[] args) {
        final EmailVerification emailVerification = new EmailVerification("mail@mail.com");
        System.out.printf("http://localhost:8080/verifyEmail?email=%s&uuid=%s%n",
                emailVerification.getEmail(), emailVerification.getUuid());
    }
}
