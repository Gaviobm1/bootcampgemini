package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilmActorTest {

    private FilmActor filmActor;
    private FilmActorPK filmActorPK;

    @BeforeEach
    public void setUp() {
        filmActor = new FilmActor();
        filmActorPK = new FilmActorPK();
    }

    @Test
    public void testSetId() {
        filmActor.setId(filmActorPK);
        assertEquals(filmActorPK, filmActor.getId());
    }
}