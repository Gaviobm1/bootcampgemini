package com.example.domains.entities.models;

import org.springframework.data.rest.core.config.Projection;

import com.example.domains.entities.Category;

@Projection(name = "categoriaAcortada", types= {Category.class})
public interface CategoryProjection {
    public int getCategoryId();
    public String getName();
}
