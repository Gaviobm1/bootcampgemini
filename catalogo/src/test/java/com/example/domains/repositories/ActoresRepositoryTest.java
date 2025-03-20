package com.example.domains.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.domains.contracts.repositories.ActoresRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;


@DataJpaTest
public class ActoresRepositoryTest {

    @Autowired
	private TestEntityManager em;

    @Autowired
    ActoresRepository repo;

    private int firstId;
    private int secondId;
    private int thirdId;
    private Integer val;

    @BeforeEach
    public void setUp() {
        var item = new Actor(0, "TOM", "HOLLAND");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		val = (Integer) em.persistAndGetId(item);
        firstId = val.intValue();
		item = new Actor(0, "JACOB", "ELORDI");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		val = (Integer) em.persistAndGetId(item);
        secondId = val.intValue();
		item = new Actor(0, "DARYL", "MCCORMACK");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		val = (Integer) em.persistAndGetId(item);
        thirdId = val.intValue();
    }

    @Test
    public void testGetAll() {
        List<Actor> value = repo.findAll();
        assertEquals(3, value.size());
    }

    @Test
    public void testGetAllDTO() {
        List<ActorDTO> value = repo.findAllBy(ActorDTO.class);
        assertEquals(3, value.size());
    }

    @Test
    public void testGetOne() {
        Optional<Actor> value = repo.findById(firstId);
        assertEquals("TOM", value.get().getFirstName());
    }
}
