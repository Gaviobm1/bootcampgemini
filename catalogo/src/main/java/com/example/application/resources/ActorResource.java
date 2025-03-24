package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.http.HttpHeaders;

import com.example.domains.contracts.services.ActorsService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.records.Title;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/actors/v1")
@Tag(name = "Actors Controller", description = "Controlador de actores")
public class ActorResource {
    private ActorsService srv;

    public ActorResource(ActorsService srv) {
        super();
        this.srv = srv;
    }

    @GetMapping
    @Hidden
    public List<ActorDTO> getAll() {
        return srv.getByProjection(ActorDTO.class);
    }

    @GetMapping(params = {"page"})
    @Operation(summary = "Obtiene una lista paginada de actores", 
    parameters = {
        @Parameter(
            name = "page",
            description = "Número de página (desde 0)",
            example = "1"
        ),
        @Parameter(
            name = "size",
            description = "Número de elementos por página",
            example = "5"
        ),
        @Parameter(
            name = "sort",
            description = "Orden en cual queremos devolver los resultados",
            example = "firstName, asc"
        )
    }
    )
    @ApiResponse(responseCode = "200", description = "Lista de actores paginada")
    public Page<ActorDTO> getAll(@ParameterObject Pageable pageable) {
        return srv.getByProjection(pageable, ActorDTO.class);
    }

    @GetMapping(path = "/{id}")
    @Operation(
        summary = "Obtiene un actor por su id", 
        parameters = @Parameter(name = "id", description = "Id del actor como path variable")
        )
    @ApiResponse(responseCode = "200", description = "El actor con el id procporcionado")
    @JsonView(Actor.Partial.class)
    public Actor getOne(@PathVariable int id) throws NotFoundException {
        Optional<Actor> actor = srv.getOne(id);
        if (actor.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return actor.get();
    }

    @GetMapping(path = "/{id}/films")
    @Operation(
        summary = "Obtiene las películas en las que ha participado un actor", 
        parameters = @Parameter(name = "id", description = "Id del actor como path variable")
    )
    @ApiResponse(responseCode = "200", description = "Lista de películas en las que ha participade el actor")
    public List<Title> getFilms(@PathVariable int id) throws NotFoundException {
        Optional<Actor> actor = srv.getOne(id);
        if (actor.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return actor.get().getFilmActors().stream()
                .map(filmActor -> new Title(filmActor.getFilm().getFilmId(), filmActor.getFilm().getTitle()))
                .toList();   
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo actor", 
    parameters = @Parameter(name = "actor", description = "Datos del actor a crear")
    )
    @ApiResponse(responseCode = "201", description = "La locación del actor creado")
    public ResponseEntity<Object> create(
        @Valid
        @RequestBody ActorDTO actor) throws BadRequestException, DuplicateKeyException, InvalidDataException {
            Actor newActor = srv.add(ActorDTO.from(actor));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newActor.getActorId())
                .toUri();
            return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualiza el actor")
    @ApiResponse(responseCode = "204", description = "El actor ha sido actualizado")
    public void update(
        @PathVariable int id,
        @Valid
        @RequestBody ActorDTO actor
    ) throws BadRequestException, NotFoundException, InvalidDataException {
        if (actor.getActorId() != id) {
            throw new BadRequestException("El id del actor no coincide con el id proporcionado");
        }
        srv.modify(ActorDTO.from(actor));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Elimina un actor por su id")
    @ApiResponse(responseCode = "204", description = "El actor ha sido eliminado")
    public ResponseEntity<Object> delete(@PathVariable int id) throws NotFoundException {
        srv.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        if (srv.getOne(id).isEmpty()) {
            headers.add("Message", "El actor ha sido eliminado");
        } else {   
            headers.add("Message", "El actor no ha sido eliminado");    
        }
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
