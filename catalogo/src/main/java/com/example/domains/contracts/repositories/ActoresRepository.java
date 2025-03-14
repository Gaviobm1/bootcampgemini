package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorShort;

public interface ActoresRepository extends JpaRepository<Actor, Integer> {

	List<Actor> queryByFirstNameStartingWithIgnoreCase(String prefijo);
	
	List<Actor> findTop5ByFirstNameStartingWith(
			String prefijo,
			Sort orderBy);
	
	List<Actor> findByActorIdGreaterThan(int id);
	
	List<ActorShort> queryByActorIdGreaterThan(int id);
	
	@Query(value = "SELECT a FROM Actor a WHERE a.actorId > ?1")
	List<Actor> findNovedadesJPQL(int id);
	
	@Query(value = "SELECT * FROM actor a WHERE a.actor_id > :id", nativeQuery = true)
	List<Actor> findNovedadesSQL(int id);
	
	List<Actor> findAll(Specification<Actor> spec);
}
