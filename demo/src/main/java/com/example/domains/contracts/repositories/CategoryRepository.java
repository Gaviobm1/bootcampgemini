package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domains.entities.Category;
import com.example.domains.entities.models.NameOnly;

@RepositoryRestResource(excerptProjection = NameOnly.class)
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
