package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.entities.Actor;

@Service
public interface ActorsService extends ProjectionDomainService<Actor, Integer> {
	void repartePremios();
	List<Actor> novedades(Timestamp fecha);
}
