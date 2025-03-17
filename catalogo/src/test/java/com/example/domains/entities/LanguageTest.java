package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class LanguageTest {

    private Language language;

    @BeforeEach
    public void setUp() {
        language = new Language(1, "English");
    }

    @Test
    public void testGetLanguageId() {
        assertEquals(1, language.getLanguageId());
    }

    @Test
    public void testSetLanguageId() {
        language.setLanguageId(2);
        assertEquals(2, language.getLanguageId());
    }

    @Test
    public void testGetName() {
        assertEquals("English", language.getName());
    }

    @Test
    public void testSetName() {
        language.setName("Spanish");
        assertEquals("Spanish", language.getName());
    }

    @Test
    public void testGetLastUpdate() {
        assertNull(language.getLastUpdate());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        language.setLastUpdate(timestamp);
        assertEquals(timestamp, language.getLastUpdate());
    }

    @Test
    public void testSetLastUpdate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        language.setLastUpdate(timestamp);
        assertEquals(timestamp, language.getLastUpdate());
    }

    @Test
    public void testGetFilms() {
        assertNull(language.getFilms());
        List<Film> films = new ArrayList<>();
        language.setFilms(films);
        assertNotNull(language.getFilms());
        assertEquals(films, language.getFilms());
    }

    @Test
    public void testAddFilm() {
        Film film = new Film();
        language.setFilms(new ArrayList<>());
        language.addFilm(film);
        assertEquals(1, language.getFilms().size());
        assertEquals(language, film.getLanguage());
    }

    @Test
    public void testRemoveFilm() {
        Film film = new Film();
        language.setFilms(new ArrayList<>());
        language.addFilm(film);
        language.removeFilm(film);
        assertEquals(0, language.getFilms().size());
        assertNull(film.getLanguage());
    }

    @Test
    public void testGetFilmsVO() {
        assertNull(language.getFilmsVO());
        List<Film> filmsVO = new ArrayList<>();
        language.setFilmsVO(filmsVO);
        assertNotNull(language.getFilmsVO());
        assertEquals(filmsVO, language.getFilmsVO());
    }

    @Test
    public void testAddFilmsVO() {
        Film filmVO = new Film();
        language.setFilmsVO(new ArrayList<>());
        language.addFilmsVO(filmVO);
        assertEquals(1, language.getFilmsVO().size());
        assertEquals(language, filmVO.getLanguageVO());
    }

    @Test
    public void testRemoveFilmsVO() {
        Film filmVO = new Film();
        language.setFilmsVO(new ArrayList<>());
        language.addFilmsVO(filmVO);
        language.removeFilmsVO(filmVO);
        assertEquals(0, language.getFilmsVO().size());
        assertNull(filmVO.getLanguageVO());
    }

    @Test
    public void testHashCode() {
        Language language2 = new Language(1, "English");
        assertEquals(language.hashCode(), language2.hashCode());
    }

    @Test
    public void testEquals() {
        Language language2 = new Language(1, "English");
        assertEquals(language, language2);
        language2.setLanguageId(2);
        assertNotEquals(language, language2);
    }

    @Test
    public void testToString() {
        assertEquals("Language [languageId=1, name=English]", language.toString());
    }
}