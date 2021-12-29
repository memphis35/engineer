package com.example.engineer.repository;

import com.example.engineer.domain.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesCrudRepository extends CrudRepository<Authority, Long> {
}
