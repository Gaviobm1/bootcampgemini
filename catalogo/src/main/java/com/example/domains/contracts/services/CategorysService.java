package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.entities.Category;

@Service
public interface CategorysService extends ProjectionDomainService<Category, Integer> {
    List<Category> novedades(Timestamp fecha);
}
