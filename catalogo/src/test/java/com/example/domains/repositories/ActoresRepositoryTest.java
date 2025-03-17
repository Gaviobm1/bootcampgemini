package com.example.domains.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.contracts.repositories.ActoresRepository;
import com.example.domains.entities.Actor;


@DataJpaTest
public class ActoresRepositoryTest {

    @Autowired
	private TestEntityManager em;

    @Autowired
    ActoresRepository repo;

    @BeforeEach
    public void setUp() {
        var item = new Actor(0, "TOM", "HOLLAND");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
		item = new Actor(0, "JACOB", "ELORDI");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
		item = new Actor(0, "DARYL", "MCCORMACK");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
    }

    @Test
    public void testGetAll() {
        List<Actor> value = repo.findAll();
        assertEquals(3, value.size());
    }

    @Test 
    public void testGetOne() {
        Optional<Actor> value = repo.findById(1);
        assertEquals("TOM", value.get().getFirstName());
        assertEquals("HOLLAND", value.get().getLastName());
    }

   
    
}
