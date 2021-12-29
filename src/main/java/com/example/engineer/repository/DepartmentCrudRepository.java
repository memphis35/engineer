package com.example.engineer.repository;

import com.example.engineer.domain.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentCrudRepository extends CrudRepository<Department, Long> {
}
