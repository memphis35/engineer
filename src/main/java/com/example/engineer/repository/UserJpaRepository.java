package com.example.engineer.repository;

import com.example.engineer.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);

    @Query(value = "select u from User u join fetch u.authorities where u.email=:email")
    User findUserByEmailWithAuths(String email);

    boolean existsByEmail(String email);
}
