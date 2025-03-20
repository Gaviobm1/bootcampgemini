package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorsService;
import com.example.domains.entities.models.ActorDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Actores microservico", description = "API para el mantenimiento de actores")
@RequestMapping("/actores/v1")
public class ActoresResource {
    private ActorsService srv;

    public ActoresResource(ActorsService srv) {
        super();
        this.srv = srv;
    }

    @GetMapping
    public List<ActorDTO> getAll() {
        return srv.getByProjection(ActorDTO.class);
    }

    @GetMapping(params = { "page" })
    @Operation(
        summary = "Obtiene todos los actores paginados",
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
    public Page<ActorDTO> getAll(Pageable pageable) {
        return srv.getByProjection(pageable, ActorDTO.class);
    }

    @GetMapping(path = "/{id}")
    public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return ActorDTO.from(item.get());
    }

    record Titulo(int id, String titulo) {
    };

    @Operation(summary = "Obtiene las películas en las que ha particpade el actor")
    @ApiResponse(responseCode = "200", description = "Una List de Titulo de las películas")
    @GetMapping(path = "/{id}/films")
    public List<Titulo> getFilms(
        @Parameter(description = "${actor.id.value}", required = true)
        @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return item.get().getFilmActors().stream()
                .map(o -> new Titulo(o.getFilm().getFilmId(), o.getFilm().getTitle()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(ActorDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getActorId() != id) {
            throw new BadRequestException("El id del actor no coincide con el recurso a modificar");
        }
        srv.modify(ActorDTO.from(item));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        srv.deleteById(id);
    }
}