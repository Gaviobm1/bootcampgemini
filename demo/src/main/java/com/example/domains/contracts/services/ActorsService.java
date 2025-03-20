package com.example.domains.contracts.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.domains.core.contracts.services.DomainService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.models.ActorDTO;

public interface ActorsService extends DomainService<Actor, Integer> {
	List<ActorDTO> getByProjection(Class<ActorDTO> projection);
	Page<ActorDTO> getByProjection(Pageable pageable, Class<ActorDTO> projection);
}
