package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.entities.Language;

public interface LanguagesRepository extends JpaRepository<Language, Integer> {
    
}
