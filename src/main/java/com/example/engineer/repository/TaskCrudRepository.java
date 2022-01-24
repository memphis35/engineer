package com.example.engineer.repository;

import com.example.engineer.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCrudRepository extends JpaRepository<Task, Long> {

}
