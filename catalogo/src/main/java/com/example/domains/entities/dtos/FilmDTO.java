package com.example.domains.entities.dtos;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class FilmDTO {
    private int filmId;
    private String title;
    private String description;
    private Short releaseYear;
    private Language language;
    private Language originalLanguage;

    public static FilmDTO from(Film source) {
        return new FilmDTO(
            source.getFilmId(), 
            source.getTitle(), 
            source.getDescription(), 
            source.getReleaseYear(), 
            source.getLanguage(), 
            source.getLanguageVO()
            );
    }

    public static Film from(FilmDTO source) {
        return new Film(
            source.getFilmId(),
            source.getTitle(),
            source.getDescription(),
            source.getReleaseYear(),
            source.getLanguage(),
            source.getOriginalLanguage()
        );
    }

}