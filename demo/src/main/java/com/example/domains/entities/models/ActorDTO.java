package com.example.domains.entities.models;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="Actor", description = "Datos del actor")
@Data @AllArgsConstructor @NoArgsConstructor
public class ActorDTO {

	@JsonProperty("id")
	@Schema(description = "Identificador del actor", example = "437644")
	private int actorId;
	@NotBlank
	@Size(min = 2, max = 45)
	@Schema(description = "Nombre del actor", example = "Daryl")
	private String firstName;
	@NotBlank
	@Size(min = 2, max = 45)
	@Schema(description = "Apellido del actor", example = "McCormack")
	private String lastName;
	
	public static ActorDTO from(Actor source) {
		return new ActorDTO(source.getActorId(), source.getFirstName(), source.getLastName());
	}
	
	public static Actor from(ActorDTO source) {
		return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
	}
}