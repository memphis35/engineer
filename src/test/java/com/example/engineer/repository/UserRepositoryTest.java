package com.example.engineer.repository;

import com.example.engineer.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "classpath:data.sql")
public class UserRepositoryTest {

    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "asmirnov@engineer.com";

    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryTest(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Test
    public void whenFindUserWithTasks_thenSuccess() {
        final User actual = userJpaRepository.findUserWithTasks(USER_EMAIL);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(5, actual.getTasks().size());
    }
}
