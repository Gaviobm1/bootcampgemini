package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(category);
    }

    @Test
    public void testParameterizedConstructor() {
        Category category = new Category(1, "Action");
        assertEquals(1, category.getCategoryId());
        assertEquals("Action", category.getName());
    }

    @Test
    public void testGetAndSetCategoryId() {
        category.setCategoryId(1);
        assertEquals(1, category.getCategoryId());
    }

    @Test
    public void testGetAndSetLastUpdate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        category.setLastUpdate(timestamp);
        assertEquals(timestamp, category.getLastUpdate());
    }

    @Test
    public void testGetAndSetName() {
        category.setName("Action");
        assertEquals("Action", category.getName());
    }

    @Test
    public void testGetAndSetFilmCategories() {
        List<FilmCategory> filmCategories = new ArrayList<>();
        category.setFilmCategories(filmCategories);
        assertEquals(filmCategories, category.getFilmCategories());
    }

    @Test
    public void testAddFilmCategory() {
        FilmCategory filmCategory = new FilmCategory();
        category.setFilmCategories(new ArrayList<>());
        category.addFilmCategory(filmCategory);
        assertTrue(category.getFilmCategories().contains(filmCategory));
        assertEquals(category, filmCategory.getCategory());
    }

    @Test
    public void testRemoveFilmCategory() {
        FilmCategory filmCategory = new FilmCategory();
        List<FilmCategory> filmCategories = new ArrayList<>();
        filmCategories.add(filmCategory);
        category.setFilmCategories(filmCategories);
        category.removeFilmCategory(filmCategory);
        assertFalse(category.getFilmCategories().contains(filmCategory));
        assertNull(filmCategory.getCategory());
    }
}
