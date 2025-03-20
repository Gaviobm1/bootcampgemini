package com.example.application.resources;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.FilmsService;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.records.ActorName;
import com.example.domains.entities.records.Title;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/films/v1")
@Tag(name = "Films Controller", description = "Controlador de películas")
public class FilmResource {

    private FilmsService srv;

    public FilmResource(FilmsService srv) {
        super();
        this.srv = srv;
    }

    @GetMapping
    @Hidden
    public List<FilmDetailsDTO> getAll() {
        List<Film> films = srv.getAll();
        return films.stream().map(film -> FilmDetailsDTO.from(film)).toList();
    }

    @GetMapping(params = { "page" })
    @Operation(summary = "Devuelve una lista de películas paginado", parameters = {
            @Parameter(name = "page", description = "Número de página (desde 0)", example = "1"),
            @Parameter(name = "size", description = "Número de elementos por página", example = "5"),
            @Parameter(name = "sort", description = "Orden en cual queremos devolver los resultados", example = "title,asc")
    })
    @ApiResponse(responseCode = "200", description = "Busca y devulve una lista de películas")
    public Page<FilmDetailsDTO> getAll(Pageable pageable) {
        Page<Film> films = srv.getAll(pageable);
        List<FilmDetailsDTO> filmDTOs = films.stream().map(film -> FilmDetailsDTO.from(film)).toList();
        return new PageImpl<FilmDetailsDTO>(filmDTOs, pageable, films.getTotalElements());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca la película con el id especificado", parameters = @Parameter(name = "id", example = "6"))
    @ApiResponse(responseCode = "200", description = "Película con el id especificado")
    public FilmDetailsDTO getOne(@PathVariable int id) throws NotFoundException {
        Optional<Film> film = srv.getOne(id);
        if (film.isEmpty()) {
            throw new NotFoundException("No se encontró la película con el id " + id);
        }
        return FilmDetailsDTO.from(film.get());
    }

    @GetMapping("/{id}/actors")
    @Operation(summary = "Obtiene una lista de los actores en una película", parameters = @Parameter(name = "id", example = "3"))
    @ApiResponse(responseCode = "200", description = "Actores del película del id especificado")
    public List<ActorName> getActors(@PathVariable int id) throws NotFoundException {
        Optional<Film> film = srv.getOne(id);
        if (film.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return film.get().getFilmActors().stream()
                .map(filmActor -> new ActorName(filmActor.getActor().getActorId(), filmActor.getActor().getFirstName(), filmActor.getActor().getLastName()))
                .toList();
    }

}
