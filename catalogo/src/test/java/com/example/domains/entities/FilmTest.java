
package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FilmTest {

    private Film film;

    @BeforeEach
    public void setUp() {
        film = new Film();
        film.setFilmId(1);
        film.setTitle("TEST");
        film.setReleaseYear((short) 2020);
        film.setDescription("Test description");
        film.setLength(120);
        film.setRating("G");
        film.setRentalDuration((byte) 5);
        film.setRentalRate(BigDecimal.valueOf(4.99));
        film.setReplacementCost(BigDecimal.valueOf(19.99));
        film.setLanguage(new Language(1, "English"));
        film.setLanguageVO(new Language(2, "Spanish"));
        film.setActors(new ArrayList<>());
        film.setCategories(new ArrayList<>());
    }

    @Test
    public void testFilmId() {
        assertEquals(1, film.getFilmId());
    }

    @Test
    public void testTitle() {
        assertEquals("TEST", film.getTitle());
    }

    @Test
    public void testReleaseYear() {
        assertEquals((short)2020, film.getReleaseYear());
    }

    @Test
    public void testDescription() {
        assertEquals("Test description", film.getDescription());
    }

    @Test
    public void testLength() {
        assertEquals(120, film.getLength());
    }

    @Test
    public void testRating() {
        assertEquals("G", film.getRating());
    }

    @Test
    public void testRentalDuration() {
        assertEquals(5, film.getRentalDuration());
    }

    @Test
    public void testRentalRate() {
        assertEquals(BigDecimal.valueOf(4.99), film.getRentalRate());
    }

    @Test
    public void testReplacementCost() {
        assertEquals(BigDecimal.valueOf(19.99), film.getReplacementCost());
    }

    @Test
    public void testLanguage() {
        assertNotNull(film.getLanguage());
        assertEquals("English", film.getLanguage().getName());
    }

    @Test
    public void testLanguageVO() {
        assertNotNull(film.getLanguageVO());
        assertEquals("Spanish", film.getLanguageVO().getName());
    }

    @Test
    public void testFilmActors() {
        assertNotNull(film.getFilmActors());
        assertTrue(film.getFilmActors().isEmpty());
    }

    @Test
    public void testFilmCategories() {
        assertNotNull(film.getCategories());
        assertTrue(film.getCategories().isEmpty());
    }
}