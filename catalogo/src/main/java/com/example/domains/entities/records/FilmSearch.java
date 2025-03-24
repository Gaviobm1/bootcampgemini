package com.example.domains.entities.records;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record FilmSearch(
        @Schema(description = "Que el titulo contenga") String title,
        @Schema(description = "Duración mínima de la pelicula") Integer minlength,
        @Schema(description = "Duración máxima de la pelicula") Integer maxlength,
        @Schema(description = "La clasificación por edades asignada a la película", allowableValues = {
                "G", "PG", "PG-13", "R", "NC-17" }) @Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$") String rating,
        @Schema(description = "Duración máxima de la pelicula", type = "string", allowableValues = {
                "details", "short" }, defaultValue = "short") String mode){
}