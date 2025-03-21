package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.entities.Language;

@Service
public interface LanguagesService extends ProjectionDomainService<Language, Integer> {
    List<Language> novedades(Timestamp fecha);
}
