package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Language;

public interface LanguagesRepository extends ProjectionsAndSpecificationJpaRepository<Language, Integer> {
    List<Language> findAllByOrderByName();
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
