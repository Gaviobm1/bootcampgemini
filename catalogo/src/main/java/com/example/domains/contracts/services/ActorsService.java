package com.example.domains.contracts.services;

import com.example.domains.core.contracts.services.DomainService;
import com.example.domains.entities.Actor;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ActorsService extends DomainService<Actor, Integer> {
	void repartePremios();

	String actorToJson(int id) throws JsonProcessingException;
}
