package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.Film;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Schema(name= "Film", description = "Los datos de la película")
public class FilmDetailsDTO {

    private int filmId;

    @Schema(description = "Título de la película", minimum = "2", maximum = "45")
    private String title;

    @Schema(description = "Descripción de la película", minimum = "0", maximum = "1000")
    private String description;

    @Schema(description = "Año de lanzamiento de la película", minimum = "1890", maximum = "2200")
    private Short releaseYear;

    @Schema(description = "El idioma de la película")
    private String language;

    @Schema(description = "El idioma original de la película")
    private String originalLanguage;

    @Schema(description = "Lista de actores que ha participado en la película")
    private List<String> actors;

    @Schema(description = "List de categorías de la película")
    private List<String> categorias;

    public static FilmDetailsDTO from(Film source) {
        return new FilmDetailsDTO(
            source.getFilmId(), 
            source.getTitle(), 
            source.getDescription(), 
            source.getReleaseYear(), 
            source.getLanguage() ==  null ? null : source.getLanguage().getName(), 
            source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
            source.getActors().stream().map(actor -> actor.getFirstName() + " " + actor.getLastName())
                .sorted()
                .toList(),
            source.getCategories().stream().map(categoria -> categoria.getName()).sorted().toList()
            );
    }
}