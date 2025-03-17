package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilmCategoryTest {

    private FilmCategory filmCategory;
    private FilmCategoryPK filmCategoryPK;
    private Timestamp lastUpdate;
    private Category category;
    private Film film;

    @BeforeEach
    public void setUp() {
        filmCategoryPK = new FilmCategoryPK();
        lastUpdate = new Timestamp(System.currentTimeMillis());
        category = new Category();
        film = new Film();

        filmCategory = new FilmCategory();
        filmCategory.setId(filmCategoryPK);
        filmCategory.setLastUpdate(lastUpdate);
        filmCategory.setCategory(category);
        filmCategory.setFilm(film);
    }

    @Test
    public void testGetId() {
        assertEquals(filmCategoryPK, filmCategory.getId());
    }

    @Test
    public void testSetId() {
        FilmCategoryPK newId = new FilmCategoryPK();
        filmCategory.setId(newId);
        assertEquals(newId, filmCategory.getId());
    }

    @Test
    public void testGetLastUpdate() {
        assertEquals(lastUpdate, filmCategory.getLastUpdate());
    }

    @Test
    public void testSetLastUpdate() {
        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis());
        filmCategory.setLastUpdate(newTimestamp);
        assertEquals(newTimestamp, filmCategory.getLastUpdate());
    }

    @Test
    public void testGetCategory() {
        assertEquals(category, filmCategory.getCategory());
    }

    @Test
    public void testSetCategory() {
        Category newCategory = new Category();
        filmCategory.setCategory(newCategory);
        assertEquals(newCategory, filmCategory.getCategory());
    }

    @Test
    public void testGetFilm() {
        assertEquals(film, filmCategory.getFilm());
    }

    @Test
    public void testSetFilm() {
        Film newFilm = new Film();
        filmCategory.setFilm(newFilm);
        assertEquals(newFilm, filmCategory.getFilm());
    }

    @Test
    public void testFilmCategoryNotNull() {
        assertNotNull(filmCategory);
    }
}
