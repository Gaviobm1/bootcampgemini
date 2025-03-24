package com.example.domains.entities.models;

import org.springframework.data.rest.core.config.Projection;

import com.example.domains.entities.Film;

@Projection(name = "filmAcortada", types= {Film.class})
public interface FilmProjection {
    public int getFilmId();
    public String getTitle();
}