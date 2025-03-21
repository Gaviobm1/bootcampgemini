package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Film;
import com.example.domains.entities.Film.SpecialFeature;
import com.example.domains.entities.Language;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Pelicula (Editar)", description = "Version editable de las películas")
@Data @AllArgsConstructor @NoArgsConstructor
public class FilmEditDTO {

	@Schema(description = "Identificador de la película", accessMode = AccessMode.READ_ONLY, example = "0")
	private int filmId;

	@Schema(description = "Una breve descripción o resumen de la trama de la película", minLength = 2, example = "Una aventura en tren")
	private String description;

	@Schema(description = "La duración de la película, en minutos", minimum = "0", exclusiveMinimum = true, example = "120")
	private Integer length;

	@Schema(description = "La clasificación por edades asignada a la película", allowableValues = {"G", "PG", "PG-13", "R", "NC-17"}, example = "R")
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;

	@Schema(description = "El año en que se estrenó la película", minimum = "1901", maximum = "2155", example = "1995")
	private Short releaseYear;

	@Schema(description = "La duración del período de alquiler, en días", minimum = "0", exclusiveMinimum = true, example = "5")
	@NotNull
	private byte rentalDuration;

	@Schema(description = "El coste de alquilar la película por el período establecido", minimum = "0", exclusiveMinimum = true, example = "5.99")
	@NotNull
	private BigDecimal rentalRate;

	@Schema(
        description = "El importe cobrado al cliente si la película no se devuelve o se devuelve en un estado dañado", 
        minimum = "0", exclusiveMinimum = true, example = "25.99")
	@NotNull
	private BigDecimal replacementCost;

	@Schema(description = "El título de la película", example = "VÍA KILLERS")
	@NotBlank
	@Size(min=2, max = 128)
	private String title;

	@Schema(description = "El identificador del idioma de la película", example = "1")
	@NotNull
	private Integer languageId;

	@Schema(description = "El identificador del idioma original de la película", example = "2")
	private Integer languageVOId;

	@Schema(description = "Contenido Adicional", example = "[\"Trailers\", \"Commentaries\", \"Deleted Scenes\"]")
	private List<String> specialFeatures = new ArrayList<>();

	@Schema(description = "La lista de identificadores de actores que participan en la película", example = "[3, 4, 6, 7, 8]")
	private List<Integer> actors = new ArrayList<>();

    @Schema(description = "La lista de identificadores de categorías asignadas a la película", example = "[1, 2, 3]")
    @ArraySchema(uniqueItems = true, minItems = 1, maxItems = 3)
	private List<Integer> categories = new ArrayList<>();

 	public static FilmEditDTO from(Film source) {
		return new FilmEditDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating() == null ? null : source.getRating().getValue(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getLanguageId(),
				source.getSpecialFeatures().stream().map(item -> item.getValue()).sorted().toList(),
				source.getActors().stream().map(item -> item.getActorId())
					.collect(Collectors.toList()),
				source.getCategories().stream().map(item -> item.getCategoryId())
					.collect(Collectors.toList())
				);
	}
	public static Film from(FilmEditDTO source) {
		return new Film(
				source.getFilmId(), 
				source.getTitle(),
				source.getLanguageId() == null ? null : new Language(source.getLanguageId()),
				source.getRentalDuration(),
				source.getRentalRate(),	
				source.getReplacementCost()
				);
	}
}