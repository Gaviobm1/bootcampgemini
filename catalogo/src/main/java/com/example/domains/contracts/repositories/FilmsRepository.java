package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.entities.Film;

public interface FilmsRepository extends JpaRepository<Film, Integer> {

}
