package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Category;

public interface CategorysRepository extends ProjectionsAndSpecificationJpaRepository<Category, Integer> {
    List<Category> findAllByOrderByName();
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
