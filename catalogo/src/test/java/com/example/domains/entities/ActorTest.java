import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

package com.example.domains.entities;




public class ActorTest {

    private Actor actor;

    @BeforeEach
    public void setUp() {
        actor = new Actor(1, "JOHN", "DOE");
    }

    @Test
    public void testActorConstructor() {
        Actor actor = new Actor(1, "JOHN", "DOE");
        assertNotNull(actor);
        assertEquals(1, actor.getActorId());
        assertEquals("JOHN", actor.getFirstName());
        assertEquals("DOE", actor.getLastName());
    }

    @Test
    public void testSetAndGetActorId() {
        actor.setActorId(2);
        assertEquals(2, actor.getActorId());
    }

    @Test
    public void testSetAndGetFirstName() {
        actor.setFirstName("JANE");
        assertEquals("JANE", actor.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        actor.setLastName("SMITH");
        assertEquals("SMITH", actor.getLastName());
    }

    @Test
    public void testSetAndGetLastUpdate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        actor.setLastUpdate(timestamp);
        assertEquals(timestamp, actor.getLastUpdate());
    }

    @Test
    public void testSetAndGetFilmActors() {
        List<FilmActor> filmActors = new ArrayList<>();
        actor.setFilmActors(filmActors);
        assertEquals(filmActors, actor.getFilmActors());
    }

    @Test
    public void testAddAndRemoveFilmActor() {
        FilmActor filmActor = new FilmActor();
        actor.setFilmActors(new ArrayList<>());
        actor.addFilmActor(filmActor);
        assertTrue(actor.getFilmActors().contains(filmActor));
        actor.removeFilmActor(filmActor);
        assertTrue(!actor.getFilmActors().contains(filmActor));
    }

    @Test
    public void testEqualsAndHashCode() {
        Actor actor1 = new Actor(1, "JOHN", "DOE");
        Actor actor2 = new Actor(1, "JOHN", "DOE");
        assertTrue(actor1.equals(actor2));
        assertEquals(actor1.hashCode(), actor2.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Actor [actorId=1, firstName=JOHN, lastName=DOE, lastUpdate=null]";
        assertEquals(expected, actor.toString());
    }
}