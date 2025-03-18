package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.example.domains.entities.Language;

public interface LanguagesRepository extends ListCrudRepository<Language, Integer> {
    List<Language> findAllByOrderByName();
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
