package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

import com.example.domains.contracts.services.LanguagesService;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.domains.entities.records.Title;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/languages/v1")
@Tag(name="Languages Controller", description = "Controlador de idiomas")
public class LanguageResource {

    private LanguagesService srv;

    public LanguageResource(LanguagesService srv) {
        super();
        this.srv = srv;
    }

    @GetMapping
    @Hidden
    public List<LanguageDTO> getAll() {
        List<Language> languages = srv.getAll();
        return languages.stream().map(language -> LanguageDTO.from(language)).toList();
    }

    @GetMapping(params = { "page" })
    @Operation(summary = "Devuelve una lista de idiomas paginado")
    @ApiResponse(responseCode = "200", description = "Busca y devulve una lista de idiomas")
    public Page<LanguageDTO> getAll(Pageable pageable) {
        return srv.getByProjection(pageable, LanguageDTO.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca el idioma con el id especificado", parameters = @Parameter(name = "id", example = "6"))
    @ApiResponse(responseCode = "200", description = "Idioma con el id especificado")
    public LanguageDTO getOne(@PathVariable int id) throws NotFoundException {
        Optional<Language> language = srv.getOne(id);
        if (language.isEmpty()) {
            throw new NotFoundException("No se encontró el idioma con el id " + id);
        }
        return LanguageDTO.from(language.get());
    }
    
    @GetMapping(path = "/{id}/films")
    @Operation(
        summary = "Obtiene las películas en el idioma", 
        parameters = @Parameter(name = "id", description = "Id del idioma como path variable")
    )
    @ApiResponse(responseCode = "200", description = "Lista de películas que usa este idioma")
    public List<Title> getFilms(@PathVariable int id) throws NotFoundException {
        Optional<Language> language = srv.getOne(id);
        if (language.isEmpty()) {
            throw new NotFoundException("No se encontró el idioma con id " + id);
        }
        return language.get().getFilms().stream()
                .map(film -> new Title(film.getFilmId(), film.getTitle()))
                .toList();   
    }

    @GetMapping(path = "/{id}/filmsvo")
    @Operation(
        summary = "Obtiene las películas en el idioma VO", 
        parameters = @Parameter(name = "id", description = "Id del idioma como path variable")
    )
    @ApiResponse(responseCode = "200", description = "Lista de películas que usa este idioma en VO")
    public List<Title> getFilmsVO(@PathVariable int id) throws NotFoundException {
        Optional<Language> language = srv.getOne(id);
        if (language.isEmpty()) {
            throw new NotFoundException("No se encontró el idioma con id " + id);
        }
        return language.get().getFilmsVO().stream()
                .map(film -> new Title(film.getFilmId(), film.getTitle()))
                .toList();   
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo idioma", parameters = @Parameter(name = "language", description = "Los datos del idioma para crear"))
    @ApiResponse(responseCode = "201", description = "La locación de el nuevo idioma en header 'Location'")
    public ResponseEntity<Object> create(@Valid @RequestBody LanguageDTO language)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        Language newLanguage = srv.add(LanguageDTO.from(language));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLanguage.getLanguageId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualiza el idioma")
    @ApiResponse(responseCode = "204", description = "El idioma ha sido actualizado")
    public void update(
            @PathVariable int id,
            @Valid @RequestBody LanguageDTO language) throws BadRequestException, NotFoundException, InvalidDataException {
        if (language.getLanguageId() != id) {
            throw new BadRequestException("El id del idioma no coincide con el id proporcionado");
        }
        srv.modify(LanguageDTO.from(language));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Elimina un idioma por su id")
    @ApiResponse(responseCode = "204", description = "El idioma ha sido eliminado")
    public ResponseEntity<Object> delete(@PathVariable int id) throws NotFoundException {
        srv.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        if (srv.getOne(id).isEmpty()) {
            headers.add("Message", "El idioma ha sido eliminado");
        } else {
            headers.add("Message", "El idioma no ha sido eliminado");
        }
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
