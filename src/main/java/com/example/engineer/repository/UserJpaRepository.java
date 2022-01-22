package com.example.engineer.repository;

import com.example.engineer.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserJpaRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("select u from User u left join u.tasks where u.email=:email")
    User findUserWithTasks(String email);

    boolean existsByEmail(String email);
}
