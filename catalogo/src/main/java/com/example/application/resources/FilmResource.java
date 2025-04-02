package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmsService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.domains.entities.records.ActorName;
import com.example.domains.entities.records.CategoryName;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.entities.records.FilmSearch;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/films/v1")
@Tag(name = "Films Controller", description = "Controlador de películas")
public class FilmResource {

    private FilmsService srv;

    public FilmResource(FilmsService srv) {
        super();
        this.srv = srv;
    }

    @GetMapping(params = { "page" })
    @Operation(summary = "Devuelve una lista de películas paginado", parameters = {
            @Parameter(name = "page", description = "Número de página (desde 0)", example = "1"),
            @Parameter(name = "size", description = "Número de elementos por página", example = "5"),
            @Parameter(name = "sort", description = "Orden en cual queremos devolver los resultados", example = "title,asc")
    })
    @ApiResponse(responseCode = "200", description = "Busca y devuelve una lista de películas")
    public Page<FilmDetailsDTO> getAll(Pageable pageable) {
        Page<Film> films = srv.getAll(pageable);
        List<FilmDetailsDTO> filmDTOs = films.stream().map(film -> FilmDetailsDTO.from(film)).toList();
        return new PageImpl<FilmDetailsDTO>(filmDTOs, pageable, films.getTotalElements());
    }

    @GetMapping(path = "/{id}", params = "mode=short")
    @Operation(summary = "Busca la película con el id especificado", parameters = @Parameter(name = "id", example = "6"))
    @ApiResponse(responseCode = "200", description = "Película con el id especificado")
    public FilmShortDTO getOneShort(
            @Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
            @Parameter(required = false, allowEmptyValue = true, schema = @Schema(type = "string", allowableValues = {
                    "details", "short",
                    "edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
            throws NotFoundException {
        Optional<Film> film = srv.getOne(id);
        if (film.isEmpty()) {
            throw new NotFoundException("No se encontró la película con el id " + id);
        }
        return FilmShortDTO.from(film.get());
    }

    @GetMapping(path = "/{id}", params = "mode=details")
    public FilmDetailsDTO getOneDetalle(
            @Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
            @Parameter(required = false, schema = @Schema(type = "string", allowableValues = { "details", "short",
                    "edit" }, defaultValue = "edit")) @RequestParam(required = false, defaultValue = "edit") String mode)
            throws Exception {
        Optional<Film> rslt = srv.getOne(id);
        if (rslt.isEmpty())
            throw new NotFoundException();
        return FilmDetailsDTO.from(rslt.get());
    }

    @Operation(summary = "Consulta filtrada de peliculas")
    @GetMapping("/filtro")
    public List<?> search(@ParameterObject @Valid FilmSearch filter) throws BadRequestException {
        if (filter.minlength() != null && filter.maxlength() != null && filter.minlength() > filter.maxlength())
            throw new BadRequestException("la duración máxima debe ser superior a la mínima");
        Specification<Film> spec = null;
        if (filter.title() != null && !"".equals(filter.title())) {
            Specification<Film> cond = (root, query, builder) -> builder.like(root.get("title"),
                    "%" + filter.title().toUpperCase() + "%");
            spec = spec == null ? cond : spec.and(cond);
        }
        if (filter.rating() != null && !"".equals(filter.rating())) {
            if (!List.of(Rating.VALUES).contains(filter.rating()))
                throw new BadRequestException("rating desconocido");
            Specification<Film> cond = (root, query, builder) -> builder.equal(root.get("rating"),
                    Rating.getEnum(filter.rating()));
            spec = spec == null ? cond : spec.and(cond);
        }
        if (filter.minlength() != null) {
            Specification<Film> cond = (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("length"),
                    filter.minlength());
            spec = spec == null ? cond : spec.and(cond);
        }
        if (filter.maxlength() != null) {
            Specification<Film> cond = (root, query, builder) -> builder.lessThanOrEqualTo(root.get("length"),
                    filter.maxlength());
            spec = spec == null ? cond : spec.and(cond);
        }
        if (spec == null)
            throw new BadRequestException("Faltan los parametros de filtrado");
        var query = srv.getAll(spec).stream();
        if ("short".equals(filter.mode()))
            return query.map(e -> FilmShortDTO.from(e)).toList();
        else {
            return query.map(e -> FilmDetailsDTO.from(e)).toList();
        }
    }

    @GetMapping("/{id}/actors")
    @Operation(summary = "Obtiene una lista de los actores en una película", parameters = @Parameter(name = "id", example = "3"))
    @ApiResponse(responseCode = "200", description = "Actores de la película del id especificado")
    public List<ActorName> getActors(@PathVariable int id) throws NotFoundException {
        Optional<Film> film = srv.getOne(id);
        if (film.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return film.get().getFilmActors().stream()
                .map(filmActor -> new ActorName(filmActor.getActor().getActorId(), filmActor.getActor().getFirstName(),
                        filmActor.getActor().getLastName()))
                .toList();
    }

    @GetMapping("/{id}/categorias")
    @Operation(summary = "Obtiene una lista de las categorías en una película", parameters = @Parameter(name = "id", example = "3"))
    @ApiResponse(responseCode = "200", description = "Categorías de la película del id especificado")
    public List<CategoryName> getCategories(@PathVariable int id) throws NotFoundException {
        Optional<Film> film = srv.getOne(id);
        if (film.isEmpty()) {
            throw new NotFoundException("No se encontró la película con id " + id);
        }
        return film.get().getCategories().stream()
                .map(category -> new CategoryName(category.getCategoryId(), category.getName()))
                .toList();
    }

    @PostMapping
    @Operation(summary = "Crea una nueva película", parameters = @Parameter(name = "film", description = "Los datos de la película para crear"))
    @ApiResponse(responseCode = "201", description = "La locación de la nueva película en header 'Location'")
    public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO film)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        Film newFilm = srv.add(FilmEditDTO.from(film));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newFilm.getFilmId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualiza la película")
    @ApiResponse(responseCode = "204", description = "El película ha sido actualizado")
    public void update(
            @PathVariable int id,
            @Valid @RequestBody FilmEditDTO film) throws BadRequestException, NotFoundException, InvalidDataException {
        if (film.getFilmId() != id) {
            throw new BadRequestException("El id de la película no coincide con el id proporcionado");
        }
        srv.modify(FilmEditDTO.from(film));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Elimina una película por su id")
    @ApiResponse(responseCode = "204", description = "La película ha sido eliminado")
    public ResponseEntity<Object> delete(@PathVariable int id) throws NotFoundException {
        srv.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        if (srv.getOne(id).isEmpty()) {
            headers.add("Message", "La película ha sido eliminado");
        } else {
            headers.add("Message", "La película no ha sido eliminado");
        }
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
