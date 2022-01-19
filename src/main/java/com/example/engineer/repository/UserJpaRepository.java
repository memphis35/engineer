package com.example.engineer.repository;

import com.example.engineer.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);

    boolean existsByEmail(String email);
}
