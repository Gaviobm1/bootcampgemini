package com.example.application.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.CategorysService;
import com.example.domains.entities.Category;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.records.Title;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    @Operation(summary = "Busca el idioma con el id especificado", parameters = @Parameter(name = "id", example = "6"))
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
}
