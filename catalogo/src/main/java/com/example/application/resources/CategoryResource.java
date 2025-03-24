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

import com.example.domains.contracts.services.CategorysService;
import com.example.domains.entities.Category;
import com.example.domains.entities.dtos.CategoryDTO;
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
@RequestMapping("/categories/v1")
@Tag(name="Categories Controller", description = "Controlador de categorías")
public class CategoryResource {

    private CategorysService srv;

    public CategoryResource(CategorysService srv) {
        this.srv = srv;
    }

    @GetMapping
    @Hidden
    public List<CategoryDTO> getAll() {
        List<Category> categories = srv.getAll();
        return categories.stream().map(category -> CategoryDTO.from(category)).toList();
    }

    @GetMapping(params = { "page" })
    @Operation(summary = "Devuelve una lista de categorías paginado")
    @ApiResponse(responseCode = "200", description = "Busca y devulve una lista de categorías")
    public Page<CategoryDTO> getAll(Pageable pageable) {
        return srv.getByProjection(pageable, CategoryDTO.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca la categoría con el id especificado", parameters = @Parameter(name = "id", example = "6"))
    @ApiResponse(responseCode = "200", description = "Idioma con el id especificado")
    public CategoryDTO getOne(@PathVariable int id) throws NotFoundException {
        Optional<Category> category = srv.getOne(id);
        if (category.isEmpty()) {
            throw new NotFoundException("No se encontró la categoría con el id " + id);
        }
        return CategoryDTO.from(category.get());
    }

    @GetMapping(path = "/{id}/films")
    @Operation(
        summary = "Obtiene las películas de esta categoría", 
        parameters = @Parameter(name = "id", description = "Id de la categoría como path variable")
    )
    @ApiResponse(responseCode = "200", description = "Lista de películas que incluye esta categoría")
    public List<Title> getFilms(@PathVariable int id) throws NotFoundException {
        Optional<Category> category = srv.getOne(id);
        if (category.isEmpty()) {
            throw new NotFoundException("No se encontró el idioma con id " + id);
        }
        return category.get().getFilmCategories().stream()
                .map(filmCategory -> new Title(filmCategory.getFilm().getFilmId(), filmCategory.getFilm().getTitle()))
                .toList();   
    }

    @PostMapping
    @Operation(summary = "Crea una nueva categoria", parameters = @Parameter(name = "category", description = "Los datos de la categoria para crear"))
    @ApiResponse(responseCode = "201", description = "La locación de la neuva categoria en header 'Location'")
    public ResponseEntity<Object> create(@Valid @RequestBody CategoryDTO categoria)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        Category newCategory = srv.add(CategoryDTO.from(categoria));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.getCategoryId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualiza la categoria")
    @ApiResponse(responseCode = "204", description = "La categoría ha sido actualizado")
    public void update(
            @PathVariable int id,
            @Valid @RequestBody CategoryDTO category) throws BadRequestException, NotFoundException, InvalidDataException {
        if (category.getCategoryId() != id) {
            throw new BadRequestException("El id de la categoría no coincide con el id proporcionado");
        }
        srv.modify(CategoryDTO.from(category));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Elimina una categoría por su id")
    @ApiResponse(responseCode = "204", description = "Incluye un header 'Message' que comunica si la categoía ha sido eliminado o no")
    public ResponseEntity<Object> delete(@PathVariable int id) throws NotFoundException {
        srv.deleteById(id);
        HttpHeaders headers = new HttpHeaders();
        if (srv.getOne(id).isEmpty()) {
            headers.add("Message", "La categoría ha sido eliminado");
        } else {
            headers.add("Message", "La categoría no ha sido eliminado");
        }
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
