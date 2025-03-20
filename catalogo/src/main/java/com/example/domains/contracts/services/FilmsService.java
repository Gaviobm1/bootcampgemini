package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.core.contracts.services.SpecificationDomainService;
import com.example.domains.entities.Film;

@Service
public interface FilmsService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer> {
    List<Film> novedades(Timestamp fecha);
}
